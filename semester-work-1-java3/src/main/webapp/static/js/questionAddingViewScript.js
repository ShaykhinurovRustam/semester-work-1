let buttonAddQuestion = document.getElementById("#btn_add_question");
let formAddQuestion = document.getElementById("#form_add_question");
buttonAddQuestion.addEventListener('click', function () {
    formAddQuestion.style.display = 'block';
    buttonAddQuestion.style.display = 'none';
});
