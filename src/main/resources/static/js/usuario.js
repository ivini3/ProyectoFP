document.getElementById('perfilContainer').onclick = function() {
    var menu = document.getElementById('opcionesPerfil');
    var iconoFlecha = document.getElementById('iconoFlecha');
  
    if (menu.style.display === 'block') {
      menu.style.display = 'none';
      iconoFlecha.src = '/images/flecha-correcta.png'; // Cambiar con la ruta real de la flecha hacia la derecha
      menu.style.zIndex=-1;
    } else {
      menu.style.display = 'block';
      iconoFlecha.src = '/images/flecha-abajo.png'; // Cambiar con la ruta real de la flecha hacia abajo
    }
  }
  
  // Cerrar el menú si se hace clic fuera de él
  window.onclick = function(event) {
    if (!event.target.matches('#perfilContainer') && !event.target.matches('#fotoPerfil') && !event.target.matches('#iconoFlecha')) {
      var opcionesPerfil = document.getElementById('opcionesPerfil');
      var iconoFlecha = document.getElementById('iconoFlecha');
      
      if (opcionesPerfil.style.display === 'block') {
        opcionesPerfil.style.display = 'none';
        iconoFlecha.src = '/images/flecha-abajo.png'; // Cambiar con la ruta real de la flecha hacia la derecha
      }
    }
  }