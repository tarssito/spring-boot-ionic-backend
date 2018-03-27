package com.tarssito.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tarssito.cursomc.domain.Cliente;
import com.tarssito.cursomc.domain.ItemPedido;
import com.tarssito.cursomc.domain.PagamentoComBoleto;
import com.tarssito.cursomc.domain.Pedido;
import com.tarssito.cursomc.domain.Produto;
import com.tarssito.cursomc.domain.enums.EstatoPagamento;
import com.tarssito.cursomc.repositories.ClienteRepository;
import com.tarssito.cursomc.repositories.ItemPedidoRepository;
import com.tarssito.cursomc.repositories.PagamentoRepository;
import com.tarssito.cursomc.repositories.PedidoRepository;
import com.tarssito.cursomc.repositories.ProdutoRepository;
import com.tarssito.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repository;

	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private BoletoService boletoService;
	
	public Pedido find(Integer id) {
		Optional<Pedido> pedido = repository.findById(id);
		return pedido.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
	
	public Pedido insert(Pedido pedido) {
		pedido.setId(null);
		pedido.setInstante(new Date());
		
		Cliente cliente = clienteRepository.findById(pedido.getCliente().getId()).get();
		pedido.setCliente(cliente);
		
		pedido.getPagamento().setEstado(EstatoPagamento.PENDENTE);
		pedido.getPagamento().setPedido(pedido);
		if (pedido.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) pedido.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, pedido.getInstante());
		}
		
		pedido = repository.save(pedido);
		pagamentoRepository.save(pedido.getPagamento());
		
		for (ItemPedido ip : pedido.getItens()) {
			ip.setDesconto(0.0);
			
			Produto produto = produtoRepository.findById(ip.getProduto().getId()).get();
			ip.setProduto(produto);
			
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(pedido);
		}
		
		itemPedidoRepository.saveAll(pedido.getItens());
		System.out.println(pedido);
		return pedido;
	}
}
