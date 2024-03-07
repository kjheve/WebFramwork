package com.kh.demo.domain.member.svc;

import com.kh.demo.domain.entity.Member;
import com.kh.demo.domain.member.dao.MemberDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor // final의 생성자 생성 어노테이션
public class MemberSVCImpl implements MemberSVC{

  private final MemberDAO memberDAO;

  // 생성자를 이용해서 MemberDAO객체를 주입 받는 방법
//  @Autowired
//  public MemberSVCImpl(MemberDAO memberDAO) {
//    this.memberDAO = memberDAO;
//  }

  // 생성자를 이용해서 MemberDAO객체, 구현 클래스 impl2를 주입 받는 방법
//  @Autowired
//  public MemberSVCImpl(@Qualifier("memberDAOImpl2") MemberDAO memberDAO) {
//    this.memberDAO = memberDAO;
//  }

  // 회원가입
  @Override
  public Long joinMember(Member member) {
    return memberDAO.insertMember(member);
  }

  // 이메일 유무(회원 아이디 조회)
  @Override
  public boolean existEmail(String email) {
    return memberDAO.existEmail(email);
  }

  // 회원조회
  @Override
  public Optional<Member> findByEmailAndPasswd(String email, String passwd) {
    return memberDAO.findByEmailAndPasswd(email, passwd);
  }
}
