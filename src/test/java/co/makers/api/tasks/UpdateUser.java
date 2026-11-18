package co.makers.api.tasks;

import co.makers.api.models.User;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Put;

import static net.serenitybdd.screenplay.Tasks.instrumented;

/** PUT /users/{id}: reemplazo total del usuario. */
public class UpdateUser implements Task {

    private final String id;
    private final User user;

    public UpdateUser(String id, User user) {
        this.id = id;
        this.user = user;
    }

    public static UpdateUser completely(String id, String name, String job) {
        return instrumented(UpdateUser.class, id, new User(name, job));
    }

    @Override
    @Step("{0} actualiza el usuario #id con #user")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Put.to("/users/{id}")
                .with(request -> ReqresRequest.base(request).pathParam("id", id).body(user)));
    }
}
