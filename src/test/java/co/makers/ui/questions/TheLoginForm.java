package co.makers.ui.questions;

import co.makers.ui.userinterfaces.InventoryPage;
import co.makers.ui.userinterfaces.LoginPage;
import net.serenitybdd.screenplay.Question;

/** Preguntas sobre el estado de la interfaz que no cubre directamente Ensure. */
public final class TheLoginForm {

    private TheLoginForm() {
    }

    /** Cantidad de campos del formulario resaltados con el estilo de error. */
    public static Question<Integer> fieldsMarkedWithError() {
        return Question.about("la cantidad de campos marcados con error")
                .answeredBy(actor -> LoginPage.FIELDS_WITH_ERROR.resolveAllFor(actor).size());
    }

    /** Cantidad de productos listados en el inventario. */
    public static Question<Integer> numberOfProductsDisplayed() {
        return Question.about("la cantidad de productos del catálogo")
                .answeredBy(actor -> InventoryPage.PRODUCTS.resolveAllFor(actor).size());
    }
}
