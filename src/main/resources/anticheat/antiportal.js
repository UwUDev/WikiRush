// Only for the French wiki

let portalLockMessage = "Portal blocked by the rules !"
let homonymieLockMessage = "Homonymie bloquée par les règles !";
let categoryLockMessage = "Category blocked by the rules !";

if (window.location.href.includes("fr.wikipedia.org")) {
    portalLockMessage = "Portail bloqué par les règles !";
    homonymieLockMessage = "Homonymie bloquée par les règles !";
    categoryLockMessage = "Catégorie bloquée par les règles !";
} else if (window.location.href.includes("es.wikipedia.org")) {
    portalLockMessage = "Portal bloqueado por las reglas !";
    homonymieLockMessage = "Homónimo bloqueado por las reglas !";
    categoryLockMessage = "Categoría bloqueada por las reglas !";
} else if (window.location.href.includes("ru.wikipedia.org")) {
    portalLockMessage = "Портал заблокирован правилами!";
    homonymieLockMessage = "Омоним заблокирован правилами!";
    categoryLockMessage = "Категория заблокирована правилами!";
} else if (window.location.href.includes("it.wikipedia.org")) {
    portalLockMessage = "Portale bloccato dalle regole!";
    homonymieLockMessage = "Omonimia bloccata dalle regole!";
    categoryLockMessage = "Categoria bloccata dalle regole!";
} else if (window.location.href.includes("de.wikipedia.org")) {
    portalLockMessage = "Portal durch die Regeln blockiert!";
    homonymieLockMessage = "Homonymie durch die Regeln blockiert!";
    categoryLockMessage = "Kategorie durch die Regeln blockiert!";
}

try {
    document.getElementById("bandeau-portail").innerHTML = portalLockMessage;
} catch (e) {
}

// only for some wikis languages
const portalBox = document.getElementsByClassName("portalbox");
for (let k = 0; k < portalBox.length; k++) {
    const box = portalBox[k];
    box.innerHTML = "Portals are blocked by the rules !";
}

const disallowedUrls = [
    "/wiki/Portal:", // English and Spanish
    "/wiki/Portail:",
    "/wiki/Портал:",
    "/wiki/Portale:",
];

const as = document.getElementsByTagName("a");
for (let i = 0; i < as.length; i++) {
    const a = as[i];
    if (a.hasAttribute("href")) {
        for (let j = 0; j < disallowedUrls.length; j++) {
            const disallowedUrl = disallowedUrls[j];
            if (a.getAttribute("href") != null) {
                if (a.getAttribute("href").includes(disallowedUrl)) {

                    // block the <a> element
                    a.removeAttribute("href")
                    a.innerHTML = portalLockMessage;
                }
            }
        }
    }
}

const homonymies = document.getElementsByClassName("homonymie");
for (let i = 0; i < homonymies.length; i++) {
    const homonymie = homonymies[i];
    homonymie.innerHTML = homonymieLockMessage;
}

try {
    document.getElementById("catlinks").innerHTML = categoryLockMessage;
} catch (e) {
}