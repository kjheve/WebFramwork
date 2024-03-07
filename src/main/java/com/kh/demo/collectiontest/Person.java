package com.kh.demo.collectiontest;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ToString
@AllArgsConstructor // 모든 필드를 매개변수로 받는 생성자
@NoArgsConstructor // Default 생성자
public class Person {
  private String name;
  private int age;

  public void smile() {
    System.out.println("웃다");
  }
  public void eat() {
    System.out.println("먹다");
  }
}
