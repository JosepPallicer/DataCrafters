use onlinestore;

SHOW TABLES LIKE 'TiposClientes'; /*Para verificar la existencia de una tabla*/
DROP table IF EXISTS Clientes;  /* Borrar una tabla*/
DROP table IF EXISTS Tipoclientes;
DROP table IF EXISTS Pedidos;
DROP table IF EXISTS Articulos;
DROP table IF EXISTS detallePedido;

---------------------------------------------------------------------------------------
CREATE TABLE TipoClientes (
    tipo VARCHAR(20) PRIMARY KEY,
    cuotaAnual DECIMAL(10, 2),
    descuentoEnvio DECIMAL(4, 2)
);
DELETE FROM TipoClientes WHERE tipo = 'Cliente Premiun';

INSERT INTO TipoClientes (tipo, cuotaAnual, descuentoEnvio) VALUES
    ('Cliente Estandar', 00.00, 00.00),
    ('Cliente Premiun', 30.00, 06.00);
select * from TipoClientes;
--------------------------------------------------------------------------------

CREATE TABLE Clientes (
	id_cliente INT auto_increment primary KEY, 
	nif VARCHAR(10) unique,
    email VARCHAR(25) unique,
    nombre VARCHAR(20),
    apellido1 varchar(20),
    apellido2 varchar(20),
    domicilio VARCHAR(50),
    tipo VARCHAR(20),
    FOREIGN KEY (tipo) REFERENCES TipoClientes (tipo)
);
alter table Clientes auto_increment= 1;
-------------------------------------------------------------------------------------

CREATE TABLE Articulos (
    id_codigo int auto_increment PRIMARY KEY,
    nombre VARCHAR(50) unique,
    descripcion VARCHAR(255),
    precio DECIMAL(10, 2),
    tiempoPreparacion INT,
    gastosEnvio DECIMAL(10, 2)
);
--------------------------------------------------------------------------------------
CREATE TABLE Pedidos (
    id_numeroPedido INT AUTO_INCREMENT  PRIMARY KEY,
    id_cliente INT,
    fechaHora DATETIME,
    enviado ENUM('si','no'),
    pagado ENUM('si','no'),
    FOREIGN KEY (id_cliente) REFERENCES Clientes(id_cliente)
);
alter table Pedidos auto_increment= 1;
--------------------------------------------------------------------------------------

CREATE TABLE DetallePedido (
    id_numeroPedido INT,
    id_codigo int,
    PRIMARY KEY (id_numeroPedido, id_codigo),
    cantidad DECIMAL(10, 2),
    precio DECIMAL(10, 2),
    FOREIGN KEY (id_numeroPedido) REFERENCES Pedidos(id_numeroPedido),
    FOREIGN KEY (id_codigo) REFERENCES Articulos(id_codigo)
);


/**********************************Creacion de Procedures **************************/

DROP PROCEDURE IF EXISTS agregarArticulo; /*Para borrar el procedure*/

DELIMITER //

CREATE PROCEDURE agregarArticulo(
    IN nombre VARCHAR(50),
    IN p_descripcion VARCHAR(255),
    IN p_precio DECIMAL(10, 2),
    IN p_tiempoPreparacion INT,
    IN p_gastosEnvio DECIMAL(10, 2)
)
BEGIN
    INSERT INTO Articulos (descripcion, precio, tiempoPreparacion, gastosEnvio)
    VALUES (p_descripcion, p_precio, p_tiempoPreparacion, p_gastosEnvio);
END //

DELIMITER ;

CALL agregarArticulo( 'Libreta Logan', 'Libretas de colores de la marca Logan', 4.95, 10, 5.95);
SELECT * FROM Articulos;

---------------------------------------------------------------------------------------


DELIMITER //

CREATE PROCEDURE eliminarArticulo(
    IN p_id_codigo INT
)
BEGIN
    -- Eliminar el artículo si existe
    DELETE FROM Articulos WHERE id_codigo = p_id_codigo;
END //

DELIMITER ;

call eliminarArticulo(1);
--------------------------------------------------------------------------------

drop procedure if exists agregarCliente;

DELIMITER //
CREATE PROCEDURE agregarCliente(
    IN p_nif VARCHAR(10),
    IN p_email VARCHAR(25),
    IN p_nombre VARCHAR(20),
    IN p_apellido1 varchar(20),
     IN p_apellido2 varchar(20),
    IN p_domicilio VARCHAR(50),
    IN p_tipo VARCHAR(20)
)
BEGIN
    INSERT INTO Clientes (nif, email, nombre, domicilio, tipo)
    VALUES (p_nif, p_email, p_nombre, p_domicilio, p_tipo);
END //

DELIMITER ;


CALL agregarCliente('123456 A','anaRam@email.com', 'Ana', 'Ramirez', 'Bolivia', 'Calle Sacromonte 1',  'Cliente Estandar');
CALL agregarCliente('123456 B','luisBarr@email.com', 'Luis', 'Barrena', 'Conse', 'Calle Buenavista 1',  'Cliente Premiun');


SELECT * FROM Clientes;
SELECT * FROM Clientes WHERE tipo = 'Cliente Estandar';
SELECT * FROM Clientes WHERE tipo = 'Cliente Premiun';

-----------------------------------------------------------------------------------

drop procedure if exists eliminarCliente;

DELIMITER //

CREATE PROCEDURE eliminarCliente(IN p_nif VARCHAR(10))
BEGIN
    DELETE FROM Clientes WHERE nif = p_nif;
END //

DELIMITER ;

CALL eliminarCliente("123456 A"); 

--------------------------------------------------------------------------------------


