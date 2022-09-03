package Framework;

import Framework.inbuiltFunctions.*;
import com.github.wnameless.json.flattener.JsonFlattener;
import com.github.wnameless.json.unflattener.JsonUnflattener;
import com.google.common.collect.ImmutableMap;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class DataTransformation {

    private static final Configuration conf = Configuration.builder().options(Option.SUPPRESS_EXCEPTIONS).build();
    private static DataTransformation dataTransformation;
    private static DocumentContext constant;
    private static Starter starter;
    private static Transformations transform;
    private static ImmutableMap<String, FunctionHandler> functions;

    private DataTransformation(String constant, Map<String, Class> customFunctions) {

        DataTransformation.constant = JsonPath.using(conf).parse(constant);
        DataTransformation.starter = new Starter();
        DataTransformation.transform = new Transformations();

        //Adding Inbuilt Functions
        Map<String, FunctionHandler> tempFunctions = new HashMap<>();

        FunctionHandler obj1 = new GetConstant();
        FunctionHandler obj2 = new GetInput();
        FunctionHandler obj3 = new Concatenate();
        FunctionHandler obj4 = new FirstOneOf();

        tempFunctions.put("getConstant", obj1);
        tempFunctions.put("getInput", obj2);
        tempFunctions.put("concatenate", obj3);
        tempFunctions.put("firstOneOf", obj4);

        //Adding Custom Functions
        for(String fname : customFunctions.keySet()){
            if(tempFunctions.containsKey(fname)) continue;
            try{
                FunctionHandler obj = (FunctionHandler) customFunctions.get(fname).getDeclaredConstructor().newInstance();
                tempFunctions.put(fname, obj);
            }catch(Exception e){
                System.out.println("This function can not be added due to " + e.getMessage());
            }
         }

        //constructed Immutable Map
        functions = ImmutableMap.<String, FunctionHandler>builder().putAll(tempFunctions).build();
    }

    //for initialising single object out of this class!! (simple)
    //It will take some stuff for sure in future!!
    public static synchronized DataTransformation start(String constant, Map<String, Class> customFunctions){
        if(dataTransformation == null){
            dataTransformation = new DataTransformation(constant, customFunctions);
        }
        return dataTransformation;
    }

    //Now I have to unit test this as well for sure!! (no??)
    public JsonObject performDataTransformation(String mappings, String input){
        DocumentContext inputCtx = JsonPath.using(conf).parse(input);

        JsonObject mapping = new JsonObject(mappings);  //converting string to JsonObject
        JsonArray insideMapping = mapping.getJsonArray("mappings");
        if(insideMapping == null) return new JsonObject();  //simple check if "mappings" key exist or not

        JsonObject flattenedMappings = new JsonObject();
        for(Object map : insideMapping){
            JsonObject currentMapping = (JsonObject) map;  //cov
            String key = currentMapping.getString("target");
            Object value = DataTransformation.starter.action(dataTransformation, currentMapping, inputCtx);
            flattenedMappings.put(key, value);
        }

        return flattenedMappings;
    }
    public String performUnflattening(JsonObject result){
        return JsonUnflattener.unflatten(result.toString());
    }
    public DocumentContext getConstant(){ return DataTransformation.constant; }
    public Starter getStarter(){
        return DataTransformation.starter;
    }
    public Transformations getTransformation(){
        return DataTransformation.transform;
    }
    public Map<String, FunctionHandler> getFunctionsMap(){ return DataTransformation.functions; };

}
