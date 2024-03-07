// ProductSVC 구현 클레스

package com.kh.demo.domain.product.svc;

import com.kh.demo.domain.entity.Product;
import com.kh.demo.domain.product.dao.ProductDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service // SVC 역할을 하는 클래스
public class ProductSVCImpl implements ProductSVC{

  private ProductDAO productDAO; // 이 객체가 생성될 때

  ProductSVCImpl(ProductDAO productDAO) {
    this.productDAO = productDAO;
  }

  // ★등록
  @Override
  public Long save(Product product) {
    return productDAO.save(product);
  }

  // ★조회
  @Override
  public Optional<Product> findByID(Long productId) {
    return productDAO.findByID(productId);
  }

  // ★ 목록
  @Override
  public List<Product> findAll() {
    return productDAO.findAll();
  }
  // ★ 목록(페이징)
  @Override
  public List<Product> findAll(Long reqPage, Long recordCnt) {
    return productDAO.findAll(reqPage, recordCnt);
  }

  // ★ 단건삭제
  @Override
  public int deleteById(Long productId) {
    return productDAO.deleteById(productId);
  }

  // ★ 여러건삭제
  @Override
  public int deleteByIds(List<Long> productIds) {
    return productDAO.deleteByIds(productIds);
  }

  // ★ 수정
  @Override
  public int updateById(Long productId, Product product) {
    return productDAO.updateById(productId, product);
  }

  // ♣총 레코드 건수 (CSR)
  @Override
  public int totalCnt() {
    return productDAO.totalCnt();
  }

}
