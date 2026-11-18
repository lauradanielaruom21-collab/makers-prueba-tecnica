package co.makers.ui.tasks;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class NavigateBack implements Task {

    public static NavigateBack withTheBrowserButton() {
        return instrumented(NavigateBack.class);
    }

    @Override
    @Step("{0} presiona el botón 'atrás' del navegador")
    public <T extends Actor> void performAs(T actor) {
        BrowseTheWeb.as(actor).getDriver().navigate().back();
    }
}
