package org.example.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.example.pages.LoginPage;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class Login implements Task {

    private final String email;
    private final String password;

    public Login(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static Login withCredentials(String email, String password) {
        return instrumented(Login.class, email, password);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(LoginPage.INPUT_EMAIL, isVisible()).forNoMoreThan(10).seconds(),
                Enter.theValue(email).into(LoginPage.INPUT_EMAIL),
                Enter.theValue(password).into(LoginPage.INPUT_PASSWORD),
                WaitUntil.the(LoginPage.BTN_INGRESAR, isClickable()).forNoMoreThan(10).seconds(),
                Click.on(LoginPage.BTN_INGRESAR)
        );
    }
}