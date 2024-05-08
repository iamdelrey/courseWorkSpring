// document.getElementById("postForm").addEventListener("submit", function(event) {
//     event.preventDefault(); // Предотвращаем стандартное поведение формы
//     let formData = new FormData(document.getElementById("postForm"));
//     fetch("/admin", {
//         method: "POST",
//         body: formData
//     }).then(response => {
//         // Обработка ответа, если необходимо
//         console.log("POST запрос выполнен успешно");
//     }).catch(error => {
//         console.error('Ошибка при отправке запроса:', error);
//     });
//         event.target.reset();
//     });

// JavaScript код для post_form.js
document.getElementById("postForm").addEventListener("submit", async function(event) {
    event.preventDefault();
    const formData = new FormData(event.target);

    try {
        const response = await fetch("/admin", {
            method: "POST",
            body: formData
        });

        const responseData = await response.text();
        const responseMessage = document.getElementById("response_message");

        if (response.ok) {
            responseMessage.innerHTML = "<p style='color: green;'>" + responseData + "</p>";
        } else {
            responseMessage.innerHTML = "<p style='color: red;'>" + responseData + "</p>";
        }
    } catch (error) {
        console.error('Error:', error);
    }
});
