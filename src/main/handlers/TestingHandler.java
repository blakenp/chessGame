package handlers;

import com.google.gson.Gson;
import responses.ClearResponse;
import services.TestingService;

/**
 * Object representation of http handler used to handle requests to clear database for testing purposes
 */
public class TestingHandler {
    /**
     * service called by handler to perform clearing of database
     */
    private static TestingService testingService = new TestingService();

    /**
     * Method that clears all data stored in database
     *
     * @return Returns response of clearing data in form of ClearResponse object
     */
    public static String handleClear() {
        Gson gson = new Gson();

        ClearResponse clearResponse = testingService.clear();
        return gson.toJson(clearResponse);
    }
}
