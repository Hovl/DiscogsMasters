package ebs.discogs.workers;

import ebs.discogs.data.DiscogsData;
import ebs.discogs.xml.MastersParser;

import java.util.logging.Logger;

/**
 * Created by Aleksey Dubov
 * Date: 14/12/27
 * Time: 14:29
 * Copyright (c) 2014
 */
public class LocalXMLWorker {
	private static Logger logger = Logger.getLogger(LocalXMLWorker.class.getName());

	public static void main(String args[]) throws Exception {
		if (args.length == 0) {
			System.out.println("Provide the url to parse as a parameter.");
			return;
		}

		String url = args[0];
		logger.info("going to parse " + url);
		logger.info("xml is parsed:\n" +
				new MastersParser(url).parseGZipFile().saveToFile(DiscogsData.MASTERS_JSON).getResult());
	}
}
