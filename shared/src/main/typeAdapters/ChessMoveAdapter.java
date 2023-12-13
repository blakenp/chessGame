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
        JsonObject jsonPromotionPiece = moveObject.has("promotionPiece") ? moveObject.getAsJsonObject("promotionPiece") : null;
        ChessPiece.PieceType promotionPiece = null;

        System.out.println("json object: " + jsonObject);
        System.out.println("move object: " + moveObject);

        if (jsonPromotionPiece != null) {
            promotionPiece = ChessPiece.PieceType.valueOf(jsonPromotionPiece.getAsString());
        }

        ChessPosition extractedStartPosition = new ChessPositionImpl(jsonStartPosition.getAsJsonPrimitive("row").getAsInt(), jsonStartPosition.getAsJsonPrimitive("col").getAsInt());
        ChessPosition extractedEndPosition = new ChessPositionImpl(jsonEndPosition.getAsJsonPrimitive("row").getAsInt(), jsonEndPosition.getAsJsonPrimitive("col").getAsInt());

        return new ChessMoveImpl(extractedStartPosition, extractedEndPosition, promotionPiece);
    }
}
