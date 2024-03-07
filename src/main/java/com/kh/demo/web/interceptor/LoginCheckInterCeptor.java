package com.kh.demo.web.interceptor;

import com.kh.demo.web.form.member.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import java.net.URLEncoder;

@Slf4j
public class LoginCheckInterCeptor implements HandlerInterceptor {
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    // 리다이렉트 URL만들기
    String redirectUrl = null;

    // 요청 파라미터 정보가 있는 경우 ex) http://localhost:9080/products?aaa=xxx&bbb=yyy
    String requestURI = request.getRequestURI(); // products
    log.info("요청uri = {}", requestURI);               // ex) /products/add
    log.info("요청url = {}", request.getRequestURL());  // ex) http://localhost:9080/products/add

    // 요청 쿼리 파라미터 정보가 있는 경우
    if(request.getQueryString() != null){  // getQueryString()을 하면 ? 뒤에 쿼리파라미터를 가져올 수 있다.
      // 요청파리미터 인코딩 (한글이 올 수 있기 때문에 UTF-8로 인코딩)
      String queryString = URLEncoder.encode(request.getQueryString(), "UTF-8"); // aaa=xxx&bbb=yyy
      StringBuffer str = new StringBuffer();
      redirectUrl = str.append(requestURI).append("?").append(queryString).toString(); // products?aaa=xxx&bbb=yyy
    } else { // 요청 쿼리 파라미터가 없는 경우
      redirectUrl = requestURI; // products
    }

    // 세션 정보 읽어오기
    HttpSession session = request.getSession(false);
    if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
      log.info("미인증 상태");
      // 302 http://localhost:9080/login
      // 리다이렉트는 response를 이용
      response.sendRedirect("/login?redirectUrl="+redirectUrl);
      return false; // 다음 인터셉터 포함하여 컨트롤러 수행하지 않음
    }
    return true;
  }
}
