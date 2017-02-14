package ebs.discogs.web;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ebs.discogs.html.HTMLDataMastersParser;
import ebs.discogs.xml.MastersParser;
import ebs.web.BasicJSONServlet;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Created by Aleksey Dubov
 * Date: 14/11/28
 * Time: 23:32
 * Copyright (c) 2014
 */
public class PingServlet extends BasicJSONServlet {
	@Override
	protected void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		JsonObject result = (JsonObject) writeOk();
		result.addProperty("url", HTMLDataMastersParser.getLatestMastersXMLURL());
		writeAnswer(resp, result);
	}
}
