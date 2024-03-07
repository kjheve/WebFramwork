// test의 url.html을 위한 컨트롤러 (타임리프연습)


package com.kh.demo.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/a") // http://localhost:9080/test
public class TestControllerV2 {

  @GetMapping("/{v}/c")
  public String case1(@PathVariable("v") String value) {
    log.info("value={}", value);
    return "test/url";
  }

  @GetMapping("/{v1}/{v2}")
  public String case2(@PathVariable("v1") String value1,
                      @PathVariable("v2") String value2) {
    log.info("value1={}, value2={}", value1, value2);
    return "test/url";
  }

  @GetMapping("/{v1}/{v2}/z")
  public String case3(@PathVariable("v1") String value1,
                      @PathVariable("v2") String value2,
                      @RequestParam("d") String value3,
                      @RequestParam("e") String value4) {
    log.info("value1={}, value2={}, value3={}, value4={}", value1, value2, value3, value4);
    return "test/url";
  }


}
