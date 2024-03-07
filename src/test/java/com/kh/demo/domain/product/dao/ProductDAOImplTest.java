package com.kh.demo.domain.product.dao;

import com.kh.demo.domain.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@SpringBootTest // springboot 테스트환경 실행
class ProductDAOImplTest {

  @Autowired // springboot 컨테이너의 객체를 주입 받는다.
  ProductDAO productDAO;

  @Test
  @DisplayName("상품등록")
  void save() {

    Product product = new Product();
    product.setPname("노트북");
    product.setQuantity(3L);
    product.setPrice(2_000_000L);


    Long productId = productDAO.save(product);
//    log.info("productId={}{}", productId, "2"); // 오류발생, 정상작동은 함 (오류+DB에 목록의 제일 위로 올라감)
    log.info("productId={}", productId);
  }

  @Test
  @DisplayName("상품여러건등록")
  void saveMultipleProducts() {
    for (long i = 1; i < 156; i++) {
      Product product = new Product();
      product.setPname("노트북"+i);
      product.setQuantity(i+10);
      product.setPrice(i*2);
      productDAO.save(product);
    }
//    log.info("productId={}{}", productId, "2"); // 오류발생, 정상작동은 함 (오류+DB에 목록의 제일 위로 올라감)
//    log.info("productId={}", productId);
  }

  @Test
  @DisplayName("상품조회")
  void findByID() {
    Long productId = 1L;
    Optional<Product> findedProduct = productDAO.findByID(productId);
    Product product = findedProduct.orElse(null);
    log.info("product = {}", product);
  }

  @Test
  @DisplayName("상품목록")
  void findAll() {
    List<Product> list = productDAO.findAll();
    for (Product product : list) {
      log.info("product={}", product);
    }
    log.info("size={}", list.size());
  }
  @Test
  @DisplayName("상품목록(페이징)")
  void findAllByPaging() {
    List<Product> list = productDAO.findAll(1L, 10L);
    for (Product product : list) {
      log.info("product={}", product);
    }
    log.info("size={}", list.size());
  }

  @Test
  @DisplayName("상품삭제(단건)")
  void deleteById() {
    Long pid = 6L;
    int deleteRowCnt = productDAO.deleteById(pid);
    log.info("삭제건수={}", deleteRowCnt);
    // 삭제한게 존재하는지 확인
    Assertions.assertThat(deleteRowCnt).isEqualTo(1);
  }

  @Test
  @DisplayName("상품삭제(여러건)")
  void deleteByIds() {
    List<Long> pids = new ArrayList<>();
    pids.add(51L);
    pids.add(50L);
    pids.add(49L);
//    productDAO.deleteByIds(List.of(51L, 50L, 49L));
    int deleteRowCnt = productDAO.deleteByIds(pids);
    log.info("삭제건수={}", deleteRowCnt);
    Assertions.assertThat(deleteRowCnt).isEqualTo(pids.size());
  }

  @Test
  @DisplayName("상품수정")
  void updateById() {

    Long productId = 21L;
    Product product = new Product();
    product.setPname("대나무헬리곱터");
    product.setQuantity(1L);
    product.setPrice(1000L);

    int updatedRowCnt = productDAO.updateById(productId, product);
    log.info("updateRowCnt={}", updatedRowCnt);
    if (updatedRowCnt == 0) {
      Assertions.fail("변경 내역이 없습니다");
    }
    Optional<Product> optionalProduct = productDAO.findByID(productId);
    if (optionalProduct.isPresent()) {
      Product findedProduct = optionalProduct.get();
      Assertions.assertThat(findedProduct.getPname()).isEqualTo("대나무헬리곱터");
      Assertions.assertThat(findedProduct.getQuantity()).isEqualTo(1L);
      Assertions.assertThat(findedProduct.getPrice()).isEqualTo(1000L);
    } else {
      Assertions.fail("변경할 상품이 없습니다");
    }

  }

  @Test
  @DisplayName("테이블 총 건수")
  void totalCnt() {
    int totalCnt = productDAO.totalCnt();
    log.info("totalCnt = {} ", totalCnt);
    // 상품 조회해서 가져온 레코드 건수랑 같음
    List<Product> list = productDAO.findAll();

    Assertions.assertThat(totalCnt).isEqualTo(list.size());
  }
}
