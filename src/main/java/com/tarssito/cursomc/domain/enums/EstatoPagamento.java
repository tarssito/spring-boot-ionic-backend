package com.tarssito.cursomc.domain.enums;

public enum EstatoPagamento {

	PENDENTE(1, "Pendente"),
	QUITADO(2, "Quitado"),
	CANCELADO(3, "Cancelado");
	
	private int cod;
	private String descricao;
	
	private EstatoPagamento(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	public int getCod() {
		return cod;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public static EstatoPagamento toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}
		for (EstatoPagamento t : EstatoPagamento.values()) {
			if (cod.equals(t.getCod())) {
				return t;
			}
		}
		throw new IllegalArgumentException("Id inválido: " + cod);
	}
	
}
