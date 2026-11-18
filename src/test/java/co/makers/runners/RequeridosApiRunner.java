package co.makers.runners;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.FILTER_TAGS_PROPERTY_NAME;

/**
 * API: flujo que pide el enunciado (POST /users y POST → GET). El GET falla a propósito por BUG-API-01.
 * <p>
 * Desde IntelliJ: clic derecho → Run. Desde Maven: {@code mvn clean verify -Dit.test=RequeridosApiRunner}
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features/api")
@ConfigurationParameter(key = FILTER_TAGS_PROPERTY_NAME, value = "@requerimiento")
public class RequeridosApiRunner {
}
