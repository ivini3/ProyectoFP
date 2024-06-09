document.addEventListener('DOMContentLoaded', function() {
  var perfilContainer = document.getElementById('perfilContainer');
  var iconMenu = document.getElementById('icon-menu');
  var opcionesPerfil = document.getElementById('opcionesPerfil');
  var iconoFlecha = document.getElementById('iconoFlecha');

  if (perfilContainer) {
      perfilContainer.onclick = function() {
          if (opcionesPerfil.style.display === 'block') {
              opcionesPerfil.style.display = 'none';
              iconoFlecha.src = '/images/flecha-correcta.png'; // Cambiar con la ruta real de la flecha hacia la derecha
              opcionesPerfil.style.zIndex = -1;
          } else {
              opcionesPerfil.style.display = 'block';
              iconoFlecha.src = '/images/flecha-abajo.png'; // Cambiar con la ruta real de la flecha hacia abajo
          }
      };

      // Cerrar el menú si se hace clic fuera de él
      window.onclick = function(event) {
          if (!event.target.matches('#perfilContainer') && !event.target.matches('#fotoPerfil') && !event.target.matches('#iconoFlecha')) {
              if (opcionesPerfil.style.display === 'block') {
                  opcionesPerfil.style.display = 'none';
                  iconoFlecha.src = '/images/flecha-correcta.png'; // Cambiar con la ruta real de la flecha hacia la derecha
              }
          }
      };
  }

  if (iconMenu) {
      iconMenu.addEventListener('click', function() {
          document.querySelector('#headerconmenu nav').classList.toggle('active');
      });
  }
});
