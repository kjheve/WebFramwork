package com.kh.demo.domain.pubdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class StockPrice {
  private final WebClient webClient;
  private String baseUrl = "https://apis.data.go.kr";
  //파라미터
  private final String serviceKey = "ujJZyIpqUNjIlySqbN9TbaGpFnw1sLUAQJOiXf36bBii4fZpdeAqVE5kQG6H71I+I4G+rxQ+tXt0emq0IQ75mw==";

//  private final String numOfRows = "20"; // 한페이지에 보여줄 레코드수
//  private final String pageNo = "1"; // 요청 페이지 (시작페이지 1)
  private final String resultType = "json";
  // private final String itmsNm = "SK하이닉스";
//  private final String beginBasDt = "20240201";
//  private final String endBasDt = "20240221";


  @Autowired
  public StockPrice(WebClient.Builder webClientBilder) {

    // 서비스키값 이상할 때 (URL에 대해 인코딩 하지 않기)
//    DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(baseUrl);
//    factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);

    this.webClient = webClientBilder
//            .uriBuilderFactory(factory)
            .baseUrl(baseUrl)
//            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE) //json포맷요청
            .build();
  }

  public String reqStockPrice(String itmsNm, String beginBasDt, String endBasDt, String pageNo, String numOfRows) {

    // http get 요청하면 http 응답메시지 수신
    Mono<String> response = webClient.get()
            .uri(uriBuilder -> uriBuilder
                    .path("/1160100/service/GetStockSecuritiesInfoService/getStockPriceInfo")                 //베이스url 이하 경로
                    .queryParam("serviceKey", serviceKey)
                    .queryParam("numOfRows", numOfRows)
                    .queryParam("pageNo", pageNo)
                    .queryParam("resultType", resultType)
                    .queryParam("itmsNm", itmsNm)
                    .queryParam("beginBasDt", beginBasDt)
                    .queryParam("endBasDt", endBasDt)
                    .build())
//            .header("X-Naver-Client-Id",Client_Id)
//            .header("X-Naver-Client-Secret",Client_Secret)
            .retrieve()
            .bodyToMono(String.class);
    // http응답메시지 바디를 읽어 문자열로 반환
    String data = response.block();
    return data;
  }


}