# Pablostify 📱

Proyecto desarrollado en **Kotlin Multiplatform (KMP)** con **Compose Multiplatform**, **Koin**, **Firebase** y arquitectura **MVI / MVVM**.

---

## 🚀 Características Principales

- **Prueba de Logs gRPC:** Integración completa de cliente gRPC (`LogService.send`) con Protobuf (`LogApi.proto`).
- **Auditoría Automática:** Envío de logs gRPC al crear/eliminar notas en Firebase y al iniciar sesión.
- **Notas en Tiempo Real:** Sincronización con Firebase Realtime Database.
- **Remote Config & Notificaciones:** Gestión dinámica de funciones e integración con Firebase Cloud Messaging (FCM).
- **Diseño & Localización:** Componentes reutilizables (Design System), soporte multi-idioma (Español e Inglés) y modo oscuro/claro.

---

## 🛠️ Guía Paso a Paso para Arrancar el Proyecto

### 1. Iniciar el Backend gRPC
En una terminal, ejecuta el servidor ejecutable `.jar`:
```bash
java -jar logs-0.0.1-SNAPSHOT.jar
```
*El servidor escuchará por defecto en el puerto `9090`.*

---

### 2. Exponer el Backend con Ngrok (para túnel seguro HTTP/2)
Abre otra terminal y expón el puerto 9090:
```bash
ngrok http --app-protocol=http2 9090
```
Copia el subdominio generado por ngrok (ejemplo: `unseeing-trimmer-confining.ngrok-free.dev`).

---

### 3. Configurar la constante de gRPC en la App
Verifica o actualiza el host en el archivo `LogsConfig.kt`:

📍 **`androidApp/src/main/kotlin/edu/ucb/pablostify/logs/LogsConfig.kt`**

```kotlin
object LogsConfig {
    const val HOST = "unseeing-trimmer-confining.ngrok-free.dev" // Tu dominio de ngrok (sin https://)
    const val PORT = 443
    const val USE_PLAINTEXT = false // SSL/TLS activado para ngrok
}
```

> **Nota para emulador local (sin ngrok):**  
> Si prefieres probar únicamente en el emulador de Android Studio conectando a `localhost`:
> ```kotlin
> const val HOST = "10.0.2.2"
> const val PORT = 9090
> const val USE_PLAINTEXT = true
> ```

---

### 4. Configurar Firebase (Opcional)
Si deseas sincronización con Firebase Realtime Database o Push Notifications, asegúrate de colocar tu archivo `google-services.json` en:
```text
androidApp/google-services.json
```

---

### 5. Compilar y Ejecutar en Android Studio

1. Abre la carpeta del proyecto en **Android Studio**.
2. Realiza la sincronización de Gradle (*Sync Project with Gradle Files*).
3. Selecciona el módulo **`androidApp`** en la barra superior.
4. Presiona **Run ▶** (o `Shift + F10`) seleccionando un emulador o dispositivo físico.

---

## 🧪 Cómo Probar las Funcionalidades

1. **Prueba Directa de Logs gRPC:**
   - En la pantalla principal (pestaña "Inicio"), presiona el botón **"Enviar log de prueba"**.
   - Verás el estado de carga y el resultado `LOG_RESULT_OK` devuelto por el servidor gRPC.

2. **Prueba de Auditoría Automática (Firebase + gRPC):**
   - Ve a la pestaña **Notas**.
   - Agrega o elimina una nota. Automáticamente se sincronizará con Firebase y se enviará un log de auditoría al servidor gRPC.
