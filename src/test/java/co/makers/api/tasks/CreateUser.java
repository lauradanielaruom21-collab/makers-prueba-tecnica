package co.makers.api.tasks;

import co.makers.api.models.User;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;

import static net.serenitybdd.screenplay.Tasks.instrumented;

/** POST /users. Si la respuesta trae un id, el actor lo recuerda para encadenar la siguiente petición. */
public class CreateUser implements Task {

    public static final String CREATED_USER_ID = "createdUserId";

    private final User user;

    public CreateUser(User user) {
        this.user = user;
    }

    public static CreateUser withData(String name, String job) {
        return instrumented(CreateUser.class, new User(name, job));
    }

    @Override
    @Step("{0} crea el usuario #user mediante POST /users")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Post.to("/users").with(request -> ReqresRequest.base(request).body(user))
        );
        String id = SerenityRest.lastResponse().jsonPath().getString("id");
        if (id != null) {
            actor.remember(CREATED_USER_ID, id);
        }
    }
}
