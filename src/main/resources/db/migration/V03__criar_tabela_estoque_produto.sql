CREATE TABLE produto (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	caminho_foto VARCHAR(100),
	nome VARCHAR(50) NOT NULL,
	descricao VARCHAR(50),
	codigo_barra VARCHAR(50),
	observacao VARCHAR(50),
	categoria_codigo BIGINT(20),
	FOREIGN KEY (categoria_codigo) REFERENCES categoria(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;