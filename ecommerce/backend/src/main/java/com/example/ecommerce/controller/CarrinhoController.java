package com.example.ecommerce.controller;
import com.example.ecommerce.model.Produto;
import com.example.ecommerce.repository.ProdutoRepository;
import com.example.ecommerce.services.CarrinhoService;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/carrinho")
@CrossOrigin("*")
public class CarrinhoController {
    private CarrinhoService carrinhoService;

    public CarrinhoController(CarrinhoService carrinhoService) {
        this.carrinhoService = carrinhoService;
    }

    @PostMapping("/adicionar")
    public ResponseEntity<Produto> adicionarProduto(@RequestBody Produto produto) {
        Produto novoProduto = carrinhoService.adicionarProduto(produto);
        return ResponseEntity.ok(novoProduto);
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listarProdutos() {
        return ResponseEntity.ok(carrinhoService.listarProdutos());
    }

    @DeleteMapping("/limpar")
    public ResponseEntity<String> limparCarrinho() {
        carrinhoService.limparCarrinho();
        return ResponseEntity.ok("Carrinho limpo com sucesso!");
    }
}