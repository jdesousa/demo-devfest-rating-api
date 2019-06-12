package fr.leroymerlin.demodevfest.rating.api.service;

import fr.leroymerlin.demodevfest.rating.api.model.TvShowRating;
import fr.leroymerlin.demodevfest.rating.api.utils.CSVLineSplitOperator;
import fr.leroymerlin.demodevfest.rating.api.utils.CsvHelper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.stream.BaseStream;
import java.util.stream.StreamSupport;

@Service
public class CsvFileReader {

	public Flux<TvShowRating> readTvShowRating() {
		return readFile("series.ratings.tsv")
				   .map(line -> createTvShowRating(line));
	}

	private Flux<String[]> readFile(String fileName) {
		return Flux.using(() -> StreamSupport.stream(new CSVLineSplitOperator(CsvHelper.getReader(fileName)), false), Flux::fromStream,
			BaseStream::close);
	}

	private TvShowRating createTvShowRating(String[] line) {
		return TvShowRating.builder()
						   .tvShowId(line[0])
						   .averageRating(Float.valueOf(line[1]))
						   .numVotes(Integer.valueOf(line[2]))
						   .build();
	}
}
