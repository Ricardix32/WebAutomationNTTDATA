package org.example.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Attribute;
import org.example.pages.LoginPage;

public class FieldValidationMessage implements Question<String> {

    public static FieldValidationMessage inEmail() {
        return new FieldValidationMessage();
    }

    @Override
    public String answeredBy(Actor actor) {
        String mensaje = Attribute.of(LoginPage.INPUT_EMAIL)
                .named("validationMessage")
                .answeredBy(actor);
        return mensaje != null ? mensaje.trim() : "";
    }
}