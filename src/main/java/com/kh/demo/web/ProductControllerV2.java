package com.kh.demo.web;


// V2 : 유효성검사 추가 된 코드

import com.kh.demo.domain.entity.Product;
import com.kh.demo.domain.product.svc.ProductSVC;
import com.kh.demo.web.form.product.AddForm;
import com.kh.demo.web.form.product.UpdateForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Slf4j
@Controller
@RequestMapping("/products") // URL 매핑 // http://localhost:9080/products
public class ProductControllerV2 {

  private ProductSVC productSVC;

  ProductControllerV2(ProductSVC productSVC) {
    this.productSVC = productSVC;
  }

  // ☆상품등록양식
  @GetMapping("/add") // Get, http://localhost:9080/productsv2/add
  public String addForm(Model model) {
    model.addAttribute("addForm", new AddForm());
    return "productsv2/add"; // view 이름 (상품 등록 화면)
    // resources/templates/productsv2/add.html 파일 이름이 맞아야함
  }

  // ★상품등록처리
  @PostMapping("/add") // Post, http://localhost:9080/productsv2/add
  public String add(AddForm addForm, // form객체 : 양식과 매핑되는 객체
                    Model model,
                    RedirectAttributes redirectAttributes) {

    log.info("addForm={}", addForm); // toString() 형태로 값을 찍어볼 수 있다.

    // \/\/\/\/\/\/\/\/\/\/ 유효성체크 \/\/\/\/\/\/\/\/\/\/\/\/
    // ※※※※※※※※필드 레벨
    // 1-1) 상품명
    String pattern = "^[a-zA-Z0-9가-힣_-]{3,10}$";
    // 패턴과 문자열을 비교
    if (!Pattern.matches(pattern, addForm.getPname())) {
      model.addAttribute("addForm", addForm);
      model.addAttribute("s_err_pname", "상품명은 10자 이내여야 합니다.");
      return "productsv2/add";
    }
    // 1-2) 수량
    pattern = "^\\d{1,10}$";
    // 패턴과 문자열을 비교
    if (!Pattern.matches(pattern, String.valueOf(addForm.getQuantity()))) {
      model.addAttribute("addForm", addForm);
      model.addAttribute("s_err_quantity", "숫자만 10자리까지");
      return "productsv2/add";
    }

    // 1-3) 가격
    pattern = "^\\d{1,10}$";
    // 패턴과 문자열을 비교
    if (!Pattern.matches(pattern, String.valueOf(addForm.getPrice()))) {
      model.addAttribute("addForm", addForm);
      model.addAttribute("s_err_price", "숫자만 10자리까지");
      return "productsv2/add";
    }

    // ※※※※※※※※글로벌 레벨
    if (addForm.getQuantity() * addForm.getPrice() > 10_000_000) {
      model.addAttribute("addForm", addForm);
      model.addAttribute("s_err_global", "총액 1000만원 초과");
      return "productsv2/add";
    }

    // ★상품등록
    Product product = new Product();
    product.setPname(addForm.getPname());
    product.setQuantity(addForm.getQuantity());
    product.setPrice(addForm.getPrice());

    Long productId = productSVC.save(product);
    log.info("상품번호={}", productId);

    redirectAttributes.addAttribute("pid", productId);
    return "redirect:/products/{pid}/detail"; // 상품조회 화면 302 GET, http://localhost:9080/productsv2/pid/detail
  }


  // ★★★★★★★★조회★★★★★★★★
  @GetMapping("/{pid}/detail")     // GET, http://localhost:9080/productsv2/상품번호(productId)/detail
  public String findById(@PathVariable("pid") Long productId, Model model) {

    Optional<Product> findedProduct = productSVC.findByID(productId);

    Product product = findedProduct.orElseThrow();
    model.addAttribute("product", product);

    return "productsv2/detailForm";
  }

  // ---------------------------------------------------------------------------------

  // ★단건 삭제
  @ResponseBody // 응답 메세지 바디에 직접 작성
  @DeleteMapping("/{pid}/del")
  public ResponseEntity<?> deleteById(@PathVariable("pid") Long productId) {
    log.info("deleteById={}", productId);

    // 1) 상품 삭제 -> 상품 테이블에서 삭제
    int deletedRowCnt = productSVC.deleteById(productId);
    if (deletedRowCnt == 1) {
      return ResponseEntity.ok().build(); //응답코드 200 OK
    } else {
      return ResponseEntity.notFound().build(); // 응답코드 404 NotFound
    }

    // 2) 상품 목록 -> 상품 테이블에서 상품목록을 가져와서 Model 객체에 저장 (이미 ★목록에 있음 redirect:로 요청)
//    return "redirect:/products"; // GET http://localhost:9080/productsv2/
  }

  // ★여러건 삭제
  @PostMapping("/del") // POST, http://localhost:9080/productsv2/del
  public String deleteByIds(@RequestParam("pids") List<Long> pids) {
    log.info("deleteByIds={}", pids);
    int deleteRowCnt = productSVC.deleteByIds(pids);

    return "redirect:/products";
  }

  // ☆수정양식
  @GetMapping("/{pid}/edit") // GET, http://localhost:9080/productsv2/상품번호/edit
  public String updateForm(@PathVariable("pid") Long productId, Model model) {
    Optional<Product> optionalProduct = productSVC.findByID(productId);
    Product findedproduct = optionalProduct.orElseThrow();

    model.addAttribute("product", findedproduct);
    return "productsv2/updateForm";
  }

  // ★수정처리
  @PostMapping("/{pid}/edit")
  public String update(
          // 경로변수 pid로부터 상품번호를 읽어온다
          @PathVariable("pid") Long productId,
          // 요청 메세지 바디로부터 대응되는 상품정보를 읽어온다.
          UpdateForm updateForm,
          // 리다이렉트시 경로변수에 값을 설정하기위해 사용
          RedirectAttributes redirectAttributes,
          Model model) {
    // \/\/\/\/\/\/\/\/\/\/ 유효성체크 \/\/\/\/\/\/\/\/\/\/\/\/
    // ※※※※※※※※필드 레벨
    // 1-1) 상품명
    String pattern = "^[a-zA-Z0-9가-힣_-]{3,10}$";
    // 패턴과 문자열을 비교
    if (!Pattern.matches(pattern, updateForm.getPname())) {
      model.addAttribute("product", updateForm);
      model.addAttribute("s_err_pname", "상품명은 10자 이내여야 합니다.");
      return "productsv2/updateForm";
    }
    // 1-2) 수량
    pattern = "^\\d{1,10}$";
    // 패턴과 문자열을 비교
    if (!Pattern.matches(pattern, String.valueOf(updateForm.getQuantity()))) {
      model.addAttribute("product", updateForm);
      model.addAttribute("s_err_quantity", "숫자만 10자리까지");
      return "productsv2/updateForm";
    }

    // 1-3) 가격
    pattern = "^\\d{1,10}$";
    // 패턴과 문자열을 비교
    if (!Pattern.matches(pattern, String.valueOf(updateForm.getPrice()))) {
      model.addAttribute("product", updateForm);
      model.addAttribute("s_err_price", "숫자만 10자리까지");
      return "productsv2/updateForm";
    }

    // ※※※※※※※※글로벌 레벨
    if (updateForm.getQuantity() * updateForm.getPrice() > 10_000_000) {
      model.addAttribute("product", updateForm);
      model.addAttribute("s_err_global", "총액 1000만원 초과");
      return "productsv2/updateForm";
    }


    // 정상처리
    Product product = new Product();
    product.setPname(updateForm.getPname());
    product.setQuantity(updateForm.getQuantity());
    product.setPrice(updateForm.getPrice());
    int updateRowCnt = productSVC.updateById(productId, product);

    redirectAttributes.addAttribute("pId", productId);
    return "redirect:/products/{pId}/detail";
  }

  /*
    // ★목록
    @GetMapping // GET, http://localhost:9080/products
    public String findAll(Model model) {
  //                        ,HttpServletRequest request) {
  //    HttpSession session = request.getSession(false);
  //
  //    if (session != null) {
  //      Member member = (Member) session.getAttribute("loginOk");
  //    } else {
  //      return "redirect:/";
  //    }

      List<Product> list = productSVC.findAll();
      model.addAttribute("list", list);

      return "productsv2/all";
    }
  */
  // ★목록 (페이징)
  @GetMapping // GET, http://localhost:9080/products?reqPage=2&recCnt=10
  public String findAllByPaging(
          Model model,
          @RequestParam(value = "reqPage", defaultValue = "1") Long reqPage, // 요청 페이지
          @RequestParam(value = "recCnt", defaultValue = "10") Long recCnt,  // 레코드 수
          @RequestParam(value = "cpgs", defaultValue = "1") Long cpgs, // 페이지 그룹 시작번호
          @RequestParam(value = "cp", defaultValue = "1") Long cp) { // 현재 페이지

    List<Product> list = productSVC.findAll(reqPage, recCnt);
    int totalCnt = productSVC.totalCnt();

    model.addAttribute("list", list);
    // 뷰에서 totalCnt도 참고 할 수 있게 추가
    model.addAttribute("totalCnt", totalCnt);
    model.addAttribute("cpgs", cpgs);
    model.addAttribute("cp", cp);

    return "productsv2/allByPaging";
  }
}
