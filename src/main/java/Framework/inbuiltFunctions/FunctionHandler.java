package Framework.inbuiltFunctions;

import Framework.DataTransformation;
import com.jayway.jsonpath.DocumentContext;
import io.vertx.core.json.JsonObject;

public interface FunctionHandler {
    public Object handler(DataTransformation dataTransformation, JsonObject entity, DocumentContext input);
}
