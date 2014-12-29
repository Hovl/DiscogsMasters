package ebs.discogs.data;

/**
 * Created by Aleksey Dubov
 * Date: 14/03/01
 * Time: 18:22
 * Copyright (c) 2014
 */
public class ReleasesPerYear {
	private Integer year;
	private Integer numberOfReleases;

	public ReleasesPerYear(Integer year) {
		this.year = year;
		this.numberOfReleases = 0;
	}

	public Integer getYear() {
		return year;
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

		ReleasesPerYear that = (ReleasesPerYear) o;

		return !(year != null ? !year.equals(that.year) : that.year != null);

	}

	@Override
	public int hashCode() {
		return year != null ? year.hashCode() : 0;
	}

	@Override
	public String toString() {
		return "ReleasesPerYear{" +
				"year=" + year +
				", numberOfReleases=" + numberOfReleases +
				'}';
	}
}
