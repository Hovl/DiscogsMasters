package ebs.discogs.workers;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import ebs.discogs.data.DiscogsData;
import ebs.discogs.html.HTMLDataMastersParser;
import ebs.discogs.xml.MastersParser;

import java.io.File;
import java.util.logging.Logger;

/**
 * Created by Aleksey Dubov
 * Date: 15/01/10
 * Time: 15:11
 * Copyright (c) 2015
 */
public class LocalDataMastersWorker {
	private static Logger logger = Logger.getLogger(LocalDataMastersWorker.class.getName());

	public static void main(String args[]) throws Exception {
		String latestMastersXMLURL = HTMLDataMastersParser.getLatestMastersXMLURL(null);
		logger.info(latestMastersXMLURL);

		new MastersParser(latestMastersXMLURL).parseGZipFile().saveToFile(DiscogsData.MASTERS_JSON);
		logger.info("Result: \n" + Files.toString(new File(DiscogsData.MASTERS_JSON), Charsets.UTF_8));
	}
}
