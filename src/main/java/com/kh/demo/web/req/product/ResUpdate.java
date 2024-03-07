// 수정 된 데이터 응답 보내기

package com.kh.demo.web.req.product;

import lombok.Data;

@Data
public class ResUpdate {
  private Long productId;
  private String pname;
  private Long quantity;
  private Long price;
}
