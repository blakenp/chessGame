package server;

import org.eclipse.jetty.websocket.api.Session;

public record Connection(String authTokenString, Session session) {
}
