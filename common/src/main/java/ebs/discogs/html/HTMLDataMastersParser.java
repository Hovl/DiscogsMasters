package ebs.discogs.html;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

	public static final String DUMP_HTTP_URL = "http://www.discogs.com/data/";
	public static final String HREF_PATH = "html body div.list table tbody tr td.n a[href]";
	public static final Pattern MASTERS_FILE_NAME_PATTERN =
			Pattern.compile("discogs_(\\d\\d\\d\\d\\d\\d\\d\\d)_masters\\.xml\\.gz");
	public static final SimpleDateFormat YYYYMMDD = new SimpleDateFormat("yyyyMMdd");

	public static String getLatestMastersXMLURL(Date currentMastersDate) throws IOException {
		Connection connection = Jsoup.connect(DUMP_HTTP_URL);
		connection.header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_2) AppleWebKit/537.36 (KHTML, " +
				"like Gecko) Chrome/39.0.2171.95 Safari/537.36");
		Document document = connection.get();
		Elements elements = document.select(HREF_PATH);

		String latestURL = null;
		Date latestDate = currentMastersDate;
		for (Element element : elements) {
			String url = element.attr("href");

			Matcher matcher = MASTERS_FILE_NAME_PATTERN.matcher(url);
			while (matcher.find()) {
				String dateSt = matcher.group(1);

				try {
					Date date = YYYYMMDD.parse(dateSt);

					if(latestDate == null || latestDate.compareTo(date) > 0) {
						latestDate = date;
						latestURL = url;
					}

				} catch (ParseException e) {
					logger.warning("Cannot parse date:" + dateSt + ", url:" + url);
				}
			}
		}

		if(latestURL == null) {
			return null;
		} else {
			logger.info("Latest masters file:" + latestURL);
			return DUMP_HTTP_URL + latestURL;
		}
	}
}
