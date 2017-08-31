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

	private static final String DUMP_HTTP_URL = "http://discogs-data.s3-us-west-2.amazonaws.com/";
	private static final String HREF_PATH = "ListBucketResult Contents Key";
	private static final Pattern MASTERS_FILE_NAME_PATTERN =
			Pattern.compile("data/\\d\\d\\d\\d/discogs_(\\d\\d\\d\\d\\d\\d\\d\\d)_masters\\.xml\\.gz");
	private static final SimpleDateFormat YYYYMMDD = new SimpleDateFormat("yyyyMMdd");

	public static String getLatestMastersXMLURL() throws IOException {
		Connection connection = Jsoup.connect(DUMP_HTTP_URL);
		connection.header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_2) AppleWebKit/537.36 (KHTML, " +
				"like Gecko) Chrome/39.0.2171.95 Safari/537.36");
		Document document = connection.get();
		Elements elements = document.select(HREF_PATH);

		String latestURL = null;
		Date latestDate = null;
		for (Element element : elements) {
			String url = element.text();

			Matcher matcher = MASTERS_FILE_NAME_PATTERN.matcher(url);
			while (matcher.find()) {
				String dateSt = matcher.group(1);

				try {
					Date date = YYYYMMDD.parse(dateSt);

					if(latestDate == null || latestDate.compareTo(date) < 0) {
						latestDate = date;
						latestURL = url;
					}

				} catch (ParseException e) {
					logger.warning("Cannot parse date:" + dateSt + ", url:" + url);
				}
			}
		}

		if(latestURL == null) {
			throw new IOException("Unable to find URL!");
		} else {
			logger.info("Latest masters file:" + latestURL);
			return DUMP_HTTP_URL + latestURL;
		}
	}
}
