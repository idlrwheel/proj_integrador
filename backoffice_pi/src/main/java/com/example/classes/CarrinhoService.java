package com.example.classes;

import java.util.List;
import java.util.Optional;

public class CarrinhoService {
    private CarrinhoRepository repository;

    public List<ItemCarrinho> listarItens(String sessionId) {
        return repository.findBySessionId(sessionId);
    }

    public ItemCarrinho adicionarOuAtualizarItem(String sessionId, ItemCarrinho novoItem) {
        Optional<ItemCarrinho> itemExistente = repository.findByNomeProdutoAndSessionId(novoItem.getNomeProduto(), sessionId);

        if (itemExistente.isPresent()) {
            // Atualiza a quantidade do item já existente
            ItemCarrinho item = itemExistente.get();
            item.setQuantidade(item.getQuantidade() + novoItem.getQuantidade());
            return repository.save(item);
        } else {
            // Adiciona um novo item ao carrinho
            novoItem.setSessionId(sessionId);
            return repository.save(novoItem);
        }
    }

    public void removerItem(Long id) {
        repository.deleteById(id);
    }
}
