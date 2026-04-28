package com.demo.adeline.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired; // Para el @Autowired
import com.demo.adeline.model.Mensaje; // Para la entidad Mensaje
import com.demo.adeline.repository.MensajeRepository; // Para el repositorio

@Controller
public class ChatController {

	@Autowired
	private MensajeRepository mensajeRepo;

	@MessageMapping("/enviar-mensaje")
	@SendTo("/topic/mensajes")
	public Map<String, Object> procesarMensaje(Map<String, Object> mensajeData) {
	    // 1. Creamos la entidad Mensaje con los datos que vienen del Front
	    Mensaje nuevo = new Mensaje();
	    nuevo.setTexto((String) mensajeData.get("texto"));
	    nuevo.setEmisorId(Long.valueOf(mensajeData.get("emisorId").toString()));
	    nuevo.setReceptorId(Long.valueOf(mensajeData.get("receptorId").toString()));
	    nuevo.setHora((String) mensajeData.get("hora"));

	    // 2. LO GUARDAMOS EN LA BASE DE DATOS (Supabase)
	    mensajeRepo.save(nuevo);

	    // 3. Lo devolvemos para que el socket lo muestre en tiempo real
	    return mensajeData;
	}
}