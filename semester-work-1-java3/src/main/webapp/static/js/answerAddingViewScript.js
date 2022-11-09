let addButton = document.getElementById('#btn_add_answer');
let addAnswerForm = document.getElementById("#form_add_answer");
addButton.addEventListener('click', function () {
    addAnswerForm.style.display = "block";
    addButton.style.display = "none";
});
