# WMS/TMS e-Commerce

Plataforma de Gestión de Depósitos (WMS) y de Gestión de Envíos (TMS) para una
empresa de e-Commerce.

Trabajo Práctico Integrador: "Sistemas de Gestión de la Configuración".
Ingeniería y Calidad del Software - UTN FRSF - 2026.

---

## 1. Descripción

- **WMS (Warehouse Management System):** gestiona lo que ocurre dentro del
  depósito: recepción de mercadería, ubicación (slotting), picking, packing y
  despacho.
- **TMS (Transportation Management System):** gestiona el movimiento de la
  mercadería desde el depósito hasta el cliente: ruteo, asignación de carriers,
  tracking, cálculo de costos y medición de SLA.

El código es una base mínima cuyo objetivo es aplicar la Gestión de la
Configuración con Git, GitHub y GitFlow.

## 2. Requisitos

- Java 17
- Maven 3.9 o superior

## 3. Instalación y ejecución

```bash
git clone https://github.com/mateogrosso/TrabajoPracticoIyCdS.git
cd TrabajoPracticoIyCdS
mvn clean verify
```

Las configuraciones locales (credenciales, rutas propias de cada máquina) van en
`src/main/resources/application-local.properties`. Ese archivo **no se
versiona** porque está incluido en `.gitignore`.

## 4. Estructura del proyecto

| Ruta | Contenido |
|---|---|
| `src/main/java/ar/utn/wmstms/wms/` | Módulo de depósito (`RecepcionService`) |
| `src/main/java/ar/utn/wmstms/tms/` | Módulo de envíos (`CalculadorCostoFlete`, `EstadoEnvio`) |
| `src/test/java/` | Tests unitarios |
| `.github/CODEOWNERS` | Responsables de revisión por módulo |
| `.github/workflows/ci.yml` | Pipeline de integración continua |
| `.github/pull_request_template.md` | Plantilla para Pull Requests |
| `.gitignore` | Archivos que no se versionan |

## 5. Flujo de trabajo: GitFlow

| Rama | Nace de | Se integra en | Uso |
|---|---|---|---|
| `main` | - | - | Código en producción. Cada versión lleva un tag. |
| `develop` | `main` | - | Integración de funcionalidades terminadas. |
| `feature/*` | `develop` | `develop` | Nuevas funcionalidades. |
| `release/*` | `develop` | `main` y `develop` | Preparación de una versión. |
| `hotfix/*` | `main` | `main` y `develop` | Corrección urgente de producción. |

### Reglas del repositorio
- `main` y `develop` están **protegidas**: no se permite push directo.
- Todo cambio entra por **Pull Request** con:
  - aprobación de al menos un **Code Owner** distinto del autor,
  - **CI en verde** (compilación y tests),
  - conversaciones resueltas.
- Mensajes de commit con formato `tipo(módulo): descripción`.
  Ejemplos: `feat(tms): ...`, `fix(wms): ...`, `chore(release): ...`, `docs: ...`

## 6. Versionado

Se usa **Versionado Semántico** `MAJOR.MINOR.PATCH`:
- **MAJOR:** cambios que rompen compatibilidad.
- **MINOR:** nuevas funcionalidades compatibles.
- **PATCH:** corrección de errores.

Cada versión en producción se marca en `main` con un **tag anotado** `vX.Y.Z`,
que representa una **línea base** del producto.

| Versión | Tipo | Contenido |
|---|---|---|
| v1.0.0 | Release | Proyecto base (WMS y TMS) y validación de recepción |
| v1.0.1 | Hotfix | Corrección del costo de flete con peso volumétrico |
| v1.1.0 | Release | Tracking de envíos y documentación |

## 7. Equipo

| Integrante | Usuario GitHub | Responsabilidades |
|---|---|---|
| Mateo Grosso | @mateogrosso | Administración del repositorio, releases |
| Lautaro Zatti | @LautaroZatti | Proyecto base, hotfix |
| Franco García | @FranGarcia23 | Funcionalidades, documentación |

---

## 8. Resolución de las consignas del TP

| Consigna | Cómo se resolvió |
|---|---|
| 1. Repositorio en GitHub | Repositorio `TrabajoPracticoIyCdS`, docentes agregados como colaboradores. |
| 2. CODEOWNERS | `.github/CODEOWNERS`, con dos responsables por módulo para que siempre haya un revisor distinto del autor. |
| 3. CI | `.github/workflows/ci.yml`: compila y ejecuta tests con Maven en cada Pull Request y en cada push a `main` y `develop`. |
| 4. Circuito de PR | Rama local, `git push -u origin feature/...`, PR hacia `develop` o `main`, merge desde GitHub. |
| 5. Participación | Los tres integrantes realizaron commits, PRs y revisiones. |
| 6. PR comentado y merges | En `feature/validar-recepcion` se dejaron comentarios y sugerencias de código. Hubo merges desde GitHub y merges por comandos (`git merge --no-ff`). |
| 7.a Modificación y push | `feature/validar-recepcion`: validación de cantidad recibida en el WMS. |
| 7.b Configuraciones locales | `.gitignore` excluye compilados (`target/`), archivos de IDEs, logs y configuraciones locales. Si un archivo ya estaba versionado, se quita del índice con `git rm --cached <archivo>`. |
| 7.c, 7.d, 7.e Release 1 | Rama `release/1.0.0` desde `develop`; se fijó la versión 1.0.0 en el `pom.xml`; se mergeó a `main` con tag `v1.0.0` y se devolvió a `develop`. |
| 7.f, 7.g Hotfix | Rama `hotfix/1.0.1` desde `main`; se corrigió el cálculo del flete; PR a `main` con tag `v1.0.1` y PR a `develop` para que el error no reaparezca. |
| 7.h, 7.i Feature con revert | Rama `feature/tracking-envios`: modificación A (estados de envío) con push, modificación B (notificador) con push, y `git revert` de B para volver al estado A. |
| 7.j Feature a producción | Interpretamos que "la rama creada en el punto 1" se refiere a la rama del punto 7.h. Se integró a `develop` por PR y se llevó a producción con `release/1.1.0` y el tag `v1.1.0`. |

### ¿Por qué `git revert` y no `git reset` en la consigna 7.i?
La modificación B ya estaba publicada. `git revert` crea un commit nuevo que
deshace B sin borrar historia, por lo que no afecta a quienes ya la descargaron
y conserva la trazabilidad del cambio. `git reset` + `push --force` reescribe el
historial compartido, rompe las copias del resto del equipo y elimina la
evidencia necesaria para la auditoría de la configuración.

---

## 9. ¿Qué documentamos en este README y por qué? (Consigna 8.a)

Documentamos todo lo que una persona nueva necesita para **entender, ejecutar y
modificar** el sistema sin depender del equipo:

- **Qué es** el sistema y qué problema resuelve.
- **Qué necesita** para funcionar (requisitos).
- **Cómo se ejecuta** y cómo se manejan las configuraciones locales.
- **Cómo está organizado** el código.
- **Cómo se trabaja**: ramas, reglas, formato de commits y versionado.
- **Quiénes son los responsables** de cada parte.
- **Cómo contribuir** (ver sección 10).

**¿Por qué se versiona junto al código?** Porque el README es un **elemento de
configuración** más. Al versionarlo en el mismo repositorio, cada línea base
(tag) contiene la documentación que corresponde exactamente a esa versión del
software. Si se consulta `v1.0.0`, el README describe `v1.0.0` y no una
versión posterior.

---

## 10. Guía para Pull Requests externos (Consigna 8.b)

### ¿Qué datos le pedimos a una persona externa que realiza un cambio?

1. **Título descriptivo** con tipo y módulo.
   Ejemplo: `fix(tms): corrige cálculo de SLA`.
2. **Descripción:** qué cambia, desde el punto de vista funcional.
3. **Motivación:** qué problema resuelve y el issue asociado (`Closes #42`).
4. **Tipo de cambio:** funcionalidad, corrección, refactor, documentación o configuración.
5. **Módulo afectado:** WMS, TMS o ambos. Permite saber a quién le corresponde revisar.
6. **Cómo se probó:** tests agregados o modificados y pasos para reproducir.
7. **Impacto:** si rompe compatibilidad o requiere cambios de configuración o
   migración de datos. Esto define si la próxima versión sube MAJOR, MINOR o PATCH.
8. **Evidencia:** capturas, logs o ejemplos de entrada y salida, si corresponde.
9. **Checklist:** compila, tests en verde, sin configuraciones locales subidas,
   documentación actualizada.

**Racional:** quien revisa no escribió el código. Necesita entender **qué**
cambió, **por qué** y con qué **riesgo**, sin tener que reconstruirlo leyendo el
código línea por línea. Estos datos equivalen a la **solicitud de cambio**
(descripción, justificación e impacto) del proceso de **control de cambios** de
la Gestión de la Configuración: permiten evaluar el cambio antes de aprobarlo.

### ¿Qué nos ofrece GitHub para ayudarnos?

| Herramienta | Qué hace | Racional |
|---|---|---|
| **Plantilla de PR** (`.github/pull_request_template.md`) | Precarga el formulario al abrir un PR | Estandariza la información y evita que se omitan datos |
| **CODEOWNERS** (`.github/CODEOWNERS`) | Asigna revisores automáticamente según los archivos modificados | El cambio lo revisa quien conoce ese módulo |
| **Protección de ramas** | Exige PR, aprobación de Code Owner y CI en verde | Nadie integra código sin control, ni siquiera los administradores |
| **GitHub Actions** (`.github/workflows/ci.yml`) | Compila y ejecuta los tests en cada PR | Verificación objetiva y automática del cambio |
| **Revisiones con comentarios y sugerencias** | Permite comentar líneas y proponer código que el autor acepta con un clic | Mejora la calidad y deja registro de la discusión |
| **Vinculación con Issues** (`Closes #N`) | Relaciona el PR con el pedido que lo originó | Trazabilidad: pedido → cambio → versión |
| **Plantillas de Issues, Labels y Draft PRs** | Estandarizan reportes, clasifican cambios e indican si un PR está listo | Organización y visibilidad del trabajo |
| **Releases y Tags** | Marcan versiones y permiten publicar notas de versión | Identificación de líneas base y comunicación de cambios |