let title = document.getElementsByClassName("w-25 mx-auto form-control")[0];
let logParagraph = document.getElementById("#titleForInputWatcher");
title.addEventListener('input', function (e) {
    logParagraph.innerText = title.value.length + "/20";
});
