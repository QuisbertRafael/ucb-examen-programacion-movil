package edu.ucb.pablostify.logs.data

import android.content.Context
import android.provider.Settings
import androidx.core.content.pm.PackageInfoCompat
import com.develoop.logs.LogApi
import com.google.protobuf.ByteString
import edu.ucb.pablostify.logs.data.datasource.LogsRemoteDataSource
import edu.ucb.pablostify.scheduler.LogUploader
import java.util.UUID

class GrpcLogUploader(
    private val context: Context,
    private val dataSource: LogsRemoteDataSource
) : LogUploader {

    private val androidId: String =
        Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID) ?: ""
    private val appInstanceId: String = UUID.randomUUID().toString()

    private val versionCode: Int = try {
        PackageInfoCompat.getLongVersionCode(
            context.packageManager.getPackageInfo(context.packageName, 0)
        ).toInt()
    } catch (e: Exception) {
        1
    }

    override suspend fun upload(message: String, stackTrace: String): Boolean {
        val now = System.currentTimeMillis()
        val logData = LogApi.LogData.newBuilder()
            .setAndroidId(ByteString.copyFromUtf8(androidId))
            .setAppInstanceId(ByteString.copyFromUtf8(appInstanceId))
            .setLogLevel(LogApi.ELogLevel.LEVEL_INFO)
            .setMessage(message)
            .setStackTrace(stackTrace)
            .setServerTimeStamp(now)
            .setMobileTimeStamp(now)
            .setVersionCode(versionCode)
            .setUserId("pablostify")
            .build()

        val request = LogApi.LogRequest.newBuilder().addLogs(logData).build()
        val response = dataSource.send(request)
        return response.resultCode == LogApi.ELogResultCode.LOG_RESULT_OK
    }
}
