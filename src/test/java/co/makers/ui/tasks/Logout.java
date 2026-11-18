package co.makers.ui.tasks;

import co.makers.ui.userinterfaces.SideMenu;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;

public class Logout implements Task {

    public static Logout fromTheSideMenu() {
        return instrumented(Logout.class);
    }

    @Override
    @Step("{0} cierra sesión desde el menú lateral")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(SideMenu.OPEN_MENU),
                // El menú tiene animación de apertura: se espera a que la opción sea clicable
                WaitUntil.the(SideMenu.LOGOUT, isClickable()).forNoMoreThan(5).seconds(),
                Click.on(SideMenu.LOGOUT)
        );
    }
}
