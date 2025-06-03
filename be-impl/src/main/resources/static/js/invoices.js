const modal = document.getElementById("myModal");
const btnOpenModal = document.getElementById("openModal");
const btnCloseModal = document.getElementsByClassName("close")[0];

const currentPath = window.location.pathname;
const pathParts = currentPath.split('/');
const basePath = '/' + pathParts[1];

btnOpenModal.onclick = function () {
    modal.style.display = "block";
}

btnCloseModal.onclick = function () {
    modal.style.display = "none";
}

window.onclick = function (event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}

function toggleDetails(row) {
    const details = row.nextElementSibling;
    if (details.style.display === "none" || details.style.display === "") {
        details.style.display = "block";
        details.style.opacity = 0;
        setTimeout(() => {
            details.style.transition = "opacity 0.3s ease";
            details.style.opacity = 1;
        }, 10);
    } else {
        details.style.opacity = 0;
        setTimeout(() => {
            details.style.display = "none";
        }, 300);
    }
}

function deleteInvoice(invoiceId, element) {
    $.ajax({
        url: basePath + `/invoices/${invoiceId}`,
        type: 'DELETE',
        success: function (response) {
            console.log('Пользователь успешно удален:', response);
            element.remove();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.error('Ошибка при удалении пользователя:', textStatus, errorThrown);
        }
    });
}

function showInvoiceInfo(invoiceId, invoiceNumber, invoiceDate, sum, count, countTov) {
    document.getElementById('invoiceDetails').innerHTML = `
            <p>Номер накладной: ${invoiceNumber}</p>
            <p>Дата накладной: ${invoiceDate}</p>
            <p>Всего позиций: ${count}</p>
            <p>Количество товаров: ${countTov}</p>
            <p>Сумма: ${sum}</p>
        `;

    document.getElementById('invoiceModal').style.display = "block";
}

function closeModal() {
    document.getElementById('invoiceModal').style.display = "none";
}

window.onclick = function (event) {
    var modal = document.getElementById('invoiceModal');
    if (event.target === modal) {
        modal.style.display = "none";
    }
}