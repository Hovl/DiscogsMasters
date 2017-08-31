package ebs.discogs.web;

import com.google.gson.JsonObject;
import ebs.discogs.html.HTMLDataMastersParser;
import ebs.web.BasicJSONServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Aleksey Dubov
 * Date: 14/11/28
 * Time: 23:32
 * Copyright (c) 2014
 */
public class GetLatestMastersFileUrlServlet extends BasicJSONServlet {
	@Override
	protected void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		JsonObject result = (JsonObject) writeOk();
		result.addProperty("url", HTMLDataMastersParser.getLatestMastersXMLURL());
		writeAnswer(resp, result);
	}
}
