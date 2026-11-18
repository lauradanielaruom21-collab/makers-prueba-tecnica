# Makers — Prueba Técnica QA

Este repositorio es la solución de la prueba técnica de QA de Makers. Contiene tres módulos:

1. **Automatización web:** un robot abre Google Chrome, entra a la tienda de demostración [SauceDemo](https://www.saucedemo.com/) y prueba el inicio de sesión como lo haría una persona.
2. **Pruebas de API:** un programa le envía peticiones al servicio [reqres.in](https://reqres.in/api/) y verifica que sus respuestas sean correctas.
3. **Pruebas funcionales:** el plan de pruebas de **MakersPay**, una billetera digital ficticia. Incluye escenarios, casos de prueba, técnicas y reportes de bugs.

Al terminar cada ejecución se genera un **reporte en HTML y en PDF** con los resultados. El HTML se abre solo en el navegador.

---

## Contenido

1. [¿Qué pide el reto y cómo se cumple?](#1-qué-pide-el-reto-y-cómo-se-cumple)
2. [Herramientas utilizadas](#2-herramientas-utilizadas)
3. [Paso a paso: instalar y ejecutar](#3-paso-a-paso-instalar-y-ejecutar)
4. [Todos los comandos](#4-todos-los-comandos)
5. [Qué prueba cada escenario](#5-qué-prueba-cada-escenario)
6. [Reportes de resultados](#6-reportes-de-resultados)
7. [Hallazgo importante en la API](#7-hallazgo-importante-en-la-api)
8. [Módulo funcional MakersPay](#8-módulo-funcional-makerspay)
9. [Estructura del proyecto](#9-estructura-del-proyecto)
10. [Decisiones técnicas](#10-decisiones-técnicas)
11. [Preguntas frecuentes y problemas comunes](#11-preguntas-frecuentes-y-problemas-comunes)
12. [Glosario](#12-glosario)

---

## 1. ¿Qué pide el reto y cómo se cumple?

### Módulo de automatización: smoke test de inicio de sesión (SauceDemo)

| Lo que pide el reto | Cómo se cumple | Estado |
|---|---|:---:|
| Usar Cypress, Serenity BDD o Selenium con JavaScript, Java o Python | **Serenity BDD + Selenium con Java 17** | ✅ |
| 1. Login exitoso con credenciales válidas | Escenario *"Login exitoso con credenciales válidas"*: verifica el título "Products", la dirección de la página, el carrito y que se vean los 6 productos | ✅ |
| 2. Login fallido con contraseña incorrecta | Escenario *"Login fallido con contraseña incorrecta"*: verifica el mensaje de error, que el usuario no entre y que los campos queden marcados en rojo | ✅ |
| 3. Validación de campos obligatorios | Escenario *"Validación de campos obligatorios"*, probado 3 veces: ambos campos vacíos, solo la contraseña vacía y solo el usuario vacío | ✅ |
| Buenas prácticas de automatización | Patrón **Screenplay**, escenarios en lenguaje natural (español), selectores estables, sin esperas fijas, reportes con capturas de pantalla | ✅ |
| *Plus:* casos adicionales | Usuario bloqueado, acceso sin iniciar sesión, cierre de sesión y una **compra completa de principio a fin (E2E)** | ➕ |

### Módulo API (reqres.in)

| Lo que pide el reto | Cómo se cumple | Estado |
|---|---|:---:|
| POST a `/users` con `{"name": "Test User", "job": "Automation Engineer"}` | Escenario *"Crear un usuario y extraer su id"* | ✅ |
| Verificar código HTTP 201 | Se valida el 201, que vuelvan el nombre y el trabajo enviados, que exista un id y que la fecha de creación sea la actual | ✅ |
| Extraer el id y hacer GET a `/users/{id}` | Escenario *"Consultar por su id el usuario recién creado"*: toma el id de la respuesta y lo consulta | ✅ |
| Verificar código 200 y que nombre y trabajo coincidan | Se valida en ese mismo escenario. **reqres.in responde 404 porque no guarda los usuarios creados.** Está documentado como bug (ver [sección 7](#7-hallazgo-importante-en-la-api)) | ✅ implementado · ⚠️ falla por el servicio |
| *Plus:* casos de prueba adicionales | Contrato JSON de la respuesta, consulta de un usuario existente, usuario inexistente (404), actualización (PUT) y eliminación (DELETE) | ➕ |

### Módulo funcional (MakersPay)

| Lo que pide el reto | Dónde está | Estado |
|---|---|:---:|
| Escenarios de prueba | [Plan de pruebas, sección 8](docs/modulo-funcional/MakersPay-plan-de-pruebas.md#8-escenarios-de-prueba): 11 escenarios | ✅ |
| Casos de prueba | [Sección 9](docs/modulo-funcional/MakersPay-plan-de-pruebas.md#9-casos-de-prueba) y la [matriz en Excel](docs/modulo-funcional/MakersPay-matriz-de-pruebas.xlsx): 32 casos | ✅ |
| Reporte de bugs | [Sección 12](docs/modulo-funcional/MakersPay-plan-de-pruebas.md#12-reporte-de-bugs): 5 reportes con severidad, prioridad, pasos y evidencia | ✅ |
| Técnicas de prueba | [Sección 5](docs/modulo-funcional/MakersPay-plan-de-pruebas.md#5-técnicas-de-prueba) y [sección 7](docs/modulo-funcional/MakersPay-plan-de-pruebas.md#7-diseño-con-técnicas): partición de equivalencia, valores límite, tabla de decisión y transición de estados | ✅ |
| Tipos de prueba | [Sección 6](docs/modulo-funcional/MakersPay-plan-de-pruebas.md#6-tipos-de-prueba) | ✅ |
| Reglas de negocio (monto mínimo y máximo, saldo, auto-envío, débito y crédito, historial, mensajes de error) | Cubiertas y trazadas en la [sección 11 (trazabilidad)](docs/modulo-funcional/MakersPay-plan-de-pruebas.md#11-trazabilidad) | ✅ |

### Criterios de evaluación del reto

| Criterio | Peso | Cómo se atiende |
|---|:---:|---|
| Correcta implementación técnica | 30 % | Código compilado y ejecutado; todos los escenarios pasan, excepto el GET documentado como bug del servicio |
| Cobertura de los requisitos | 25 % | Cada punto del enunciado tiene su escenario (tablas de arriba) |
| Buenas prácticas y legibilidad | 20 % | Screenplay, escenarios en español legibles por negocio y código separado por responsabilidades |
| Uso adecuado de la herramienta | 15 % | Serenity BDD + Cucumber + REST Assured, con reportes de documentación viva |
| Entregables claros y ejecutables | 10 % | Un comando por grupo de pruebas, reporte HTML/PDF automático y este paso a paso |

---

## 2. Herramientas utilizadas

| Herramienta | Versión | ¿Para qué sirve? (en palabras simples) |
|---|---|---|
| **Java** | 17 | El lenguaje de programación en el que están escritas las pruebas |
| **Maven** | 3.9+ | Descarga lo que el proyecto necesita y ejecuta las pruebas con un solo comando |
| **Serenity BDD** | 4.2.34 | El framework principal: organiza las pruebas y genera reportes con capturas de pantalla de cada paso |
| **Screenplay** (patrón de Serenity) | — | Forma de escribir las pruebas como si un *actor* (por ejemplo, "Ana") realizara *tareas* ("inicia sesión", "agrega al carrito") y respondiera *preguntas* ("¿cuántos productos ve?") |
| **Cucumber** | 7.20 | Permite escribir los escenarios en español normal (Dado / Cuando / Entonces), entendibles por cualquier persona |
| **Selenium WebDriver** | 4.33 (incluido en Serenity) | Controla Google Chrome: escribe, hace clic y lee la pantalla como una persona |
| **Selenium Manager** | incluido | Descarga solo el "driver" que corresponde a tu versión de Chrome; no hay que instalar nada a mano |
| **REST Assured** | 5.5 | Envía peticiones a la API y revisa las respuestas |
| **JSON Schema Validator** | 5.5 | Verifica que la estructura de las respuestas de la API sea la esperada (su "contrato") |
| **JUnit Platform** | 1.11 | El motor que lanza las pruebas (los *runners*) |
| **AssertJ / Hamcrest** | 3.26 | Librerías para escribir las verificaciones ("debería ser igual a…") |
| **Google Chrome** | cualquier versión reciente | El navegador donde corren las pruebas web |
| **GitHub Actions** | — | Ejecuta las pruebas automáticamente en la nube con cada cambio y guarda los reportes |
| **Postman** | — | Colección opcional para probar la API manualmente ([`postman/`](postman/reqres-users.postman_collection.json)) |
| **Excel / Markdown** | — | Documentación del módulo funcional |

---

## 3. Paso a paso: instalar y ejecutar

> Las instrucciones son para **Windows**. En Mac o Linux los pasos son iguales; solo cambia cómo se instalan los programas.

### Paso 1. Instalar los programas necesarios (solo la primera vez)

| Programa | Dónde descargarlo | Cómo comprobar que quedó instalado |
|---|---|---|
| **Java 17** (JDK) | https://adoptium.net (elegir *Temurin 17*) | En una terminal: `java -version` → debe decir `17` o más |
| **Maven** | https://maven.apache.org/download.cgi (descomprimirlo y agregar su carpeta `bin` a la variable de entorno *PATH*) | `mvn -version` |
| **Google Chrome** | https://www.google.com/chrome | Que abra normalmente |
| **Git** | https://git-scm.com/downloads | `git --version` |

> Si usas **IntelliJ IDEA**, ya trae Maven incluido: puedes saltarte la instalación de Maven y usar los runners (ver [Paso 5](#paso-5-opcional-ejecutar-desde-intellij-idea)).

### Paso 2. Descargar el proyecto

Abre una terminal (en Windows: tecla Windows → escribe **PowerShell** → Enter) y ejecuta:

```bash
git clone https://github.com/lauradanielaruom21-collab/makers-prueba-tecnica.git
cd makers-prueba-tecnica
```

### Paso 3. Ejecutar las pruebas

La primera vez tarda unos minutos más, porque Maven descarga las librerías.

```bash
mvn clean verify -Prequeridos
```

Qué vas a ver:
1. Se abre **Google Chrome** y el robot escribe usuario y contraseña, hace clic, etc. Chrome se abre y se cierra una vez por escenario.
2. En la terminal aparecen los pasos de cada escenario.
3. Al final debe aparecer **`BUILD SUCCESS`**, que significa que todo pasó.
4. Se abre automáticamente el **reporte HTML** en tu navegador.

### Paso 4. Ver los reportes

Quedan en la carpeta `target/reporte/`:

```bash
start target\reporte\reporte-ejecucion.html
start target\reporte\reporte-ejecucion.pdf
```

El reporte completo, con capturas de pantalla de cada paso, está en `target\site\serenity\index.html`.

### Paso 5 (opcional). Ejecutar desde IntelliJ IDEA

1. *File → Open* y elegir la carpeta del proyecto. IntelliJ lo reconoce como proyecto Maven y descarga todo.
2. Ir a `src/test/java/co/makers/runners/`.
3. Clic derecho sobre el runner que quieras → **Run**. Chrome se abre y se ve la ejecución.

---

## 4. Todos los comandos

Todos se ejecutan desde la carpeta del proyecto.

### Pruebas web (SauceDemo), con Chrome visible

| Comando | Qué ejecuta |
|---|---|
| `mvn clean verify -Prequeridos` | Los **3 escenarios que pide el reto** (5 ejecuciones) |
| `mvn clean verify -Padicionales` | Los **4 escenarios adicionales**: usuario bloqueado, acceso sin sesión, cierre de sesión y compra E2E |
| `mvn clean verify -Ptodos` | Requeridos + adicionales |

### Pruebas de API (reqres.in)

| Comando | Qué ejecuta |
|---|---|
| `mvn clean verify -Papi-requeridos` | El flujo que pide el reto: POST `/users` y GET `/users/{id}`. **El GET falla a propósito** (ver [sección 7](#7-hallazgo-importante-en-la-api)) |
| `mvn clean verify -Papi-adicionales` | Contrato, consulta, usuario inexistente, PUT y DELETE |
| `mvn clean verify -Papi` | Toda la API, excepto el bug conocido |

### Otros

| Comando | Qué ejecuta |
|---|---|
| `mvn clean verify` | Todo (web + API) sin abrir Chrome en pantalla (modo *headless*), sin el bug conocido |
| `mvn clean verify -Psmoke` | Lo exigido por el reto (web + POST de la API), todo en verde |
| `mvn clean verify -Pdefectos` | Solo el escenario del bug conocido (falla a propósito, como evidencia) |
| `mvn clean verify "-Pui,headed"` | Agregar `headed` a cualquier perfil hace visible a Chrome |
| `mvn clean verify "-Dtags=@seguridad or @contrato"` | Cualquier combinación de etiquetas |
| `mvn clean verify "-Dreporte.abrir=false"` | No abrir el reporte al terminar |

> **PowerShell:** cuando un comando tiene comas o espacios (por ejemplo `"-Pui,headed"`), va entre comillas.

### Runners (para ejecutar desde IntelliJ)

| Runner (`src/test/java/co/makers/runners/`) | Qué ejecuta |
|---|---|
| `RequeridosUiRunner` | Web: lo que pide el reto |
| `AdicionalesUiRunner` | Web: adicionales |
| `TodosUiRunner` | Web: requeridos + adicionales |
| `RequeridosApiRunner` | API: lo que pide el reto (el GET falla a propósito) |
| `AdicionalesApiRunner` | API: adicionales |
| `CucumberTestSuite` | Runner general que usa Maven con los perfiles |

- Un runner también se puede lanzar desde la terminal: `mvn clean verify -Dit.test=RequeridosUiRunner`.
- Al ejecutar desde IntelliJ no se genera el reporte HTML/PDF automáticamente. Para generarlo después: `mvn serenity:aggregate serenity:reports exec:java@reporte-pdf`.

---

## 5. Qué prueba cada escenario

Los escenarios están escritos en español en los archivos `.feature`, así que cualquier persona puede leerlos:
- Web: [`inicio_sesion.feature`](src/test/resources/features/ui/inicio_sesion.feature) · [`compra.feature`](src/test/resources/features/ui/compra.feature)
- API: [`usuarios.feature`](src/test/resources/features/api/usuarios.feature)

Ejemplo:

```gherkin
Escenario: Login fallido con contraseña incorrecta
  Dado que Ana está en la página de inicio de sesión
  Cuando Ana inicia sesión con el usuario "standard_user" y la contraseña "clave_incorrecta"
  Entonces debería ver el mensaje de error "Epic sadface: Username and password do not match any user in this service"
  Y debería permanecer en la página de inicio de sesión
  Y los campos de usuario y contraseña deberían quedar marcados con error
```

### Web (SauceDemo)

| Escenario | Qué verifica | ¿Lo pide el reto? |
|---|---|:---:|
| Login exitoso | Con `standard_user` / `secret_sauce` se entra a la página de productos y se ven los 6 productos | ✅ |
| Login fallido | Con una contraseña incorrecta aparece el error, no se entra y los campos se marcan en rojo | ✅ |
| Campos obligatorios (×3) | Sin usuario → "Username is required"; sin contraseña → "Password is required" | ✅ |
| Usuario bloqueado | `locked_out_user` no puede entrar y ve "Sorry, this user has been locked out." | ➕ |
| Acceso sin sesión | Entrar directo a la página de productos sin iniciar sesión devuelve al login con un error | ➕ |
| Cierre de sesión | Después de cerrar sesión, el botón "atrás" del navegador no vuelve a mostrar los productos | ➕ |
| **Compra E2E** | Agrega 2 productos, el carrito marca 2, se diligencian los datos de envío, el resumen muestra los productos y el subtotal **$39.98**, se confirma la compra, aparece "Thank you for your order!" y el carrito queda vacío | ➕ |

### API (reqres.in)

| Escenario | Qué verifica | ¿Lo pide el reto? |
|---|---|:---:|
| Crear usuario | POST `/users` → 201, devuelve el nombre y el trabajo enviados, un id y la fecha de creación actual | ✅ |
| Consultar el usuario creado | GET `/users/{id}` con el id recién creado → debería ser 200 con los mismos datos (**falla: bug del servicio**) | ✅ |
| Contrato de creación | La respuesta tiene la estructura esperada y es JSON | ➕ |
| Consultar usuario existente | GET `/users/2` → 200, con el contrato y el correo esperados | ➕ |
| Usuario inexistente | GET `/users/23` → 404 | ➕ |
| Actualizar (PUT) | 200, con los datos nuevos y la fecha de actualización | ➕ |
| Eliminar (DELETE) | 204, sin contenido | ➕ |

---

## 6. Reportes de resultados

Cada ejecución con Maven genera:

| Archivo | Qué es |
|---|---|
| `target/reporte/reporte-ejecucion.html` | **Resumen de resultados** (se abre solo al terminar) |
| `target/reporte/reporte-ejecucion.pdf` | El mismo resumen en **PDF**, listo para adjuntar o enviar |
| `target/site/serenity/index.html` | Reporte completo de Serenity: cada escenario, paso a paso, con **capturas de pantalla** (web) y el detalle de cada petición y respuesta (API) |
| `target/cucumber-reports/cucumber.html` | Reporte nativo de Cucumber |

> El comando `mvn clean` borra la carpeta `target`. Si vas a guardar un reporte, cópialo a otra carpeta.

---

## 7. Hallazgo importante en la API

**reqres.in es una API de demostración (*mock*): cuando creas un usuario con POST te responde 201 y un id, pero en realidad no lo guarda.** Por eso, al consultar ese id con GET responde **404 (no encontrado)** en lugar del 200 que pide el reto.

Cómo se manejó:
- El escenario se implementó **exactamente como lo pide el enunciado** y se reportó como bug: [BUG-API-01](docs/modulo-funcional/MakersPay-plan-de-pruebas.md#bug-api-01--get-usersid-responde-404-para-el-usuario-recién-creado-con-post-users).
- Tiene la etiqueta `@defecto-conocido`: la ejecución normal lo excluye, para que el resultado refleje el estado real y no una limitación conocida del servicio.
- `mvn clean verify -Pdefectos` o `-Papi-requeridos` lo ejecutan y dejan la evidencia (petición y respuesta) en el reporte.
- La lógica de consulta y validación sí se prueba en verde con un usuario que existe (`GET /users/2`).

### Límite de uso de reqres.in

Sin cuenta, reqres.in permite **40 peticiones por día** desde programas como este. Si se pasa el límite, responde `429 rate_limit_exceeded` y las pruebas de API fallan por esa razón, no por un error del proyecto. Hay dos opciones:
- Esperar a que se reinicie el cupo (medianoche UTC; 7:00 p. m. en Colombia).
- Crear una cuenta gratuita en https://app.reqres.in y pasar la llave sin tocar código:
  ```bash
  mvn clean verify -Papi "-Dreqres.api.key=TU_LLAVE"
  ```
  También se puede usar la variable de entorno `REQRES_API_KEY`.

---

## 8. Módulo funcional MakersPay

Documentación completa en [`docs/modulo-funcional/MakersPay-plan-de-pruebas.md`](docs/modulo-funcional/MakersPay-plan-de-pruebas.md) y la [matriz en Excel](docs/modulo-funcional/MakersPay-matriz-de-pruebas.xlsx).

| Sección | Contenido |
|---|---|
| Análisis y alcance | Requerimiento, reglas de negocio, qué entra y qué no |
| Supuestos y preguntas al PO | Dudas del requerimiento a resolver con negocio |
| Riesgos | Qué puede salir mal y su impacto |
| Técnicas | Partición de equivalencia, valores límite ($4.999 / $5.000 / $2.000.000 / $2.000.001), tabla de decisión y transición de estados |
| Tipos de prueba | Smoke, funcionales (positivas y negativas), integración, regresión, API/contrato, seguridad, concurrencia/rendimiento, usabilidad/accesibilidad y compatibilidad |
| 11 escenarios y 32 casos | Con precondiciones, pasos, datos y resultado esperado |
| Escenarios BDD | Escritos en Gherkin |
| Trazabilidad | Cada regla de negocio enlazada a sus casos |
| 5 reportes de bugs | 4 de MakersPay (ilustrativos) y BUG-API-01 (real, de reqres.in) |
| Criterios de entrada/salida y propuesta de automatización | Cuándo empezar, cuándo terminar y qué automatizar |

---

## 9. Estructura del proyecto

```
makers-prueba-tecnica
├── pom.xml                                  # Configuración de Maven: librerías y perfiles de ejecución
├── README.md                                # Este documento
├── docs/modulo-funcional/                   # Módulo funcional MakersPay (.md + .xlsx)
├── postman/                                 # Colección de Postman para la API
├── .github/workflows/tests.yml              # Ejecución automática en GitHub Actions
└── src/test
    ├── java/co/makers
    │   ├── runners/                         # Runners: puntos de entrada para ejecutar las pruebas
    │   ├── hooks/StageHooks.java            # Prepara a los "actores" antes de cada escenario
    │   ├── utils/Config.java                # URLs y llave de la API (configurables sin tocar código)
    │   ├── utils/GenerarReportePdf.java     # Genera el reporte HTML/PDF al terminar
    │   ├── ui
    │   │   ├── userinterfaces/              # Dónde están los elementos en pantalla (login, productos, carrito, checkout)
    │   │   ├── tasks/                       # Qué hace el usuario (iniciar sesión, agregar al carrito, pagar…)
    │   │   ├── questions/                   # Qué se le pregunta a la pantalla (¿cuántos productos hay?)
    │   │   └── stepdefinitions/             # Conectan cada frase en español con el código
    │   └── api
    │       ├── models/User.java             # Datos del usuario que se envían
    │       ├── tasks/                       # Crear, consultar, actualizar y eliminar usuarios
    │       └── stepdefinitions/             # Conectan cada frase en español con el código
    └── resources
        ├── features/ui/                     # Escenarios web en español (.feature)
        ├── features/api/                    # Escenarios de API en español (.feature)
        ├── schemas/                         # Estructura esperada de las respuestas de la API
        ├── serenity.conf                    # Configuración del navegador y de Serenity
        ├── junit-platform.properties        # Configuración de Cucumber
        └── logging.properties               # Silencia avisos informativos de Selenium
```

---

## 10. Decisiones técnicas

*Para revisores técnicos.*

- **Screenplay en lugar de Page Object clásico:** separa *qué* hace el usuario (tasks), *dónde* está cada elemento (targets) y *qué* se verifica (questions / `Ensure`). El mismo modelo de actor sirve para UI y API.
- **Gherkin declarativo y en español** (`# language: es`): los escenarios describen comportamiento de negocio, no clics.
- **Selectores `data-test`** cuando la aplicación los expone. En el catálogo, el botón "Add to cart" se ubica por el nombre visible del producto, así el escenario puede elegir productos por nombre.
- **Sin `Thread.sleep`:** las esperas son explícitas (`WaitUntil`) y solo donde hay animación (menú lateral).
- **Tareas reutilizables:** una sola tarea `Login` cubre los casos positivos, negativos y de campos vacíos (no escribe los campos vacíos).
- **Etiquetas por propósito** (`@smoke`, `@regresion`, `@negativo`, `@seguridad`, `@e2e`, `@contrato`, `@defecto-conocido`) y perfiles Maven y runners por grupo.
- **Defecto conocido aislado:** `@defecto-conocido` se excluye por defecto y tiene su propio perfil, para que el pipeline sea confiable sin ocultar el hallazgo.
- **Configuración externa:** URLs, llave de la API y modo headless se cambian con `-D` o variables de entorno, sin recompilar.
- **Chrome visible en local y headless en CI:** `serenity.conf` deja Chrome visible para el IDE. Maven lo pone en headless por defecto y lo deja visible con los perfiles de UI o con `headed`.
- **Compatibilidad con Chrome reciente:** Selenium Manager resuelve el driver. El aviso informativo de CDP, que aparece cuando Chrome es más nuevo que Selenium, se silencia en `logging.properties`; no se usan DevTools.
- **Reporte HTML/PDF automático:** en `post-integration-test`, `exec-maven-plugin` imprime el resumen de Serenity a PDF con Chrome headless, usando el comando de impresión estándar de WebDriver. Se genera aunque haya pruebas fallidas, para servir de evidencia.
- **CI con GitHub Actions:** matriz UI/API, ejecución diaria programada y publicación de los reportes como artefacto.

---

## 11. Preguntas frecuentes y problemas comunes

| Situación | Causa y solución |
|---|---|
| `mvn` no se reconoce como comando | Maven no está instalado o no está en el *PATH*. Instálalo (Paso 1) o usa IntelliJ |
| `java -version` muestra una versión menor a 17 | Instala Java 17 desde https://adoptium.net |
| No veo Chrome al ejecutar | Usa `-Prequeridos`, `-Padicionales`, `-Ptodos` o agrega `headed`. `mvn clean verify` sin perfil corre sin ventana |
| Las pruebas de API fallan con `429` | Se agotó el cupo diario gratuito de reqres.in (ver [sección 7](#7-hallazgo-importante-en-la-api)) |
| `Failed to delete ...\target` | Hay una ventana del explorador, el reporte abierto u otra terminal usando la carpeta `target`. Ciérrala y vuelve a ejecutar |
| El escenario "Consultar por su id el usuario recién creado" falla | Es esperado: reqres.in no guarda usuarios (BUG-API-01) |
| La primera ejecución tarda mucho | Maven está descargando las librerías; las siguientes son más rápidas |

---

## 12. Glosario

| Término | Significado |
|---|---|
| **Smoke test** | Conjunto mínimo de pruebas que confirma que lo más importante funciona |
| **E2E (end to end)** | Prueba de un flujo completo, de principio a fin, como lo haría un usuario real |
| **API** | Forma en que dos programas se comunican; aquí se le envían peticiones a reqres.in |
| **POST / GET / PUT / DELETE** | Tipos de petición a una API: crear, consultar, actualizar y eliminar |
| **Código HTTP** | Número con que responde la API: 200 = OK, 201 = creado, 204 = sin contenido, 404 = no encontrado, 429 = demasiadas peticiones |
| **Escenario / Feature** | Descripción en lenguaje natural de un comportamiento a probar (archivos `.feature`) |
| **Gherkin** | El formato *Dado / Cuando / Entonces* con que se escriben los escenarios |
| **Runner** | Clase que indica qué escenarios ejecutar |
| **Perfil (`-P…`)** | Atajo de Maven que elige qué grupo de pruebas correr |
| **Headless** | El navegador corre sin mostrarse en pantalla |
| **Mock** | Servicio de demostración que simula respuestas sin lógica real |
| **Contrato (JSON Schema)** | La estructura que debe tener una respuesta de la API |
| **CI (integración continua)** | Ejecución automática de las pruebas en la nube con cada cambio |
