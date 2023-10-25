package handlers;

import requests.LoginRequest;
import responses.LoginResponse;
import responses.LogoutResponse;
import spark.Request;
import spark.Response;
import spark.Route;

import java.io.IOException;

public class AuthHandler implements Route {
    public static String handleLogin(LoginRequest req, LoginResponse res) throws Exception {
        return null;
    }

    public static String handleLogout(LogoutResponse res) throws Exception {

        return null;
    }

    @Override
    public String handle(Request request, Response response) throws Exception {
        request.body();
        return null;
    }
}
