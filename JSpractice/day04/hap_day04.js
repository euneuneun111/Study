
for (let i = 1; i < 5; i++) {

    let img = document.createElement("img");
    img.setAttribute("src", "./image/" + i + ".jpg");

    let span = document.createElement("span");
    span.innerText = menu[i];

    let div = document.createElement("div");
    div.append(img);
    div.append(document.createElement("br"));
    div.append(span);

    imgs[i - 1] = div;
}

