
// ♣목록 ----------------------------------------
async function list() {
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

    } else {
      new Error('목록가져오기 실패!');
    }
    // console.log(result);
  } catch (err) {
    console.err(err.message);
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
  pname : '라떼',
  quantity : 10,
  price : 10000
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