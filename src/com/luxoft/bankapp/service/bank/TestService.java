package com.luxoft.bankapp.service.bank;

import com.luxoft.bankapp.annotations.NoDB;

import java.lang.reflect.Field;
import java.util.Collection;


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
    public static boolean isEquals(Object o1, Object o2) {
        boolean result = false;
        Class c1 = o1.getClass();
        Class c2 = o2.getClass();
        Field[] fields1 = c1.getDeclaredFields();
        Field[] fields2 = c2.getDeclaredFields();

        for (Field f : fields1) {

            boolean isAnno = f.isAnnotationPresent(NoDB.class);
            Class type = f.getType();
            for (Field f1 : fields2) {
                boolean isAnno2 = f1.isAnnotationPresent(NoDB.class);
                if (!isAnno && !isAnno2) {
                    Class type2 = f1.getType();
                    try {

                        if (type.equals(type2) && type.equals(Collection.class)) {

                        } else if (type.equals(type2) && f.get(o1).equals(f1.get(o2))) {

                            result = true;
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }
}


