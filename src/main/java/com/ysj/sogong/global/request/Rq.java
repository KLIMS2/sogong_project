package com.ysj.sogong.global.request;

import com.ysj.sogong.global.utility.Util;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Rq
{
  private HttpServletRequest req;
  private HttpServletResponse resp;

  private static Map<String, Map<String, Map<String, Object>>> cache;

  static
  {
    cache = new LinkedHashMap<>();
  }

  public Rq(HttpServletResponse resp)
  {
    this.resp = resp;
    this.resp.setContentType("text/html; charset=UTF-8");
    this.resp.setCharacterEncoding("UTF-8");

    // 뒤로가기 시 캐시된 페이지를 보여주지 않도록 설정
    this.resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
    this.resp.setHeader("Pragma", "no-cache"); // HTTP 1.0.
    this.resp.setHeader("Expires", "0"); // Proxies.
  }

  /*
  html 관련
  */

  private boolean print(String html)
  {
    try
    {
      resp.getWriter().print(html);
      return true;
    }
    catch (IOException e)
    {
      return false;
    }
  }

  public boolean printAlert(String content)
  {
    return print("""
        <script>
            alert('%s');
        </script>
        """.formatted(content));
  }
  
  public boolean replace(String url)
  {
    return print("""
        <script>
            location.replace('%s');
        </script>
        """.formatted(url));
  }

  /*
  일반 함수 관련
   */

  public static void settingInputSize(Class<?> clazz, Model model)
  {
    // 캐시에 없을 시 캐시 생성
    String className = clazz.getName();
    if(!cache.containsKey(className))
    {
      Map<String, Map<String, Object>> cacheData = new LinkedHashMap<>();
      cache.put(className, cacheData);
    }

    // 캐시에서 데이터 불러오기
    Map<String, Map<String, Object>> input_size = cache.get(className);

    // 캐시에 데이터가 없으면 데이터 생성 후 캐시에 저장
    if(input_size.isEmpty())
    {
      List<String> fieldNames = Util.getFieldNames(clazz);
      for(String fieldName : fieldNames)
      {
        Map<String, Object> data = Util.getAnnotaionValues(clazz, fieldName);
        input_size.put(fieldName, data);
      }

      cache.put(className, input_size);
    }

    // 모델에 어노테이션 값을 넘김
    model.addAttribute("input_size", input_size);
  }
}
