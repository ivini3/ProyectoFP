package com.example.myapp.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.myapp.domain.Contactanos;


 
@Service
public class ContactanosService {
    private final JavaMailSender mailSender;
    private static final Logger logger = LoggerFactory.getLogger(ContactanosService.class);

    @Autowired
    public ContactanosService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void procesarIncidencia(Contactanos Contactanos) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setFrom(Contactanos.getEmail()); // El remitente será el correo del usuario
        mensaje.setTo("proyectofinalfp@gmail.com"); // Tu correo de soporte
        mensaje.setSubject("Nueva Contactanos Reportada");

        // Construcción del cuerpo del mensaje
        String cuerpoMensaje = String.format(
                "Nombre: %s\nEmpresa: %s\nEmail: %s\nTeléfono: %s\n\nDescripción de la Incidencia:\n%s",
                Contactanos.getNombre(),
                Contactanos.getEmpresa(),
                Contactanos.getEmail(),
                Contactanos.getTelefono(),
                Contactanos.getMensaje());

        mensaje.setText(cuerpoMensaje);

        try {
            logger.info("Enviando incidencia de {} a soporte", Contactanos.getEmail());
            mailSender.send(mensaje);
            logger.info("Incidencia enviada exitosamente desde {}", Contactanos.getEmail());
        } catch (Exception e) {
            logger.error("Error al enviar la incidencia: {}", e.getMessage(), e);
        }
    }
}
