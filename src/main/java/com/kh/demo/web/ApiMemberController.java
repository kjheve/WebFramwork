package com.kh.demo.web;

import com.kh.demo.domain.member.svc.MemberSVC;
import com.kh.demo.web.api.ApiResponse;
import com.kh.demo.web.req.member.ReqExitsEmail;
import com.kh.demo.web.req.product.ResCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class ApiMemberController {

  private final MemberSVC memberSVC;

  // 회원 중복 체크 -------------------
  @PostMapping("/dupchk")
  public ApiResponse<?> dupchk(@RequestBody ReqExitsEmail reqExitsEmail) {
    ApiResponse<?> res = null;

    log.info("reqExitsEmail = {}", reqExitsEmail);

    boolean existEmail = memberSVC.existEmail(reqExitsEmail.getEmail());
    if (existEmail) {
      res = ApiResponse.createApiResponse(ResCode.EXIST.getCode(), ResCode.EXIST.name(), null);
    } else {
      res = ApiResponse.createApiResponse(ResCode.NONE.getCode(), ResCode.NONE.name(), null);
    }
    return res;
  }

}
