package ebs.discogs.web;

import com.google.gson.JsonElement;
import ebs.discogs.xml.MastersParser;
import ebs.web.BasicJSONServlet;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by Aleksey Dubov
 * Date: 14/03/01
 * Time: 14:18
 * Copyright (c) 2014
 */
public class TestServlet extends BasicJSONServlet {
	private static Logger logger = Logger.getLogger(TestServlet.class.getName());

	@Override
	protected void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String url = getParameterAsString(req, "url");
		if(url == null) {
			writeError("No 'url' parameter");
			return;
		}

		JsonElement result = null;
		try {
			result = new MastersParser(url).parseGZipFile().getResultAsJsonElement();
		} catch (ParserConfigurationException | SAXException e) {
			writeFailure(e);
		}

		if(result == null) {
			writeError("Cannot parse the file from the given url:" + url);
			return;
		}

		writeAnswer(resp, result);
	}
}
