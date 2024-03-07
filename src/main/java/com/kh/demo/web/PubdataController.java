package com.kh.demo.web;

import com.kh.demo.domain.pubdata.NaverNews;
import com.kh.demo.domain.pubdata.StockDivide;
import com.kh.demo.domain.pubdata.StockPrice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping("/pubdata")
@RequiredArgsConstructor // final 필드를 매개변수값으로 생성자를 자동 만들어줌
public class PubdataController {


  private final NaverNews naverNews; // 네이버 뉴스
  private final StockPrice stockPrice; // 주식 가격
  private final StockDivide stockDivide;
  // 주석처리 : @RequiredArgsConstructor 어노테이션을 해줘서 아래의 생성자 코드 주석처리
//  @Autowired
//  public PubdataController(NaverNews naverNews) {
//    this.naverNews = naverNews;
//  }

  // 뉴스정보------------------
  @GetMapping("/news") // GET, http://localhost:9080/pubdata/news
  public String news() {
    return "pubdata/news";
  }
  
  @ResponseBody
  @GetMapping("/news/search")
  public String search(@RequestParam("keyword") String keyword,
                       @RequestParam("start") int start,
                       @RequestParam("display") int display) {
    log.info("keyword={}", keyword);
    String data = naverNews.reqNews(keyword, start, display);
    return data; // body에 바로 쏘려고 @ResponseBody 어노테이션도 추가
  }

  // 주식정보------------------
  @GetMapping("/stock")
  public String stock() {
    return "pubdata/stock";
  }

  @ResponseBody
  @GetMapping("/stock/search")
  public String stock_search(@RequestParam("itmsNm") String itmsNm,
                             @RequestParam("beginBasDt") String beginBasDt,
                             @RequestParam("endBasDt") String endBasDt,
                             @RequestParam("pageNo") String pageNo,
                             @RequestParam("numOfRows") String numOfRows) {
//    log.info("keyword={}", keyword);
    String data = stockPrice.reqStockPrice(itmsNm, beginBasDt, endBasDt, pageNo, numOfRows);
//    log.info("data={}", data);
    return data;
  }

  // 주식 배당 정보 ----------------------
  @GetMapping("/divide")
  public String divide() {
    return "pubdata/divide";
  }
  @ResponseBody
  @GetMapping("/divide/search")
  public String divide_search(@RequestParam("keyword") String keyword) {
    log.info("keyword={}", keyword);
    String data = stockDivide.reqDiviInfo(keyword);
    return data;
  }
}

