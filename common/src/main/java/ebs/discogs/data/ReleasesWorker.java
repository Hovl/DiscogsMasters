package ebs.discogs.data;

import com.google.gson.JsonElement;
import ebs.json.JSONWorker;

import java.util.*;
import java.util.logging.Logger;

/**
 * Created by Aleksey Dubov
 * Date: 14/03/01
 * Time: 17:21
 * Copyright (c) 2014
 */
public class ReleasesWorker {
	private static Logger logger = Logger.getLogger(ReleasesWorker.class.getName());

	private List<ReleasesPerYearGenre> dataMap = new ArrayList<ReleasesPerYearGenre>(100);

	private Set<Integer> years = new HashSet<Integer>(100);

	public void incrementRelease(String genre, Integer year) {
		if(genre == null || year == null) {
			return;
		}

		ReleasesPerYearGenre releasesPerYearGenreTemp = new ReleasesPerYearGenre(year, genre);

		if (!dataMap.contains(releasesPerYearGenreTemp)) {
			dataMap.add(releasesPerYearGenreTemp);
		}

		ReleasesPerYearGenre releasesPerYearGenre = dataMap.get(dataMap.indexOf(releasesPerYearGenreTemp));

		releasesPerYearGenre.incrementRelease();

		years.add(year);
	}

	public JsonElement transformResultsToJsonElement() {
		Set<String> genres = new HashSet<String>(100);
		for (ReleasesPerYearGenre releasesPerYearGenre : dataMap) {
			genres.add(releasesPerYearGenre.getGenre());
		}

		List<List<?>> rows = new ArrayList<List<?>>(years.size() + 1);

		List<String> firstRow = new ArrayList<String>(genres.size() + 1);
		firstRow.add("Month");
		firstRow.addAll(genres);

		rows.add(0, firstRow);

		List<Integer> yearz = new ArrayList<Integer>(years);
		Collections.sort(yearz);

		for (int i = 1; i < yearz.size(); i++) {
			Integer year = yearz.get(i);

			List<Object> row = new ArrayList<Object>(firstRow.size());

			row.add(0, year.toString());

			for (int j = 1; j < firstRow.size(); j++) {
				Integer number = getNumberOfReleases(firstRow.get(j), year);

				row.add(j, number == null ? 0 : number);
			}

			rows.add(i, row);
		}

		return JSONWorker.convertToJsonElement(rows);
	}

	private Integer getNumberOfReleases(String genre, Integer year) {
		ReleasesPerYearGenre releasesPerYearGenreTemp = new ReleasesPerYearGenre(year, genre);

		if (!dataMap.contains(releasesPerYearGenreTemp)) {
			return null;
		}

		return dataMap.get(dataMap.indexOf(releasesPerYearGenreTemp)).getNumberOfReleases();
	}
}
