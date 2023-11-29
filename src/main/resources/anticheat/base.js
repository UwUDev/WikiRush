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

//class vector-page-toolbar
const vectorPageToolbar = document.getElementsByClassName("vector-page-toolbar");
for (let i = 0; i < vectorPageToolbar.length; i++) {
    const vectorPageToolbarElement = vectorPageToolbar[i];
    vectorPageToolbarElement.remove();
}

try {
    document.getElementById("vector-main-menu-dropdown-checkbox").remove();
} catch (e) { }

const vectorHeaderContainer = document.getElementsByClassName("vector-header-container");
for (let i = 0; i < vectorHeaderContainer.length; i++) {
    const vectorHeaderContainerElement = vectorHeaderContainer[i];
    vectorHeaderContainerElement.remove();
}

const mwFooterContainer = document.getElementsByClassName("mw-footer-container");
for (let i = 0; i < mwFooterContainer.length; i++) {
    const mwFooterContainerElement = mwFooterContainer[i];
    mwFooterContainerElement.remove();
}

const extQuickSurveyDefault = document.getElementsByClassName("ext-quick-survey-default");
for (let i = 0; i < extQuickSurveyDefault.length; i++) {
    const extQuickSurveyDefaultElement = extQuickSurveyDefault[i];
    extQuickSurveyDefaultElement.remove();
}

const vectorSitenoticeContainer = document.getElementsByClassName("vector-sitenotice-container");
for (let i = 0; i < vectorSitenoticeContainer.length; i++) {
    const vectorSitenoticeContainerElement = vectorSitenoticeContainer[i];
    vectorSitenoticeContainerElement.remove();
}

const bandeauContainer = document.getElementsByClassName("bandeau-container");
for (let i = 0; i < bandeauContainer.length; i++) {
    const bandeauContainerElement = bandeauContainer[i];
    bandeauContainerElement.remove();
}