package co.makers.ui.tasks;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;

import static co.makers.ui.userinterfaces.CartPage.CHECKOUT_BUTTON;
import static co.makers.ui.userinterfaces.CheckoutPage.CONTINUE_BUTTON;
import static co.makers.ui.userinterfaces.CheckoutPage.FIRST_NAME;
import static co.makers.ui.userinterfaces.CheckoutPage.LAST_NAME;
import static co.makers.ui.userinterfaces.CheckoutPage.POSTAL_CODE;
import static co.makers.ui.userinterfaces.InventoryPage.CART_ICON;
import static net.serenitybdd.screenplay.Tasks.instrumented;

/** Va al carrito, inicia el checkout y diligencia los datos de envío hasta llegar al resumen de la orden. */
public class Checkout implements Task {

    private final String firstName;
    private final String lastName;
    private final String postalCode;

    public Checkout(String firstName, String lastName, String postalCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.postalCode = postalCode;
    }

    public static Checkout withShippingInformation(String firstName, String lastName, String postalCode) {
        return instrumented(Checkout.class, firstName, lastName, postalCode);
    }

    @Override
    @Step("{0} diligencia los datos de envío de #firstName #lastName (#postalCode)")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(CART_ICON),
                Click.on(CHECKOUT_BUTTON),
                Enter.theValue(firstName).into(FIRST_NAME),
                Enter.theValue(lastName).into(LAST_NAME),
                Enter.theValue(postalCode).into(POSTAL_CODE),
                Click.on(CONTINUE_BUTTON)
        );
    }
}
