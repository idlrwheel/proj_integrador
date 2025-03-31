const carregarProdutos = async () => {
    const container = document.getElementById('produtos'); 

    if (!container) {
        console.error('Elemento com ID "produtos" não encontrado no DOM.');
        return;
    }

    container.innerHTML = '';

    try {
        const response = await fetch('http://localhost:8080/produtos'); 
        if (!response.ok) {
            throw new Error(`Erro na API: ${response.status} ${response.statusText}`);
        }

        const produtos = await response.json();
        console.log('Produtos carregados:', produtos);

        if (!Array.isArray(produtos)) {
            throw new Error('A resposta da API não é um array de produtos.');
        }

        produtos.forEach(produto => {
            console.log(`Produto: ${produto.nome}, Status: ${produto.status}, Estoque: ${produto.qtdEstoque}`);
        
            if (produto.status === 'ativo' && produto.qtdEstoque > 0) {
        
                const card = document.createElement('div'); 
                card.classList.add('produto-card');
        
                card.innerHTML = `
                    <img src="${produto.imagens?.[0]?.diretorioOrigem || '/ecommerce/frontend/assets/default.png'}" alt="${produto.nome}" class="produto-imagem">
                    <div class="produto-info">
                        <h3>${produto.nome}</h3>
                        <p>${produto.avaliacao !== null ? produto.avaliacao + ' ⭐' : 'Sem avaliação'}</p>
                        <p>R$ ${produto.valorProduto?.toFixed(2) || '0.00'}</p>
                        <button onclick="verDetalhes(${produto.codigo})" class="botao-detalhes">Detalhes</button>
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
    window.location.href = `/ecommerce/frontend/detalhes.html?codigo=${codigo}`; 
};

document.addEventListener('DOMContentLoaded', () => {
    console.log('DOM carregado!');
    carregarProdutos();
});

