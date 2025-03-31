package com.example.ecommerce.services;
import com.example.ecommerce.model.Produto;
import com.example.ecommerce.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CarrinhoService {
    private ProdutoRepository produtoRepository;

    public CarrinhoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Produto adicionarProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    public List<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }

    public void limparCarrinho() {
        produtoRepository.deleteAll();
    }
}