const carregarProdutos = async () => {
    const container = document.getElementById('produtos'); 
    try {
        const response = await fetch('http://localhost:8080/produtos'); 
        const produtos = await response.json();

        produtos.forEach(produto => {
            if (produto.status === 'ativo' && produto.qtdEstoque > 0) {
                const card = document.createElement('div'); 
                card.classList.add('produto-card');
                card.innerHTML = `
                    <img src="${produto.imagens[0]?.diretorioOrigem || '/frontend/assets/default.jpg'}" alt="${produto.nome}" class="produto-imagem">
                    <div class="produto-info">
                        <h3>${produto.nome}</h3>
                        <p>Avaliação: ${produto.avaliacao} ⭐</p>
                        <p>Preço: R$ ${produto.valorProduto.toFixed(2)}</p>
                        <button onclick="verDetalhes(${produto.codigo})" class="botao-detalhes">Ver Detalhes</button>
                    </div>
                `;
                container.appendChild(card);
            }
        });
    } catch (error) {
        console.error('Erro ao carregar produtos:', error);
        container.innerHTML = '<p>Erro ao carregar produtos. Tente novamente mais tarde.</p>';
    }
};

const verDetalhes = (codigo) => {
    window.location.href = `/frontend/detalhes.html?codigo=${codigo}`; 
};


document.addEventListener('DOMContentLoaded', carregarProdutos);
