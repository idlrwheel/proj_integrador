const params = new URLSearchParams(window.location.search);
const codigoProduto = params.get("codigo");

const carregarDetalhesProduto = async () => {
    if (!codigoProduto) {
        document.querySelector(".detalhes-produto").innerHTML = "<p>Produto não encontrado. Código não fornecido.</p>";
        return;
    }

    const nomeElemento = document.getElementById("nome-produto");
    const avaliacaoElemento = document.getElementById("avaliacao-produto");
    const precoElemento = document.getElementById("preco-produto");
    const descricaoElemento = document.getElementById("descricao-produto");
    const swiperWrapper = document.getElementById("swiper-wrapper");

    try {
        const response = await fetch(`http://localhost:8080/produtos/${codigoProduto}`);
        if (!response.ok) {
            throw new Error("Erro ao buscar detalhes do produto.");
        }

        const produto = await response.json();

        if (!produto || !produto.nome) {
            document.querySelector(".detalhes-produto").innerHTML = "<p>Produto não encontrado.</p>";
            return;
        }

        const produtoImagens = produto.imagens.length > 0 ? produto.imagens : ["/ecommerce/frontend/assets/default.png"];

        produtoImagens.forEach((imagem) => {
            const slide = document.createElement("div");
            slide.classList.add("swiper-slide");
            slide.innerHTML = `<img src="${imagem.diretorioOrigem || "/ecommerce/frontend/assets/default.png"}" alt="Imagem do Produto">`;
            swiperWrapper.appendChild(slide);
        });

        nomeElemento.innerText = produto.nome;
        avaliacaoElemento.innerText = `Avaliação: ${produto.avaliacao} ⭐`;
        precoElemento.innerText = `R$ ${produto.valorProduto.toFixed(2)}`;
        descricaoElemento.innerText = `Descrição: ${produto.descricaoDetalhada}`;

        new Swiper(".swiper-container", {
            loop: produtoImagens.length >= 3,
            navigation: {
                nextEl: ".swiper-button-next",
                prevEl: ".swiper-button-prev",
            },
            pagination: {
                el: ".swiper-pagination",
                clickable: true,
            },
        });

        const botaoComprar = document.getElementById("comprar-botao");

        botaoComprar.addEventListener("click", async () => {
            try {
                const carrinho = {
                    produto: { codigo: produto.codigo },
                    quantidade: 1 
                };

                console.log("Objeto enviado ao backend:", carrinho);

                const responseCarrinho = await fetch('http://localhost:8080/api/carrinho/adicionar', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(carrinho),
                });

                if (!responseCarrinho.ok) {
                    const errorDetails = await responseCarrinho.text();
                    throw new Error(`Erro ao adicionar ao carrinho: ${responseCarrinho.status} ${responseCarrinho.statusText}. Detalhes: ${errorDetails}`);
                }

                console.log("Produto adicionado ao carrinho com sucesso!");
                window.location.href = "/ecommerce/frontend/carrinho.html";
            } catch (error) {
                console.error("Erro ao adicionar produto ao carrinho:", error);
                document.querySelector(".detalhes-produto").innerHTML = "<p>Erro ao adicionar o produto ao carrinho. Tente novamente mais tarde.</p>";
            }
        });

    } catch (error) {
        console.error("Erro ao carregar os detalhes do produto:", error);
        document.querySelector(".detalhes-produto").innerHTML = "<p>Erro ao carregar os detalhes do produto. Tente novamente mais tarde.</p>";
    }
};

document.addEventListener("DOMContentLoaded", carregarDetalhesProduto);