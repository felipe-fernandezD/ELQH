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

git clone https://github.com/tu-usuario/tu-repositorio.git


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
 ┃ ┃ ┃ ┃ ┣ 📜 MainActivity.kt
 ┃ ┃ ┃ ┃ ┣ 📜 LoginActivity.kt
 ┃ ┃ ┃ ┃ ┣ 📜 ProfileActivity.kt
 ┃ ┃ ┃ ┣ 📂 res
 ┃ ┃ ┃ ┃ ┣ 📂 layout
 ┃ ┃ ┃ ┃ ┣ 📂 drawable
 ┃ ┃ ┃ ┃ ┣ 📂 values
 ┣ 📜 build.gradle.kts
 ┣ 📜 README.md

🖼️ Capturas de Pantalla

(Agrega tus imágenes dentro de la carpeta /docs/screenshots/)

Pantalla Principal	Perfil de Usuario

	
💡 Próximas Mejoras

🔍 Búsqueda avanzada de recetas.

💬 Sección de comentarios y valoraciones.

📊 Recomendaciones personalizadas según el historial del usuario.

🤝 Contribuciones

¡Las contribuciones son bienvenidas!
Sigue estos pasos:

Realiza un fork del proyecto.

Crea una nueva rama:

git checkout -b feature/nueva-funcionalidad


Realiza tus cambios y súbelos:

git commit -m "Agrega nueva funcionalidad"
git push origin feature/nueva-funcionalidad


Envía un Pull Request 🚀

📜 Licencia

Este proyecto está bajo la licencia MIT.
Consulta el archivo LICENSE
 para más información.

❤️ Autores

Desarrollado con pasión por [Tu Nombre Aquí] 👨‍💻

“Cocinar es como programar: ambos necesitan creatividad, precisión y amor por el detalle.” 🍲
