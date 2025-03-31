const params = new URLSearchParams(window.location.search);
const codigoProduto = params.get("codigo");
console.log("Código do produto:", codigoProduto);

const carregarDetalhesProduto = async () => {
    const nomeElemento = document.getElementById("nome-produto");
    const avaliacaoElemento = document.getElementById("avaliacao-produto");
    const precoElemento = document.getElementById("preco-produto");
    const estoqueElemento = document.getElementById("estoque-produto");
    const descricaoElemento = document.getElementById("descricao-produto");
    const swiperWrapper = document.getElementById("swiper-wrapper");

    try {
        console.log(`Buscando detalhes do produto com o código: ${codigoProduto}`);
        const response = await fetch(`http://localhost:8080/produtos/${codigoProduto}`);
        console.log("Resposta da requisição:", response);

        if (!response.ok) {
            throw new Error(`Erro na requisição: ${response.status} ${response.statusText}`);
        }

        const produto = await response.json();
        console.log("Produto carregado:", produto);

        const produtoImagens = produto.imagens.length > 0 ? produto.imagens : ["/ecommerce/frontend/assets/default.png"];
        
        produtoImagens.forEach((imagem) => {
            const slide = document.createElement("div");
            slide.classList.add("swiper-slide");
            slide.innerHTML = `<img src="${imagem.diretorioOrigem || "/ecommerce/frontend/assets/default.png"}" alt="Imagem do Produto">`;
            swiperWrapper.appendChild(slide);
        });

        if (produtoImagens.length < 3) {
            produtoImagens.forEach((imagem) => {
                const slideDuplicado = document.createElement("div");
                slideDuplicado.classList.add("swiper-slide");
                slideDuplicado.innerHTML = `<img src="${imagem.diretorioOrigem || "/ecommerce/frontend/assets/default.png"}" alt="Imagem do Produto">`;
                swiperWrapper.appendChild(slideDuplicado);
            });
        }

        nomeElemento.innerText = produto.nome;
        avaliacaoElemento.innerText = `Avaliação: ${produto.avaliacao} ⭐`;
        precoElemento.innerText = `Preço: R$ ${produto.valorProduto.toFixed(2)}`;
        estoqueElemento.innerText = `Estoque: ${produto.qtdEstoque > 0 ? `${produto.qtdEstoque} unidades` : "Indisponível"}`;
        descricaoElemento.innerText = produto.descricaoDetalhada;

        
        const isLoopEnabled = produtoImagens.length >= 3;
        new Swiper(".swiper-container", {
            loop: isLoopEnabled,
            navigation: {
                nextEl: ".swiper-button-next",
                prevEl: ".swiper-button-prev",
            },
            pagination: {
                el: ".swiper-pagination",
                clickable: true,
            },
        });
    } catch (error) {
        console.error("Erro ao carregar os detalhes do produto:", error);
        document.querySelector(".detalhes-produto").innerHTML = "<p>Erro ao carregar os detalhes do produto. Tente novamente mais tarde.</p>";
    }
};

document.addEventListener("DOMContentLoaded", () => {
    console.log("DOM carregado!");
    carregarDetalhesProduto();
});

