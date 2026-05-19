package com.demo.adeline.controller;

import java.util.List; // 🚨 NUEVO IMPORT
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;              // 🚨 NUEVO IMPORT
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping; // 🚨 NUEVO IMPORT
import org.springframework.web.bind.annotation.PathVariable; // 🚨 NUEVO IMPORT
import org.springframework.web.bind.annotation.ResponseBody; // 🚨 NUEVO IMPORT

import com.demo.adeline.model.Mensaje;
import com.demo.adeline.repository.MensajeRepository;

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
	
	
	
	
	// --- 2. NUEVO MÉTODO PARA LA BANDEJA DE ENTRADA (HTTP GET) ---
		@GetMapping("/api/chats/bandeja/{usuarioId}")
		@ResponseBody // 🚀 Indica a Spring que devuelva un JSON directo a tu Angular
		public ResponseEntity<List<Map<String, Object>>> obtenerBandejaEntrada(@PathVariable Long usuarioId) {
		    // Llamamos al repositorio que creamos antes usando la Query avanzada
		    List<Map<String, Object>> historialBandeja = mensajeRepo.findConversacionesRecientes(usuarioId);
		    return ResponseEntity.ok(historialBandeja);
		}
	
	
}