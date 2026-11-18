package co.makers.ui.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

/** Página del carrito de compras. */
public final class CartPage {

    public static final Target CHECKOUT_BUTTON = Target.the("botón Checkout")
            .located(By.cssSelector("[data-test='checkout']"));

    private CartPage() {
    }
}
