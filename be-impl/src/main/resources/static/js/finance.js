/*
* Определяем контекст пути
* */
const currentPath = window.location.pathname;
const pathParts = currentPath.split('/');
const basePath = '/' + pathParts[1];


/*
* Открытие и закрытие модальных окон
* */
const modalRevenue = document.getElementById("modal-revenue-container");
const modalExpense = document.getElementById("modal-expense-container");
const modalMoney = document.getElementById("modal-money-container");

const revenueBox = document.getElementById("revenue-box");
const expenseBox = document.getElementById("expense-box");
const moneyBox = document.getElementById("money-box");

const closeRevenue = document.getElementById("close-revenue");
const closeExpense = document.getElementById("close-expense");
const closeMoney = document.getElementById("close-money");


revenueBox.onclick = function () {
    modalRevenue.style.display = "block";
}

expenseBox.onclick = function () {
    modalExpense.style.display = "block";
}

moneyBox.onclick = function () {
    modalMoney.style.display = "block";
}

closeRevenue.onclick = function () {
    modalRevenue.style.display = "none";
}

closeMoney.onclick = function () {
    modalMoney.style.display = "none";
}

closeExpense.onclick = function () {
    modalExpense.style.display = "none";
}

window.onclick = function (event) {
    if (event.target === modalRevenue) {
        modalRevenue.style.display = "none";
    }
    if (event.target === modalExpense) {
        modalExpense.style.display = "none";
    }
    if (event.target === modalMoney) {
        modalExpense.style.display = "none";
    }
}


/*
* Получение данных аналитики от своего api
* */
$(document).ready(function () {
    const requests = [
        {url: basePath + '/api/v1/stats/expense', selector: '#expense-box p', defaultMessage: 'Загрузка...'},
        {url: basePath + '/api/v1/stats/revenue', selector: '#revenue-box p', defaultMessage: 'Загрузка...'},
        {url: basePath + '/api/v1/stats/money', selector: '#money-box p', defaultMessage: 'Загрузка...'},
        {url: basePath + '/api/v1/stats/profit', selector: '#profit-box p', defaultMessage: 'Загрузка...'},
    ];

    function fetchData(url, selector, defaultMessage) {
        $(selector).text(defaultMessage);

        $.ajax({
            url: url,
            method: 'GET',
            dataType: 'json',
            success: function (data) {
                const formattedValue = (data.value).toLocaleString('ru-RU', {
                    minimumFractionDigits: 2,
                    maximumFractionDigits: 2
                });
                $(selector).text(formattedValue + ' ₽');
            },
            error: function (xhr, status, error) {
                console.error("AJAX Error:", status, error);
                $(selector).text('Ошибка загрузки данных');
            }
        });
    }

    requests.forEach(request => {
        fetchData(request.url, request.selector, request.defaultMessage);
    });
});


/*
* Построение графика прибыли
* */
$(document).ready(function () {
    const ctx1 = document.getElementById('profitChart').getContext('2d');
    $.ajax({
        url: basePath + '/api/v1/stats/profit-analytics',
        type: 'GET',
        dataType: 'json',
        success: function (response) {
            const months = response.date;
            const actualData = response.amount;
            const forecastDate = response.forecastDate;
            const forecastAmount = response.forecastAmount;
            let forecastData = [];
            if (months.length === 0) {
                const chart = new Chart(ctx1, {
                    type: 'line',
                    data: {
                        labels: months,
                        datasets: [
                            {
                                label: 'Реальная прибыль',
                                data: [],
                            },
                            {
                                label: 'Прогноз прибыли',
                                data: [],
                            }
                        ]
                    }
                });
            } else {
                for (let i = 0; i < 6 - forecastData.size; i++) {
                    forecastData.push(null)
                }
                if (actualData.length < 1) {
                    forecastData.push(null);
                    forecastData.push(null);
                } else {
                    console.log(actualData[actualData.length - 1]);
                    forecastData.push(actualData[actualData.length - 1]);
                    forecastData.push(forecastAmount);
                }

                const chart = new Chart(ctx1, {
                    type: 'line',
                    data: {
                        labels: months,
                        datasets: [
                            {
                                label: 'Реальная прибыль',
                                data: actualData,
                                borderColor: 'rgba(54, 162, 235, 1)',
                                backgroundColor: 'rgba(54, 162, 235, 0.2)',
                                pointStyle: 'circle',
                                pointRadius: 6,
                                pointHoverRadius: 10,
                                pointBackgroundColor: 'rgba(54, 162, 235, 1)',
                                borderWidth: 3,
                                tension: 0.4,
                                fill: true
                            },
                            {
                                label: 'Прогноз прибыли',
                                data: [null, null, null, null, actualData[actualData.length - 1], forecastAmount],
                                borderColor: 'rgba(255, 99, 132, 1)',
                                backgroundColor: 'rgba(255, 99, 132, 0.2)',
                                borderDash: [5, 5],
                                pointStyle: 'circle',
                                pointRadius: 6,
                                pointHoverRadius: 10,
                                pointBackgroundColor: 'rgba(255, 99, 132, 1)',
                                borderWidth: 2,
                                tension: 0.4,
                                fill: true,
                                spanGaps: true
                            }
                        ]
                    },
                    options: {
                        responsive: true,
                        plugins: {
                            legend: {
                                display: true,
                                position: 'top',
                            },
                            tooltip: {
                                callbacks: {
                                    label: function (context) {
                                        const label = context.dataset.label || '';
                                        const value = context.raw;
                                        return `${label}: ₽${value}`;
                                    }
                                }
                            }
                        },
                        scales: {
                            x: {
                                title: {
                                    display: true,
                                    text: 'Месяцы',
                                    color: '#333',
                                    font: {
                                        size: 14,
                                        weight: 'bold'
                                    }
                                },
                                ticks: {
                                    color: '#333',
                                    font: {
                                        size: 12
                                    }
                                }
                            },
                            y: {
                                title: {
                                    display: true,
                                    text: 'Прибыль (₽)',
                                    color: '#333',
                                    font: {
                                        size: 14,
                                        weight: 'bold'
                                    }
                                },
                                ticks: {
                                    stepSize: 500,
                                    color: '#333',
                                    font: {
                                        size: 12
                                    },
                                    callback: function (value) {
                                        return `₽${value}`;
                                    }
                                },
                                beginAtZero: true
                            }
                        },
                        animation: {
                            duration: 2000,
                            easing: 'easeOutQuart'
                        }
                    }
                });
            }
        },
        error: function (xhr, status, error) {
            console.error('Ошибка при получении данных:', error);
        }
    });
});


/*
* Построение графика расходов
* */
$(document).ready(function () {
    const ctx2 = document.getElementById('expensesChart').getContext('2d');

    function drawEmptyChart() {
        const emptyChart = new Chart(ctx2, {
            type: 'doughnut',
            data: {
                labels: ['Нет данных'],
                datasets: [{
                    label: 'Расходы',
                    data: [1],
                    backgroundColor: ['rgba(255, 159, 64, 0.6)'],
                    borderColor: ['rgba(255, 159, 64, 1)'],
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: {
                        display: true,
                        position: 'top'
                    },
                    tooltip: {
                        enabled: false
                    },
                    datalabels: {
                        display: true,
                        formatter: function (value, context) {
                            return 'Нет данных';
                        },
                        color: '#000',
                        font: {
                            weight: 'bold',
                            size: '16'
                        }
                    }
                }
            }
        });
    }

    $.ajax({
        url: basePath + '/api/v1/stats/expense-analytics',
        type: 'GET',
        dataType: 'json',
        success: function (response) {
            const categories = response.categories || [];
            const amounts = response.amounts || [];

            if (categories.length === 0 || amounts.length === 0) {
                drawEmptyChart();
            } else {
                const expensesChart = new Chart(ctx2, {
                    type: 'doughnut',
                    data: {
                        labels: categories,
                        datasets: [{
                            label: 'Расходы',
                            data: amounts,
                            backgroundColor: [
                                'rgba(255, 99, 132, 0.6)',
                                'rgba(54, 162, 235, 0.6)',
                                'rgba(255, 206, 86, 0.6)',
                                'rgba(75, 192, 192, 0.6)',
                                'rgba(153, 102, 255, 0.6)',
                                'rgba(255, 159, 64, 0.6)',
                                'rgba(255, 205, 86, 0.6)'
                            ],
                            borderColor: [
                                'rgba(255, 99, 132, 1)',
                                'rgba(54, 162, 235, 1)',
                                'rgba(255, 206, 86, 1)',
                                'rgba(75, 192, 192, 1)',
                                'rgba(153, 102, 255, 1)',
                                'rgba(255, 159, 64, 1)',
                                'rgba(255, 205, 86, 1)'
                            ],
                            borderWidth: 1
                        }]
                    },
                    options: {
                        responsive: true,
                        plugins: {
                            tooltip: {
                                callbacks: {
                                    label: function (tooltipItem) {
                                        const label = tooltipItem.dataset.label || '';
                                        const value = tooltipItem.raw;
                                        return `${label}: ₽${value}`;
                                    }
                                }
                            },
                            legend: {
                                display: true,
                                position: 'top'
                            }
                        }
                    }
                });
            }
        },
        error: function (xhr, status, error) {
            console.error('Ошибка при получении данных:', error);
            drawEmptyChart();
        }
    });
});


/*
* Пагинация и история доходов/расходов
* */
let currentPage = 1;
let totalPages = 1;

function fetchData(page) {
    $.ajax({
        url: basePath + `/api/v1/stats/revenues-expenses/items?page=${page}`,
        method: 'GET',
        success: function (data) {
            renderData(data);
        },
        error: function () {
            alert('Ошибка при загрузке данных');
        }
    });
}

function fetchTotalPages() {
    $.ajax({
        url: basePath + '/api/v1/stats/revenues-expenses/count',
        method: 'GET',
        success: function (data) {
            totalPages = data.totalPages;
            renderPagination();
        },
        error: function () {
            alert('Ошибка при загрузке количества страниц');
        }
    });
}

function renderData(data) {
    const container = $('#data-container');
    container.empty();

    container.append(`
            <div class="list-group-item item-header">
                <div class="column">Тип</div>
                <div class="column">Сумма</div>
                <div class="column">Категория</div>
                <div class="column">Дата</div>
            </div>
        `);

    data.forEach(item => {
        if (item.type === 'Доход') {
            container.append(`
                <div class="list-group-item" style="color: #4CAF50">  
                    <div class="column">${item.type}</div>
                    <div class="column">${item.amount}</div>
                    <div class="column">${item.category}</div>
                    <div class="column">${item.date}</div>
                </div>
            `);
        } else {
            container.append(`
                <div class="list-group-item" style="color: #FF6B6B">  
                    <div class="column">${item.type}</div>
                    <div class="column">${item.amount}</div>
                    <div class="column">${item.category}</div>
                    <div class="column">${item.date}</div>
                </div>
            `);
        }
    });
}

function renderPagination() {
    const pagination = $('#pagination');
    pagination.empty();

    if (currentPage > 1) {
        pagination.append(`
                <li class="page-item">
                    <a class="page-link" onclick="changePage(${currentPage - 1})">Назад</a>
                </li>
            `);
    }

    for (let i = 1; i <= totalPages; i++) {
        pagination.append(`
                <li class="page-item ${i === currentPage ? 'active' : ''}">
                    <a class="page-link" onclick="changePage(${i})">${i}</a>
                </li>
            `);
    }

    if (currentPage < totalPages) {
        pagination.append(`
                <li class="page-item">
                    <a class="page-link" onclick="changePage(${currentPage + 1})">Вперед</a>
                </li>
            `);
    }
}

function changePage(page) {
    if (page < 1 || page > totalPages) return;
    currentPage = page;
    fetchData(currentPage);
    renderPagination();
}

$(document).ready(function () {
    fetchTotalPages();
    fetchData(currentPage);
});


function toggleDropdown() {
    document.getElementById('category-dropdown').style.display = 'block';
}

function selectCategory(category) {
    document.getElementById('expense-category').value = category;
    document.getElementById('category-dropdown').style.display = 'none';
}

document.addEventListener('click', (event) => {
    const dropdown = document.querySelector('.dropdown');
    if (!dropdown.contains(event.target)) {
        document.getElementById('category-dropdown').style.display = 'none';
    }
});