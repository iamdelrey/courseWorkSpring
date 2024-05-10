let listProductHTML = document.querySelector(".listProduct");
let listCartHTML = document.querySelector(".listCart");
let iconCart = document.querySelector(".icon-cart");
let iconCartSpan = document.querySelector(".icon-cart span");
let body = document.querySelector("body");
let closeCart = document.querySelector(".close");
let products = [];
let cart = [];

iconCart.addEventListener("click", () => {
  body.classList.toggle("showCart");
});
closeCart.addEventListener("click", () => {
  body.classList.toggle("showCart");
});

const addDataToHTML = () => {
  if (products.length > 0) {
    products.forEach((product) => {
      let newProduct = document.createElement("div");
      newProduct.dataset.id = product.id;
      newProduct.classList.add("item");
      newProduct.innerHTML = `<img src="${product.image}" alt="">
                <h2>${product.name}</h2>
                <div class="price">${product.price} ₽</div>
                <button class="addCart">В корзину</button>`;
      listProductHTML.appendChild(newProduct);
    });
  }
};
listProductHTML.addEventListener("click", (event) => {
  let positionClick = event.target;
  if (positionClick.classList.contains("addCart")) {
    let id_product = positionClick.parentElement.dataset.id;
    addToCart(id_product);
  }
});
const addToCart = (product_id) => {
  let positionThisProductInCart = cart.findIndex(
    (value) => value.product_id == product_id
  );
  if (cart.length <= 0) {
    cart = [
      {
        product_id: product_id,
        quantity: 1,
      },
    ];
  } else if (positionThisProductInCart < 0) {
    cart.push({
      product_id: product_id,
      quantity: 1,
    });
  } else {
    cart[positionThisProductInCart].quantity =
      cart[positionThisProductInCart].quantity + 1;
  }
  addCartToHTML();
  addCartToMemory();
};
const addCartToMemory = () => {
  localStorage.setItem("cart", JSON.stringify(cart));
};
const calculateTotalSum = () => {
  let totalSum = 0;
  cart.forEach((item) => {
    let positionProduct = products.findIndex(
      (value) => value.id == item.product_id
    );
    let info = products[positionProduct];
    totalSum += info.price * item.quantity;
  });
  return totalSum;
};

const addCartToHTML = () => {
  listCartHTML.innerHTML = "";
  let totalQuantity = 0;
  let totalSum = 0;

  if (cart.length > 0) {
    cart.forEach((item) => {
      totalQuantity += item.quantity;
      let newItem = document.createElement("div");
      newItem.classList.add("item");
      newItem.dataset.id = item.product_id;

      let positionProduct = products.findIndex(
        (value) => value.id == item.product_id
      );
      let info = products[positionProduct];
      listCartHTML.appendChild(newItem);
      newItem.innerHTML = `
        <div class="image">
          <img src="${info.image}">
        </div>
        <div class="name">
          ${info.name}
        </div>
        <div class="totalPrice">${info.price * item.quantity} ₽</div>
        <div class="quantity">
          <span class="minus">&#8592;</span>
          <span>${item.quantity}</span>
          <span class="plus">&#8594;</span>
        </div>
      `;
    });

    totalSum = calculateTotalSum();
  }

  let totalSumElement = document.createElement("div");
  totalSumElement.classList.add("totalSum");
  totalSumElement.innerHTML = `Итого: ${totalSum} ₽`;
  listCartHTML.appendChild(totalSumElement);

  iconCartSpan.innerText = totalQuantity;
};

listCartHTML.addEventListener("click", (event) => {
  let positionClick = event.target;
  if (
    positionClick.classList.contains("minus") ||
    positionClick.classList.contains("plus")
  ) {
    let product_id = positionClick.parentElement.parentElement.dataset.id;
    let type = "minus";
    if (positionClick.classList.contains("plus")) {
      type = "plus";
    }
    changeQuantityCart(product_id, type);
  }
});
const changeQuantityCart = (product_id, type) => {
  let positionItemInCart = cart.findIndex(
    (value) => value.product_id == product_id
  );
  if (positionItemInCart >= 0) {
    let info = cart[positionItemInCart];
    switch (type) {
      case "plus":
        cart[positionItemInCart].quantity =
          cart[positionItemInCart].quantity + 1;
        break;

      default:
        let changeQuantity = cart[positionItemInCart].quantity - 1;
        if (changeQuantity > 0) {
          cart[positionItemInCart].quantity = changeQuantity;
        } else {
          cart.splice(positionItemInCart, 1);
        }
        break;
    }
  }
  addCartToHTML();
  addCartToMemory();
};

const initApp = () => {
  fetch("js/products.json")
    .then((response) => response.json())
    .then((data) => {
      products = data;
      addDataToHTML();

      if (localStorage.getItem("cart")) {
        cart = JSON.parse(localStorage.getItem("cart"));
        addCartToHTML();
      }
    });
};
initApp();

// document.addEventListener('DOMContentLoaded', function () {
//   const listProductHTML = document.querySelector(".listProduct");
//   const listCartHTML = document.querySelector(".listCart");
//   const iconCart = document.querySelector(".icon-cart");
//   const iconCartSpan = document.querySelector(".icon-cart span");
//   const body = document.querySelector("body");
//   const closeCart = document.querySelector(".close");
//   let products = [];
//   let cart = JSON.parse(localStorage.getItem("cart")) || [];
//   let stompClient;
//
//   if (iconCart && closeCart) {
//     iconCart.addEventListener("click", () => {
//       body.classList.toggle("showCart");
//     });
//
//     closeCart.addEventListener("click", () => {
//       body.classList.toggle("showCart");
//     });
//   }
//
//   function connect() {
//     const socket = new SockJS('/ws');
//     stompClient = Stomp.over(socket);
//     stompClient.connect({}, function (frame) {
//       console.log('Connected: ' + frame);
//       stompClient.subscribe('/topic/productUpdate', function (productUpdate) {
//         const product = JSON.parse(productUpdate.body);
//         if (product && product.img) {
//           products.push(product);
//           renderProducts();
//         }
//       });
//     });
//   }
//
//   function renderProducts() {
//     if (listProductHTML) {
//       listProductHTML.innerHTML = ''; // Clear the list first
//       products.forEach(product => {
//         const productHTML = `
//           <div class="item" data-id="${product.id}">
//             <img src="data:image/jpeg;base64,${product.img}" alt="${product.name}">
//             <h2>${product.name}</h2>
//             <div class="price">${product.price} ₽</div>
//             <button class="addCart">В корзину</button>
//           </div>
//         `;
//         listProductHTML.insertAdjacentHTML('beforeend', productHTML);
//       });
//     }
//   }
//
//   if (listProductHTML) {
//     listProductHTML.addEventListener("click", (event) => {
//       let positionClick = event.target;
//       if (positionClick.classList.contains("addCart")) {
//         let id_product = positionClick.parentElement.dataset.id;
//         addToCart(id_product);
//       }
//     });
//   }
//
//   function addToCart(product_id) {
//     let product = products.find(p => p.id == product_id);
//     if (!product) return;
//
//     let cartItem = cart.find(item => item.id == product_id);
//     if (cartItem) {
//       cartItem.quantity += 1;
//     } else {
//       cart.push({ ...product, quantity: 1 });
//     }
//     updateCartUI();
//   }
//
//   function updateCartUI() {
//     if (listCartHTML) {
//       listCartHTML.innerHTML = '';
//       cart.forEach(item => {
//         const itemHTML = `
//           <div class="cart-item" data-id="${item.id}">
//             <img src="data:image/jpeg;base64,${item.img}" alt="${item.name}">
//             <div>${item.name}</div>
//             <div>${item.quantity} x ${item.price} ₽</div>
//             <button class="remove-item">Удалить</button>
//           </div>
//         `;
//         listCartHTML.insertAdjacentHTML('beforeend', itemHTML);
//       });
//       localStorage.setItem("cart", JSON.stringify(cart));
//       iconCartSpan.innerText = cart.reduce((sum, item) => sum + item.quantity, 0);
//     }
//   }
//
//   if (listCartHTML) {
//     listCartHTML.addEventListener("click", (event) => {
//       if (event.target.classList.contains("remove-item")) {
//         const id_product = event.target.closest(".cart-item").dataset.id;
//         removeItemFromCart(id_product);
//       }
//     });
//   }
//
//   function removeItemFromCart(id) {
//     const index = cart.findIndex(item => item.id == id);
//     if (index > -1) {
//       cart.splice(index, 1);
//       updateCartUI();
//     }
//   }
//
//   connect();
//   updateCartUI();
// });