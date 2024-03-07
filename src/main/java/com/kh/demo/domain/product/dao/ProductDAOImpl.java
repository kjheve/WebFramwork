package com.kh.demo.domain.product.dao;

import com.kh.demo.domain.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository // DAO 역할을 하는 클래스
public class ProductDAOImpl implements ProductDAO {

  private final NamedParameterJdbcTemplate template;

  ProductDAOImpl(NamedParameterJdbcTemplate template) {
    this.template = template;
  }


  // ★등록
  @Override
  public Long save(Product product) {
    StringBuffer sql = new StringBuffer();
    sql.append("insert into product(product_id,pname,quantity,price) ");
    sql.append("values(product_product_id_seq.nextval, :pname, :quantity, :price) ");

    // SQL파라미터(매개변수) 자동매핑
    SqlParameterSource param = new BeanPropertySqlParameterSource(product); // (파라미터 받아오기 첫번째 방법)
    KeyHolder keyHolder = new GeneratedKeyHolder();
//    template.update(sql.toString(), param, keyHolder, new String[]{"product_id", "pname"}); // ★두개 넣으면 에러뜸
    template.update(sql.toString(), param, keyHolder, new String[]{"product_id"});
    long productId = keyHolder.getKey().longValue(); // 상품 아이디 // ★여기서 한개만 받기 때문


//    template.update(sql.toString(), param, keyHolder, new String[]{"product_id", "pname", "quantity"});
////    log.info("keyHolder={}", keyHolder.getKeys());
//    String pname = (String)keyHolder.getKeys().get("pname");
//    log.info("pname = {}", pname);
//    Long product_id = ((BigDecimal)keyHolder.getKeys().get("product_id")).longValue();
//    log.info("product_id = {}", product_id);
//return product_id;
    return productId;
  }

  // ★조회
  @Override
  public Optional<Product> findByID(Long productId) {
    StringBuffer sql = new StringBuffer();
    sql.append("select product_id, pname, quantity, price, cdate, udate ");
    sql.append("from product ");
    sql.append("where product_id = :productId ");

    // RowMapper
    // Product product = template.queryForObject(sql.toString(), map, BeanPropertyRowMapper.newInstance(Product.class));
                          // queryForObject는 값이 없으면 예외를 발생시킴 그래서 아래처럼

    try {
      Map<String, Long> map = Map.of("productId", productId); // (키, 값) (파라미터 받아오기 2번째방법)
      Product product = template.queryForObject(sql.toString(), map, BeanPropertyRowMapper.newInstance(Product.class));
      return Optional.of(product);

    } catch (EmptyResultDataAccessException e) {
      //조회 결과가 없는 경우
      return Optional.empty();
    }
  }

  // ★목록 전체 -------------------------------------------
  @Override
  public List<Product> findAll() {
    StringBuffer sql = new StringBuffer();
    sql.append("select product_id, pname, quantity, price, cdate, udate ");
    sql.append("from product ");
    sql.append("order by product_id desc ");

    List<Product> list = template.query(sql.toString(), BeanPropertyRowMapper.newInstance(Product.class));

    return list;
  }

  // ★☆★☆★목록 페이징 ★☆★☆★ ------------------------------------------------
  @Override
  public List<Product> findAll(Long reqPage, Long recCnt) {
    StringBuffer sql = new StringBuffer();
    sql.append("select product_id, pname, quantity, price, cdate, udate ");
    sql.append("from product ");
    sql.append("order by product_id desc ");
    sql.append("offset (:reqPage-1) * :recCnt rows ");
    sql.append("fetch first :recCnt rows only ");

    Map<String, Long> map = Map.of("reqPage", reqPage, "recCnt", recCnt);
    List<Product> list = template.query(sql.toString(), map, BeanPropertyRowMapper.newInstance(Product.class));

    return list;
  }

  // ★단건삭제
  @Override
  public int deleteById(Long productId) {
    StringBuffer sql = new StringBuffer();
    sql.append("delete from product ");
    sql.append("where product_id = :productId ");

    SqlParameterSource param = new MapSqlParameterSource()
            .addValue("productId", productId); // (파라미터 받아오기 3번째 방법) 더있으면 .addValue로 계속해서..
    int deleteRowCnt = template.update(sql.toString(), param);

    return deleteRowCnt;
  }

  // ★여러건삭제
  @Override
  public int deleteByIds(List<Long> productIds) {
    StringBuffer sql = new StringBuffer();
    sql.append("delete from product ");
    sql.append("where product_id in (:productIds) ");

    Map<String, List<Long>> map = Map.of("productIds", productIds); // (키, 값) (파라미터 받아오기 2번째방법)
    int deletedRowCnt = template.update(sql.toString(), map);
    return deletedRowCnt;
  }

  // ★수정
  @Override
  public int updateById(Long productId, Product product) {
    StringBuffer sql = new StringBuffer();
    sql.append("update product ");
    sql.append("set pname = :pname, ");
    sql.append("    quantity = :quantity, ");
    sql.append("    price = :price, ");
    sql.append("    udate = default ");
    sql.append("where product_id = :productId ");

    // sql 파라미터 변수에 값 매핑
    SqlParameterSource param = new MapSqlParameterSource().addValue("pname", product.getPname())
            .addValue("quantity", product.getQuantity())
            .addValue("price", product.getPrice())
            .addValue("productId", productId);
    // update 수행 후 변경된 행수 반환
    int updateRowCnt = template.update(sql.toString(), param);
    return updateRowCnt;
  }

  // ♣ 총 레코드 건수 (CSR)
  @Override
  public int totalCnt() {
    // 1줄이라 스트링버퍼가 필요없음
    String sql = "select count(product_id) from product ";
    SqlParameterSource param = new MapSqlParameterSource();
    Integer cnt = template.queryForObject(sql, param, Integer.class);
    // 스칼라값이라 queryForObject
//    Integer cnt = template.queryForObject(sql, Collections.emptyMap(), Integer.class);
    return cnt;
  }


}
