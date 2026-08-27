package ebs.discogs.html;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Aleksey Dubov
 * Date: 15/01/10
 * Time: 15:12
 * Copyright (c) 2015
 */
public class HTMLDataMastersParser {

	private static Logger logger = Logger.getLogger(HTMLDataMastersParser.class.getName());

	private static final String DUMP_BASE_URL = "https://data.discogs.com/";
	private static final Pattern MASTERS_FILE_NAME_PATTERN =
			Pattern.compile("discogs_(\\d{8})_masters\\.xml\\.gz");
	private static final SimpleDateFormat YYYYMMDD = new SimpleDateFormat("yyyyMMdd");

	public static String getLatestMastersXMLURL() throws IOException {
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);

		// Try current year, then previous year
		for (int year = currentYear; year >= currentYear - 1; year--) {
			String listUrl = DUMP_BASE_URL + "?prefix=data/" + year + "/";
			logger.info("Checking for masters dumps at: " + listUrl);

			try {
				Connection connection = Jsoup.connect(listUrl);
				connection.header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36");
				connection.timeout(30000);
				Document document = connection.get();

				String latestURL = null;
				Date latestDate = null;
				Elements links = document.select("a[href*=masters]");
				logger.info("Found " + links.size() + " masters links");

				for (Element link : links) {
					String text = link.text();
					logger.info("Checking link text: " + text);

					Matcher matcher = MASTERS_FILE_NAME_PATTERN.matcher(text);
					if (matcher.find()) {
						String dateSt = matcher.group(1);
						try {
							Date date = YYYYMMDD.parse(dateSt);
							if (latestDate == null || latestDate.compareTo(date) < 0) {
								latestDate = date;
								latestURL = DUMP_BASE_URL + "?download=data/" + year + "/" + matcher.group(0);
							}
						} catch (ParseException e) {
							logger.warning("Cannot parse date:" + dateSt);
						}
					}
				}

				if (latestURL != null) {
					logger.info("Latest masters file:" + latestURL);
					return latestURL;
				}
			} catch (IOException e) {
				logger.warning("Failed to fetch " + listUrl + ": " + e.getMessage());
			}
		}

		// Fallback: use a known URL with ?download= format
		String fallback = DUMP_BASE_URL + "?download=data/2026/discogs_20260801_masters.xml.gz";
		logger.warning("Using fallback URL: " + fallback);
		return fallback;
	}
}
