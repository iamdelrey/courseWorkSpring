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

document.addEventListener("DOMContentLoaded", function() {
    var form = document.getElementById("postForm");

    form.addEventListener("submit", function(event) {
        event.preventDefault();

        var formData = new FormData(form);

        fetch("/admin", {
            method: "POST",
            body: formData,
        })
            .then(response => {
                if(response.ok) {
                    return response.text();
                }
                throw new Error('Ошибка в запросе: ' + response.statusText);
            })
            .then(data => {
                console.log(data);
                alert("Форма успешно отправлена: " + data);
                window.location.href = '/download/' + data;
            })
            .catch(error => {
                console.error('Ошибка:', error);
                alert("Произошла ошибка при отправке формы: " + error);
            });
    });
});