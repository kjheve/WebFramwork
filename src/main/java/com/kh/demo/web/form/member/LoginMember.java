package com.kh.demo.web.form.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginMember {
    private Long memberId;       // MEMBER_ID	NUMBER(10,0)
    private String email;         // EMAIL	VARCHAR2(50 BYTE)
    private String nickname;      // NICKNAME	VARCHAR2(30 BYTE)
    private String gubun;         // GUBUN	VARCHAR2(11 BYTE)

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public void setGubun(String gubun) {
    this.gubun = gubun;
  }

}

