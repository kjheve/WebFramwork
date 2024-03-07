// 타임리프 test2을 위한 컨트롤러 (타임리프 연습)

package com.kh.demo.web;

import com.kh.demo.collectiontest.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/test") // http://localhost:9080/test
public class TestController {

//  @GetMapping("/t1") // 접속 URL
//  public String t1() {
//    return "test/test1"; // 폴더 경로 맞추기
//  }

  @GetMapping("/t1") // 접속 URL, Get, http://localhost:9080/test/t1
  public String t1(Model model) {

//    model.addAttribute(KEY, VALUE);
    model.addAttribute("key1", "KH인재교육원1");
    model.addAttribute("key2", "KH인재교육원2");
    model.addAttribute("key3", "KH인재교육원3");


    // ▶컬렉션을 공부하고 시작!
    // 이 List에는 여러개의 사람(Person) 객체를 담을 수 있다
    List<Person> persons = new ArrayList<>();
    Person p1 = new Person("홍길동", 30);
    Person p2 = new Person("홍길순", 20);
    Person p3 = new Person("홍길남", 40);

    persons.add(p1);
    persons.add(p2);
    persons.add(p3);

    model.addAttribute("persons", persons);


    return "test/test1"; // 폴더 경로 맞추기
  }

  @GetMapping("/t2")     // Get, http://localhost:9080/test/t2
  public String t2() {
    return "test/test2";
  }

  @GetMapping("/product") // Get, http://localhost:9080/test/product
  public String get(@RequestParam("pname") String pname,
                    @RequestParam("price") Long price) {

    log.info("get() 호출 됨");
    log.info("pname={}, price={}", pname, price);
    return "redirect:/test/t2";
  }

  @GetMapping("/product/{pname}/{price}") // Get, http://localhost:9080/test/product/상품명/가격
  public String get2(@PathVariable("pname") String pname,
                    @PathVariable("price") Long price) {

    log.info("get2() 호출 됨");
    log.info("pname={}, price={}", pname, price);
    return "redirect:/test/t2";
  }

  @PostMapping("/product") // Post, http://localhost:9080/test/product
  public String post(@RequestParam("pname") String pname,
                    @RequestParam("price") Long price) {

    log.info("post() 호출 됨");
    log.info("pname={}, price={}", pname, price);
    return "redirect:/test/t2";
  }





  // TestControllerV2에서 테스트할 url 매핑
  @GetMapping("/url/t1")         // GET http://localhost:9080/test/url/t1
  public String url_t1() {
    return "test/url";
  }

}
