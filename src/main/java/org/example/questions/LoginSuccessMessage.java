package org.example.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.example.pages.LoginPage;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class LoginSuccessMessage implements Question<String> {

    public static LoginSuccessMessage text() {
        return new LoginSuccessMessage();
    }

    @Override
    public String answeredBy(Actor actor) {
        actor.attemptsTo(
                WaitUntil.the(LoginPage.LBL_EXITO_MODAL, isVisible())
                        .forNoMoreThan(15).seconds()
        );
        return Text.of(LoginPage.LBL_EXITO_MODAL).answeredBy(actor).trim();
    }
}