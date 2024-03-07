class Pagination {
  constructor(recordsPerPage, pagesPerPage) {
    this.totalRecords = 0; //총 레코드수
    this.recordsPerPage = recordsPerPage; //페이지당 레코드수
    this.pagesPerPage = pagesPerPage; //페이지당 페이지수
    this.currentPage = 1; //현재 페이지
    this.currentPageGroupStart = 1; //현재 페이지의 시작페이지
  }

  getTotalRecords() {
    return this.totalRecords;
  }

  getTotalPages() {
    return Math.ceil(this.totalRecords / this.recordsPerPage);
  }

  displayNavigation() {
    const totalPages = this.getTotalPages();
    let pageNavigation =
      this.currentPageGroupStart > 1
        ? '<button id="first">처음</button> <button id="prev">이전</button> '
        : '';

    for (
      let i = this.currentPageGroupStart;
      i < this.currentPageGroupStart + this.pagesPerPage && i <= totalPages;
      i++
    ) {
      if (i === this.currentPage) {
        pageNavigation += `<button class="active" id="page${i}">${i}</button> `;
      } else {
        pageNavigation += `<button id="page${i}">${i}</button> `;
      }
    }

    pageNavigation +=
      this.currentPageGroupStart + this.pagesPerPage - 1 < totalPages
        ? '<button id="next">다음</button> <button id="last">끝</button>'
        : '';
    return pageNavigation;
  }

  setTotalRecords(totalRecords) {
    this.totalRecords = totalRecords;
  }

  setCurrentPage(pageNumber) {
    this.currentPage = pageNumber;
  }

  setNextPageGroup() {
    this.currentPageGroupStart += this.pagesPerPage;
  }

  setPrevPageGroup() {
    this.currentPageGroupStart -= this.pagesPerPage;
    if (this.currentPageGroupStart < 1) {
      this.currentPageGroupStart = 1;
    }
  }

  displayPagination(callback) {
    //callback 내부에서 호출된 함수
    document.getElementById('pagination').innerHTML = this.displayNavigation();
    for (
      let i = this.currentPageGroupStart;
      i < this.currentPageGroupStart + this.pagesPerPage &&
      i <= this.getTotalPages();
      i++
    ) {
      document.getElementById(`page${i}`).addEventListener('click', evt => {
        this.setCurrentPage(i);
        //요청페이지 처리
        callback();
        this.displayPagination(callback);
      });
    }
    if (document.getElementById('first')) {
      document.getElementById('first').addEventListener('click', evt => {
        this.setCurrentPage(1);
        this.currentPageGroupStart = 1;
        this.displayPagination(callback);
      });
    }
    if (document.getElementById('prev')) {
      document.getElementById('prev').addEventListener('click', evt => {
        if (this.currentPageGroupStart > 1) {
          this.setPrevPageGroup();
          this.setCurrentPage(this.currentPageGroupStart);
          this.displayPagination(callback);
        }
      });
    }
    if (document.getElementById('next')) {
      document.getElementById('next').addEventListener('click', evt => {
        if (
          this.currentPageGroupStart + this.pagesPerPage - 1 <
          this.getTotalPages()
        ) {
          this.setNextPageGroup();
          this.setCurrentPage(this.currentPageGroupStart);
          this.displayPagination(callback);
        }
      });
    }
    if (document.getElementById('last')) {
      document.getElementById('last').addEventListener('click', evt => {
        const totalPages = this.getTotalPages();
        this.currentPageGroupStart =
          totalPages - (totalPages % this.pagesPerPage) + 1;
        this.setCurrentPage(totalPages);
        this.displayPagination(callback);
      });
    }
  }
}

function chageDateTime(dt) {
  // 주어진 날짜 문자열을 Date 객체로 변환
  const date = new Date(dt);

  // 필요한 날짜 및 시간 요소를 추출
  const year = date.getFullYear();
  const month = date.getMonth() + 1; // getMonth() 함수는 0부터 11까지의 값을 반환하므로 1을 더해줍니다.
  const day = date.getDate();
  let hour = date.getHours();
  const ampm = hour >= 12 ? 'pm' : 'am';

  // 12시간제로 변환
  hour = hour % 12;
  hour = hour ? hour : 12; // 0시를 12시로 변환

  // 원하는 형식으로 날짜 및 시간 문자열을 조합
  const dateString =
    year + '-' + month + '-' + day + ' ' + ampm + ' ' + hour + ':00';
  return dateString;
}

export { Pagination, chageDateTime };