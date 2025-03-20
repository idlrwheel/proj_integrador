const containerProdutos = document.getElementById('produtos');

async function carregarProdutos() {
    try {
        const response = await fetch('http://localhost:8080/api/produtos');
        const produtos = await response.json();

        produtos.forEach(produto => {
            const card = document.createElement('div');
            card.classList.add('card');
            card.innerHTML = `
                <img src="${produto.imagemPrincipal}" alt="${produto.nome}">
                <h3>${produto.nome}</h3>
                <p>R$ ${produto.valorProduto.toFixed(2)}</p>
                <button onclick="abrirDetalhe(${produto.codigo})">Detalhes</button>
            `;
            containerProdutos.appendChild(card);
        });
    } catch (error) {
        console.error('Erro ao carregar produtos:', error);
    }
}

function abrirDetalhe(codigo) {
    window.location.href = `detalhe-produto.html?id=${codigo}`;
}

carregarProdutos();