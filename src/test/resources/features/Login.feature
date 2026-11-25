@LoginWeb
Feature: Autenticacion de usuarios en Yuntas Publicidad
  Como administrador de la plataforma Yuntas
  Quiero iniciar sesion desde el portal web
  Para acceder al panel administrativo de forma segura

  @CP-001_ingresoValido @smoke
  Scenario: Usuario administrador ingresa correctamente
    Given el usuario abre la pagina de login
    When inicia sesion con credenciales validas de administrador
    Then visualiza el mensaje de exito "Acceso verificado correctamente"
    And la URL actual contiene "/admin"

  @CP-002_ingresoInvalido @negative
  Scenario Outline: Login no permitido con credenciales incorrectas o inexistentes
    Given el usuario abre la pagina de login
    When inicia sesion con "<usuario>" y "<password>"
    Then visualiza el mensaje de error "<mensaje>"

    Examples:
      | usuario            | password         | mensaje                                          |
      | admin@admin.com    | clave_errada_123 | Las credenciales proporcionadas son incorrectas. |
      | noexiste@admin.com | password         | Las credenciales proporcionadas son incorrectas. |

  @CP-003_camposVacios @negative
  Scenario: Login bloqueado cuando los campos obligatorios estan vacios
    Given el usuario abre la pagina de login
    When inicia sesion con "" y ""
    Then el formulario muestra una validacion nativa en el campo de correo
    And la URL actual contiene "/login"