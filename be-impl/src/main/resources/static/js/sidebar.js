/*
* Подсветка текущей вкладки
* */
document.addEventListener('DOMContentLoaded', () => {
    const currentPath = window.location.pathname;

    const navLinks = document.querySelectorAll('.nav-link');

    navLinks.forEach(link => {
        const href = link.getAttribute('href');
        if (currentPath.endsWith(href)
            && !link.classList.contains("logout")
            && !link.classList.contains("delete")) {
            link.classList.add('active');
        }
    });
});

document.getElementById('logoutForm').addEventListener('click', function (event) {
    document.getElementById('logoutButton').click();
});

document.getElementById('deleteForm').addEventListener('click', function (event) {
    document.getElementById('deleteButton').click();
});