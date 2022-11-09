let usersList = document.getElementsByClassName("list-group-item");
console.log(usersList.length);
if (usersList[0] != null) {
    usersList[0].style.backgroundColor = "#ffd700";
}
if (usersList[1] != null) {
    usersList[1].style.backgroundColor = "#c0c0c0";
}
if (usersList[2] != null) {
    usersList[2].style.backgroundColor = "#cd7f32";
}
for (let i = 0; i < usersList.length; i++) {
    let number = document.getElementsByClassName("position-absolute top-50 start-0 ms-3")[i];
    number.textContent = "#" + (i + 1);
}
