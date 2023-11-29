document.onkeydown = function (e) {
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