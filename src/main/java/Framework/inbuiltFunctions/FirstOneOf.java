package Framework.inbuiltFunctions;

import Framework.DataTransformation;
import Framework.DataUtil;
import com.jayway.jsonpath.DocumentContext;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class FirstOneOf implements FunctionHandler {
    @Override
    public Object handler(DataTransformation dataTransformation, JsonObject entity, DocumentContext input) {
        JsonArray sources = entity.getJsonArray("sources");
        String dataType = entity.getString("dataType");
        if(sources == null || dataType == null) return null;
        for (Object source : sources) {
            Object value = dataTransformation.getStarter().action(dataTransformation, (JsonObject) source, input);
            Object nValue = DataUtil.convertData(value, dataType);
            if(nValue != null) return nValue;
        }
        return null;
    }
}