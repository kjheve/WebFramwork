package com.kh.demo.web;

import com.kh.demo.domain.entity.Product;
import com.kh.demo.domain.product.svc.ProductSVC;
import com.kh.demo.web.api.ApiResponse;
import com.kh.demo.web.req.product.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController // @Contoller + @ResponseBody
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ApiProductController {

  private final ProductSVC productSVC;
//  public ApiProductController(ProductSVC productSVC) {
//    this.productSVC = productSVC;
//  }

  // ♣1. 등록 ----------------------------------------------------------
//  @ResponseBody
  @PostMapping  // POST http://localhost:9080/api/products
  public ApiResponse<?> added(@RequestBody ReqSave reqSave) { // @RequestBody : 요청 메세지 바디의 json포맷 무자열 => 자바 객체로 매핑
    log.info("reqSave={}", reqSave); // 값이 제대로 넘어오는지 확인

    // 1) 유효성 검증

    // 2) 상품 등록처리
    Product product = new Product();
    // 아래의 코드는 BeanUtils를 이용하는 것과 같음
//    product.setPname(reqSave.getPname());
//    product.setQuantity(reqSave.getQuantity());
//    product.setPrice(reqSave.getPrice());
    BeanUtils.copyProperties(reqSave, product); // BeanUtils를 이용하여 요청시 데이터와 상품객체의 데이터를 매칭
    Long productId = productSVC.save(product);

    ResSave resSave = new ResSave(productId, reqSave.getPname());// JSON 데이터에 찍힐 body만 추출

    // ApiResponse<Product> res = ApiResponse.createApiResponse(ResCode.OK.getCode(), ResCode.OK.name(), product);
    String rtDetail = "상품번호 " + productId + " 가 등록 되었습니다";
    // ResSave으로 받아서 찍힐 body만
    ApiResponse<ResSave> res = ApiResponse.createApiResponseDetail(
            ResCode.OK.getCode(), ResCode.OK.name(), rtDetail, resSave);
    return res;
  }

  // ♣2. 조회 ----------------------------------------------------------
//  @ResponseBody
  @GetMapping("/{pid}")
  public ApiResponse<?> findById(@PathVariable("pid") Long productId) {
    log.info("productId = {}", productId); // 잘 받아오는지 확인 (컨트롤러까지 오는지)
    Optional<Product> optionalProduct = productSVC.findByID(productId);

    ApiResponse<ResFindById> res = null;

    // Optional 객체의 값이 있으면 (상품을 찾은 경우)
    if (optionalProduct.isPresent()) {
      Product findedProduct = optionalProduct.get();
      ResFindById resFindById = new ResFindById();
      // BeanUtils를 이용하여 찾은 상품(findedProduct)와 응답시상품(ResFindById)를 매칭
      BeanUtils.copyProperties(findedProduct, resFindById);

      res = ApiResponse.createApiResponse(ResCode.OK.getCode(), ResCode.OK.name(), resFindById);

      // 상품을 못 찾은 경우
    } else {
      String rtDetail = "찾고자하는 상품정보가 없습니다";
      res = ApiResponse.createApiResponseDetail(
              ResCode.FAIL.getCode(), ResCode.FAIL.name(), rtDetail, null);
    }
    return res;
  }

  // ♣5. 수정 ----------------------------------------------------------
//  @ResponseBody
  @PatchMapping("/{pid}")
  public ApiResponse<?> update(@PathVariable("pid") Long productId,
                               @RequestBody ReqUpdate reqUpdate) {
    log.info("productId = {}", productId); // 수정할 데이터들이 왔는지 확인
    log.info("reqUpdate = {}", reqUpdate);
    // 1) 유효성 체크

    // 2) 수정 처리
    Product product = new Product();
    BeanUtils.copyProperties(reqUpdate, product);

    int updateCnt = productSVC.updateById(productId, product);

    ApiResponse<ResUpdate> res = null;
    if (updateCnt == 1) {
      ResUpdate resUpdate = new ResUpdate();
      BeanUtils.copyProperties(reqUpdate, resUpdate);
      resUpdate.setProductId(productId);

      res = ApiResponse.createApiResponse(ResCode.OK.getCode(), ResCode.OK.name(), resUpdate);
    } else {
      String rtDetail = "수정 실패";
      res = ApiResponse.createApiResponseDetail(
              ResCode.FAIL.getCode(), ResCode.FAIL.name(), rtDetail, null);
    }

    return res;
  }

  // ♣4. 삭제 ----------------------------------------------------------
//  @ResponseBody
  @DeleteMapping("/{pid}")
  public ApiResponse<?> delete(@PathVariable("pid") Long productId) {
    int deletedCnt = productSVC.deleteById(productId);

    ApiResponse<ResUpdate> res = null;

    if (deletedCnt == 1) {
      // 삭제됨
      res = ApiResponse.createApiResponse(ResCode.OK.getCode(), ResCode.OK.name(), null);
    } else {
      // 삭제할 데이터가 없음
      String rtDetail = "삭제 실패";
      res = ApiResponse.createApiResponseDetail(
              ResCode.FAIL.getCode(), ResCode.FAIL.name(), rtDetail, null);
    }
    return res;
  }

  /*  // ♣3. 목록 ----------------------------------------------------------
  //  @ResponseBody
    @GetMapping
    public ApiResponse<?> list() {

      // 로딩 이미지 확인하기 위해 쓰레드를 만들어 3초 지연시키게 만들었음
      try {
        Thread.sleep(3000); // 3초 지연
      } catch (InterruptedException e) {
        throw new RuntimeException();
      }

      List<Product> list = productSVC.findAll(); // 목록찾기
      String rtDetail = "상품정보가 없습니다";
      ApiResponse<List<Product>> res = null;
      if (list.size() > 0) {
        res = ApiResponse.createApiResponse(ResCode.OK.getCode(), ResCode.OK.name(), list);
        int totCnt = productSVC.totalCnt();// 테이블 총레코드 카운터
        // 원래는 총 레코드 카운터를 세는 DAO가 있어야함
        // res.setTotalCnt(list.size());
        res.setTotalCnt(totCnt);
      } else {
        res = ApiResponse.createApiResponseDetail(
                ResCode.FAIL.getCode(), ResCode.FAIL.name(), "등록된 상품이 1건도 없습니다", list);
      }
      return res;
    }*/
// ♣3-1. 목록(페이징) ----------------------------------------------------------
  @GetMapping
  public ApiResponse<?> list(
          @RequestParam("reqPage") Long reqPage,
          @RequestParam("recCnt") Long recCnt) {

    // 로딩 이미지 확인하기 위해 쓰레드를 만들어 1초 지연시키게 만들었음
    try {
      Thread.sleep(1000); // 3초 지연
    } catch (InterruptedException e) {
      throw new RuntimeException();
    }

    List<Product> list = productSVC.findAll(reqPage, recCnt); // 목록찾기

    ApiResponse<List<Product>> res = null;
    if (list.size() > 0) {
      res = ApiResponse.createApiResponse(ResCode.OK.getCode(), ResCode.OK.name(), list);
      int totCnt = productSVC.totalCnt();// 테이블 총레코드 카운터
      res.setTotalCnt(totCnt);
      res.setReqPage(reqPage.intValue()); // 정수로 변환
      res.setReqPage(recCnt.intValue());
    } else {
      res = ApiResponse.createApiResponseDetail(
              ResCode.FAIL.getCode(), ResCode.FAIL.name(), "등록된 상품이 1건도 없습니다", list);
    }
    return res;
  }
}
