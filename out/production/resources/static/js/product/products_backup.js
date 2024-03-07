// practice_test의 fetch3 업그레이드 됨

let $productList = ''; // 목록 엘리먼트를 타깃
let $loaddingImg = ''; // 로딩 이미지

// document.addEventListener("DOMContentLoaded", renderHTML);
renderHTML();
function renderHTML() {
  const $div = document.createElement('div');
  $div.innerHTML =
    `
    <div>
      <form id='frm'>
        <h3> 상품등록 </h3>
        <div>
          <label for="pname">상품명</label>
          <input id='pname' name='pname' type="text">
        </div>
        <div>
          <label for="quantity">수량</label>
          <input id='quantity' name='quantity' type="text">
        </div>
        <div>
          <label for="price">가격</label>
          <input id='price' name='price' type="text">
        </div>
        <div><button id='addBtn' type='button'>등록</button></div>
      </form>
     </div>
     <div id='productList'></div>
     <img id="loadding" src='/img/loadding.svg'>
    `;

  document.body.appendChild($div); // body 요소 밑에 만든 태그 추가

  const $addBtn = $div.querySelector('#addBtn');
  $addBtn.addEventListener('click', evt => {
    console.log('등록');

    const formData = new FormData($div.querySelector('#frm'));
      const product = {
        pname : formData.get('pname'),
        quantity : formData.get('quantity'),
        price : formData.get('price')
      }
      add(product);

  });

  $div.style.position='relative';

  // 상품 목록
  $productList = $div.querySelector('#productList');
  
  // 로딩 이미지
  $loaddingImg = $div.querySelector('#loadding');
  $loaddingImg.style.position='absolute';
  $loaddingImg.style.top = '50vh';
  $loaddingImg.style.left = '50vw';
  $loaddingImg.style.transform = 'translate(-50%, -50%)';
  $loaddingImg.style.display = 'none';
  list();
}




// ♣목록 ----------------------------------------
async function list() {
  $loaddingImg.style.display = 'block';
  const url = `http://localhost:9080/api/products`;
  const option = {
    method: 'GET',
    headers: {
      accept: 'application/json'
    }
  };
  try {
    const res = await fetch(url, option);
    if (!res.ok) return new Error('서버응답오류');
    const result = await res.json(); // 응답 메세지 바디를 읽어 json포맷 문자열 => js객체
    if (result.header.rtcd == '00') {
      console.log(result.body);

      // let $rowHTML = '';
      // result.body.forEach(item => {
      //   $rowHTML += `<div>
      //                 <span>${item.productId}</span>
      //                 <span>${item.pname}</span>
      //                 <span>${item.quantity}</span>
      //                 <span>${item.price}</span>
      //                </div>`;
      // });
      // $productList.innerHTML = $rowHTML; // forEach 사용

      const str = result.body.map(item => 
                    `<div>
                      <span>${item.productId}</span>
                      <span>${item.pname}</span>
                      <span>${item.quantity}</span>
                      <span>${item.price}</span>
                     </div>`
      ).join('');
      
      $productList.innerHTML = str; // map과 join 사용

    } else {
      new Error('목록가져오기 실패!');
    }
    // console.log(result);
  } catch (err) {
    console.err(err.message);
  } finally {
    $loaddingImg.style.display = 'none';
  }
}
// list();


// ♣등록 ----------------------------------------
async function add(product) {
  const url = `http://localhost:9080/api/products`;
  const payload = product
  const option = {
    method: 'POST',
    headers: {
      'accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(payload),   // ★js 객체 => json 포맷 문자열
  };
  try {
    const res = await fetch(url, option);
    if (!res.ok) return new Error('서버응답오류');
    const result = await res.json(); // 응답 메세지 바디를 읽어 json포맷 문자열 => js객체
    if (result.header.rtcd == '00') {
      console.log(result.body);

      list(); // 성공했으니 목록을 갱신

    } else {
      new Error('등록 실패!');
    }
    // console.log(result);
  } catch (err) {
    console.err(err.message);
  }
}
// product = {
//   "pname": "커피",
//   "quantity": 10,
//   "price": 3000
// }
// add(product);


// ♣조회 ----------------------------------------
async function find(pid) {
  const url = `http://localhost:9080/api/products/${pid}`;
  const option = {
    method: 'GET',
    headers: {
      'accept': 'application/json',
      'Content-Type': 'application/json'
    },
  };
  try {
    const res = await fetch(url, option);
    if (!res.ok) return new Error('서버응답오류');
    const result = await res.json(); // 응답 메세지 바디를 읽어 json포맷 문자열 => js객체
    if (result.header.rtcd == '00') { // 상품을 찾은 경우
      console.log(result.body);

    } else if (result.header.rtcd == '01') { // 상품을 못찾은 경우
      console.log(result.header.rtmsg, result.header.rtdetail);
    } else {
      new Error('조회 실패!');
    }
    // console.log(result);
  } catch (err) {
    console.err(err.message);
  }
}
// find(3);

// ♣수정 ----------------------------------------
async function update(pid, product) {
  const url = `http://localhost:9080/api/products/${pid}`;
  const payload = product
  const option = {
    method: 'PATCH',
    headers: {
      'accept': 'application/json', // 응답메세지 바디의 데이터포맷 타입
      'Content-Type': 'application/json' // 요청 메세지 바디의 데이터포맷 타입
    },
    body: JSON.stringify(payload),   // ★js 객체 => json 포맷 문자열
  };
  try {
    const res = await fetch(url, option);
    if (!res.ok) return new Error('서버응답오류');
    const result = await res.json(); // 응답 메세지 바디를 읽어 json포맷 문자열 => js객체
    if (result.header.rtcd == '00') {
      console.log(result.body);
    } else {
      new Error('수정 실패!');
    }
    console.log(result);
  } catch (err) {
    console.err(err.message);
  }
}
const product = {
  pname: '라떼',
  quantity: 10,
  price: 10000
}
// update(9, product);

// ♣삭제 ----------------------------------------
async function del(pid) {
  const url = `http://localhost:9080/api/products/${pid}`;
  const option = {
    method: 'DELETE',
    headers: {
      'accept': 'application/json',
    },
  };
  try {
    const res = await fetch(url, option);
    if (!res.ok) return new Error('서버응답오류');
    const result = await res.json(); // 응답 메세지 바디를 읽어 json포맷 문자열 => js객체
    if (result.header.rtcd == '00') {
      console.log(result.body);
    } else {
      new Error('삭제 실패!');
    }
    console.log(result);
  } catch (err) {
    console.err(err.message);
  }
}
// del(25);