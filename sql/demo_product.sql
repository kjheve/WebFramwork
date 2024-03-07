-------- 삭제
-- 테이블 삭제
drop table product;
-- 시퀀스 삭제
drop sequence product_product_id_seq;

---------
--상품관리
--------
create table product(
    product_id  number(10),
    pname       varchar(30),
    quantity    number(10),
    price       number(10),
    cdate       timestamp, -- 생성일시
    udate       timestamp -- 수정일시
);
--기본키
alter table product add constraint product_product_id_pk primary key(product_id);

--시퀀스생성
create sequence product_product_id_seq;

--디폴트
alter table product modify cdate default systimestamp; -- 운영체제 일시를 기본값으로
alter table product modify udate default systimestamp; -- 운영체제 일시를 기본값으로

--필수값들 NOT NULL
alter table product modify quantity not null;
alter table product modify price not null;

--생성--
insert into product(product_id,pname,quantity,price)
     values(product_product_id_seq.nextval, '컴퓨터', 5, 1000000);

insert into product(product_id,pname,quantity,price)
     values(product_product_id_seq.nextval, '모니터', 5, 500000);

insert into product(product_id,pname,quantity,price)
     values(product_product_id_seq.nextval, '프린터', 1, 300000);

commit;


------쿼리문 준비
--목록
select product_id, pname, quantity, price, cdate, udate
  from product
  order by product_id desc;

select count(*) from product;

--단건조회
select product_id, pname, quantity, price, cdate, udate
  from product
  where product_id = 21;

--단건수정
update product
  set pname = '대나무헬리곱터',
      quantity = 1,
      price = 1000,
      udate = default
  where product_id = 21;

--단건삭제
delete from product
  where product_id = 1;
--여러건 삭제
delete from product
  where product_id in ( 1, 2, 3 );


rollback;