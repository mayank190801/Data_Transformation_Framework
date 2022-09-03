package Framework.inbuiltFunctions;

import Framework.DataTransformation;
import Framework.DataUtil;
import com.jayway.jsonpath.DocumentContext;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class Concatenate implements FunctionHandler{
    @Override
    public Object handler(DataTransformation dataTransformation, JsonObject entity, DocumentContext input) {
        JsonArray sources = entity.getJsonArray("sources");
        StringBuilder sb = new StringBuilder();
        if(sources == null) return null;
        for (Object source : sources) {
            Object value = dataTransformation.getStarter().action(dataTransformation, (JsonObject) source, input);
            if(!(value instanceof String)) continue;
            sb.append(value);
        }
        return sb.toString();
    }




}
