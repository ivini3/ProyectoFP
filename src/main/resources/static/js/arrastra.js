$(document).ready(function() {
    $('#imagen').change(function(event) {
        if (event.target.files && event.target.files[0]) {
            var reader = new FileReader();
            
            reader.onload = function(e) {
                $('#area-carga').hide(); // Oculta el área de carga
                $('#vista-previa-imagen').attr('src', e.target.result).show();
                $('#borrar-imagen').show(); // Muestra el botón de borrar
            };
            
            reader.readAsDataURL(event.target.files[0]);
        }
    });

    $('#borrar-imagen').click(function() {
        $('#imagen').val(''); // Limpia el input de archivo
        $('#vista-previa-imagen').hide(); // Oculta la vista previa de la imagen
        $('#area-carga').show(); // Muestra nuevamente el área de carga
        $(this).hide(); // Oculta el botón de borrar
    });

    // Los manejadores de eventos para dragover, dragleave y drop siguen igual
    $('#area-carga').on('dragover', function(event) {
        event.preventDefault();
        event.stopPropagation();
        $(this).css('color', 'green').text('Suelta para cargar la imagen');
    });

    $('#area-carga').on('dragleave', function(event) {
        event.preventDefault();
        event.stopPropagation();
        $(this).css('color', '#add8e6').text('Arrastra aquí tu imagen o haz clic para seleccionar');
    });

    $('#area-carga').on('drop', function(event) {
        event.preventDefault();
        event.stopPropagation();
        $(this).css('color', '#add8e6').text('Arrastra aquí tu imagen o haz clic para seleccionar');
        
        var files = event.originalEvent.dataTransfer.files;
        $('#imagen').prop('files', files).change();
    });
});
