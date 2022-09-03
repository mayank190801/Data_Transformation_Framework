package Framework;

import org.apache.commons.lang3.math.NumberUtils;
import java.util.Objects;

public class DataUtil {

    public static Object convertData(Object data, String type){
        try{
            switch(type){
                case "string" :
                    if(data instanceof Number || data instanceof Boolean) return String.valueOf(data);
                    if(data instanceof String) return data;

                case "long" :
                    if(data instanceof Number) return Long.valueOf(String.valueOf(data));
                    if(data instanceof Boolean) return Objects.equals(String.valueOf(data), "true") ? 1L : 0L;
                    if(data instanceof String && NumberUtils.isCreatable(String.valueOf(data))) return Long.valueOf(String.valueOf(data));

                case "double" :
                    if(data instanceof Number) return Double.valueOf(String.valueOf(data));
                    if(data instanceof Boolean) return Objects.equals(String.valueOf(data), "true") ? 1.0 : 0.0;
                    if(data instanceof String && NumberUtils.isCreatable(String.valueOf(data))) return Double.valueOf(String.valueOf(data));

                case "boolean":
                    if(data instanceof Boolean) return Boolean.valueOf(String.valueOf(data));
                    if(data instanceof String ){
                        if(Objects.equals(String.valueOf(data), "true")) return true;
                        if(Objects.equals(String.valueOf(data), "false")) return false;
                    }
                    if(data instanceof Number) {
                        if(Objects.equals(String.valueOf(data), "1")) return true;
                        if(Objects.equals(String.valueOf(data), "0")) return false;
                    }
                default :
                    return null;
            }
        }catch(Exception e){
            return null;
        }
    }


}
