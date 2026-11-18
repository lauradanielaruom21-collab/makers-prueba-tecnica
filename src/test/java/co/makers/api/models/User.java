package co.makers.api.models;

/** Cuerpo de la petición para crear / actualizar un usuario en reqres. */
public class User {

    private final String name;
    private final String job;

    public User(String name, String job) {
        this.name = name;
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }

    @Override
    public String toString() {
        return "{name='" + name + "', job='" + job + "'}";
    }
}
