package services;

import responses.ClearResponse;

/**
 * Object representation of clear service that make the API call to clear the database of all its data for testing purposes
 */
public class TestingService {
    /**
     * A method that executes the logic to clear the whole database of its data. Could also return an error message if server issues occur
     * @return Will either return null and a successful 200 status code that will result in clearing the whole database or an error in doing so
     */
    public ClearResponse clear() {
        return null;
    }
}
