# Pablostify 📱

Proyecto desarrollado en Kotlin Multiplatform (KMP) con Compose Multiplatform, Koin y arquitectura MVI / MVVM.

## 🚀 Características y Funcionalidades

- **Autenticación:** Pantallas de inicio de sesión y registro de usuarios.
- **Notas en tiempo real:** Sincronización instantánea con Firebase Realtime Database.
- **Configuración remota y Notificaciones:** Integración con Firebase Remote Config (mensajes de bienvenida, modo mantenimiento) y Firebase Cloud Messaging (FCM).
- **Servicio gRPC:** Envío de logs estructurados (`LogApi.proto`) mediante cliente gRPC Kotlin Coroutines.
- **Internacionalización y Tema:** Soporte para idiomas (Español e Inglés) y modo oscuro/claro persistente.
- **Design System:** Componentes reutilizables y paleta de colores centralizada.

---

## 🛠️ Configuración e Instalación

### 1. Firebase (`google-services.json`)
Descarga el archivo `google-services.json` desde tu consola de Firebase y colócalo en la carpeta:
```text
androidApp/google-services.json
```

### 2. Backend gRPC (Opcional para logs)
Para probar el servicio de logs vía gRPC:
1. Inicia el servidor backend en tu computadora:
   ```bash
   java -jar logs-0.0.1-SNAPSHOT.jar
   ```
2. Si utilizas un celular físico o red externa, expón el puerto con ngrok:
   ```bash
   ngrok http 9090
   ```

---

## 💻 Ejecución

1. Abre el proyecto en **Android Studio**.
2. Sincroniza Gradle (*Sync Project with Gradle Files*).
3. Selecciona la configuración de ejecución `androidApp` y presiona **Run** ▶.
