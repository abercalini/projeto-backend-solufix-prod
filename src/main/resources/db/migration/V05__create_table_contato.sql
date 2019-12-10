CREATE TABLE contato (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50),
	telefone VARCHAR(20),
	email VARCHAR(30),
	mensagem TEXT
) ENGINE=InnoDB DEFAULT charset=utf8;