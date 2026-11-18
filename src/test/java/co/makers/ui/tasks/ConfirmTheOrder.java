package co.makers.ui.tasks;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;

import static co.makers.ui.userinterfaces.CheckoutPage.FINISH_BUTTON;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class ConfirmTheOrder implements Task {

    public static ConfirmTheOrder withTheFinishButton() {
        return instrumented(ConfirmTheOrder.class);
    }

    @Override
    @Step("{0} confirma la orden")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Click.on(FINISH_BUTTON));
    }
}
