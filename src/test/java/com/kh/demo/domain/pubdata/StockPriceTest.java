package com.kh.demo.domain.pubdata;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class StockPriceTest {

  @Autowired
  StockPrice stockPrice;

  @Test
  void getStockPrice(){
    String data = stockPrice.reqStockPrice("SK하이닉스", "20240201", "20240221", "1", "10");
    log.info("data={}", data);
  }
}
