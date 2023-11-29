document.onkeydown = function (e) {
    if (!0 === e.ctrlKey && 70 === e.keyCode) // CTRL + F
        return e.preventDefault(), alert("searching (CTRL + F)"), !1
    if (!0 === e.altKey && !0 === e.shiftKey && 70 === e.keyCode) // ALT + SHIFT + F
        return e.preventDefault(), alert("searching (ALT + SHIFT + F)"), !1
    // CTRL + R
    if (!0 === e.ctrlKey && 82 === e.keyCode)
        return e.preventDefault(), alert("reloaded"), !1
    // CTRL + SHIFT + R
    if (!0 === e.ctrlKey && !0 === e.shiftKey && 82 === e.keyCode)
        return e.preventDefault(), alert("reloaded"), !1
    // CTRL + F5
    if (!0 === e.ctrlKey && 116 === e.keyCode)
        return e.preventDefault(), alert("reloaded"), !1
    // CTRL + SHIFT + F5
    if (!0 === e.ctrlKey && !0 === e.shiftKey && 116 === e.keyCode)
        return e.preventDefault(), alert("reloaded"), !1
};

const inputs = document.getElementsByTagName("input");
for (let i = 0; i < inputs.length; i++) {
    const input = inputs[i];
    if (input.hasAttribute("type")) {
        if (input.getAttribute("type") === "search") {
            input.setAttribute("disabled", "disabled");
            input.setAttribute("placeholder", "searching is blocked by the rules !");
        }
    }
}

let searchButtons = document.getElementsByClassName("searchButton");
for (let i = 0; i < searchButtons.length; i++) {
    const searchButton = searchButtons[i];
    searchButton.remove();
}

// need twice idk why
searchButtons = document.getElementsByClassName("searchButton");
for (let i = 0; i < searchButtons.length; i++) {
    const searchButton = searchButtons[i];
    searchButton.remove();
}

searchButtons = document.getElementsByClassName("cdx-search-input__end-button");
for (let i = 0; i < searchButtons.length; i++) {
    const searchButton = searchButtons[i];
    searchButton.remove();
}

//search-toggle
searchButtons = document.getElementsByClassName("search-toggle");
for (let i = 0; i < searchButtons.length; i++) {
    const searchButton = searchButtons[i];
    searchButton.remove();
}