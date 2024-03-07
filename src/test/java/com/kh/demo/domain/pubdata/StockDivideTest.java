package com.kh.demo.domain.pubdata;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class StockDivideTest {

  @Autowired
  StockDivide stockDivide;

  @Test
  void reqStockPrice() {
    String data = stockDivide.reqDiviInfo("LX");
    log.info("data={}", data);
  }
}