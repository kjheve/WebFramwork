package com.kh.demo.domain.member.svc;

import com.kh.demo.domain.entity.Member;

import java.util.Optional;

public interface MemberSVC {
  // 1) 회원가입
  Long joinMember(Member member);

  // 1-1) 이메일 존재 유무 (회원 아이디 조회)
  boolean existEmail(String email);

  // 2) 회원 조회
  Optional<Member> findByEmailAndPasswd(String email, String passwd);

  // 3) 회원 수정

  // 4) 회원 탈퇴
}
