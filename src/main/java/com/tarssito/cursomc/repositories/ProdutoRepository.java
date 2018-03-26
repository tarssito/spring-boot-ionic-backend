package com.tarssito.cursomc.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tarssito.cursomc.domain.Categoria;
import com.tarssito.cursomc.domain.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

	@Transactional(readOnly=true) // desabilita transação
	@Query("SELECT DISTINCT p FROM Produto p INNER JOIN p.categorias cat WHERE p.nome LIKE %:nome% AND cat IN :categorias")
	Page<Produto> search(@Param("nome") String nome, @Param("categorias") List<Categoria> categorias, Pageable pageRequest);
	
	/**
	 * Método que executa a mesma query do método acima, sem utilização da anotação @Query
	 * @param nome
	 * @param categorias
	 * @param pageRequest
	 * @return
	 */
	Page<Produto> findDistinctByNomeContainingAndCategoriasIn(String nome, List<Categoria> categorias, Pageable pageRequest);
}
