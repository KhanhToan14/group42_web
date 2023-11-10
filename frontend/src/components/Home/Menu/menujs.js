import { checkBar, openMenu } from "../../../utils/Functionjs";

window.addEventListener("DOMContentLoaded", () => {
    const $ = document.querySelector.bind(document);
    const $$ = document.querySelectorAll.bind(document);

    const ListMenu = $$("nav>.item");
    checkBar();

    ListMenu.forEach((item, index) => {
        item.onclick = () => {
            $(".item.active").classList.remove("active");
            item.classList.toggle("active");
        };
    });
    openMenu();
    window.addEventListener("resize", () => {
        checkBar();
    });
});
