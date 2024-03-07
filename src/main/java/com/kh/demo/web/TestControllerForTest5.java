package com.kh.demo.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/b")
public class TestControllerForTest5 {
  @GetMapping
  public String t1(@RequestParam(value = "name", required = false) String name,
                   @RequestParam(value = "age", required = false) Integer age) {
    log.info("name={}, age={}", name, age);
    return "test/test5";
  }
}
