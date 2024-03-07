// 동기
{
  console.log('시작');
  console.log('실행중');
  let sum = 0;
  for (let i = 0; i < 99000000; i++) {
    sum += i;
  }

  console.log(`sum = ${sum}`);
  console.log('종료');
}

console.log('----------------------------');

// 비동기
{
  console.log('시작');
  console.log('실행중');
  const result = new Promise((resolve, reject) => {
    // 비동기 로직 처리, 보통 시간이 소요되는 작업 ex)서버와의 통신
    let sum = 0;
    for (let i = 0; i < 99000000; i++) {
      sum += i;
    }
    resolve(sum);
  });
  result.then(res => console.log(`sum=${res}`));
  console.log('종료');
}

console.log('----------------------------');

// 수정해야함....................
// 비동기2
// {
//   console.log('시작');
//   console.log('실행중');
//   const method2 = async () => {
//     const result = await new Promise((resolve, reject) => {
//       try {// 비동기 로직 처리, 보통 시간이 소요되는 작업 ex)서버와의 통신
//         let sum = 0;
//         for (let i = 0; i < 99000000; i++) {
//           sum += i;
//         }
//         if (!true) {
//           resolve(sum);
//         } else {
//           reject(new Error('오류발행'));
//         }
//       });
//       console.log(result);
//     }
//       catch(err) {
//         console.log('err');
//       }
      
//   }

//   method1();
//   console.log('종료');
// }