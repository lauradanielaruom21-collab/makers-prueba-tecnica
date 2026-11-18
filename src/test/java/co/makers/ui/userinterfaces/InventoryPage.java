package co.makers.ui.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

/** Página de productos (inventario) a la que se llega tras un login exitoso. */
public final class InventoryPage {

    public static final String PATH = "/inventory.html";

    public static final Target TITLE = Target.the("título de la página")
            .located(By.cssSelector(".title"));
    public static final Target PRODUCTS = Target.the("productos del inventario")
            .located(By.cssSelector(".inventory_item"));
    public static final Target CART_ICON = Target.the("ícono del carrito")
            .located(By.cssSelector(".shopping_cart_link"));
    public static final Target CART_BADGE = Target.the("contador del carrito")
            .located(By.cssSelector(".shopping_cart_badge"));

    /** Botón "Add to cart" del producto cuyo nombre se recibe como parámetro. */
    public static final Target ADD_TO_CART_BUTTON = Target.the("botón agregar al carrito de '{0}'")
            .locatedBy("//div[contains(@class,'inventory_item_name') and normalize-space()='{0}']"
                    + "/ancestor::div[contains(@class,'inventory_item_description')]//button");

    private InventoryPage() {
    }
}
