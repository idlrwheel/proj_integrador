const listaCarrinho = document.getElementById('lista-carrinho');
const botaoLimpar = document.getElementById('limpar-carrinho');


const adicionarAoCarrinho = async (produto) => {
    try {
        const response = await fetch('http://localhost:8080/carrinho/adicionar', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(produto),
        });
        const data = await response.json();
        console.log(data.message);
        atualizarCarrinho(); 
    } catch (error) {
        console.error('Erro ao adicionar ao carrinho:', error);
    }
};


const atualizarCarrinho = async () => {
    try {
        const response = await fetch('http://localhost:8080/carrinho');
        const produtos = await response.json();
        listaCarrinho.innerHTML = ''; 
        produtos.forEach((produto) => {
            const item = document.createElement('li');
            item.innerText = `Produto: ${produto.nome} | Preço: R$ ${produto.preco.toFixed(2)}`;
            listaCarrinho.appendChild(item);
        });
    } catch (error) {
        console.error('Erro ao atualizar o carrinho:', error);
    }
};

botaoLimpar.addEventListener('click', async () => {
    try {
        const response = await fetch('http://localhost:8080/carrinho/limpar', { method: 'DELETE' });
        const data = await response.json();
        console.log(data.message);
        atualizarCarrinho(); 
    } catch (error) {
        console.error('Erro ao limpar o carrinho:', error);
    }
});


document.addEventListener('DOMContentLoaded', atualizarCarrinho);

document.getElementById('comprar-botao').addEventListener('click', () => {
    const produto = {
        nome: document.getElementById('nome-produto').innerText,
        preco: parseFloat(document.getElementById('preco-produto').innerText.replace('R$', '').trim()),
    };
    adicionarAoCarrinho(produto);
});
