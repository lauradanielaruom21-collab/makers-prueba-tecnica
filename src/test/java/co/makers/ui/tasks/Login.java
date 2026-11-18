package co.makers.ui.tasks;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;

import java.util.ArrayList;
import java.util.List;

import static co.makers.ui.userinterfaces.LoginPage.LOGIN_BUTTON;
import static co.makers.ui.userinterfaces.LoginPage.PASSWORD;
import static co.makers.ui.userinterfaces.LoginPage.USERNAME;
import static net.serenitybdd.screenplay.Tasks.instrumented;

/**
 * Diligencia el formulario de login. Los campos vacíos no se escriben, para poder
 * probar la validación de campos obligatorios con la misma tarea.
 */
public class Login implements Task {

    private final String username;
    private final String password;

    public Login(String username, String password) {
        this.username = username == null ? "" : username;
        this.password = password == null ? "" : password;
    }

    public static Login withCredentials(String username, String password) {
        return instrumented(Login.class, username, password);
    }

    @Override
    @Step("{0} inicia sesión con el usuario '#username'")
    public <T extends Actor> void performAs(T actor) {
        List<Performable> steps = new ArrayList<>();
        if (!username.isEmpty()) {
            steps.add(Enter.theValue(username).into(USERNAME));
        }
        if (!password.isEmpty()) {
            steps.add(Enter.theValue(password).into(PASSWORD));
        }
        steps.add(Click.on(LOGIN_BUTTON));
        actor.attemptsTo(steps.toArray(new Performable[0]));
    }
}
