package fr.leroymerlin.demodevfest.rating.api.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Wither;

@Data
@Builder
@Wither
public class TvShowRating {
	private String tvShowId;
	private Float averageRating;
	private  Integer numVotes;

	public static TvShowRating NO_TV_SHOW_RATING = TvShowRating.builder()
															   .numVotes(0)
															   .averageRating(0f)
															   .build();
}
