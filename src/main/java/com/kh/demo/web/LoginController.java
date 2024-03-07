package com.kh.demo.web;

import com.kh.demo.domain.entity.Member;
import com.kh.demo.domain.member.svc.MemberSVC;
import com.kh.demo.web.form.member.LoginForm;
import com.kh.demo.web.form.member.LoginMember;
import com.kh.demo.web.form.member.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

  private final MemberSVC memberSVC;

  // 로그인 양식 ----------------------------------------------------
  @GetMapping("/login")
  public String loginForm() {
    return "login";
  }

  // 로그인 처리 --- // /login?redirectUrl = 사용자가 요청한 url=사용자가 요청한 url ----------------------
  @PostMapping("/login")
  public String login(LoginForm loginForm, HttpServletRequest request,
                      @RequestParam(value = "redirectUrl", required = false) String redirectUrl) {
    log.info("loginForm={}", loginForm);

    // 1. 유효성 체크

    // 2. 회원 유무 체크
    // 2-1. 회원 '아이디' 존재 유무 체크
    if(memberSVC.existEmail(loginForm.getEmail())) {
      // 회원 조회 (입력 : 아이디, 비밀번호)
      Optional<Member> optionalMember = memberSVC.findByEmailAndPasswd(loginForm.getEmail(), loginForm.getPasswd());

      // 3. 회원 조회가 된 경우 회원정보를 세션에 저장
      if (optionalMember.isPresent()) {
        // 세션 생성 : 세션정보가 있으면 있는 세션정보를, 없으면 새로이 생성 getSession(true)
        HttpSession session = request.getSession(true);
        // 회원 정보를 세션에 저장 (set)
        Member member = optionalMember.get();
        LoginMember loginMember = new LoginMember(
                member.getMember_id(), member.getEmail(),
                member.getNickname(), member.getGubun());
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
        // 나중에 view에서 뿌려줄때 ${session.loginOK} 찍을 때 ${session.loginOK.nickname}
        // -> loginOK를 loginMember로 바꿈
      }
    } else {
      return "login";
    }

    return redirectUrl != null ? "redirect:"+redirectUrl : "redirect:/";
  }

  // 로그아웃 ----------------------------------------------------
  @ResponseBody
  @PostMapping("/logout")
  public String logout(HttpServletRequest request) {

    String flag = "NoOK";

    // 세션이 있으면 가져오고 없으면 생성하지 않는다
    HttpSession session = request.getSession(false);

    if (session != null) {
      session.invalidate(); // 세션 제거 invalidate()
      flag = "OK";
    }
    return flag;
  }

}
