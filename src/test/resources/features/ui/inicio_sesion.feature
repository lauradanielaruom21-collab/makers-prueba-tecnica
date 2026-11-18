# language: es
@ui
Característica: Inicio de sesión en SauceDemo
  Como comprador de SauceDemo
  Quiero iniciar sesión con mis credenciales
  Para acceder al catálogo de productos de forma segura

  Antecedentes:
    Dado que Ana está en la página de inicio de sesión

  # ───────────────────────────── SMOKE TEST (alcance exigido) ─────────────────────────────

  @smoke @login-exitoso
  Escenario: Login exitoso con credenciales válidas
    Cuando Ana inicia sesión con el usuario "standard_user" y la contraseña "secret_sauce"
    Entonces debería ver la página de productos
    Y el catálogo debería mostrar 6 productos

  @smoke @login-fallido
  Escenario: Login fallido con contraseña incorrecta
    Cuando Ana inicia sesión con el usuario "standard_user" y la contraseña "clave_incorrecta"
    Entonces debería ver el mensaje de error "Epic sadface: Username and password do not match any user in this service"
    Y debería permanecer en la página de inicio de sesión
    Y los campos de usuario y contraseña deberían quedar marcados con error

  @smoke @campos-obligatorios
  Esquema del escenario: Validación de campos obligatorios - <caso>
    Cuando Ana inicia sesión con el usuario "<usuario>" y la contraseña "<contrasena>"
    Entonces debería ver el mensaje de error "<mensaje>"
    Y debería permanecer en la página de inicio de sesión

    Ejemplos:
      | caso                       | usuario       | contrasena   | mensaje                             |
      | ambos campos vacíos        |               |              | Epic sadface: Username is required  |
      | contraseña vacía           | standard_user |              | Epic sadface: Password is required  |
      | usuario vacío              |               | secret_sauce | Epic sadface: Username is required  |

  # ──────────────────────────── ESCENARIOS ADICIONALES (plus) ────────────────────────────

  @regresion @negativo
  Escenario: Usuario bloqueado no puede iniciar sesión
    Cuando Ana inicia sesión con el usuario "locked_out_user" y la contraseña "secret_sauce"
    Entonces debería ver el mensaje de error "Epic sadface: Sorry, this user has been locked out."
    Y debería permanecer en la página de inicio de sesión

  @regresion @seguridad
  Escenario: No se puede acceder al inventario sin iniciar sesión
    Cuando Ana intenta abrir directamente la ruta "/inventory.html"
    Entonces debería ver el mensaje de error "Epic sadface: You can only access '/inventory.html' when you are logged in."
    Y debería permanecer en la página de inicio de sesión

  @regresion @seguridad @sesion
  Escenario: Cerrar sesión invalida el acceso al inventario
    Dado que Ana inició sesión con el usuario "standard_user" y la contraseña "secret_sauce"
    Cuando cierra sesión
    Entonces debería permanecer en la página de inicio de sesión
    Y al regresar con el botón atrás del navegador no debería ver el catálogo de productos
