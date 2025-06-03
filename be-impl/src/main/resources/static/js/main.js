const editButton = document.getElementById('edit-button');
const formElements = document.querySelectorAll('#business-info-form input, #business-info-form select, #business-info-form textarea');
let isEditing = false;

editButton.addEventListener('click', () => {
    isEditing = !isEditing;

    formElements.forEach(element => {
        if (isEditing) {
            element.removeAttribute('readonly');
            element.removeAttribute('disabled');
            editButton.textContent = 'Сохранить';
        } else {
            element.setAttribute('readonly', true);
            element.setAttribute('disabled', true);
            editButton.textContent = 'Редактировать';
        }
    });
});

formElements.forEach(element => {
    element.setAttribute('readonly', true);
});