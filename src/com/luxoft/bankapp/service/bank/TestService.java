package com.luxoft.bankapp.service.bank;

import com.luxoft.bankapp.annotations.NoDB;

import java.lang.reflect.Field;
import java.util.HashMap;


/**
 * Created by User on 28.02.14.
 */
public class TestService {
    /**
     * Данный метод должен анализировать поля классов o1 и o2
     * Он должен сравнивать все поля с помощью equals,
     * за исключением тех полей, которые помечены аннотацией
     *
     * @NoDB и возвращать true, если все поля совпали.
     * также он должен уметь сравнивать коллекции.
     */
    public static boolean isEquals(Object o1, Object o2) throws IllegalAccessException {
        Class c1 = o1.getClass();
        Class c2 = o2.getClass();
        HashMap<String, Field> map = new HashMap<>();
        Field[] fields1 = c1.getDeclaredFields();
        Field[] fields2 = c2.getDeclaredFields();

        for (Field f1 : fields1) {
            if (!f1.isAnnotationPresent(NoDB.class)) {
                map.put(f1.getName(), f1);
            }
        }
        for (Field field2 : fields2) {
            if (map.containsKey(field2.getName())) {
                Object fieldValue1;
                Object fieldValue2;
                Field field1 = map.get(field2.getName());
                field1.setAccessible(true);
                fieldValue1 = field1.get(o1);
                field2.setAccessible(true);
                fieldValue2 = field2.get(o2);
                if (fieldValue1 != null) {
                    if (fieldValue2 != null) {

                        if (!fieldValue1.equals(fieldValue2)) return logFalse(field2, fieldValue1, fieldValue2);

                    } else return logFalse(field2, fieldValue1, fieldValue2);
                } else if (fieldValue2 != null) {
                    return logFalse(field2, fieldValue1, fieldValue2);
                }
            }
        }
        return true;
    }

    private static boolean logFalse(Field field, Object fieldValue1, Object fieldValue2) {
        System.out.println("Equals failed due to field " + field.getName() + " value1=" + fieldValue1 + ", value2=" + fieldValue2);
        return false;
    }

}


