# Exam-FrontEnd

Estudiantes: Joaquin Cortes y Cesar Calvo

# Exam FrontEnd – Aplicación Android Jetpack Compose

Este repositorio contiene la aplicación móvil del proyecto **Exam**, construida con Jetpack Compose, Kotlin Coroutines y Retrofit. Esta aplicación consume la API REST desarrollada en el repositorio [Exam-Backend](https://github.com/JoaquinCT/Exam-Backend).

## Requisitos

- Android Studio Giraffe o superior  
- Dispositivo físico con Android 8.0 (API 26) o superior **o** emulador de Google API  
- Acceso al backend ejecutándose localmente en el puerto **5292**

## Clonar y abrir en Android Studio

git clone https://github.com/JoaquinCT/Exam-FrontEnd.git
cd Exam-FrontEnd


## Configuración de la URL de la API

Dirígete al archivo:
app/src/main/java/com/una/exam_frontend/common/Constants.kt

Este archivo contiene la constante API_BASE_URL, la cual debe apuntar al backend. Cambia su valor dependiendo del entorno de ejecución:

   // Si ejecutas en un EMULADOR:
    const val API_BASE_URL = "http://10.0.2.2:5292"

  // Si ejecutas en un DISPOSITIVO FÍSICO dentro de la red local:
    const val API_BASE_URL = "http://[Tu_IP_Publica]:5292"


## Configuración de red segura (HTTP)

Verifica que exista solo un archivo network-security-config.xml en la ruta:

app/src/main/res/xml/network-security-config.xml

El contenido debe ser el siguiente para permitir tráfico HTTP en desarrollo:

  <network-security-config>
      <base-config cleartextTrafficPermitted="true" />
  </network-security-config>

  Es importante asegurarse de que no haya duplicados de este archivo en otras carpetas de res/xml, ya que pueden causar errores de compilación. Tambien que cleartextTrafficPermitted siempre sea true


Ejecución de la aplicación

    Asegúrate de que el backend (repositorio Exam-Backend) esté ejecutándose en el puerto 5292.

    Verifica que la URL esté correctamente configurada en Constants.kt.

    Selecciona el entorno adecuado:

        Emulador: no requiere configuración adicional.

        Dispositivo físico: asegúrate de que el backend esté expuesto en 0.0.0.0 y accedido desde la IP local de tu red.

    Ejecuta la app desde Android Studio haciendo clic en "Run" .
