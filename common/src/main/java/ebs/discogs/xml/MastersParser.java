package ebs.discogs.xml;

import ebs.discogs.data.ReleasesWorker;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;

/**
 * Created by Aleksey Dubov
 * Date: 14/03/01
 * Time: 16:10
 * Copyright (c) 2014
 */
public class MastersParser {
	private static Logger logger = Logger.getLogger(MastersParser.class.getName());

	private String url;
	private ReleasesWorker releasesWorker;

	public MastersParser(String url, ReleasesWorker releasesWorker) {
		this.url = url;
		this.releasesWorker = releasesWorker;
	}

	public void parseLocalFile() throws ParserConfigurationException, SAXException, IOException {
		SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
		parser.parse(url, new MastersHandler(releasesWorker));
	}

	public void parseGZipFile() throws IOException, ParserConfigurationException, SAXException {
		URL u = new URL(url);
		HttpURLConnection urlConnection = (HttpURLConnection) u.openConnection();
		urlConnection.setConnectTimeout(0);
		urlConnection.setReadTimeout(0);
		urlConnection.setRequestMethod("GET");
		urlConnection.setDoOutput(true);
		urlConnection.setRequestProperty("User-Agent",
				"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");

		InputStream stream = urlConnection.getInputStream();
		InputStream gzipStream = new GZIPInputStream(stream);

		SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
		saxParser.parse(gzipStream, new MastersHandler(releasesWorker));
	}
}
