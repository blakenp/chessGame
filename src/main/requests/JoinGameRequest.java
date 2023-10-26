package requests;

import chess.ChessGame;

/**
 * Object representation of the requests the client can make to join a chess game session.
 */
public record JoinGameRequest(ChessGame.TeamColor playerColor, Integer gameID, String authToken) {
}
