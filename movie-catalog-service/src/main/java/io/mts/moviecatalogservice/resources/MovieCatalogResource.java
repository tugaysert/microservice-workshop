package io.mts.moviecatalogservice.resources;

import io.mts.moviecatalogservice.models.CatalogItem;
import io.mts.moviecatalogservice.models.Movie;
import io.mts.moviecatalogservice.models.Rating;
import io.mts.moviecatalogservice.models.UserRating;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    private final RestTemplate restTemplate;
    private final WebClient.Builder webClientBuilder;

    public MovieCatalogResource(RestTemplate restTemplate, WebClient.Builder webClientBuilder) {
        this.restTemplate = restTemplate;
        this.webClientBuilder = webClientBuilder;
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

        // 1. go RatingDataResource and get ratings by userId
        UserRating userRating = restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/ " + userId, UserRating.class);

        // 2. for each movie id, call MovieInfoResource and get details
        // 3. put them all together
        assert userRating != null;
        return userRating.getUserRating().stream()
                .map(rating -> {
                            Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
                          /*  Movie movie = webClientBuilder.build()
                                    .get()
                                    .uri("http://localhost:8082/movies/" + rating.getMovieId())
                                    .retrieve()
                                    .bodyToMono(Movie.class)
                                    .block();*/

                            return new CatalogItem(movie.getName(), "Desc", rating.getRating());
                        }
                )
                .collect(Collectors.toList());
    }
}
