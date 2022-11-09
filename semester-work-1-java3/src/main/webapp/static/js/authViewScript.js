let form = document.getElementById("#auth_form");
let body = document.getElementById("#main");
document.getElementById("#link_auth").addEventListener('click', function () {
    form.style.display = 'block';
    body.style.display = 'none';
});

document.getElementById("#btn_close").addEventListener('click', function () {
    form.style.display = 'none';
    body.style.display = 'block';
});
