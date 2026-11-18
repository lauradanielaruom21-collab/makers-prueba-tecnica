# language: es
@ui
Característica: Compra de productos en SauceDemo
  Como comprador de SauceDemo
  Quiero agregar productos al carrito y pagarlos
  Para recibir mi pedido

  Antecedentes:
    Dado que Ana está en la página de inicio de sesión

  @regresion @e2e @compra
  Escenario: Compra completa de productos de principio a fin
    Dado que Ana inició sesión con el usuario "standard_user" y la contraseña "secret_sauce"
    Cuando agrega al carrito los productos:
      | Sauce Labs Backpack   |
      | Sauce Labs Bike Light |
    Entonces el carrito debería indicar 2 productos
    Cuando diligencia sus datos de envío con nombre "Ana", apellido "Gómez" y código postal "110111"
    Entonces el resumen de la compra debería listar los productos seleccionados
    Y el subtotal debería ser "$39.98"
    Cuando confirma la compra
    Entonces debería ver el mensaje de confirmación "Thank you for your order!"
    Y el carrito debería quedar vacío
