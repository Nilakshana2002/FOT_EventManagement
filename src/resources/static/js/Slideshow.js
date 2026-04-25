// ═══════════════════════════════════════
// SLIDESHOW CONTROLLER
// ═══════════════════════════════════════

let slideIndex = 0;
let slideTimer;

document.addEventListener('DOMContentLoaded', function() {
    showSlides();
    autoAdvance();
});

function showSlides() {
    const slides = document.getElementsByClassName("mySlides");
    if (slides.length === 0) return;

    // Clamp index
    if (slideIndex >= slides.length) slideIndex = 0;
    if (slideIndex < 0) slideIndex = slides.length - 1;

    // Hide all, show current
    for (let i = 0; i < slides.length; i++) {
        slides[i].style.display = "none";
        slides[i].style.opacity = "0";
    }

    slides[slideIndex].style.display = "block";
    // Trigger reflow for smooth transition
    void slides[slideIndex].offsetWidth;
    slides[slideIndex].style.opacity = "1";
}

function autoAdvance() {
    slideTimer = setInterval(function() {
        slideIndex++;
        showSlides();
    }, 4000);
}

function plusSlides(n) {
    clearInterval(slideTimer);
    slideIndex += n;
    showSlides();
    autoAdvance();
}