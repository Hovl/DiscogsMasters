package ebs.discogs.xml;

import com.google.common.base.Charsets;
import com.google.common.io.CharSink;
import com.google.common.io.Files;
import com.google.gson.JsonElement;
import ebs.discogs.data.ReleasesWorker;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
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

	public MastersParser(String url) {
		this.url = url;
		this.releasesWorker = new ReleasesWorker();
	}

	public String getUrl() {
		return url;
	}

	public JsonElement getResultAsJsonElement() {
		return releasesWorker.transformResultsToJsonElement();
	}

	public String getResult() {
		return getResultAsJsonElement().getAsString();
	}

	public MastersParser parseLocalFile() throws ParserConfigurationException, SAXException, IOException {
		SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
		parser.parse(url, new MastersHandler(releasesWorker));

		return this;
	}

	public MastersParser parseGZipFile() throws IOException, ParserConfigurationException, SAXException {
		logger.info("Parsing url: " + url);
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

		return this;
	}

	public MastersParser saveToFile(String fileName) throws IOException {
		CharSink charSink = Files.asCharSink(new File(fileName), Charsets.UTF_8);
		charSink.write(releasesWorker.transformResultsToJsonElement().toString());
		return this;
	}
}
