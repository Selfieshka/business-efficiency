const form = document.querySelector("form");
const modalContainer = document.getElementById("modalContainer");
const addEmployeeBtn = document.getElementById("addEmployeeBtn");
const closeModal = document.getElementById("closeModal");
const jobSearch = document.getElementById("jobSearch");
const jobOptions = document.getElementById("jobOptions");
const selectedJobs = document.getElementById("selectedJobs");

const allJobs = ["Стажёр", "Рабочий", "Менеджер", "Директор"];
const selectedJobValues = new Set();

const currentPath = window.location.pathname;
const pathParts = currentPath.split('/');
const basePath = '/' + pathParts[1];


addEmployeeBtn.addEventListener("click", () => {
    modalContainer.style.display = "block";
    jobOptions.innerHTML = "";
});

closeModal.addEventListener("click", () => {
    modalContainer.style.display = "none";
});

function addJob(job) {
    if (!selectedJobValues.has(job)) {
        selectedJobValues.add(job);

        const jobTag = document.createElement("div");
        jobTag.className = "job-tag";
        jobTag.innerHTML = `${job} <span class="remove-tag">&times;</span>`;

        jobTag.querySelector(".remove-tag").addEventListener("click", () => {
            jobTag.remove();
            selectedJobValues.delete(job);
            updatePositionsField();
            hideJobOptions();
        });

        selectedJobs.appendChild(jobTag);
        updatePositionsField();
        hideJobOptions();
    }
}

function filterJobs() {
    const searchValue = jobSearch.value.toLowerCase();
    jobOptions.innerHTML = "";
    const filteredJobs = allJobs.filter(job =>
        job.toLowerCase().includes(searchValue) && !selectedJobValues.has(job)
    );

    if (filteredJobs.length > 0) {
        jobOptions.style.display = 'block';
        filteredJobs.forEach(job => {
            const optionDiv = document.createElement("div");
            optionDiv.textContent = job;
            optionDiv.className = "job-option";
            optionDiv.onclick = () => addJob(job);
            jobOptions.appendChild(optionDiv);
        });
    } else {
        jobOptions.style.display = 'none';
    }
}

function showJobOptions() {
    if (jobSearch.value) {
        filterJobs();
    }
}

function hideJobOptions() {
    jobOptions.style.display = 'none';
}

window.onclick = (event) => {
    if (event.target === modalContainer) {
        modalContainer.style.display = "none";
    }
    if (event.target !== jobSearch) {
        hideJobOptions();
    }
}

function updatePositionsField() {
    const positionsInput = document.getElementById("positions");
    positionsInput.value = Array.from(selectedJobValues).join(",");
}

form.addEventListener("submit", (event) => {
    updatePositionsField();
});


function deleteEmployee(employeeId, element) {
    $.ajax({
        url: basePath + `/staff/${employeeId}`,
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