package fr.leroymerlin.demodevfest.rating.api.repository;

import fr.leroymerlin.demodevfest.rating.api.model.TvShowRating;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface TvShowRatingRepository extends ReactiveMongoRepository<TvShowRating, String> {
	Flux<TvShowRating> findByTvShowIdIn(List<String> ids);

	Mono<TvShowRating> findByTvShowId(String id);
}
