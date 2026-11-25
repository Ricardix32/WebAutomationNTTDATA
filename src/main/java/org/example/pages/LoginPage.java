package org.example.pages;

import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.By;

@DefaultUrl("https://www.demoblaze.com/")
public class LoginPage extends PageObject {
    public static final Target INPUT_EMAIL = Target.the("campo de correo electronico")
            .located(By.name("email"));

    public static final Target INPUT_PASSWORD = Target.the("campo de contrasena")
            .located(By.name("password"));

    public static final Target BTN_INGRESAR = Target.the("boton Ingresar")
            .located(By.cssSelector("button[type='submit']"));

    public static final Target LBL_ERROR_BACKEND = Target.the("banner de error de autenticacion")
            .located(By.xpath("//div[contains(@class,'bg-red-50') and contains(@class,'text-red-700')]"));

    public static final Target LBL_EXITO_MODAL = Target.the("mensaje de acceso verificado en modal")
            .located(By.id("swal2-html-container"));

    public static final Target BTN_IR_AL_PANEL = Target.the("boton Ir al panel del modal")
            .located(By.cssSelector("button.swal2-confirm"));
}
