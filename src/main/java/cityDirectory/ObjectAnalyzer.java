package cityDirectory;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

public class ObjectAnalyzer {

    private ArrayList<Object> visited = new ArrayList<>();

    public String toString(Object obj) throws ReflectiveOperationException {

        if (obj == null) return "null";
        if (visited.contains(obj)) return "...";

        visited.add(obj);
        Class cl = obj.getClass();
        if (cl == String.class) return (String) obj;

        if (cl.isArray()) {
            String resultRow = cl.getComponentType() + "[]{";
            for (int i = 0; i < Array.getLength(obj); i++) {
                if (i > 0) resultRow += ", ";
                Object val = Array.get(obj, i);
                if (cl.getComponentType().isPrimitive())
                    resultRow += val;
                else
                    resultRow += toString(val);
            }
            return resultRow;
        }

        String resultRow = cl.getSimpleName() + "{";

        do {
            Field[] fields = cl.getDeclaredFields();
            AccessibleObject.setAccessible(fields, true);
            ArrayList<String> fieldRows = new ArrayList<>();

            for (Field field : fields) {
                if (!Modifier.isStatic(field.getModifiers())) {
                    Object valueField = field.get(obj);
                    Class typeField = field.getType();
                    var valueFieldStr = (typeField.isPrimitive()) ? valueField : toString(valueField);
                    fieldRows.add(field.getName() + "='" + valueFieldStr + "'");
                }
            }

            String fieldsStr = String.join(", ", fieldRows);
            resultRow = resultRow.concat(fieldsStr);
            cl = cl.getSuperclass();

        } while (cl != null);

        resultRow += "}";

        return resultRow;

    }
}
