package typeAdapters;

import chess.ChessBoard;
import chess.ChessPiece;
import chess.ChessPosition;
import chessImplementation.ChessBoardImpl;
import chessImplementation.ChessPositionImpl;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ChessBoardAdapter implements JsonDeserializer<ChessBoard> {
    @Override
    public ChessBoard deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        ChessBoard board = new ChessBoardImpl();
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonArray boardArrayObject = jsonObject.getAsJsonArray("chessBoard");
        ChessPieceAdapter chessPieceAdapter = new ChessPieceAdapter();

        for (var i = 0; i < 8; i++) {
            JsonArray rowObject = boardArrayObject.get(i).getAsJsonArray();
            for (var j = 0; j < 8; j++) {
                JsonElement jsonChessPieceElement = rowObject.get(j);
                if (!jsonChessPieceElement.isJsonNull()) {
                    JsonObject jsonChessPiece = jsonChessPieceElement.getAsJsonObject();
                    ChessPosition chessPosition = new ChessPositionImpl(i + 1, j + 1);

                    ChessPiece chessPiece = chessPieceAdapter.deserialize(jsonChessPiece, ChessPiece.class, jsonDeserializationContext);
                    board.addPiece(chessPosition, chessPiece);
                }
            }
        }
        return board;
    }
}
