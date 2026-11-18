package co.makers.api.tasks;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;

import static net.serenitybdd.screenplay.Tasks.instrumented;

/** GET /users/{id}. */
public class ConsultUser implements Task {

    private final String id;

    public ConsultUser(String id) {
        this.id = id;
    }

    public static ConsultUser withId(String id) {
        return instrumented(ConsultUser.class, id);
    }

    @Override
    @Step("{0} consulta el usuario con id #id mediante GET /users/{id}")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Get.resource("/users/{id}").with(request -> ReqresRequest.base(request).pathParam("id", id))
        );
    }
}
