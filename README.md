🍳 ELQH - Explora Lo Que Hay

📱 Aplicación móvil para Android creada para amantes de la cocina.
Descubre, guarda y gestiona tus recetas favoritas con una experiencia moderna, fluida y conectada a la nube.

📖 Descripción General

ELQH (Explora Lo Que Hay) es una app móvil para Android desarrollada en Kotlin.
Permite a los usuarios descubrir nuevas recetas, guardar sus favoritas y personalizar su perfil con fotos almacenadas en la nube.

Integrada completamente con Firebase, ofrece:

Autenticación de usuarios 🔐

Base de datos en tiempo real ☁️

Gestión de imágenes 📸

✨ Características Principales
🔑 Autenticación de Usuarios

Inicio de sesión y registro con Firebase Authentication.

Gestión de perfiles personales.

🍴 Exploración de Recetas

Visualización de recetas con imágenes, ingredientes e instrucciones.

Interfaz moderna con RecyclerView.

👤 Perfil de Usuario

Muestra el correo electrónico y la foto de perfil del usuario.

Permite actualizar la foto usando la cámara del dispositivo.

Incluye una lista de recetas favoritas.

☁️ Sincronización en la Nube

Todos los datos se guardan en Cloud Firestore.

Fotos gestionadas a través de Firebase Storage.

🧩 Tecnologías Utilizadas
Categoría	Tecnología
Lenguaje	Kotlin
Arquitectura	Actividades y Vistas (XML)
Base de Datos	Cloud Firestore
Autenticación	Firebase Authentication
Almacenamiento	Firebase Storage
📚 Librerías Principales

androidx.appcompat y material → Componentes modernos de UI.

androidx.recyclerview → Listado eficiente de recetas.

com.github.bumptech.glide → Carga y visualización de imágenes.

de.hdodenhof.circleimageview → Fotos de perfil circulares.

⚙️ Instalación y Configuración
🔧 Prerrequisitos

Android Studio (versión Iguana o superior).

Cuenta de Google con acceso a Firebase.

🧠 Pasos de Instalación

Clona el repositorio

git clone https://github.com/felipe-fernandezD/ELQH.git


Configura Firebase

Crea un proyecto en Firebase Console
.

Activa los servicios:

🔐 Authentication

🗄️ Cloud Firestore

🖼️ Firebase Storage

Descarga el archivo google-services.json y colócalo en:

app/google-services.json


Sincroniza con Gradle

Abre el proyecto en Android Studio.

Espera a que las dependencias se sincronicen correctamente.

🗂️ Estructura del Proyecto
📦 ELQH
 ┣ 📂 app
 ┃ ┣ 📂 src
 ┃ ┃ ┣ 📂 main
 ┃ ┃ ┃ ┣ 📂 java/com/elqh
 ┃ ┃ ┃ ┃ ┣ 📂 adapters
 ┃ ┃ ┃ ┃ ┃ ┣ 📜 MenuAdapter
 ┃ ┃ ┃ ┃ ┃ ┣ 📜 RecetaAdapter
 ┃ ┃ ┃ ┃ ┣ 📂 models
 ┃ ┃ ┃ ┃ ┃ ┣ 📜 Categoria
 ┃ ┃ ┃ ┃ ┃ ┣ 📜 Receta
 ┃ ┃ ┃ ┃ ┣ 📜 MainActivity.kt
 ┃ ┃ ┃ ┃ ┣ 📜 Ingrdientes.kt
 ┃ ┃ ┃ ┃ ┣ 📜 Inicio.kt
 ┃ ┃ ┃ ┃ ┣ 📜 M_recetas.kt
 ┃ ┃ ┃ ┃ ┣ 📜 Perfil.kt
 ┃ ┃ ┃ ┃ ┣ 📜 RecetaDetallesActivity.kt
 ┃ ┃ ┃ ┃ ┣ 📜 Registro.kt
 ┃ ┃ ┃ ┣ 📂 res
 ┃ ┃ ┃ ┃ ┣ 📂 layout
 ┃ ┃ ┃ ┃ ┣ 📂 drawable
 ┃ ┃ ┃ ┃ ┣ 📂 values
 ┣ 📜 build.gradle.kts
 ┣ 📜 README.md


“Cocinar es como programar: ambos necesitan creatividad, precisión y amor por el detalle.” 🍲
