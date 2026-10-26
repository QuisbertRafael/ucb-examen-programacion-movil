package edu.ucb.pablostify.logs

/**
 * UNICO lugar donde cambiar la direccion del backend gRPC.
 *
 * Con ngrok (ngrok http --app-protocol=http2 <puerto>):
 *   HOST = "xxxx.ngrok-free.app" (sin https://), PORT = 443, USE_PLAINTEXT = false
 * Emulador sin ngrok:
 *   HOST = "10.0.2.2", PORT = <puerto del .jar>, USE_PLAINTEXT = true
 */
object LogsConfig {
    const val HOST = "unseeing-trimmer-confining.ngrok-free.dev"
    const val PORT = 443
    const val USE_PLAINTEXT = false
}
