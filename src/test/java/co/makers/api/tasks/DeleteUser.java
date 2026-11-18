package co.makers.api.tasks;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Delete;

import static net.serenitybdd.screenplay.Tasks.instrumented;

/** DELETE /users/{id}. */
public class DeleteUser implements Task {

    private final String id;

    public DeleteUser(String id) {
        this.id = id;
    }

    public static DeleteUser withId(String id) {
        return instrumented(DeleteUser.class, id);
    }

    @Override
    @Step("{0} elimina el usuario con id #id")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Delete.from("/users/{id}").with(request -> ReqresRequest.base(request).pathParam("id", id))
        );
    }
}
