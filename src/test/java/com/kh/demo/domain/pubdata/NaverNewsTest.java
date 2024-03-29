package com.kh.demo.domain.pubdata;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@SpringBootTest
class NaverNewsTest {

  @Autowired
  private NaverNews news;

  @Test
  void reqNews() {
//    String keyword = "LG화학";
    String data = news.reqNews("LG화학", 1, 10);
    log.info("data={}", data);
  }

  @Test
  @DisplayName("네이버API 예제소스")
  void reqNews2(){
    String clientId = "rU1BGqPDYCKpVAtW30FM"; //애플리케이션 클라이언트 아이디
    String clientSecret = "cOAsPt0pi2"; //애플리케이션 클라이언트 시크릿


    String text = null;
    try {
      text = URLEncoder.encode("LG화학", "UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("검색어 인코딩 실패",e);
    }


    String apiURL = "https://openapi.naver.com/v1/search/news.json?query=" + text;    // JSON 결과
    //String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text; // XML 결과


    Map<String, String> requestHeaders = new HashMap<>();
    requestHeaders.put("X-Naver-Client-Id", clientId);
    requestHeaders.put("X-Naver-Client-Secret", clientSecret);
    String responseBody = get(apiURL,requestHeaders);


    System.out.println(responseBody);
  }

  private String get(String apiUrl, Map<String, String> requestHeaders){
    HttpURLConnection con = connect(apiUrl);
    try {
      con.setRequestMethod("GET");
      for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
        con.setRequestProperty(header.getKey(), header.getValue());
      }


      int responseCode = con.getResponseCode();
      if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
        return readBody(con.getInputStream());
      } else { // 오류 발생
        return readBody(con.getErrorStream());
      }
    } catch (IOException e) {
      throw new RuntimeException("API 요청과 응답 실패", e);
    } finally {
      con.disconnect();
    }
  }


  private HttpURLConnection connect(String apiUrl){
    try {
      URL url = new URL(apiUrl);
      return (HttpURLConnection)url.openConnection();
    } catch (MalformedURLException e) {
      throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
    } catch (IOException e) {
      throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
    }
  }


  private String readBody(InputStream body){
    InputStreamReader streamReader = new InputStreamReader(body);


    try (BufferedReader lineReader = new BufferedReader(streamReader)) {
      StringBuilder responseBody = new StringBuilder();


      String line;
      while ((line = lineReader.readLine()) != null) {
        responseBody.append(line);
      }


      return responseBody.toString();
    } catch (IOException e) {
      throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
    }
  }
}
