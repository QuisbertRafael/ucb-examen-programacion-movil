package edu.ucb.pablostify.login.data

import edu.ucb.pablostify.core.domain.model.User
import edu.ucb.pablostify.login.domain.model.Credentials
import edu.ucb.pablostify.login.domain.repository.LoginRepository
import edu.ucb.pablostify.scheduler.LogUploader

class LoginRepositoryImpl(
    private val logUploader: LogUploader? = null
) : LoginRepository {
    override suspend fun login(credentials: Credentials): Result<User> {
        logUploader?.upload("Login exitoso para usuario: ${credentials.username.value}")
        return Result.success(
            User(
                id = "1",
                fullName = "Ana García",
                email = "anagarcia@gmail.com",
                avatarUrl = null
            )
        )
    }
}
