package co.makers.ui.stepdefinitions;

import co.makers.ui.tasks.AddProductsToCart;
import co.makers.ui.tasks.Checkout;
import co.makers.ui.tasks.ConfirmTheOrder;
import co.makers.ui.userinterfaces.CheckoutPage;
import co.makers.ui.userinterfaces.InventoryPage;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.questions.Text;

import java.util.List;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

/** Pasos del flujo E2E de compra: catálogo → carrito → checkout → confirmación. */
public class PurchaseStepDefinitions {

    private static final String SELECTED_PRODUCTS = "selectedProducts";

    @Cuando("agrega al carrito los productos:")
    public void addsProductsToTheCart(List<String> products) {
        Actor actor = theActorInTheSpotlight();
        actor.remember(SELECTED_PRODUCTS, products);
        actor.attemptsTo(AddProductsToCart.named(products));
    }

    @Entonces("el carrito debería indicar {int} productos")
    public void cartShouldIndicate(int expectedProducts) {
        theActorInTheSpotlight().attemptsTo(
                Ensure.that(InventoryPage.CART_BADGE).text().isEqualTo(String.valueOf(expectedProducts))
        );
    }

    @Cuando("diligencia sus datos de envío con nombre {string}, apellido {string} y código postal {string}")
    public void fillsShippingInformation(String firstName, String lastName, String postalCode) {
        theActorInTheSpotlight().attemptsTo(Checkout.withShippingInformation(firstName, lastName, postalCode));
    }

    @Entonces("el resumen de la compra debería listar los productos seleccionados")
    public void orderSummaryShouldListTheSelectedProducts() {
        Actor actor = theActorInTheSpotlight();
        List<String> selected = actor.recall(SELECTED_PRODUCTS);
        actor.attemptsTo(
                Ensure.that(Text.ofEach(CheckoutPage.ORDER_ITEM_NAMES)).containsExactlyInAnyOrderElementsFrom(selected)
        );
    }

    @Entonces("el subtotal debería ser {string}")
    public void subtotalShouldBe(String expectedSubtotal) {
        theActorInTheSpotlight().attemptsTo(
                Ensure.that(CheckoutPage.SUBTOTAL).text().isEqualTo("Item total: " + expectedSubtotal)
        );
    }

    @Cuando("confirma la compra")
    public void confirmsThePurchase() {
        theActorInTheSpotlight().attemptsTo(ConfirmTheOrder.withTheFinishButton());
    }

    @Entonces("debería ver el mensaje de confirmación {string}")
    public void shouldSeeTheConfirmationMessage(String expectedMessage) {
        theActorInTheSpotlight().attemptsTo(
                Ensure.that(CheckoutPage.CONFIRMATION_HEADER).text().isEqualTo(expectedMessage)
        );
    }

    @Entonces("el carrito debería quedar vacío")
    public void cartShouldBeEmpty() {
        theActorInTheSpotlight().attemptsTo(
                Ensure.that(InventoryPage.CART_BADGE).isNotDisplayed()
        );
    }
}
