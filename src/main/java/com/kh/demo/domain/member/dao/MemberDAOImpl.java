package com.kh.demo.domain.member.dao;

import com.kh.demo.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@Repository
//@Primary // 동일 타입의 객체가 2개이상 존재할 때 최우선순위로 주입받을 수 있도록 설정하는 어노테이션
@RequiredArgsConstructor // final 생성자
public class MemberDAOImpl implements MemberDAO {
  private final NamedParameterJdbcTemplate template;
//  public MemberDAOImpl(NamedParameterJdbcTemplate template) {
//    this.template = template;
//  }

  // 1) 회원가입 ------------------------------------------------------
  @Override
  public Long insertMember(Member member) {
    // sql 작성
    StringBuffer sql = new StringBuffer();
    sql.append("insert into member(member_id,email,passwd,nickname) ");
    sql.append("values(member_member_id_seq.nextval, :email, :passwd, :nickname) ");

    // 1. sql 파라미터 매핑
    SqlParameterSource param = new BeanPropertySqlParameterSource(member);
    // 2. insert 된 레코드 값 가져오기 (변경된 레코드 정보 읽어오는 용도)
    KeyHolder keyHolder = new GeneratedKeyHolder();
    // 3. sql 실행
    template.update(sql.toString(), param, keyHolder,
            new String[]{"member_id"}); // 4번째 매개변수 : 가져올 레코드 컬럽
    // 4. insert된 레코드에서 회원 번호 추출
    // long member_id = keyHolder.getKey().longValue();
    Long member_id = ((BigDecimal)keyHolder.getKeys().get("member_id")).longValue();
    return member_id;
  }

  // 1-1) 이메일 존재 유무 (회원 아이디 조회)-------------------------------------------
  @Override
  public boolean existEmail(String email) {
    String sql = "select count(email) from member where email = :email ";

    // 파라미터 1개 매핑
    Map<String, String> map = Map.of("email", email);

    // 단일행 queryForObject() // 단일타입 Integer
    Integer cnt = template.queryForObject(sql, map, Integer.class);

    return (cnt == 1) ? true : false;
  }

  // 2) 회원 조회 (입력한 아이디와 비밀번호 일치하는지)
  @Override
  public Optional<Member> findByEmailAndPasswd(String email, String passwd) {
    StringBuffer sql = new StringBuffer();
    sql.append("select * from member ");
    sql.append("where email = :email ");
    sql.append("  and passwd = :passwd ");


    Map<String, String> map = Map.of("email", email, "passwd", passwd);

    try{                     //단일 행, 다중 열
      Member member = template.queryForObject(sql.toString(), map, new BeanPropertyRowMapper<>(Member.class));
      return Optional.of(member);
    }catch(EmptyResultDataAccessException e){
      return Optional.empty();
    }
  }
}

