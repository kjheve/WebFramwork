package com.kh.demo.domain.member.dao;

import com.kh.demo.domain.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@SpringBootTest
class MemberDAOImplTest {

  @Autowired
//  @Qualifier("memberDAOImpl2") // DAOImpl2를 가져오고 싶을 때
  MemberDAO memberDAO;

  @Test
  void Test() {
    log.info("memberDAO = {}", memberDAO.getClass().getName());
  }


  @Test
  @DisplayName("회원가입")
  @Transactional
  void insertMember() {
    Member member = new Member();
    member.setEmail("user04@naver.com");
    member.setPasswd("user04");
    member.setNickname("사용자4");
    Long memberId = memberDAO.insertMember(member);

    log.info("memberId = {}", memberId);
  }

  @Test
  @DisplayName("이메일 존재")
  void existMemberId() {
    boolean existEmail = memberDAO.existEmail("user01@naver.com");

    Assertions.assertThat(existEmail).isEqualTo(true);
    //                      실제값                  예상하는값
  }
  @Test
  @DisplayName("이메일 없는 경우")
  void notExistMemeberId() {
    boolean existEmail = memberDAO.existEmail("user00@naver.com");

    Assertions.assertThat(existEmail).isEqualTo(false);
    //                      실제값                  예상하는값
  }
  @Test
  @DisplayName("회원조회(O)")
  void findByEmailAndPasswd() {
    Optional<Member> optionalMember = memberDAO.findByEmailAndPasswd("user01@naver.com", "user01");

    // 결과 검증
    Assertions.assertThat(optionalMember).isPresent(); // Optional이 존재

    // 존재한 결과값 가져와서 각각 비교
    Member findedMember = optionalMember.get();
    Assertions.assertThat(findedMember.getEmail()).isEqualTo("user01@naver.com"); // 이메일 일치 여부
    Assertions.assertThat(findedMember.getPasswd()).isEqualTo("user01"); // 비밀번호 일치 여부
  }
  @Test
  @DisplayName("회원조회(X)")
  void notFindByEmailAndPasswd() {
    Optional<Member> optionalMember = memberDAO.findByEmailAndPasswd("user00@naver.com", "user00");

    // 결과 검증
    Assertions.assertThat(optionalMember).isEmpty(); // Optional이 없어야함
  }

}
