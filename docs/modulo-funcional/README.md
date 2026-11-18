# Módulo Funcional — MakersPay

**Producto (ficticio):** MakersPay, una billetera digital donde el usuario puede iniciar sesión, ver su saldo y enviar dinero a otro usuario usando su número de celular.

**Requerimiento:** un usuario autenticado puede enviar dinero a otro usuario registrado usando su número de celular.

## Entregables

| Archivo | Qué contiene |
|---|---|
| 📄 [**Plan de pruebas (documento completo)**](MakersPay-plan-de-pruebas.md) | Análisis, alcance, riesgos, técnicas, tipos de prueba, escenarios, casos, Gherkin, trazabilidad, reporte de bugs y criterios de salida |
| 📊 [**Matriz de pruebas (Excel)**](MakersPay-matriz-de-pruebas.xlsx) | Los mismos casos en formato ejecutable, con hojas de resumen, técnicas, reporte de bugs y trazabilidad |

## Lo que pide el reto y dónde está

| Pide el reto | Dónde verlo |
|---|---|
| **Escenarios de prueba** | [Sección 8](MakersPay-plan-de-pruebas.md#8-escenarios-de-prueba): 11 escenarios |
| **Casos de prueba** | [Sección 9](MakersPay-plan-de-pruebas.md#9-casos-de-prueba): 32 casos (20 de prioridad alta, 11 media y 1 baja), con precondiciones, pasos, datos y resultado esperado |
| **Reporte de bugs** | [Sección 12](MakersPay-plan-de-pruebas.md#12-reporte-de-bugs): 5 reportes con severidad, prioridad, pasos, resultado actual vs esperado y evidencia |
| **Técnicas de prueba** | [Sección 5](MakersPay-plan-de-pruebas.md#5-técnicas-de-prueba) y su aplicación en la [sección 7](MakersPay-plan-de-pruebas.md#7-diseño-con-técnicas) |
| **Tipos de prueba** | [Sección 6](MakersPay-plan-de-pruebas.md#6-tipos-de-prueba) |

## Reglas de negocio cubiertas

| ID | Regla | Casos que la prueban |
|---|---|---|
| RN-02 | Monto mínimo $5.000 COP | CP-05 a CP-07: $4.999 (rechazado), $5.000 y $5.001 (exitosos) |
| RN-03 | Monto máximo $2.000.000 COP | CP-08 a CP-10: $1.999.999 y $2.000.000 (exitosos), $2.000.001 (rechazado) |
| RN-04 | No enviar más del saldo disponible | CP-11 a CP-13: con saldo $80.000, enviar $80.000 (exitoso) y $80.001 (rechazado) |
| RN-05 | No enviar al propio número | CP-18: incluye el propio número escrito como '+57 300 111 2233' |
| RN-07 a RN-09 | Si la transacción es exitosa: se descuenta al remitente, se acredita al destinatario y queda en el historial de ambos | CP-21 a CP-23 |
| RN-10 | Si la transacción falla: mensaje claro y saldo sin cambios | CP-06, CP-09, CP-12, CP-24, CP-30 y CP-31 |

La trazabilidad completa (las 10 reglas contra los 32 casos) está en la [sección 11](MakersPay-plan-de-pruebas.md#11-trazabilidad).

## Escenarios de prueba

| ID | Escenario | Prioridad |
|---|---|---|
| ESC-01 | Autenticación y consulta de saldo | Alta |
| ESC-02 | Envío exitoso de dinero (flujo principal) | Alta |
| ESC-03 | Validación del monto mínimo por transacción | Alta |
| ESC-04 | Validación del monto máximo por transacción | Alta |
| ESC-05 | Validación de saldo disponible | Alta |
| ESC-06 | Validación del destinatario (registrado, propio número, formato) | Alta |
| ESC-07 | Integridad de saldos e historial de movimientos | Alta |
| ESC-08 | Atomicidad: una transacción fallida no afecta saldos | Alta |
| ESC-09 | Seguridad del envío | Alta |
| ESC-10 | Concurrencia e idempotencia | Alta |
| ESC-11 | Usabilidad y accesibilidad de mensajes | Media |

## Técnicas de prueba utilizadas

- **Partición de equivalencia:** clases válidas e inválidas de monto, saldo y celular.
- **Análisis de valores límite:** justo antes, en y justo después de $5.000, $2.000.000 y el saldo disponible.
- **Tabla de decisión:** combinación de las 5 condiciones de negocio.
- **Transición de estados:** ciclo de vida de la transacción.
- **Casos de uso / E2E:** login → saldo → envío → historial.
- **Adivinanza de errores:** doble clic, concurrencia, montos negativos, manipulación de la petición.
- **Pruebas exploratorias por sesiones**, con charter.

## Tipos de prueba

Smoke · funcionales (positivas y negativas) · integración · regresión · API/contrato · seguridad · concurrencia/rendimiento · usabilidad y accesibilidad · compatibilidad.

## Bugs reportados

| ID | Título | Severidad | Prioridad |
|---|---|---|---|
| BUG-MP-01 | Envío de exactamente $2.000.000 es rechazado como "monto superior al máximo" | Alta | Alta |
| BUG-MP-02 | Doble clic en "Enviar" genera dos débitos para la misma transacción | Crítica | Alta |
| BUG-MP-03 | Saldo insuficiente muestra "Error 500" en lugar de un mensaje claro | Media | Media |
| BUG-MP-04 | Se permite el auto-envío si el número propio se escribe con prefijo +57 | Alta | Alta |
| BUG-API-01 | GET /users/{id} responde 404 para el usuario recién creado con POST /users (reqres.in) | Alta | Media |

> MakersPay es un producto ficticio: BUG-MP-01 a BUG-MP-04 son ejemplos ilustrativos de cómo se reportarían los hallazgos. BUG-API-01 es real y se evidencia con el proyecto de automatización (`mvn clean verify -Pdefectos`).
