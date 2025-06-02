const currentPath = window.location.pathname;
const pathParts = currentPath.split('/');
const basePath = '/' + pathParts[1];

const editButton = document.getElementById('editButton');
const cancelButton = document.getElementById('cancelButton');
const saveButton = document.getElementById('saveButton');

let originalValues = {};

editButton.addEventListener('click', function () {
    const inputs = document.querySelectorAll('.account-edit input');
    inputs.forEach(input => {
        originalValues[input.name] = input.value;
        input.readOnly = false;
    });
    inputs.forEach(input => {
        input.removeAttribute('readonly');
    });

    document.getElementById('email').readOnly = true;

    editButton.classList.add('hidden');
    cancelButton.classList.remove('hidden');
    saveButton.classList.remove('hidden');
});

cancelButton.addEventListener('click', function () {
    const inputs = document.querySelectorAll('.account-edit input');
    inputs.forEach(input => {
        input.value = originalValues[input.name];
        input.readOnly = true;
    });

    inputs.forEach(input => {
        input.setAttribute('readonly', true);
    });

    editButton.classList.remove('hidden');
    cancelButton.classList.add('hidden');
    saveButton.classList.add('hidden');
});

$(document).ready(function () {
    $('#accountForm').on('submit', function (event) {
        event.preventDefault();

        const formData = $(this).serialize();

        $.ajax({
            type: 'POST',
            url: basePath + '/profile',
            data: formData,
            success: function (response) {
                const inputs = document.querySelectorAll('.account-edit input');

                inputs.forEach(input => {
                    input.readOnly = true;
                });

                inputs.forEach(input => {
                    input.setAttribute('readonly', true);
                });
                editButton.classList.remove('hidden');
                cancelButton.classList.add('hidden');
                saveButton.classList.add('hidden');
                console.log('Успешно отправлено:');
            },
            error: function () {
                console.error('Ошибка при отправке:');
            }
        });
    });
});


$(document).ready(function () {
    $("#profile-image").click(function () {
        $("#file-input").click();
    });

    $("#file-input").change(function (event) {
        const file = event.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function (e) {
                $("#profile-image").attr("src", e.target.result);
                sendFileToServer(file);
            };
            reader.readAsDataURL(file);
        }
    });

    function sendFileToServer(file) {
        const formData = new FormData();
        formData.append('profilePhoto', file);

        $.ajax({
            url: basePath + '/profile/upload',
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            success: function () {
                console.log('Изображение успешно загружено:');
            },
            error: function () {
                console.error('Ошибка загрузки:');
            }
        });
    }
});


