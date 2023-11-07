package typeAdapters;

import chess.ChessBoard;
import chess.ChessGame;
import chessImplementation.ChessGameImpl;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ChessGameAdapter implements JsonDeserializer<ChessGame> {
    @Override
    public ChessGame deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        ChessGame.TeamColor currentTeam = ChessGame.TeamColor.valueOf(jsonObject.get("team").getAsString());
        JsonObject jsonChessBoard = jsonObject.get("board").getAsJsonObject();
        ChessBoard chessBoard = new ChessBoardAdapter().deserialize(jsonChessBoard, ChessBoard.class, jsonDeserializationContext);

        ChessGame chessGame = new ChessGameImpl();
        chessGame.setBoard(chessBoard);
        chessGame.setTeamTurn(currentTeam);

        return chessGame;
    }
}
