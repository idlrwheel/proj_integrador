const carregarProdutos = async () => {
    const container = document.getElementById('produtos'); 
    try {
        const response = await fetch('http://localhost:8080/produtos'); 
        const produtos = await response.json();
        console.log(produtos);

        produtos.forEach(produto => {
            if (produto.status.toUpperCase() === 'ATIVO' && produto.qtdEstoque > 0) {
                const card = document.createElement('div'); 
                card.classList.add('produto-card');
                card.innerHTML = `
                    <img src="${produto.imagens[0]?.diretorioOrigem || '/ecommerce/frontend/assets/default.png'}" alt="${produto.nome}" class="produto-imagem">
                    <div class="produto-info">
                        <h3>${produto.nome}</h3>
                        <p>${produto.avaliacao} ⭐</p>
                        <p>R$ ${produto.valorProduto.toFixed(2)}</p>
                        <button onclick="verDetalhes(${produto.codigo})" class="botao-detalhes">Detalhes</button>
                    </div>
                `;
                console.log(container);
                container.appendChild(card); 
                console.log('Card criado:', card);
            }
        });
    } catch (error) {
        console.error('Erro ao carregar produtos:', error);
        container.innerHTML = '<p>Erro ao carregar produtos. Tente novamente mais tarde.</p>';
    }
};

const verDetalhes = (codigo) => {
    window.location.href = `/ecommerce/frontend/detalhes.html?codigo=${codigo}`; 
};

document.addEventListener('DOMContentLoaded', () => {
    console.log('DOM carregado!');
    carregarProdutos();
});

