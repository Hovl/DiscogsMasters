package ebs.discogs.data;

/**
 * Created by Aleksey Dubov
 * Date: 14/03/01
 * Time: 17:05
 * Copyright (c) 2014
 */
public class ReleasesPerYearGenre {
	private String genre;
	private Integer year;
	private Integer numberOfReleases;

	public ReleasesPerYearGenre(Integer year, String genre) {
		this.year = year;
		this.genre = genre;
		this.numberOfReleases = 0;
	}

	public Integer getYear() {
		return year;
	}

	public String getGenre() {
		return genre;
	}

	public Integer getNumberOfReleases() {
		return numberOfReleases;
	}

	public void incrementRelease() {
		numberOfReleases++;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ReleasesPerYearGenre that = (ReleasesPerYearGenre) o;

		return !(genre != null ? !genre.equals(that.genre) : that.genre != null) &&
				!(year != null ? !year.equals(that.year) : that.year != null);

	}

	@Override
	public int hashCode() {
		int result = year != null ? year.hashCode() : 0;
		result = 31 * result + (genre != null ? genre.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "ReleasesPerYearGenre{" +
				"year=" + year +
				", genre='" + genre + '\'' +
				", numberOfReleases=" + numberOfReleases +
				'}';
	}
}
