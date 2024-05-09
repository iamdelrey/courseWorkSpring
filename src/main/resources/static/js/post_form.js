function addNotification(message, isSuccess) {
    var notification = document.createElement('div');
    notification.classList.add('notification', isSuccess ? 'success' : 'error');
    notification.textContent = message;

    var container = document.getElementById('notification-container');
    container.appendChild(notification);

    setTimeout(function () {
        container.removeChild(notification);
    }, 4000);
}

document.getElementById("addForm").addEventListener("submit", function(event) {
    event.preventDefault();
    var file = document.getElementById("dataImg").files[0];
    var reader = new FileReader();
    reader.onload = function(event) {
        const base64String = event.target.result.split(',')[1];
        let formData = new FormData(document.getElementById("addForm"));
        formData.append("imgBase64", base64String);

        fetch("/admin", {
            method: "POST",
            body: formData
        })
            .then(response => {
                if (response.ok) {
                    addNotification("POST запрос выполнен успешно", true);
                } else {
                    addNotification("Ошибка при отправке запроса", false);
                }
            })
            .catch(error => {
                addNotification('Ошибка при отправке запроса: ' + error, false);
            });
    };
    reader.readAsDataURL(file);
});