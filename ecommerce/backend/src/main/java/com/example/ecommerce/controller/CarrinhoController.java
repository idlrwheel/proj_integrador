package com.example.ecommerce.controller;

import com.example.ecommerce.model.Produto;
import com.example.ecommerce.model.Status;
import com.example.ecommerce.services.CarrinhoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/carrinho")
@CrossOrigin("*") 
public class CarrinhoController {

    private final CarrinhoService carrinhoService;

    public CarrinhoController(CarrinhoService carrinhoService) {
        this.carrinhoService = carrinhoService;
    }

    private final Map<String, Produto> carrinho = new HashMap<>(); 

    public Produto adicionarOuAtualizarProduto(Produto produto) {
        Produto existente = carrinho.get(produto.getNome());

        if (existente != null) {
            existente.setQtdEstoque(existente.getQtdEstoque() + produto.getQtdEstoque());
        } else {
            if (produto.getQtdEstoque() <= 0) {
                produto.setQtdEstoque(1); 
            }
            carrinho.put(produto.getNome(), produto);
        }

        return carrinho.get(produto.getNome());
    }

    @PostMapping("/adicionar")
    public ResponseEntity<Produto> adicionarProduto(@RequestBody Produto produto) {
        if (produto.getStatus() == null) {
            produto.setStatus(Status.ativo); 
        }
        try {
            System.out.println("Produto recebido no CarrinhoController: " + produto);
            Produto novoProduto = carrinhoService.adicionarProduto(produto);
            if (novoProduto != null) {
                return ResponseEntity.ok(novoProduto);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            System.err.println("Erro ao adicionar produto ao carrinho: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listarProdutos() {
        try {
            List<Produto> produtos = carrinhoService.listarProdutos();
            return ResponseEntity.ok(produtos);
        } catch (Exception e) {
            System.err.println("Erro ao listar produtos do carrinho: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/limpar")
    public ResponseEntity<String> limparCarrinho() {
        try {
            carrinhoService.limparCarrinho();
            return ResponseEntity.ok("Carrinho limpo com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao limpar o carrinho: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao limpar o carrinho");
        }
    }
}
