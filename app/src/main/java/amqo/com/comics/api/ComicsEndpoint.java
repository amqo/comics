package amqo.com.comics.api;

import java.util.Map;

import amqo.com.comics.model.Comics;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface ComicsEndpoint {

    String BASE_API_URL = "http://gateway.marvel.com";

    @GET("/v1/public/characters/{characterId}/comics")
    Observable<Comics> getComics(
            @Path("characterId") String characterId,
            @QueryMap Map<String, String> parameters);
}
