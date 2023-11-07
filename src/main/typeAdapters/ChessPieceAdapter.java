package typeAdapters;

import chessImplementation.ChessPieceImpl;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class ChessPieceAdapter implements JsonDeserializer<ChessPieceImpl> {
    @Override
    public ChessPieceImpl deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return null;
    }
}
