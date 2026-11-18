package co.makers.ui.tasks;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;

import java.util.ArrayList;
import java.util.List;

import static co.makers.ui.userinterfaces.InventoryPage.ADD_TO_CART_BUTTON;
import static net.serenitybdd.screenplay.Tasks.instrumented;

/** Agrega al carrito, desde el catálogo, los productos indicados por su nombre visible. */
public class AddProductsToCart implements Task {

    private final List<String> products;

    public AddProductsToCart(List<String> products) {
        this.products = new ArrayList<>(products);
    }

    public static AddProductsToCart named(List<String> products) {
        return instrumented(AddProductsToCart.class, products);
    }

    @Override
    @Step("{0} agrega al carrito los productos #products")
    public <T extends Actor> void performAs(T actor) {
        products.forEach(product -> actor.attemptsTo(Click.on(ADD_TO_CART_BUTTON.of(product))));
    }
}
