document.addEventListener('DOMContentLoaded', async function () {
    console.log("DOM carregado!");
    const userButton = document.getElementById('userButton');
    const userMenu = document.getElementById('userMenu');
    const menuDropdown = document.getElementById('menuDropdown');
    const logoutBtn = document.getElementById('logoutBtn');

    try {
        console.log("Verificando sessão do usuário...");
        const response = await fetch("http://127.0.0.1:8080/api/usuarios/sessao", {
            method: "GET",
            credentials: "include"
        });

        console.log("Status da resposta:", response.status, response.statusText);

        if (response.ok) {
            const usuario = await response.json();
            console.log("Usuário logado:", usuario);
            userButton.innerText = "Olá, " + usuario.nome;

            userButton.addEventListener('click', (event) => {
                event.stopPropagation(); 
                menuDropdown.style.display = menuDropdown.style.display === "block" ? "none" : "block";
            });

            document.addEventListener('click', (event) => {
                if (!userMenu.contains(event.target)) {
                    menuDropdown.style.display = "none";
                }
            });

            logoutBtn.addEventListener('click', async () => {
                const confirmLogout = confirm("Você está prestes a sair. Deseja continuar?");
                if (confirmLogout) {
                    await fetch("http://127.0.0.1:8080/api/usuarios/logout", {
                        method: "POST",
                        credentials: "include"
                    });

                    alert("Você foi deslogado com sucesso!");
                    window.location.href = "index.html"; 
                }
            });

        } else {
            console.log("Usuário não logado.");
            userMenu.innerHTML = `<a href="login.html" class="userBtn">Entre ou Cadastre-se</a>
            <a href="/ecommerce/frontend/carrinho.html" class="carrinho">
            <img src="/ecommerce/frontend/assets/cart.png" alt="Carrinho" class="icone-carrinho" />
            </a>`;
        }
    } catch (error) {
        console.error("Erro ao verificar sessão:", error);
        userMenu.innerHTML = `<a href="login.html">Entre ou Cadastre-se</a>`;
    }
});
