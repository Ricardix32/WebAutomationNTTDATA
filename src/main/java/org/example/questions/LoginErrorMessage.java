package org.example.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.example.pages.LoginPage;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class LoginErrorMessage implements Question<String> {

    public static LoginErrorMessage text() {
        return new LoginErrorMessage();
    }

    @Override
    public String answeredBy(Actor actor) {
        actor.attemptsTo(
                WaitUntil.the(LoginPage.LBL_ERROR_BACKEND, isVisible())
                        .forNoMoreThan(10).seconds()
        );
        return Text.of(LoginPage.LBL_ERROR_BACKEND).answeredBy(actor).trim();
    }
}