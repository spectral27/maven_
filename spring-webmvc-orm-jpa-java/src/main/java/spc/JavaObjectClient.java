package spc;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

public class JavaObjectClient {

    private final WebClient webClient = WebClient.create();

    public void insertObject(JavaObject javaObject) {
        Mono<JavaObject> javaObjectMono = Mono.just(javaObject);
        webClient.post()
                .uri("http://0.0.0.0:8080/objects")
                .contentType(MediaType.APPLICATION_JSON)
                .body(javaObjectMono, JavaObject.class)
                .retrieve()
                .bodyToMono(JavaObject.class)
                .block();
    }

    public List<JavaObject> selectObjects() {
        return webClient.get()
                .uri("http://0.0.0.0:8080/objects")
                .retrieve()
                .bodyToFlux(JavaObject.class)
                .collectList()
                .block();
    }

}
