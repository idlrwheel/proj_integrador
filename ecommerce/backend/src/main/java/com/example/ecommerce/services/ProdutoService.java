package com.example.ecommerce.services;

import com.example.ecommerce.model.Produto;
import com.example.ecommerce.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {
    @Autowired
    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<Produto> listarProdutos() {
        return produtoRepository.findAll();

    }

    public Produto buscarPorCodigo(int codigo) {
        return produtoRepository.findById(codigo).orElse(null);
    }

    public Produto adicionarProduto(Produto produto) {
        Optional<Produto> existente = produtoRepository.findByNome(produto.getNome());
        if (existente.isPresent()) {
            throw new IllegalArgumentException("Produto com o nome '" + produto.getNome() + "' já existe.");
        }
        return produtoRepository.save(produto);
    }

    public Produto atualizarProduto(int codigo, Produto produtoAtualizado) {
        Produto produto = buscarPorCodigo(codigo);
        if (produto != null) {
            produto.setNome(produtoAtualizado.getNome());
            produto.setDescricaoDetalhada(produtoAtualizado.getDescricaoDetalhada());
            produto.setValorProduto(produtoAtualizado.getValorProduto());
            produto.setQtdEstoque(produtoAtualizado.getQtdEstoque());
            produto.setAvaliacao(produtoAtualizado.getAvaliacao());
            produto.setStatus(produtoAtualizado.getStatus());
            return produtoRepository.save(produto);
        }
        return null;
    }

    public boolean excluirProduto(int codigo) {
        Produto produto = buscarPorCodigo(codigo);
        if (produto != null) {
            produtoRepository.delete(produto);
            return true;
        }
        return false;
    }

}