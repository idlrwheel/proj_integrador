package com.example.ecommerce.controller;

import com.example.ecommerce.model.Produto;
import com.example.ecommerce.services.ProdutoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@CrossOrigin("*") 
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listarProdutos() {
        try {
            List<Produto> produtos = produtoService.listarProdutos();
            return ResponseEntity.ok(produtos);
        } catch (Exception e) {
            System.err.println("Erro ao listar produtos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Produto> buscarProdutoPorCodigo(@PathVariable int codigo) {
        Produto produto = produtoService.buscarPorCodigo(codigo);
        if (produto != null) {
            return ResponseEntity.ok(produto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Produto> adicionarProduto(@RequestBody Produto produto) {
        try {
            Produto novoProduto = produtoService.adicionarProduto(produto);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoProduto);
        } catch (Exception e) {
            System.err.println("Erro ao adicionar produto: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<Produto> atualizarProduto(@PathVariable int codigo, @RequestBody Produto produtoAtualizado) {
        try {
            Produto produto = produtoService.atualizarProduto(codigo, produtoAtualizado);
            if (produto != null) {
                return ResponseEntity.ok(produto);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            System.err.println("Erro ao atualizar produto: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<String> excluirProduto(@PathVariable int codigo) {
        try {
            boolean excluido = produtoService.excluirProduto(codigo);
            if (excluido) {
                return ResponseEntity.ok("Produto excluído com sucesso!");
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            System.err.println("Erro ao excluir produto: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno");
        }
    }
}
