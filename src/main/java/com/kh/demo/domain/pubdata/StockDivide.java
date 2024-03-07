package com.kh.demo.domain.pubdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class StockDivide {
  private final WebClient webClient;
  private String baseUrl = "http://apis.data.go.kr";
  //파라미터
  private final String serviceKey =  "ujJZyIpqUNjIlySqbN9TbaGpFnw1sLUAQJOiXf36bBii4fZpdeAqVE5kQG6H71I%2BI4G%2BrxQ%2BtXt0emq0IQ75mw%3D%3D";

  private final String numOfRows = "20";
  private final String pageNo = "1";
  private final String resultType = "json";
//   private final String stckIssuCmpyNm = "LX";


  @Autowired
  public StockDivide(WebClient.Builder webClientBilder) {

    // 서비스키값 이상할 때 (인코딩)
//    DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(baseUrl);
//    factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);

    this.webClient = webClientBilder
//            .uriBuilderFactory(factory)
            .baseUrl(baseUrl)
//            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE) //json포맷요청
            .build();
  }

  public String reqDiviInfo(String keyword) {

    // http get 요청하면 http 응답메시지 수신
    Mono<String> response = webClient.get()
            .uri(uriBuilder -> uriBuilder
                    .path("/1160100/service/GetStocDiviInfoService/getDiviInfo") //베이스url 이하 경로
                    .queryParam("serviceKey", serviceKey)
                    .queryParam("numOfRows", numOfRows)
                    .queryParam("pageNo", pageNo)
                    .queryParam("resultType", resultType)
                    .queryParam("stckIssuCmpyNm", keyword)
                    .build())
            .retrieve()
            .bodyToMono(String.class);
    // http응답메시지 바디를 읽어 문자열로 반환
    String data = response.block();
    return data;
  }
}
