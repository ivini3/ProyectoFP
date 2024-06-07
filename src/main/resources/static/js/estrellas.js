const stars = document.querySelectorAll('.fa-star');
const ratingText = document.getElementById('rating-text'); // Obtener el elemento del texto de valoración

const textosValoracion = [
    "1/5", // 1 estrella
    "2/5", // 2 estrellas
    "3/5", // 3 estrellas
    "4/5", // 4 estrellas
    "5/5" // 5 estrellas
];

stars.forEach((star, index) => {
    star.addEventListener('click', () => {
        // Remueve la clase 'active' de todas las estrellas
        stars.forEach(star => {
            star.classList.remove('active');
        });

        // Agrega la clase 'active' a las estrellas hasta el índice seleccionado
        for(let i = 0; i <= index; i++) {
            stars[i].classList.add('active');
        }
        
        // Actualizar el texto de valoración y guardar en cookie
        ratingText.textContent = textosValoracion[index];
        setRatingCookie(index + 1); // Guarda la valoración en una cookie solo una vez
    });
});

function setRatingCookie(rating) {
    const d = new Date();
    d.setTime(d.getTime() + (30*24*60*60*1000)); // Expira en 30 días
    let expires = "expires="+ d.toUTCString();
    document.cookie = "rating=" + rating + ";" + expires + ";path=/";
  }
  
  stars.forEach((star, index) => {
    star.addEventListener('click', () => {
      // Código anterior para iluminar las estrellas...
      setRatingCookie(index + 1); // Guarda la valoración en una cookie
    });
  });
  function getRatingFromCookie() {
    let name = "rating=";
    let decodedCookie = decodeURIComponent(document.cookie);
    let ca = decodedCookie.split(';');
    for(let i = 0; i < ca.length; i++) {
      let c = ca[i];
      while (c.charAt(0) == ' ') {
        c = c.substring(1);
      }
      if (c.indexOf(name) == 0) {
        return c.substring(name.length, c.length);
      }
    }
    return "";
  }
  
  function displaySavedRating() {
    const savedRating = getRatingFromCookie();
    if (savedRating != "") {
        for(let i = 0; i < savedRating; i++) {
            stars[i].classList.add('active');
        }
        // Actualizar el texto de valoración basándose en la valoración guardada
        ratingText.textContent = textosValoracion[savedRating - 1];
    }
}

  
  // Asegúrate de llamar a displaySavedRating cuando la página se cargue
  document.addEventListener("DOMContentLoaded", function(){
    displaySavedRating();
  });
    