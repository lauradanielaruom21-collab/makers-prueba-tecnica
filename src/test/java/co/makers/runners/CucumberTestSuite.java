package co.makers.runners;

import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

/**
 * Punto de entrada único de la suite (UI + API).
 * <p>
 * El filtro de tags se controla desde Maven (propiedad {@code tags} o perfiles -Psmoke, -Pui, -Papi, -Pdefectos)
 * y llega a Cucumber como {@code cucumber.filter.tags}. Plugins y glue: ver junit-platform.properties.
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
public class CucumberTestSuite {
}
