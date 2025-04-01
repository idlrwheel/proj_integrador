const listaCarrinho = document.getElementById('lista-carrinho');
const botaoLimpar = document.getElementById('limpar-carrinho');

// Função para adicionar produto ao carrinho
const adicionarAoCarrinho = async (produto) => {
    try {
        const response = await fetch('http://localhost:8080/api/carrinho/adicionar', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(produto),
        });

        if (!response.ok) {
            const errorDetails = await response.text();
            throw new Error(`Erro ao adicionar produto ao carrinho: ${errorDetails}`);
        }

        const data = await response.json();
        console.log(`Produto adicionado com sucesso: ${data.nome}`);
        atualizarCarrinho(); // Atualiza o carrinho após adicionar
    } catch (error) {
        console.error('Erro ao adicionar produto ao carrinho:', error);
    }
};

// Função para atualizar e exibir produtos no carrinho
const atualizarCarrinho = async () => {
    try {
        const response = await fetch('http://localhost:8080/api/carrinho');
        if (!response.ok) {
            throw new Error("Erro ao carregar os dados do carrinho.");
        }

        const produtos = await response.json();
        listaCarrinho.innerHTML = ''; // Limpa a lista antes de atualizar

        if (produtos.length === 0) {
            listaCarrinho.innerHTML = '<li>O carrinho está vazio.</li>';
            return;
        }

        // Renderiza os produtos no HTML
        produtos.forEach((produto) => {
            const preco = produto.valorProduto ? parseFloat(produto.valorProduto).toFixed(2) : "Preço não disponível";
            const quantidade = produto.quantidade || 0;

            const item = document.createElement('li');
            item.innerText = `Produto: ${produto.nome} | R$ ${preco} | Quantidade: ${quantidade}`;
            listaCarrinho.appendChild(item);
        });
    } catch (error) {
        console.error('Erro ao atualizar o carrinho:', error);
        listaCarrinho.innerHTML = '<li>Erro ao carregar o carrinho.</li>';
    }
};

// Função para limpar o carrinho
botaoLimpar.addEventListener('click', async () => {
    try {
        const response = await fetch('http://localhost:8080/api/carrinho/limpar', { method: 'DELETE' });
        if (!response.ok) {
            const errorDetails = await response.text();
            throw new Error(`Erro ao limpar o carrinho: ${errorDetails}`);
        }

        const message = await response.text();
        console.log(message || "Carrinho limpo com sucesso.");
        atualizarCarrinho(); // Atualiza a lista do carrinho após limpar
    } catch (error) {
        console.error('Erro ao limpar o carrinho:', error);
    }
});

// Atualiza a lista de produtos ao carregar a página
document.addEventListener('DOMContentLoaded', atualizarCarrinho);