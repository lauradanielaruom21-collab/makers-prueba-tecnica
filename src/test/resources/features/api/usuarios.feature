# language: es
@api
Característica: Gestión de usuarios en la API de reqres.in
  Como consumidor de la API de usuarios
  Quiero crear, consultar, actualizar y eliminar usuarios
  Para integrar la gestión de usuarios en mis aplicaciones

  Antecedentes:
    Dado que Carlos tiene acceso a la API de usuarios de reqres

  # ─────────────────────────────── REQUERIMIENTO DEL ENUNCIADO ───────────────────────────────

  @smoke @requerimiento
  Escenario: Crear un usuario y extraer su id
    Cuando Carlos crea un usuario con nombre "Test User" y trabajo "Automation Engineer"
    Entonces el código de respuesta debería ser 201
    Y la respuesta debería contener el nombre "Test User" y el trabajo "Automation Engineer"
    Y la respuesta debería incluir el id del usuario creado
    Y la fecha de creación debería corresponder al momento actual

  # reqres.in es un mock: los recursos creados con POST NO se persisten, por lo que el GET /users/{id}
  # del usuario recién creado responde 404. El escenario se implementa exactamente como lo pide el
  # enunciado y se excluye de la ejecución por defecto. Ejecutar con: mvn clean verify -Pdefectos
  @smoke @requerimiento @defecto-conocido @issue:BUG-API-01
  Escenario: Consultar por su id el usuario recién creado (flujo encadenado POST → GET)
    Dado que Carlos creó un usuario con nombre "Test User" y trabajo "Automation Engineer"
    Cuando consulta el usuario por el id obtenido en la creación
    Entonces el código de respuesta debería ser 200
    Y el usuario consultado debería tener el nombre "Test User" y el trabajo "Automation Engineer"

  # ─────────────────────────────── CASOS ADICIONALES (plus) ───────────────────────────────

  @regresion @contrato
  Escenario: La respuesta de creación cumple el contrato definido
    Cuando Carlos crea un usuario con nombre "Test User" y trabajo "Automation Engineer"
    Entonces el código de respuesta debería ser 201
    Y la respuesta debería cumplir el esquema "schemas/create-user-response.json"
    Y la respuesta debería ser de tipo JSON

  @regresion @consulta
  Escenario: Consultar un usuario existente
    Cuando Carlos consulta el usuario con id "2"
    Entonces el código de respuesta debería ser 200
    Y la respuesta debería cumplir el esquema "schemas/single-user-response.json"
    Y el usuario consultado debería tener id 2 y correo "janet.weaver@reqres.in"

  @regresion @negativo
  Escenario: Consultar un usuario inexistente
    Cuando Carlos consulta el usuario con id "23"
    Entonces el código de respuesta debería ser 404
    Y el cuerpo de la respuesta debería estar vacío

  @regresion @actualizacion
  Escenario: Actualizar completamente un usuario (PUT)
    Cuando Carlos actualiza completamente el usuario "2" con nombre "morpheus" y trabajo "zion resident"
    Entonces el código de respuesta debería ser 200
    Y la respuesta debería contener el nombre "morpheus" y el trabajo "zion resident"
    Y la respuesta debería incluir la fecha de actualización

  @regresion @eliminacion
  Escenario: Eliminar un usuario
    Cuando Carlos elimina el usuario "2"
    Entonces el código de respuesta debería ser 204
    Y el cuerpo de la respuesta debería estar vacío
