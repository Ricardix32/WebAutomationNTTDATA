package org.example.stepDefinition;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actions.Click;
import net.thucydides.model.util.EnvironmentVariables;
import org.example.pages.LoginPage;
import org.example.questions.LoginErrorMessage;
import org.example.questions.LoginSuccessMessage;
import org.example.questions.FieldValidationMessage;
import org.example.questions.CurrentUrl;
import org.example.tasks.Login;
import org.example.tasks.NavigateLogin;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class LoginStepDefinition {

    private EnvironmentVariables environmentVariables;

    private String getProperty(String key) {
        return EnvironmentSpecificConfiguration.from(environmentVariables).getProperty(key);
    }

    @Given("el {actor} abre la pagina de login")
    public void elUsuarioAbreLaPaginaDeLogin(Actor actor) {
        actor.attemptsTo(NavigateLogin.toUrl(getProperty("webdriver.base.url")));
    }

    @When("inicia sesion con credenciales validas de administrador")
    public void iniciaSesionConCredencialesValidasDeAdministrador() {
        String email = getProperty("credentials.admin.email");
        String password = getProperty("credentials.admin.password");
        theActorInTheSpotlight().attemptsTo(Login.withCredentials(email, password));
    }

    @When("inicia sesion con {string} y {string}")
    public void iniciaSesionConY(String usuario, String password) {
        theActorInTheSpotlight().attemptsTo(Login.withCredentials(usuario, password));
    }

    @Then("visualiza el mensaje de exito {string}")
    public void visualizaElMensajeDeExito(String mensajeEsperado) {
        theActorInTheSpotlight().should(
                seeThat("El mensaje del modal de bienvenida",
                        LoginSuccessMessage.text(),
                        equalTo(mensajeEsperado))
        );
    }

    @Then("visualiza el mensaje de error {string}")
    public void visualizaElMensajeDeError(String mensajeEsperado) {
        theActorInTheSpotlight().should(
                seeThat("El mensaje de error del formulario",
                        LoginErrorMessage.text(),
                        containsString(mensajeEsperado))
        );
    }

    @Then("el formulario muestra una validacion nativa en el campo de correo")
    public void elFormularioMuestraUnaValidacionNativaEnElCampoDeCorreo() {
        theActorInTheSpotlight().should(
                seeThat("La validacion HTML5 del campo email",
                        FieldValidationMessage.inEmail(),
                        not(emptyString()))
        );
    }

    @And("la URL actual contiene {string}")
    public void laUrlActualContiene(String rutaEsperada) {
        theActorInTheSpotlight().should(
                seeThat("La URL actual del navegador",
                        CurrentUrl.ofBrowser(),
                        containsString(rutaEsperada))
        );
    }
}