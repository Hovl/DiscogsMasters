package ebs.discogs.workers;

import com.google.common.base.Charsets;
import com.google.common.io.CharSink;
import com.google.common.io.Files;
import com.google.gson.JsonElement;
import ebs.discogs.data.ReleasesWorker;
import ebs.discogs.xml.MastersParser;

import java.io.File;
import java.util.logging.Logger;

/**
 * Created by Aleksey Dubov
 * Date: 14/12/27
 * Time: 14:29
 * Copyright (c) 2014
 */
public class LocalXMLWorker {
	private static Logger logger = Logger.getLogger(LocalXMLWorker.class.getName());

	public static final String MASTERS_JSON = "masters.json";

	public static void main(String args[]) throws Exception {
		if (args.length == 0) {
			System.out.println("Provide the url to parse as a parameter.");
			return;
		}

		String url = args[0];
		logger.info("going to parse " + url);

		ReleasesWorker releasesWorker = new ReleasesWorker();

		new MastersParser(url, releasesWorker).parseGZipFile();

		JsonElement result = releasesWorker.transformResultsToJsonElement();

		logger.info("xml is parsed:\n" + result.toString());

		CharSink charSink = Files.asCharSink(new File(MASTERS_JSON), Charsets.UTF_8);
		charSink.write(result.toString());
	}
}
