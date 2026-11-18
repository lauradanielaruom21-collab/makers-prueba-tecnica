package co.makers.ui.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

/** Pasos del checkout: datos de envío, resumen de la orden y confirmación. */
public final class CheckoutPage {

    public static final Target FIRST_NAME = Target.the("campo nombre")
            .located(By.cssSelector("[data-test='firstName']"));
    public static final Target LAST_NAME = Target.the("campo apellido")
            .located(By.cssSelector("[data-test='lastName']"));
    public static final Target POSTAL_CODE = Target.the("campo código postal")
            .located(By.cssSelector("[data-test='postalCode']"));
    public static final Target CONTINUE_BUTTON = Target.the("botón Continue")
            .located(By.cssSelector("[data-test='continue']"));

    public static final Target ORDER_ITEM_NAMES = Target.the("productos del resumen de la orden")
            .located(By.cssSelector(".cart_item .inventory_item_name"));
    public static final Target SUBTOTAL = Target.the("subtotal de la orden")
            .located(By.cssSelector(".summary_subtotal_label"));
    public static final Target FINISH_BUTTON = Target.the("botón Finish")
            .located(By.cssSelector("[data-test='finish']"));

    public static final Target CONFIRMATION_HEADER = Target.the("mensaje de confirmación")
            .located(By.cssSelector(".complete-header"));

    private CheckoutPage() {
    }
}
