# Automatización Web - Flujo de Autenticación (Yuntas Publicidad)

Proyecto de automatización de pruebas funcionales Web construido bajo **Serenity BDD**, **Cucumber (Gherkin)** y el patrón de diseño **Screenplay** en Java. Valida el flujo de inicio de sesión sobre el entorno de preproducción (`staging`) de la plataforma administrativa de Yuntas Publicidad.

---

## 📋 Requisitos del Entorno

* **Java JDK:** 17 o superior
* **Apache Maven:** 3.8+
* **Navegador:** Google Chrome actualizado (la gestión del controlador se realiza de forma automática mediante `webdriver.autodownload = true`)
* **IDE recomendado:** IntelliJ IDEA con soporte para Cucumber for Java y Gherkin

---

## 🏗️ Arquitectura del Proyecto (Patrón Screenplay)

El proyecto sigue una separación estricta de responsabilidades para facilitar su mantenimiento y escalabilidad:

```text
AutoWebSemana03
├── evidencias/                         # Capturas paso a paso generadas por ejecución
├── src
│   ├── main/java/org/example
│   │   ├── pages/
│   │   │   └── LoginPage.java          # Centralización de locators estables (UI)
│   │   ├── questions/
│   │   │   ├── CurrentUrl.java         # Consulta la URL actual del navegador
│   │   │   ├── FieldValidationMessage.java # Extrae mensajes de validación HTML5 (required)
│   │   │   ├── LoginErrorMessage.java  # Lee alertas de error devueltas por el backend
│   │   │   └── LoginSuccessMessage.java# Verifica el mensaje del modal de bienvenida
│   │   ├── tasks/
│   │   │   ├── Login.java              # Acción de ingreso de credenciales y envío
│   │   │   └── NavigateLogin.java      # Apertura de la vista /login con espera explícita
│   │   └── utils/
│   │       ├── Parameters.java         # Constantes y rutas globales de evidencias
│   │       ├── PreAndProsCondition.java# Hooks @Before/@AfterStep/@After para contexto y capturas
│   │       └── ScreenShot.java         # Utilidad de guardado de imágenes PNG
│   └── test
│       ├── java/org/example
│       │   ├── stepDefinition/
│       │   │   ├── LoginStepDefinition.java # Conexión entre pasos Gherkin y Screenplay
│       │   │   └── ParameterDefinition.java # Configuración del Actor y OnlineCast
│       │   └── RunnerTest.java         # Punto de ejecución JUnit + CucumberWithSerenity
│       └── resources
│           ├── features/
│           │   └── login.feature       # Escenarios BDD (@smoke y @negative)
│           └── serenity.conf           # Configuración de WebDriver, esperas y ambientes
└── pom.xml                             # Gestión de dependencias y plugins Maven/Serenity
```

---

## 🧪 Cobertura de Escenarios de Prueba (`@LoginWeb`)

| ID / Tag | Tipo | Descripción del Escenario | Validación Principal |
| :--- | :--- | :--- | :--- |
| `@CP-001_ingresoValido` `@smoke` | Positivo | Inicio de sesión exitoso con credenciales de administrador válidas. | Visualización de modal SweetAlert2 (`"Acceso verificado correctamente"`) y redirección a `/admin`. |
| `@CP-002_ingresoInvalido` `@negative` | Negativo (`Scenario Outline`) | Intento de ingreso con contraseña incorrecta y con correo no registrado. | Despliegue del banner de error controlado (`"Las credenciales proporcionadas son incorrectas."`). |
| `@CP-003_camposVacios` `@negative` | Negativo | Envío del formulario con campos obligatorios en blanco. | Activación de validación nativa HTML5 (`validationMessage`) y permanencia en `/login`. |

---

## ⚙️ Configuración Previas

Antes de ejecutar la suite, asegúrate de configurar las credenciales de prueba en `src/test/resources/serenity.conf`:

```hocon
environments {
  default {
    webdriver.base.url = "https://staging.yuntaspublicidad.com/login/"
  }
}

credentials {
  admin {
    email = "admin@admin.com"
    password = "<TU_PASSWORD_DE_STAGING>"
  }
}
```

---

## 🚀 Comandos de Ejecución

### Ejecutar toda la suite y generar el reporte Serenity:
```bash
mvn clean verify
```

### Ejecutar únicamente la prueba de humo (`@smoke`):
```bash
mvn clean verify -Dcucumber.filter.tags="@smoke"
```

### Ejecutar únicamente los escenarios negativos (`@negative`):
```bash
mvn clean verify -Dcucumber.filter.tags="@negative"
```

### Ejecutar en modo Headless (sin interfaz gráfica):
```bash
mvn clean verify -Dheadless.mode=true
```

---

## 📊 Reportes y Evidencias

Al finalizar la ejecución con `BUILD SUCCESS`, los resultados se generan en dos ubicaciones:

1. **Reporte Interactivo de Serenity BDD:**
   * Ruta: `target/site/serenity/index.html`
   * Incluye estadísticas de cobertura, tiempos de ejecución, pasos ejecutados y capturas de pantalla automáticas por cada acción.
2. **Evidencias por Paso (`evidencias/`):**
   * Carpeta generada en la raíz del proyecto con subcarpetas por fecha y escenario conteniendo las capturas secuenciales (`paso_1`, `paso_2`, etc.).

---

## ✨ Buenas Prácticas Aplicadas

* **Cero `Thread.sleep()`:** Sincronización controlada mediante esperas explícitas (`WaitUntil`) y `fluentwait` de Serenity.
* **Locators resilientes:** Uso prioritario de `By.name`, `By.id` y selectores CSS semánticos aislados en la capa `pages`.
* **Aislamiento de estado:** Reinicio de sesión de navegador por escenario (`serenity.restart.browser.for.each = scenario`) para evitar contaminación de cookies entre casos.