

public interface CarrinhoRepository extends JpaRepository<ItemCarrinho, Long> {
    // Buscar todos os itens de um carrinho de uma sessão específica
    List<ItemCarrinho> findBySessionId(String sessionId);

    // Buscar um item específico pelo nome do produto e sessionId
    Optional<ItemCarrinho> findByNomeProdutoAndSessionId(String nomeProduto, String sessionId);
}