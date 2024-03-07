// 공통 응답 메세지

package com.kh.demo.web.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ApiResponse<T> {
  // 헤더와 바디는 바뀌면 안되니 Getter만
  private Header header; // 헤더
  private T body;        // 바디
  // 아래의 값들은 값들을 받아와도 되니 Setter 만들기
  private int totalCnt = 1;  // 총 레코드 수
  private int recCnt = 1;    // 1페이지 보여줄 레코드 수
  private int reqPage = 1;   // 요청 페이지

  // 생성자 private ★★★★★하고 메소드를 static(정적메소드)로
  private ApiResponse(Header header, T body) {
    this.header = header;
    this.body = body;
  }

  @Getter
  @ToString
  @AllArgsConstructor
  private static class Header {
    String rtcd;      //응답코드
    String rtmsg;     //응답메세지
    String rtdetail;  //응답세부메세지

    public Header(String rtcd, String rtmsg){
      this.rtcd = rtcd;
      this.rtmsg = rtmsg;
    }
  }

  public static <T> ApiResponse<T> createApiResponse(String rtcd,String rtmsg,T body){
    return new ApiResponse<T>(new Header(rtcd,rtmsg),body);
  }
  public static <T> ApiResponse<T> createApiResponseDetail(String rtcd,String rtmsg,String rtdetail,T body){
    return new ApiResponse<T>(new Header(rtcd,rtmsg,rtdetail),body);
  }
  

  // Setter ---------------------------------------------------------------------------------------
  public void setTotalCnt(int totalCnt) {
    this.totalCnt = totalCnt;
  }

  public void setRecCnt(int recCnt) {
    this.recCnt = recCnt;
  }

  public void setReqPage(int reqPage) {
    this.reqPage = reqPage;
  }
}
