import Framework.DataTransformation;
import com.jayway.jsonpath.*;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import javax.xml.crypto.Data;
import java.util.HashMap;
import java.util.Map;

public class App {

    public static void main(String[] args) throws Exception {

        //Running the application for testing purposes
        Vertx vertx = Vertx.vertx();
        String constantsStr = String.valueOf(vertx.fileSystem().readFileBlocking("/Users/mayanksingh/IdeaProjects/DataTransfromationFramework/src/main/resources/constants.json"));
        String inputStr = String.valueOf(vertx.fileSystem().readFileBlocking("/Users/mayanksingh/IdeaProjects/DataTransfromationFramework/src/main/resources/input.json"));
        String mappingsStr = String.valueOf(vertx.fileSystem().readFileBlocking("/Users/mayanksingh/IdeaProjects/DataTransfromationFramework/src/main/resources/mappings.json"));

        Map<String, Class> customFunctions = new HashMap<>();

        DataTransformation obj = DataTransformation.start(constantsStr, customFunctions);
        JsonObject mappings = obj.performDataTransformation(mappingsStr, inputStr);
        String json = obj.performUnflattening(mappings);

        System.out.println(mappings);
        System.out.println(json);

    }
}
