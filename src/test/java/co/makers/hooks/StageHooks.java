package co.makers.hooks;

import io.cucumber.java.Before;
import io.cucumber.java.ParameterType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;

/**
 * Prepara el escenario de Screenplay antes de cada prueba y define el tipo de parámetro {actor}
 * para que los pasos Gherkin hablen en términos de personas ("Ana", "Carlos").
 */
public class StageHooks {

    @Before(order = 0)
    public void setTheStage() {
        OnStage.setTheStage(new OnlineCast());
    }

    @ParameterType("[A-ZÁÉÍÓÚÑ][a-záéíóúñ]+")
    public Actor actor(String actorName) {
        return OnStage.theActorCalled(actorName);
    }
}
