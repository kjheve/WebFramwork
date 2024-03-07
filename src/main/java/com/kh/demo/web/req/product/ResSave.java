// 등록시 응답
package com.kh.demo.web.req.product;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ResSave {
  private Long productId;
  private String pname;
}
