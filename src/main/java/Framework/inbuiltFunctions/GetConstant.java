package Framework.inbuiltFunctions;

import Framework.DataTransformation;
import Framework.DataUtil;
import com.jayway.jsonpath.DocumentContext;
import io.vertx.core.json.JsonObject;

public class GetConstant implements FunctionHandler {

    @Override
    public Object handler(DataTransformation dataTransformation, JsonObject entity, DocumentContext input) {
        String sourceType = entity.getString("sourceType");
        return switch (sourceType) {
            case "string" -> stringSourceType(dataTransformation, entity, input);
            case "object" -> objectSourceType(dataTransformation, entity, input);
            default -> null;
        };
    }

    private Object stringSourceType(DataTransformation dataTransformation, JsonObject entity, DocumentContext input){
        String source = entity.getString("source");
        String dataType = entity.getString("dataType");
        if(source == null || dataType == null) return null;
        Object value = dataTransformation.getConstant().read(source);
        return DataUtil.convertData(value, dataType);
    }

    private Object objectSourceType(DataTransformation dataTransformation, JsonObject entity, DocumentContext input){
        JsonObject sourceEntity = entity.getJsonObject("source");
        String source = (String) dataTransformation.getStarter().action(dataTransformation, sourceEntity, input);
        String dataType = entity.getString("dataType");
        if(source == null || dataType == null) return null;
        Object value = dataTransformation.getConstant().read(source);
        return DataUtil.convertData(value, dataType);
    }

}
