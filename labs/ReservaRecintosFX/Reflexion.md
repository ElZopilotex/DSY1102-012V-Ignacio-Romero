# ¿Por qué Reserva no debe conocer JavaFX?

- Por qué **Reserva** es una entidad del modelo, y las clases del proyecto no deben depender de librerias de interfaz grafica

# ¿Por qué se comparte un único repositorio?

- Para mantener consistencia sobre la información en toda la aplicación, asi, si cada controlador ejecutara su propio repositorio, cada pantalla ejecutaría una lista independiente y desincronizada de reservas

# ¿Qué observa exactamente **ObservableList**?

- Observa las modificaciones directas en la colección de elementos.Cuando se añade una reserva, ObservableList emite una notificación que actualiza de forma automática la interfaz grafica

# ¿En qué clase y en qué momento se valida la duplicidad?

- Se valida en el controlador