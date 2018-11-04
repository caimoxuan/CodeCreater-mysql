package pro.test.mappercreater.util;

public class SqlTypeUtil {

    public static String getJavaType(String jdbcType){
        switch(jdbcType){
            case "varchar":
            case "VARCHAR":
            case "text":
            case "TEXT":
            case "char":
            case "CHAR":
                return "String";
            case "int":
            case "INT":
            case "tinyint":
            case "TINYINT":
                return "Integer";
            case "bigint":
            case "BIGINT":
            case "id":
            case "ID":
                return "Long";
            case "float":
            case "FLOAT":
                return "Float";
            case "double":
            case "DOUBLE":
                return "Double";
            case "decimal":
            case "DECIMAL":
                return "BigDecimal";
            default:
                return "String";
        }
    }
}
