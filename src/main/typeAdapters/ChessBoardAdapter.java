package typeAdapters;

import chessImplementation.ChessBoardImpl;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class ChessBoardAdapter implements JsonDeserializer<ChessBoardImpl> {
    @Override
    public ChessBoardImpl deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        return null;
    }
}
