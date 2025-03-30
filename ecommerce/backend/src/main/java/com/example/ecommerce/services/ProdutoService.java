package com.example.ecommerce.services;

import com.example.ecommerce.model.Produto;
import com.example.ecommerce.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> listarProdutos() {
        return produtoRepository.findAll(); 
    }

    public Produto buscarProdutoPorId(int codigo) {
        return produtoRepository.findById(codigo).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }
}

