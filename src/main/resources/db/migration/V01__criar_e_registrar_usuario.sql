CREATE TABLE usuario (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL,
	senha VARCHAR(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE permissao (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	descricao VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE usuario_permissao (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	usuario_codigo BIGINT(20) NOT NULL,
	permissao_codigo BIGINT(20) NOT NULL,
	FOREIGN KEY (usuario_codigo) REFERENCES usuario(codigo),
	FOREIGN KEY (permissao_codigo) REFERENCES permissao(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO usuario(nome, email, senha) values ('Alisson', 'bercalini_alisson@hotmail.com', '$2a$10$H9wGcuXT9PlnRCBHVL0bZ.hmXS4VKHDa8VuFDViQq9qxiIQMeUO2i');
INSERT INTO usuario(nome, email, senha) values ('Flavio', 'fbercalini@hotmail.com', '$2a$10$OcDOwHsoP8/HPEnd0vmDd.JRQdQNiWx0bUTuISgQpiPn4DAah6pzG');


INSERT INTO permissao(descricao) values ('ROLE_CADASTRAR_CATEGORIA');
INSERT INTO permissao(descricao) values ('ROLE_PESQUISAR_CATEGORIA');
INSERT INTO permissao(descricao) values ('ROLE_REMOVER_CATEGORIA');

INSERT INTO permissao(descricao) values ('ROLE_CADASTRAR_PRODUTO');
INSERT INTO permissao(descricao) values ('ROLE_PESQUISAR_PRODUTO');
INSERT INTO permissao(descricao) values ('ROLE_REMOVER_PRODUTO');

INSERT INTO permissao(descricao) values ('ROLE_CADASTRAR_USUARIO');
INSERT INTO permissao(descricao) values ('ROLE_PESQUISAR_USUARIO');
INSERT INTO permissao(descricao) values ('ROLE_REMOVER_USUARIO');



INSERT INTO usuario_permissao(usuario_codigo, permissao_codigo) values (1,1);
INSERT INTO usuario_permissao(usuario_codigo, permissao_codigo) values (1,2);
INSERT INTO usuario_permissao(usuario_codigo, permissao_codigo) values (1,3);
INSERT INTO usuario_permissao(usuario_codigo, permissao_codigo) values (1,4);
INSERT INTO usuario_permissao(usuario_codigo, permissao_codigo) values (1,5);
INSERT INTO usuario_permissao(usuario_codigo, permissao_codigo) values (1,6);
INSERT INTO usuario_permissao(usuario_codigo, permissao_codigo) values (1,7);
INSERT INTO usuario_permissao(usuario_codigo, permissao_codigo) values (1,8);
INSERT INTO usuario_permissao(usuario_codigo, permissao_codigo) values (1,9);

INSERT INTO usuario_permissao(usuario_codigo, permissao_codigo) values (2,2);
INSERT INTO usuario_permissao(usuario_codigo, permissao_codigo) values (2,5);