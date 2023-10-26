package handlers;

import com.google.gson.Gson;
import responses.ClearResponse;
import services.TestingService;
import spark.Request;
import spark.Response;

public class TestingHandler {
    private static TestingService testingService = new TestingService();

    public static String handleClear(Request request, Response response) {
        Gson gson = new Gson();

        ClearResponse clearResponse = testingService.clear();
        return gson.toJson(clearResponse);
    }
}
