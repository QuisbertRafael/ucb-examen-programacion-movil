package edu.ucb.pablostify.scheduler

/**
 * Contrato para subir un log al backend. Vive en shared para que el Worker lo use;
 * la implementacion gRPC esta en androidApp (ahi estan el .proto y las clases generadas).
 */
interface LogUploader {
    /** @return true si el backend respondio LOG_RESULT_OK. */
    suspend fun upload(message: String, stackTrace: String = ""): Boolean
}
