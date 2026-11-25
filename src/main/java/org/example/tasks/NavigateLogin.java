package org.example.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.example.pages.LoginPage;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class NavigateLogin implements Task {

    private final String url;

    public NavigateLogin(String url) {
        this.url = url;
    }

    public static NavigateLogin toUrl(String url) {
        return instrumented(NavigateLogin.class, url);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Open.url(url),
                WaitUntil.the(LoginPage.INPUT_EMAIL, isVisible()).forNoMoreThan(10).seconds()
        );
    }
}