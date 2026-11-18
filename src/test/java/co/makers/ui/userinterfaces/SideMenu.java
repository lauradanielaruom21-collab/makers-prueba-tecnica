package co.makers.ui.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

/** Menú lateral (hamburguesa). */
public final class SideMenu {

    public static final Target OPEN_MENU = Target.the("botón abrir menú")
            .located(By.id("react-burger-menu-btn"));
    public static final Target LOGOUT = Target.the("opción Logout")
            .located(By.id("logout_sidebar_link"));

    private SideMenu() {
    }
}
