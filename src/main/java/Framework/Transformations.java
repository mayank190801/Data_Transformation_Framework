package Framework;

import Framework.inbuiltFunctions.FunctionHandler;
import com.jayway.jsonpath.DocumentContext;
import io.vertx.core.json.JsonObject;

import java.util.Map;

public class Transformations {
    public Object transformAction(DataTransformation dataTransformation, JsonObject entity, DocumentContext input){
        String type = entity.getString("type");
        String dataType = entity.getString("dataType");
        Map<String, FunctionHandler> functions = dataTransformation.getFunctionsMap();
        for(String fname : functions.keySet()){
            if(type.equals(fname)){
                Object value = functions.get(fname).handler(dataTransformation, entity, input);
                return DataUtil.convertData(value, dataType);
            }
        }
        return null;
    }

}
