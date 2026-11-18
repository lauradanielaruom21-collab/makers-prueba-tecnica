package co.makers.ui.tasks;

import co.makers.ui.userinterfaces.LoginPage;
import co.makers.utils.Config;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.actions.Open;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class OpenTheApplication implements Task {

    private final String path;

    public OpenTheApplication(String path) {
        this.path = path;
    }

    /** Abre la pantalla de login. */
    public static OpenTheApplication onTheLoginPage() {
        return instrumented(OpenTheApplication.class, "");
    }

    /** Navega directamente a una ruta interna (p. ej. para pruebas de acceso sin sesión). */
    public static OpenTheApplication directlyAt(String path) {
        return instrumented(OpenTheApplication.class, path);
    }

    @Override
    @Step("{0} abre SauceDemo en la ruta '#path'")
    public <T extends Actor> void performAs(T actor) {
        if (path.isEmpty()) {
            actor.attemptsTo(Open.browserOn().the(LoginPage.class));
        } else {
            actor.attemptsTo(Open.url(Config.uiBaseUrl() + path));
        }
    }
}
