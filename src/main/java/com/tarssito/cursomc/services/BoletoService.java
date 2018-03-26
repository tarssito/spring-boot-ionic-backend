package com.tarssito.cursomc.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.tarssito.cursomc.domain.PagamentoComBoleto;

@Service
public class BoletoService {

	public static final int TOTAL_DIAS_PAGAMENTO = 7;
	
	/**
	 * Adiciona o dia de vencimento (7 dias após o instante do pedido)
	 * Obs: Isso seria substituído por um web service, por exemplo
	 * @param pagto
	 * @param instanteDoPedido
	 */
	public void preencherPagamentoComBoleto(PagamentoComBoleto pagto, Date instanteDoPedido) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(instanteDoPedido);
		cal.add(Calendar.DAY_OF_MONTH, TOTAL_DIAS_PAGAMENTO);
		pagto.setDataVencimento(cal.getTime());
	}
}
