package com.anateam.config;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.springframework.stereotype.Component;

@Component("templateHelper")
public class TemplateHelper {

    public String getSimpleName(Class<?> clazz) {
        if (clazz == null)
            return "";
        return clazz.getSimpleName();
    }

    public String getFieldName(Field field) {
        return field == null ? "" : field.getName();
    }

    // 2. Универсальный метод для DbField и всего остального
    public String getFieldName(Object fieldObj) {
        if (fieldObj == null)
            return "";

        // Если это уже Field, вызываем наш метод
        if (fieldObj instanceof Field) {
            return ((Field)fieldObj).getName();
        }

        // Если это DbField (или что-то другое из SnapAdmin), пытаемся вызвать
        // .getName() через рефлексию
        try {
            Method getNameMethod = fieldObj.getClass().getMethod("getName");
            return (String)getNameMethod.invoke(fieldObj);
        } catch (Exception e) {
            // Если метода getName нет, вернем toString или пустую строку, чтобы
            // не падало
            return fieldObj.toString();
        }
    }
}
