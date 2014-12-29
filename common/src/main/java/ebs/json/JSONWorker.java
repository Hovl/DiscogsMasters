package ebs.json;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Created by Aleksey Dubov
 * Date: 14/03/01
 * Time: 15:35
 * Copyright (c) 2014
 */
public class JSONWorker {
	protected static Gson gson = new Gson();

	public static JsonObject getErrorResult(String description) {
		JsonObject result = new JsonObject();
		result.addProperty("result", "error");
		result.addProperty("description", description);
		return result;
	}

	public static JsonObject getOkResult() {
		JsonObject result = new JsonObject();
		result.addProperty("result", "ok");
		return result;
	}

	public static <X> JsonElement convertToJsonElement(X x) {
		return gson.toJsonTree(x);
	}
}
