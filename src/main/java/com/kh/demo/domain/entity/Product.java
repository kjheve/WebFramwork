package com.kh.demo.domain.entity;

import lombok.Data;

import java.time.LocalDateTime;

// wrapper class
// byte -> Byte , short -> Short , char -> Character, int -> Integer, long -> Long
// boolean -> Boolean, double -> Double, float -> Float


@Data
public class Product {
  private Long productId; // 상품 아이디 PRODUCT_ID NUMBER(10, 0)
  private String pname; // 상품명 PNAME VARCHAR2(30 BYTE)
  private Long quantity;	// 수량 QUANTITY NUMBER(10,0)
  private Long price;     // 가격 PRICE	NUMBER(10,0)
  private LocalDateTime cdate; // 생성일시 CDATE	TIMESTAMP(6)
  private LocalDateTime udate; // 수정일시 UDATE	TIMESTAMP(6)
}
