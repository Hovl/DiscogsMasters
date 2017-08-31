package ebs.discogs.web;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import ebs.discogs.html.HTMLDataMastersParser;
import ebs.discogs.xml.MastersParser;
import ebs.web.BasicJSONServlet;
import org.apache.commons.io.FilenameUtils;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 * Created by Aleksey Dubov
 * Date: 14/03/01
 * Time: 14:18
 * Copyright (c) 2014
 */
public class ReadMastersServlet extends BasicJSONServlet {
	private static Logger logger = Logger.getLogger(ReadMastersServlet.class.getName());

	@Override
	protected void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String url = getParameterAsString(req, "url");
		if(url == null) {
			writeError("No 'url' parameter");
			url = HTMLDataMastersParser.getLatestMastersXMLURL();
		}

		JsonElement result = null;
		try {
		    String filename = FilenameUtils.getName(url);

		    if(new File(filename).exists()) {
                logger.info("Reading local file: " + filename);
		        result = new JsonParser().parse(new FileReader(filename));
            } else {
                File[] files = new File(".").listFiles((f, p) -> p.endsWith(".xml.gz"));
                if (files != null) {
                    Arrays.stream(files).forEach(File::delete);
                }

                logger.info("Reading remote file: " + filename);
                result = new MastersParser(url).parseGZipFile().saveToFile(filename).getResultAsJsonElement();
            }
		} catch (ParserConfigurationException | SAXException e) {
			writeFailure(e);
			e.printStackTrace();
		}

		if(result == null) {
			writeError("Cannot parse the file from the given url:" + url);
			return;
		}

		writeAnswer(resp, result);
	}
}
