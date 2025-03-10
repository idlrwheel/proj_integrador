CREATE database ecommerce_pi;
USE ecommerce_pi;
CREATE TABLE userBackoffice(
id INT auto_increment PRIMARY KEY,
email VARCHAR(300) NOT NULL UNIQUE,
senha VARCHAR(100) NOT NULL,
tipoUser ENUM('adm', 'estoquista', 'cliente') NOT NULL,
status ENUM('ativado', 'desativado') NOT NULL,
nome VARCHAR(100),
cpf char(11)
);
INSERT INTO userBackoffice (email, senha, tipoUser) VALUES
('adm@exemplo.com', 'senha123', 'adm');

INSERT INTO userBackoffice (email, senha, tipoUser, status) VALUES 
('teste@teste.com', 'senha123', 'adm', 'ativado');



UPDATE userBackoffice SET senha = 'senha123' WHERE email = 'teste@teste.com';
SELECT senha FROM userBackoffice WHERE email = 'teste@teste.com';
UPDATE userBackoffice SET senha = '55a5e9e78207b4df8699d60886fa070079463547b095d1a05bc719bb4e6cd251' WHERE email = 'teste@teste.com';

INSERT INTO userBackoffice (email, senha, tipoUser, status) VALUES 
('estoque@teste.com', 'senha321', 'estoquista', 'ativado');
UPDATE userBackoffice SET senha = '2288821c6b799cf47a8c9aa231f361ffb906bbee0d5fb5e1767509e27442cc62' WHERE email = 'estoque@teste.com';
INSERT INTO userBackoffice (email, senha, tipoUser, status) VALUES 
('cliente@teste.com', 'senha321', 'cliente', 'ativado');
UPDATE userBackoffice SET senha = '2288821c6b799cf47a8c9aa231f361ffb906bbee0d5fb5e1767509e27442cc62' WHERE email = 'cliente@teste.com';

CREATE TABLE produtos(
codigo INT auto_increment PRIMARY KEY,
nome varchar(200) NOT NULL,
avaliacao DECIMAL(2,1) CHECK (avaliacao >= 1 AND avaliacao <= 5),
descricaoDetalhada varchar(2000) NOT NULL,
qtdEstoque int NOT NULL,
valorProduto DECIMAL(10,2) NOT NULL);

CREATE TABLE imagensProduto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    produto_id INT NOT NULL,
    nome_arquivo VARCHAR(255) NOT NULL,
    diretorio_origem VARCHAR(255) NOT NULL,
    principal BOOLEAN NOT NULL,
    FOREIGN KEY (produto_id) REFERENCES produtos(codigo) ON DELETE CASCADE
);

/*Adicionar a coluna de Status no banco de dados.  */
ALTER TABLE produtos ADD COLUMN status ENUM('ativo', 'desativado') NOT NULL DEFAULT 'ativo';




