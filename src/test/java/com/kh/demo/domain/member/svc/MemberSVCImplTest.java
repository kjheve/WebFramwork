package com.kh.demo.domain.member.svc;

import com.kh.demo.domain.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
class MemberSVCImplTest {
  @Autowired
  MemberSVC memberSVC;

  @Test
  @DisplayName("회원가입")
  @Transactional // 테스트 환경 : 실행 후 rollback
  void joinMember() {
    Member member = new Member();
    member.setEmail("user05@naver.com");
    member.setPasswd("user05");
    member.setNickname("사용자5");
    Long memberId = memberSVC.joinMember(member);
    log.info("memberId = {}", memberId);
  }
}
