package typeAdapters;

import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;
import chessImplementation.*;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ChessMoveAdapter implements JsonDeserializer<ChessMove> {
    @Override
    public ChessMove deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonObject moveObject = jsonObject.getAsJsonObject("move");
        JsonObject jsonStartPosition = moveObject.getAsJsonObject("startPosition");
        JsonObject jsonEndPosition = moveObject.getAsJsonObject("endPosition");
        JsonElement jsonPromotionPieceElement = moveObject.get("promotionPiece");
        ChessPiece.PieceType promotionPiece = null;

        if (jsonPromotionPieceElement != null && jsonPromotionPieceElement.isJsonPrimitive()) {
            promotionPiece = ChessPiece.PieceType.valueOf(jsonPromotionPieceElement.getAsString());
        }

        ChessPosition extractedStartPosition = new ChessPositionImpl(jsonStartPosition.getAsJsonPrimitive("row").getAsInt(), jsonStartPosition.getAsJsonPrimitive("col").getAsInt());
        ChessPosition extractedEndPosition = new ChessPositionImpl(jsonEndPosition.getAsJsonPrimitive("row").getAsInt(), jsonEndPosition.getAsJsonPrimitive("col").getAsInt());

        return new ChessMoveImpl(extractedStartPosition, extractedEndPosition, promotionPiece);
    }
}
