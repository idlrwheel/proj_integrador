
const params = new URLSearchParams(window.location.search);
const codigoProduto = params.get("codigo");

const carregarDetalhesProduto = async () => {
    const imagemElemento = document.getElementById("imagem-produto");
    const nomeElemento = document.getElementById("nome-produto");
    const avaliacaoElemento = document.getElementById("avaliacao-produto");
    const precoElemento = document.getElementById("preco-produto");
    const estoqueElemento = document.getElementById("estoque-produto");
    const descricaoElemento = document.getElementById("descricao-produto");

    try {
        const response = await fetch(`http://localhost:8080/produtos/${codigoProduto}`);
        const produto = await response.json();

        // Populando os detalhes do produto na página
        imagemElemento.src = produto.imagens[0]?.diretorioOrigem || '/frontend/assets/default.jpg';
        nomeElemento.innerText = produto.nome;
        avaliacaoElemento.innerText = `Avaliação: ${produto.avaliacao} ⭐`;
        precoElemento.innerText = `Preço: R$ ${produto.valorProduto.toFixed(2)}`;
        estoqueElemento.innerText = `Estoque: ${produto.qtdEstoque > 0 ? `${produto.qtdEstoque} unidades` : 'Indisponível'}`;
        descricaoElemento.innerText = produto.descricaoDetalhada;
    } catch (error) {
        console.error("Erro ao carregar os detalhes do produto:", error);
        document.querySelector(".detalhes-produto").innerHTML = "<p>Erro ao carregar os detalhes do produto. Tente novamente mais tarde.</p>";
    }
};

