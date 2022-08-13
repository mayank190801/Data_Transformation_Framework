package Framework;

import com.jayway.jsonpath.DocumentContext;
import io.vertx.core.json.JsonObject;

import javax.print.Doc;

public class Starter {

    //we will have functions here to sort on the basis of action provided to us
    //currently we have only one type of action, but in future while expanding this application
    //we could have even more!!
    //entity function

    //Have dataType shit in here too for fun, whenever you get the time bruh!
    public Object action(DataTransformation dataTransformation, JsonObject entity, DocumentContext input){
        String action = entity.getString("action");
        String dataType = entity.getString("dataType");
        if(action == null || dataType == null) return null;

        switch(action){
            case "transformation":
                Object data = dataTransformation.getTransformation().transformAction(dataTransformation, entity, input);
                return DataUtil.convertData(data, dataType);
        }

        return null;
    }


}
