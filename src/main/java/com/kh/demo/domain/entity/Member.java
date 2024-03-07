package com.kh.demo.domain.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Member {
   private Long member_id;       // MEMBER_ID	NUMBER(10,0)
   private String email;         // EMAIL	VARCHAR2(50 BYTE)
   private String passwd;        // PASSWD	VARCHAR2(12 BYTE)
   private String nickname;      // NICKNAME	VARCHAR2(30 BYTE)
   private String gubun;         // GUBUN	VARCHAR2(11 BYTE)
   private byte[] pic;           // PIC	BLOB
   private LocalDateTime cdate;  // CDATE	TIMESTAMP(6)
   private LocalDateTime udate;  // UDATE	TIMESTAMP(6)
}
