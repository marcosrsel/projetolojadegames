package com.generationbrasil.lojaDeGames.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.generationbrasil.lojaDeGames.model.Produto;
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{
	public List<Produto> findByNomeContainingIgnoreCase(String nome);
}
