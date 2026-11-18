package co.makers.ui.userinterfaces;

import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

/**
 * Página de inicio de sesión de SauceDemo.
 * Se priorizan los atributos data-test, que son los selectores más estables de la aplicación.
 */
@DefaultUrl("https://www.saucedemo.com/")
public class LoginPage extends PageObject {

    public static final Target USERNAME = Target.the("campo usuario")
            .located(By.cssSelector("[data-test='username']"));
    public static final Target PASSWORD = Target.the("campo contraseña")
            .located(By.cssSelector("[data-test='password']"));
    public static final Target LOGIN_BUTTON = Target.the("botón Login")
            .located(By.cssSelector("[data-test='login-button']"));
    public static final Target ERROR_MESSAGE = Target.the("mensaje de error")
            .located(By.cssSelector("[data-test='error']"));
    public static final Target CLOSE_ERROR_BUTTON = Target.the("botón cerrar error")
            .located(By.cssSelector(".error-button"));
    public static final Target FIELDS_WITH_ERROR = Target.the("campos marcados con error")
            .located(By.cssSelector("input.input_error.error"));
}
