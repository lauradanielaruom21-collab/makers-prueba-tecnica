package co.makers.utils;

/**
 * Configuración centralizada. Cada valor puede sobreescribirse con una propiedad de sistema
 * (-Dclave=valor) o una variable de entorno, útil para CI.
 */
public final class Config {

    private Config() {
    }

    public static String uiBaseUrl() {
        return value("webdriver.base.url", "UI_BASE_URL", "https://www.saucedemo.com");
    }

    public static String apiBaseUrl() {
        return value("restapi.baseurl", "API_BASE_URL", "https://reqres.in/api");
    }

    /** reqres.in exige el header x-api-key; la llave pública gratuita es el valor por defecto. */
    public static String reqresApiKey() {
        return value("reqres.api.key", "REQRES_API_KEY", "reqres-free-v1");
    }


    private static String value(String property, String envVar, String defaultValue) {
        String fromProperty = System.getProperty(property);
        if (fromProperty != null && !fromProperty.isBlank()) {
            return fromProperty;
        }
        String fromEnv = System.getenv(envVar);
        return (fromEnv != null && !fromEnv.isBlank()) ? fromEnv : defaultValue;
    }
}
