package co.makers.runners;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.FILTER_TAGS_PROPERTY_NAME;

/**
 * UI: escenarios que pide el enunciado (login exitoso, contraseña incorrecta, campos obligatorios).
 * <p>
 * Desde IntelliJ: clic derecho → Run. Desde Maven: {@code mvn clean verify -Dit.test=RequeridosUiRunner}
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features/ui")
@ConfigurationParameter(key = FILTER_TAGS_PROPERTY_NAME, value = "@smoke")
public class RequeridosUiRunner {
}
