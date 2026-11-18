package co.makers.ui.stepdefinitions;

import co.makers.ui.questions.TheLoginForm;
import co.makers.ui.tasks.Login;
import co.makers.ui.tasks.Logout;
import co.makers.ui.tasks.NavigateBack;
import co.makers.ui.tasks.OpenTheApplication;
import co.makers.ui.userinterfaces.InventoryPage;
import co.makers.ui.userinterfaces.LoginPage;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;
import static org.hamcrest.Matchers.equalTo;

public class LoginStepDefinitions {

    /** performance_glitch_user tarda varios segundos en cargar el inventario: se da un margen holgado. */
    private static final int MAX_PAGE_LOAD_SECONDS = 15;

    // ─────────────────────────────── Contexto (Dado) ───────────────────────────────

    @Dado("que {actor} está en la página de inicio de sesión")
    public void isOnTheLoginPage(Actor actor) {
        actor.wasAbleTo(OpenTheApplication.onTheLoginPage());
    }


    @Dado("que {actor} inició sesión con el usuario {string} y la contraseña {string}")
    public void isLoggedIn(Actor actor, String username, String password) {
        actor.wasAbleTo(Login.withCredentials(username, password));
        shouldSeeTheProductsPage();
    }

    // ─────────────────────────────── Acciones (Cuando) ───────────────────────────────

    @Cuando("{actor} inicia sesión con el usuario {string} y la contraseña {string}")
    public void logsIn(Actor actor, String username, String password) {
        actor.attemptsTo(Login.withCredentials(username, password));
    }


    @Cuando("{actor} intenta abrir directamente la ruta {string}")
    public void opensProtectedRoute(Actor actor, String path) {
        actor.attemptsTo(OpenTheApplication.directlyAt(path));
    }


    @Cuando("cierra sesión")
    public void logsOut() {
        theActorInTheSpotlight().attemptsTo(Logout.fromTheSideMenu());
    }

    // ─────────────────────────────── Validaciones (Entonces) ───────────────────────────────

    @Entonces("debería ver la página de productos")
    public void shouldSeeTheProductsPage() {
        theActorInTheSpotlight().attemptsTo(
                WaitUntil.the(InventoryPage.TITLE, isVisible()).forNoMoreThan(MAX_PAGE_LOAD_SECONDS).seconds(),
                Ensure.that(InventoryPage.TITLE).text().isEqualTo("Products"),
                Ensure.thatTheCurrentPage().currentUrl().endsWith(InventoryPage.PATH),
                Ensure.that(InventoryPage.CART_ICON).isDisplayed()
        );
    }

    @Entonces("el catálogo debería mostrar {int} productos")
    public void catalogShouldShowProducts(int expectedProducts) {
        theActorInTheSpotlight().should(
                seeThat(TheLoginForm.numberOfProductsDisplayed(), equalTo(expectedProducts))
        );
    }

    @Entonces("debería ver el mensaje de error {string}")
    public void shouldSeeTheErrorMessage(String expectedMessage) {
        theActorInTheSpotlight().attemptsTo(
                Ensure.that(LoginPage.ERROR_MESSAGE).isDisplayed(),
                Ensure.that(LoginPage.ERROR_MESSAGE).text().isEqualTo(expectedMessage)
        );
    }

    @Entonces("debería permanecer en la página de inicio de sesión")
    public void shouldStayOnTheLoginPage() {
        theActorInTheSpotlight().attemptsTo(
                Ensure.that(LoginPage.LOGIN_BUTTON).isDisplayed(),
                Ensure.that(InventoryPage.TITLE).isNotDisplayed()
        );
    }

    @Entonces("los campos de usuario y contraseña deberían quedar marcados con error")
    public void fieldsShouldBeMarkedWithError() {
        theActorInTheSpotlight().should(
                seeThat(TheLoginForm.fieldsMarkedWithError(), equalTo(2))
        );
    }



    @Entonces("al regresar con el botón atrás del navegador no debería ver el catálogo de productos")
    public void backButtonShouldNotRestoreTheSession() {
        Actor actor = theActorInTheSpotlight();
        actor.attemptsTo(NavigateBack.withTheBrowserButton());
        shouldStayOnTheLoginPage();
    }
}
