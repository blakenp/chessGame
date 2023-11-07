package typeAdapters;

import chess.ChessGame;
import chess.ChessPiece;
import chessImplementation.*;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ChessPieceAdapter implements JsonDeserializer<ChessPiece> {
    @Override
    public ChessPiece deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        ChessPiece.PieceType pieceType = ChessPiece.PieceType.valueOf(jsonObject.get("type").getAsString());
        ChessGame.TeamColor pieceColor = ChessGame.TeamColor.valueOf(jsonObject.get("color").getAsString());

        ChessPiece chessPiece = switch (pieceType) {
            case ROOK -> new Rook(pieceColor, pieceType);
            case KNIGHT -> new Knight(pieceColor, pieceType);
            case BISHOP -> new Bishop(pieceColor, pieceType);
            case QUEEN -> new Queen(pieceColor, pieceType);
            case KING -> new King(pieceColor, pieceType);
            default -> new Pawn(pieceColor, pieceType);
        };

        return chessPiece;
    }
}
