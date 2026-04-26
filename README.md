# Introducción

Este proyecto parte de una aplicación inspirada en el universo de **Spyro The Dragon**. A partir de la base proporcionada, se ha ampliado la aplicación con una **guía de inicio interactiva**, animaciones, sonidos y dos **easter eggs** temáticos.

El objetivo principal ha sido mejorar la experiencia del usuario al iniciar la aplicación, ayudándole a conocer sus secciones principales de forma guiada y visual.

# Características principales

- Guía de inicio interactiva con varias pantallas superpuestas sobre la aplicación.
- Explicación visual de las pestañas **Personajes**, **Mundos** y **Coleccionables**.
- Explicación del icono de información de la barra superior.
- Pantalla final con resumen de pasos completados.
- La guía solo se muestra la primera vez que se ejecuta la aplicación.
- Animaciones en bocadillos, indicadores y transiciones.
- Efectos de sonido en momentos clave de la guía.
- Easter egg de vídeo en la pestaña **Mundos**.
- Easter egg con **Canvas** sobre **Ripto** en la pestaña **Personajes**.

# Tecnologías utilizadas

- **Kotlin**
- **XML**
- **Android Studio**
- **RecyclerView**
- **Fragments**
- **ConstraintLayout**
- **FrameLayout**
- **MediaPlayer**
- **VideoView**
- **Canvas**
- **Paint**
- **SharedPreferences**

# Instrucciones de uso

## Ejecución del proyecto

1. Abrir el proyecto en **Android Studio**.
2. Esperar a que finalice la sincronización de Gradle.
3. Ejecutar la aplicación en un emulador o dispositivo físico.

## Funcionamiento de la guía

- La primera vez que se abre la aplicación se muestra la guía interactiva.
- El usuario puede avanzar por la guía o pulsar en **Omitir**.
- Al finalizarla o al omitirla, la aplicación guarda que ya fue mostrada y no vuelve a aparecer en siguientes ejecuciones.

## Easter eggs

### Easter egg de vídeo
En la pestaña **Mundos**, si se pulsa **tres veces seguidas sobre el mismo mundo**, se reproduce un vídeo a pantalla completa.

### Easter egg con Canvas
En la pestaña **Personajes**, si se realiza una **pulsación prolongada sobre Ripto**, se activa una animación con **Canvas** que simula el brillo mágico del diamante del cetro.

# Conclusiones del desarrollador

Uno de los aspectos que más tuve que ajustar en esta práctica fue la guía interactiva inicial, sobre todo para conseguir que se integrara bien sobre la propia aplicación y que el recorrido entre pantallas resultara claro y funcional.

En general, esta práctica me ha servido para comprobar que, con las herramientas vistas en esta unidad y en las anteriores, ya es posible hacer cosas bastante interesantes en Android, combinando interfaz, animaciones, sonido, vídeo e interacción dentro de una misma aplicación.
