package com.ysj.sogong.global.utility;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Util
{
  public static List<String> getFieldNames(Class<?> clazz)
  {
    List<String> fieldNames = new ArrayList<>();

    Field[] fields = clazz.getDeclaredFields();
    for(Field field : fields)
    {
      fieldNames.add(field.getName());
    }

    return fieldNames;
  }

  public static Map<String, Object> getAnnotaionValues(Class<?> clazz, String fieldName)
  {
    Map<String, Object> data = new LinkedHashMap<>();

    try
    {
      // 클래스에서 필드 추출
      Field field = clazz.getDeclaredField(fieldName);

      // 필드에 있는 어노테이션 추출
      Annotation[] annotations = field.getAnnotations();
      for(Annotation annotation : annotations)
      {
        // 어노테이션에 있는 매소드(변수) 추출
        Method[] methods = annotation.annotationType().getDeclaredMethods();
        for (Method method : methods)
        {
          try
          {
            // 어노테이션에 있는 값들 추출
            String name = method.getName();
            Object value = method.invoke(annotation);

            // 값 저장
            data.put(name, value);
          }
          catch (Exception e)
          {
            throw new RuntimeException(e);
          }
        }
      }

      return data;
    }
    catch (NoSuchFieldException e)
    {
      throw new RuntimeException(e);
    }
  }
}
