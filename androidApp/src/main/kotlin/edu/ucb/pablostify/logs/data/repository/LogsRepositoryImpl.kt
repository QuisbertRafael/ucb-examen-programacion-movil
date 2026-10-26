package edu.ucb.pablostify.logs.data.repository

import android.content.Context
import android.provider.Settings
import androidx.core.content.pm.PackageInfoCompat
import com.develoop.logs.LogApi
import com.google.protobuf.ByteString
import edu.ucb.pablostify.logs.data.datasource.LogsRemoteDataSource
import edu.ucb.pablostify.logs.domain.repository.LogsRepository
import java.util.UUID

class LogsRepositoryImpl(
    private val dataSource: LogsRemoteDataSource,
    private val context: Context
) : LogsRepository {

    override suspend fun sendTestLog(): Result<LogApi.LogResponse> {
        return try {
            val androidIdStr = Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.ANDROID_ID
            ) ?: "unknown_android_id"
            val appInstanceIdStr = UUID.randomUUID().toString()
            val versionCode = try {
                PackageInfoCompat.getLongVersionCode(
                    context.packageManager.getPackageInfo(context.packageName, 0)
                ).toInt()
            } catch (e: Exception) {
                1
            }
            val now = System.currentTimeMillis()

            val logData = LogApi.LogData.newBuilder()
                .setAndroidId(ByteString.copyFromUtf8(androidIdStr))
                .setAppInstanceId(ByteString.copyFromUtf8(appInstanceIdStr))
                .setLogLevel(LogApi.ELogLevel.LEVEL_INFO)
                .setMessage("Log de prueba extremo a extremo gRPC")
                .setStackTrace("java.lang.Exception: Stacktrace de prueba para gRPC\n\tat edu.ucb.pablostify.logs.presentation.screen.LogsScreenKt.LogsScreen(LogsScreen.kt:42)")
                .setServerTimeStamp(now)
                .setMobileTimeStamp(now)
                .setVersionCode(versionCode)
                .setUserId("pablostify_test_user")
                .build()

            val request = LogApi.LogRequest.newBuilder()
                .addLogs(logData)
                .build()

            val response = dataSource.send(request)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
