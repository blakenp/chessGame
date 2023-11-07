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
        JsonArray boardArrayObject = jsonObject.getAsJsonArray("board");
        ChessPieceAdapter chessPieceAdapter = new ChessPieceAdapter();

        for (var i = 1; i <= 8; i++) {
            JsonObject rowObject = boardArrayObject.get(i).getAsJsonObject();
            for (var j = 1; j <= 8; j++) {
                String colKey = Integer.toString(j);
                if (rowObject.has(colKey)) {
                    JsonObject jsonChessPiece = rowObject.get(colKey).getAsJsonObject();
                    if (jsonChessPiece != null) {
                        ChessPosition chessPosition = new ChessPositionImpl(i, j);

                        ChessPiece chessPiece = chessPieceAdapter.deserialize(jsonChessPiece, ChessPiece.class, jsonDeserializationContext);
                        board.addPiece(chessPosition, chessPiece);
                    }
                }
            }
        }
        return board;
    }
}
