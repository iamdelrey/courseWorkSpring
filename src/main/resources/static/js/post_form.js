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
            })
            .catch(error => {
                console.error('Ошибка:', error);
                alert("Произошла ошибка при отправке формы: " + error);
            });
    });
});