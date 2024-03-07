package com.kh.demo.web.form.product;

import lombok.Data;

@Data
public class UpdateForm {
  // input 태그의 name속성의 값과 같아야함
  private Long productId;
  private String pname;
  private Long quantity;
  private Long price;
}
