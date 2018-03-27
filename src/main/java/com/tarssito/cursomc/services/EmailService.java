package com.tarssito.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.tarssito.cursomc.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido pedido);
	
	void sendEmail(SimpleMailMessage msg);
}
