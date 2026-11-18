package co.makers.api.stepdefinitions;

import co.makers.api.tasks.ConsultUser;
import co.makers.api.tasks.CreateUser;
import co.makers.api.tasks.DeleteUser;
import co.makers.api.tasks.UpdateUser;
import co.makers.utils.Config;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;

import java.time.Duration;
import java.time.Instant;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.emptyOrNullString;

public class UsersApiStepDefinitions {

    /** Tolerancia para comparar la fecha del servidor con el reloj local (desfase de relojes / latencia). */
    private static final Duration CLOCK_TOLERANCE = Duration.ofMinutes(5);

    // ─────────────────────────────── Contexto (Dado) ───────────────────────────────

    @Dado("que {actor} tiene acceso a la API de usuarios de reqres")
    public void hasAccessToTheApi(Actor actor) {
        actor.whoCan(CallAnApi.at(Config.apiBaseUrl()));
    }

    @Dado("que {actor} creó un usuario con nombre {string} y trabajo {string}")
    public void createdAUser(Actor actor, String name, String job) {
        actor.wasAbleTo(CreateUser.withData(name, job));
        actor.should(seeThatResponse("el usuario fue creado", response -> response.statusCode(201)));
        assertThat((String) actor.recall(CreateUser.CREATED_USER_ID))
                .as("id del usuario recién creado")
                .isNotBlank();
    }

    // ─────────────────────────────── Acciones (Cuando) ───────────────────────────────

    @Cuando("{actor} crea un usuario con nombre {string} y trabajo {string}")
    public void createsAUser(Actor actor, String name, String job) {
        actor.attemptsTo(CreateUser.withData(name, job));
    }


    @Cuando("consulta el usuario por el id obtenido en la creación")
    public void consultsTheCreatedUser() {
        Actor actor = theActorInTheSpotlight();
        String createdId = actor.recall(CreateUser.CREATED_USER_ID);
        actor.attemptsTo(ConsultUser.withId(createdId));
    }

    @Cuando("{actor} consulta el usuario con id {string}")
    public void consultsAUser(Actor actor, String id) {
        actor.attemptsTo(ConsultUser.withId(id));
    }


    @Cuando("{actor} actualiza completamente el usuario {string} con nombre {string} y trabajo {string}")
    public void updatesUserCompletely(Actor actor, String id, String name, String job) {
        actor.attemptsTo(UpdateUser.completely(id, name, job));
    }


    @Cuando("{actor} elimina el usuario {string}")
    public void deletesUser(Actor actor, String id) {
        actor.attemptsTo(DeleteUser.withId(id));
    }


    // ─────────────────────────────── Validaciones (Entonces) ───────────────────────────────

    @Entonces("el código de respuesta debería ser {int}")
    public void statusCodeShouldBe(int expectedStatus) {
        theActorInTheSpotlight().should(
                seeThatResponse("el código HTTP es " + expectedStatus,
                        response -> response.statusCode(expectedStatus))
        );
    }

    @Entonces("la respuesta debería contener el nombre {string} y el trabajo {string}")
    public void responseShouldContainNameAndJob(String name, String job) {
        theActorInTheSpotlight().should(
                seeThatResponse("el cuerpo devuelve los datos enviados",
                        response -> response.body("name", equalTo(name)).body("job", equalTo(job)))
        );
    }

    @Entonces("la respuesta debería incluir el id del usuario creado")
    public void responseShouldIncludeTheId() {
        theActorInTheSpotlight().should(
                seeThatResponse("la respuesta incluye un id",
                        response -> response.body("id", not(emptyOrNullString())))
        );
    }

    @Entonces("la fecha de creación debería corresponder al momento actual")
    public void creationDateShouldBeNow() {
        assertTimestampIsRecent("createdAt");
    }

    @Entonces("la respuesta debería incluir la fecha de actualización")
    public void responseShouldIncludeUpdateDate() {
        assertTimestampIsRecent("updatedAt");
    }

    @Entonces("la respuesta debería cumplir el esquema {string}")
    public void responseShouldMatchSchema(String schemaPath) {
        theActorInTheSpotlight().should(
                seeThatResponse("la respuesta cumple el contrato " + schemaPath,
                        response -> response.body(matchesJsonSchemaInClasspath(schemaPath)))
        );
    }

    @Entonces("la respuesta debería ser de tipo JSON")
    public void responseShouldBeJson() {
        theActorInTheSpotlight().should(
                seeThatResponse("el Content-Type es JSON",
                        response -> response.contentType(containsString("application/json")))
        );
    }


    @Entonces("el usuario consultado debería tener el nombre {string} y el trabajo {string}")
    public void consultedUserShouldHave(String name, String job) {
        JsonPath body = SerenityRest.lastResponse().jsonPath();
        // El contrato de GET puede envolver el recurso en "data"; se acepta cualquiera de las dos formas.
        assertThat(firstNonNull(body.getString("name"), body.getString("data.name")))
                .as("nombre del usuario consultado").isEqualTo(name);
        assertThat(firstNonNull(body.getString("job"), body.getString("data.job")))
                .as("trabajo del usuario consultado").isEqualTo(job);
    }

    @Entonces("el usuario consultado debería tener id {int} y correo {string}")
    public void consultedUserShouldHaveIdAndEmail(int id, String email) {
        theActorInTheSpotlight().should(
                seeThatResponse("el usuario consultado es el esperado",
                        response -> response.body("data.id", equalTo(id)).body("data.email", equalTo(email)))
        );
    }

    @Entonces("el cuerpo de la respuesta debería estar vacío")
    public void bodyShouldBeEmpty() {
        String body = SerenityRest.lastResponse().asString().trim();
        assertThat(body).as("cuerpo de la respuesta").isIn("", "{}");
    }




    // ─────────────────────────────── Utilidades ───────────────────────────────

    private static void assertTimestampIsRecent(String field) {
        Response response = SerenityRest.lastResponse();
        String value = response.jsonPath().getString(field);
        assertThat(value).as(field).isNotBlank();

        Instant timestamp = Instant.parse(value);
        Instant now = Instant.now();
        assertThat(timestamp).as(field + " debe ser una fecha ISO-8601 cercana al momento de la petición")
                .isBetween(now.minus(CLOCK_TOLERANCE), now.plus(CLOCK_TOLERANCE));
    }

    private static String firstNonNull(String first, String second) {
        return first != null ? first : second;
    }
}
