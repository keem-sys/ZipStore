const menuToggle = document.querySelector('.menu-icon');
const toggle = document.querySelector('.toggle'); // You had this already
const overlay = document.querySelector('.overlay'); // Select the overlay 

menuToggle.addEventListener('click', () => {
    menuToggle.classList.toggle('open');
    toggle.classList.toggle('active'); // Use 'toggle' not 'menu'
    overlay.classList.toggle('active');
});

overlay.addEventListener('click', () => {
    menuToggle.classList.remove('open');
    toggle.classList.remove('active');
    overlay.classList.remove('active');
});