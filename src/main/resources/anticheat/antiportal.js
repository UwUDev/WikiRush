// Only for the French wiki
document.getElementById("bandeau-portail").innerHTML = "Portail bloqué par les règles !";

// only for some wikis languages
const portalBox = document.getElementsByClassName("portalbox");
for (let k = 0; k < portalBox.length; k++) {
    const box = portalBox[k];
    box.innerHTML = "Portals are blocked by the rules !";
}

const disallowedUrls = [
    {"url": "/wiki/Portal:", "reason": "Portals are blocked by the rules !"}, // English and Spanish
    {"url": "/wiki/Portail:", "reason": "Les portails sont bloqués par les règles !"},
    {"url": "/wiki/Портал:", "reason": "Порталы заблокированы правилами!"},
    {"url": "/wiki/Portale:", "reason": "I portali sono bloccati dalle regole!"},
];

const as = document.getElementsByTagName("a");
for (let i = 0; i < as.length; i++) {
    const a = as[i];
    if (a.hasAttribute("href")) {
        for (let j = 0; j < disallowedUrls.length; j++) {
            const disallowedUrl = disallowedUrls[j];
            if (a.getAttribute("href").includes(disallowedUrl.url)) {
                // block the <a> element
                a.setAttribute("href", "#");
                a.innerHTML = disallowedUrl.reason;
            }
        }
    }
}