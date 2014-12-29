package ebs.web;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.logging.Logger;

/**
 * Created by Aleksey Dubov
 * Date: 14/03/01
 * Time: 15:35
 * Copyright (c) 2014
 */
public abstract class BasicJSONServlet extends HttpServlet {
	private static Logger logger = Logger.getLogger(BasicJSONServlet.class.getName());

	protected abstract void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		prepareResponse(resp);

		handleRequest(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	public static void writeAnswer(HttpServletResponse resp, JsonElement result) {
		try {
			if (result != null) {
				JsonWriter jsonWriter = new JsonWriter(resp.getWriter());
				jsonWriter.setHtmlSafe(true);
				try {
					new GsonBuilder().disableHtmlEscaping().create().toJson(result, jsonWriter);
				} catch (Exception e) {
					new GsonBuilder().disableHtmlEscaping().create().toJson(writeFailure(e), jsonWriter);
				} finally {
					jsonWriter.close();
				}
			}
		} catch (IOException e) {
			logger.warning("Cannot respond to the request:" + e.getLocalizedMessage());
		}
	}

	public static JsonElement writeFailure(Exception e) throws IOException {
		logger.warning("Servlet Exception: " + e.getLocalizedMessage());

		JsonObject object = new JsonObject();
		object.addProperty("result", "error");
		object.addProperty("code", "-100");
		object.addProperty("description", e.getLocalizedMessage());
		return object;
	}

	public static void prepareResponse(HttpServletResponse resp) {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
	}

	public static JsonElement writeError(int code, String description) throws IOException {
		JsonObject object = new JsonObject();
		object.addProperty("result", "error");
		object.addProperty("code", code);
		object.addProperty("description", description);
		return object;
	}

	public static JsonElement writeError(String description) throws IOException {
		JsonObject object = new JsonObject();
		object.addProperty("result", "error");
		object.addProperty("code", "-100");
		object.addProperty("description", description);
		return object;
	}

	public static String getRequiredParameter(HttpServletRequest req, String name) throws ServletException {
		String ret = req.getParameter(name);
		if (ret == null) {
			throw new ServletException("Parameter missed: " + name);
		}
		return ret;
	}

	public static JsonElement writeOk() throws IOException {
		JsonObject object = new JsonObject();
		object.addProperty("result", "ok");
		return object;
	}

	public static Integer getParameterAsInteger(HttpServletRequest req, String id) {
		return getParameterAsInteger(req, id, 0);
	}

	public static Integer getParameterAsInteger(HttpServletRequest req, String id, Integer defaultValue) {
		String idStr = req.getParameter(id);
		if(idStr != null) {
			try {
				return Integer.parseInt(idStr);
			} catch (NumberFormatException e) {
				return defaultValue;
			}
		}
		return defaultValue;
	}

	public static String getParameterAsString(HttpServletRequest req, String id, String defaultValue) {
		String idStr = req.getParameter(id);
		if(idStr != null) {
			try {
				idStr = URLDecoder.decode(idStr, "UTF-8");
				if(idStr != null) {
					return idStr;
				}
			} catch (UnsupportedEncodingException e) {
				return defaultValue;
			}
		}
		return defaultValue;
	}

	public static String getParameterAsString(HttpServletRequest req, String id) {
		return getParameterAsString(req, id, null);
	}

	public static Boolean getParameterAsBoolean(HttpServletRequest req, String id) {
		String idStr = req.getParameter(id);
		if(idStr != null) {
			return Boolean.parseBoolean(idStr);
		}
		return null;
	}

	public static Boolean getParameterAsBoolean(HttpServletRequest req, String id, Boolean defaultValue) {
		String idStr = req.getParameter(id);
		if(idStr != null) {
			return Boolean.parseBoolean(idStr);
		}
		return defaultValue;
	}

	public static Float getParameterAsFloat(HttpServletRequest req, String id) {
		return getParameterAsFloat(req, id, null);
	}

	public static Float getParameterAsFloat(HttpServletRequest req, String id, Float defaultValue) {
		String idStr = req.getParameter(id);
		if(idStr != null) {
			try {
				return Float.parseFloat(idStr);
			} catch (NumberFormatException e) {
				return defaultValue;
			}
		}
		return defaultValue;
	}
}
