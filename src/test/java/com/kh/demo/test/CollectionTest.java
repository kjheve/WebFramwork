package com.kh.demo.test;

import com.kh.demo.collectiontest.Person;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Slf4j
//@SpringBootTest
public class CollectionTest {

  @Autowired
  Person person;

  @Test
  void generic() {
//    class Person {
//      // String, int 타입이 딱 정해짐
//      String name;
//      int age;
//    }
    class Person<N, A> { // N, A에 String, Int는 안됨
      // 타입이 동적으로 바뀜
      N name;
      A age;
    }

    Person<String, Integer> person = new Person<String, Integer>();
    person.name = "홍길동";
    String name = person.name;
    log.info("name={}", name);

    Person<StringBuffer, Integer> person2 = new Person<StringBuffer, Integer>();
    person2.name = new StringBuffer();
    person2.name.append("홍").append("길");
    person2.name.append("떵");
    log.info("name={}", person2.name.toString());
  }

  @Test
  void list_1() {
//    log.info("person={}", person);

//    @Data
//    @AllArgsConstructor
//    class Person {
//      String name;
//      int age;
//    }

    //    List persons = new ArrayList(); // 이거는 익숙한데 여기서 <>가 들어감
    // 제네릭 : 클래스 내부에서 사용할 데이터 타입을 외부에서 지정하는 방법 // generic() 예제 확인
    List<Person> persons = new ArrayList<>(); // list 컬렉션 생성

    // ★추가
    Person p1 = new Person("홍길동", 30);
    persons.add(p1);
    persons.add(new Person("홍길순", 20));
    persons.add(new Person("홍길남", 10));

    // ★중복 추가 -> 중복추가 O
    persons.add(p1);
    log.info("persons = {}", persons);

    // ★순회
    for (Person person : persons) {
      log.info("name = {}, age = {}", person.getName(), person.getAge());
    }

    // ★제거
    persons.remove(p1); // p1을 제거
    persons.remove(0); // 인덱스 번호 [0] 제거
    log.info("persons={}", persons);

    // ★존재유무
    log.info("p1존재유무 = {}", persons.contains(p1));

    // ★요소갯수
    log.info("요소갯수 = {}", persons.size());

    // ★전체 요소 비우기
    persons.clear();

    // 요소갯수
    log.info("요소갯수 = {}", persons.size());


  }

  @Test
  void set_1() {
//    @Data
//    @AllArgsConstructor
//    class Person {
//      String name;
//      int age;
//    }

//    Set<Person> persons = new HashSet<Person>(); // 뒤에<>는 생략가능
    Set<Person> persons = new HashSet<>(); // set 컬렉션 객체 생성

    // ★추가
    Person p1 = new Person("홍길동", 30);
    persons.add(p1);
    persons.add(new Person("홍길순", 20));
    persons.add(new Person("홍길남", 10));

    // ★순회
    for (Person person : persons) {
      log.info("name = {}, age = {}", person.getName(), person.getAge());
    }

    // ★중복추가 -> 중복추가 X (덮어씌움)
    persons.add(p1);
    log.info("persons = {}", persons);
  }

  @Test
  void map_1() {
//    @Data
//    @AllArgsConstructor
//    class Person {
//      String name;
//      int age;
//    }

    Map<Integer, Person> persons = new HashMap<>();
    // ★추가
    Person p1 = new Person("홍길동", 30);
    persons.put(1, p1);
    persons.put(2, new Person("홍길순", 20));
    persons.put(3, new Person("홍길남", 40));

    // ★★★순회★★★
    // Map.Entry로 순회
    for (Map.Entry entry : persons.entrySet()) {
      log.info("key = {}, value = {}",entry.getKey(), entry.getValue());
    }
    // Key로 순회
    for (Integer key : persons.keySet()) {
      log.info("key = {} , value = {}", key, persons.get((key)));
    }
    // Value만 순회
    for (Person person : persons.values()) {
      log.info("person = {}", person);
    }

    // ★키값 중복 추가
    persons.put(1, p1);
    log.info("size = {}", persons.size()); // 크기확인

    // ★제거
    persons.remove(1, p1);

    // ★객체 유무
    log.info("p1 = {}", persons);
    log.info("p1존재유무 = {}", persons.containsKey(1));

  }


}
