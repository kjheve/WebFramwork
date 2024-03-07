    // 목록------------------------------------
    const $listBtn = document.getElementById('listBtn');
    $listBtn.addEventListener('click', e => {
      location.href = '/products';          // GET, http://서버주소 or 서버도메인/products
    }, false);