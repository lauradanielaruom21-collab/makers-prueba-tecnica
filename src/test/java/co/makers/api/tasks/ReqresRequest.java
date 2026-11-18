package co.makers.api.tasks;

import co.makers.utils.Config;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

/** Configuración común a todas las peticiones contra reqres.in (header de API key y content type). */
final class ReqresRequest {

    static final String API_KEY_HEADER = "x-api-key";

    private ReqresRequest() {
    }

    static RequestSpecification base(RequestSpecification request) {
        return request
                .header(API_KEY_HEADER, Config.reqresApiKey())
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);
    }
}
