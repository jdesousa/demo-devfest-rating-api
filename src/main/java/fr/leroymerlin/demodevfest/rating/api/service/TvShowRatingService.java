package fr.leroymerlin.demodevfest.rating.api.service;

import fr.leroymerlin.demodevfest.rating.api.model.TvShowRating;
import fr.leroymerlin.demodevfest.rating.api.repository.TvShowRatingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TvShowRatingService {

	private TvShowRatingRepository tvShowRatingRepository;
	private CsvFileReader csvFileReader;

	public Flux<TvShowRating> saveAll() {
		return csvFileReader.readTvShowRating()
							.buffer(100)
							.flatMap(tvShows -> tvShowRatingRepository.saveAll(tvShows), 50, 100);
	}

	public Flux<TvShowRating> getAll() {
		return tvShowRatingRepository.findAll();
	}

	public Flux<TvShowRating> findByIds(List<String> tvShowIds) {
		return tvShowRatingRepository.findByTvShowIdIn(tvShowIds)
									 .collectList()
									 .flatMapMany(tvShowRatings -> addMissingTvShowRating(tvShowRatings, tvShowIds));
	}

	private Flux<TvShowRating> addMissingTvShowRating(List<TvShowRating> tvShowRatings, List<String> tvShowIds) {
		List<String> ratingTvShowIds = tvShowRatings.stream()
													.map(TvShowRating::getTvShowId)
													.collect(Collectors.toList());

		tvShowIds.stream()
				 .forEach(tvShowId -> {
					 if (!ratingTvShowIds.contains(tvShowId)) {
						 tvShowRatings.add(TvShowRating.NO_TV_SHOW_RATING.withTvShowId(tvShowId));
					 }
				 });
		return Flux.fromIterable(tvShowRatings);
	}

	public Mono<TvShowRating> findById(String id) {
		return tvShowRatingRepository.findByTvShowId(id)
									 .defaultIfEmpty(TvShowRating.NO_TV_SHOW_RATING.withTvShowId(id));
	}
}
