package com.kh.demo.test;

// 정규표현식 공부

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

@Slf4j
public class RegExTest {


  @Test
  @DisplayName("정규표현식 - 숫자")
  void t1() {
    String str = "123";
    String pattern = "[0-9]{3,10}";

    if (Pattern.matches(pattern, str)) {
      log.info("패턴일치");
    } else {
      log.info("패턴불일치");
    }
  }

  @Test
  @DisplayName("정규표현식 - 숫자")
  void t2() {
    String str = "12";
    String pattern = "\\d{3,10}";

    if (Pattern.matches(pattern, str)) {
      log.info("패턴일치");
    } else {
      log.info("패턴불일치");
    }
  }


  @Test
  @DisplayName("정규표현식-전화번호")
  void t3() {
    String str = "010-1234-5678";
    String pattern = "\\d{3}-\\d{4}-\\d{4}";

    if (Pattern.matches(pattern, str)) {
      log.info("패턴일치");
    } else {
      log.info("패턴불일치");
    }
  }

  @Test
  @DisplayName("정규표현식-pname")
  void t4() {
    String str = "abc123_한글-";
    String pattern = "^[a-zA-Z0-9가-힣_-]{3,10}$";

    if (Pattern.matches(pattern, str)) {
      log.info("패턴일치");
    } else {
      log.info("패턴불일치");
    }
  }

  @Test
  @DisplayName("이메일패턴 체크")
  void t5(){
    String email = "example@example.com"; // 검사할 이메일 주소 입력
    String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    // 패턴과 입력 이메일 주소를 비교
    if (Pattern.matches(emailPattern, email)) {
      log.info("유효한 이메일 주소입니다.");
    } else {
      log.info("이메일 주소가 유효하지 않습니다.");
    }
  }

}
