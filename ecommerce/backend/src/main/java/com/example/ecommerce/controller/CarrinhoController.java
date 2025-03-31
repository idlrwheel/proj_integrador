package com.example.ecommerce.controller;

import com.example.ecommerce.model.Produto;
import com.example.ecommerce.services.CarrinhoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carrinho")
@CrossOrigin(origins = "http://127.0.0.1:5500") // Permite o frontend acessar a API
public class CarrinhoController {

    private final CarrinhoService carrinhoService;

    public CarrinhoController(CarrinhoService carrinhoService) {
        this.carrinhoService = carrinhoService;
    }

    @PostMapping("/adicionar")
    public ResponseEntity<Produto> adicionarProduto(@RequestBody Produto produto) {
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
