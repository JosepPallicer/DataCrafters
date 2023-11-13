/***************************************************************************************************/
CREATE database onlineStore;
DROP database  onlinestore;

/*******************Creo las tablas de la base de datos********************/


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
    enviado boolean not null,
    pagado boolean not null,
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


/*************** Creacion de Procedures aplicando transacciones y manejo de errores***************************/

DROP PROCEDURE IF EXISTS agregarArticulo; /*Para borrar el procedure*/

DELIMITER //

CREATE PROCEDURE agregarArticulo(
    IN p_nombre VARCHAR(50),
    IN p_descripcion VARCHAR(255),
    IN p_precio DECIMAL(10, 2),
    IN p_tiempoPreparacion INT,
    IN p_gastosEnvio DECIMAL(10, 2)
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error al agregar el artículo';
    END;

    START TRANSACTION;
    INSERT INTO Articulos (nombre, descripcion, precio, tiempoPreparacion, gastosEnvio)
    VALUES (p_nombre, p_descripcion, p_precio, p_tiempoPreparacion, p_gastosEnvio);
    COMMIT;
END //

DELIMITER ;

CALL agregarArticulo('Libreta Logan', 'Libretas de colores de la marca Logan', 4.95, 10, 5.95);
CALL agregarArticulo( 'Lapices Decor', 'Caja de 24 lapices de colores brillantes', 8.20, 10, 2.5);
SELECT * FROM Articulos;
ALTER TABLE Articulos AUTO_INCREMENT = 1;


---------------------------------------------------------------------------------------
drop procedure if exists eliminarArticulo;
DELIMITER //

CREATE PROCEDURE eliminarArticulo(
    IN p_id_codigo INT
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error al eliminar el artículo.';
    END;

    START TRANSACTION;

    -- Verificar si el artículo existe
    IF NOT EXISTS (SELECT 1 FROM Articulos WHERE id_codigo = p_id_codigo) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'El artículo no existe';
    ELSE
        -- Eliminar el artículo
        DELETE FROM Articulos WHERE id_codigo = p_id_codigo;
        COMMIT;
    END IF;
END //

DELIMITER ;


call eliminarArticulo(5);
select *from articulos;
--------------------------------------------------------------------------------
DELIMITER //

CREATE PROCEDURE buscarArticulo(
    IN p_id_codigo INT
)
BEGIN
    DECLARE error_msg VARCHAR(255);

    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        GET DIAGNOSTICS CONDITION 1 error_msg = MESSAGE_TEXT;
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error al buscar el artículo: ';
    END;

    START TRANSACTION;

    SELECT * FROM Articulos WHERE id_codigo = p_id_codigo;

    COMMIT;

END //

DELIMITER ;


select * from articulos;
call buscarArticulo(1);
----------------------------------------------------------------------
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
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error al agregar el cliente';
    END;

    START TRANSACTION;
    INSERT INTO Clientes (nif, email, nombre, domicilio, tipo, apellido1, apellido2)
    VALUES (p_nif, p_email, p_nombre, p_domicilio, p_tipo, p_apellido1, p_apellido2);
    COMMIT;
END //

DELIMITER ;

CALL agregarCliente('123456 A', 'anaRam@email.com', 'Ana', 'Ramirez', 'Bolivia', 'Calle Sacromonte 1', 'Cliente Estandar');
CALL agregarCliente('123456 B', 'luisBarr@email.com', 'Luis', 'Barrena', 'Conse', 'Calle Buenavista 1', 'Cliente Premiun');



SELECT * FROM Clientes;
SELECT * FROM Clientes WHERE tipo = 'Cliente Estandar';
SELECT * FROM Clientes WHERE tipo = 'Cliente Premiun';
ALTER TABLE Clientes AUTO_INCREMENT = 1;

-----------------------------------------------------------------------------------

drop procedure if exists eliminarCliente;

DELIMITER //

CREATE PROCEDURE eliminarCliente(IN p_nif VARCHAR(10))
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error al eliminar el cliente';
    END;

    START TRANSACTION;
    DELETE FROM Clientes WHERE nif = p_nif;
    COMMIT;
END //

DELIMITER ;


CALL eliminarCliente("123456 B"); 
select * from pedidos;
--------------------------------------------------------------------------------------
DELIMITER //

CREATE PROCEDURE mostrarClientes()
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error al mostrar clientes';
    END;

    START TRANSACTION;
    SELECT * FROM Clientes;
    COMMIT;
END //

DELIMITER ;

Call mostrarClientes;
-----------------------------------------------------------------------------------------
drop procedure if exists crearPedido;

DELIMITER //

CREATE PROCEDURE crearPedido(
    IN p_nifCliente VARCHAR(10),
    IN p_codigoArticulo VARCHAR(50),
    IN p_cantidad INT,
    IN p_enviado BOOLEAN,
    IN p_fechaHora TIMESTAMP
)
BEGIN
    DECLARE clienteID INT;
    DECLARE articuloID INT;
    DECLARE pedidoID INT;

    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error al crear el pedido';
    END;

    START TRANSACTION;

    -- Obtener el ID del cliente
    SELECT id_cliente INTO clienteID FROM Clientes WHERE nif = p_nifCliente;

    -- Verificar si el cliente existe
    IF clienteID IS NULL THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Cliente no existe';
    END IF;

    -- Obtener el ID del artículo
    SELECT id_codigo INTO articuloID FROM Articulos WHERE nombre = p_codigoArticulo;

    -- Verificar si el artículo existe
    IF articuloID IS NULL THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Artículo no existe';
    END IF;

    -- Insertar el nuevo pedido
    INSERT INTO Pedidos (id_cliente, enviado, pagado, fechaHora)
    VALUES (clienteID, p_enviado, FALSE, p_fechaHora);

    -- Obtener el ID del nuevo pedido
    SET pedidoID = LAST_INSERT_ID();

    -- Insertar el detalle del pedido
    INSERT INTO DetallePedido (id_numeroPedido, id_codigo, cantidad, precio)
    VALUES (pedidoID, articuloID, p_cantidad, (SELECT precio FROM Articulos WHERE id_codigo = articuloID));

    COMMIT;
END //

DELIMITER ;



CALL crearPedido('123456 A', 'libreta logan', 2, true, NOW());
CALL crearPedido('123456 B', 'lapices Decor', 4, false, NOW());
select * from pedidos;
--------------------------------------------------------------------------------------------------------
DELIMITER //

CREATE PROCEDURE eliminarPedido(IN p_id_numeroPedido INT)
BEGIN
    DECLARE error_msg VARCHAR(255);

    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        GET DIAGNOSTICS CONDITION 1 error_msg = MESSAGE_TEXT;
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error al eliminar el pedido: ';
    END;

    START TRANSACTION;

    -- Eliminar detalles del pedido
    DELETE FROM DetallePedido WHERE id_numeroPedido = p_id_numeroPedido;

    -- Eliminar el pedido
    DELETE FROM Pedidos WHERE id_numeroPedido = p_id_numeroPedido;

    COMMIT;
END //

DELIMITER ;

ALTER TABLE pedidos AUTO_INCREMENT = 1;
CALL eliminarPedido(3);  
select * from pedidos;

--------------------------------------------------------------------------------------------------------
DELIMITER //

CREATE PROCEDURE mostrarPedidos()
BEGIN
    -- Mostrar todos los pedidos (enviados y no enviados)
    SELECT * FROM Pedidos;
END //

DELIMITER ;

CALL mostrarPedidos();
-----------------------------------------------------------------------------------------------------------
DELIMITER //

CREATE PROCEDURE mostrarPedidosEnviados()
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error al mostrar pedidos enviados';
    END;

    START TRANSACTION;
    SELECT * FROM Pedidos WHERE enviado = TRUE;
    COMMIT;
END //

DELIMITER ;

CALL mostrarPedidosEnviados;
----------------------------------------------------------------------------------------------
DELIMITER //

CREATE PROCEDURE mostrarPedidosPendientes()
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error al mostrar pedidos pendientes';
    END;

    START TRANSACTION;
    SELECT * FROM Pedidos WHERE enviado = FALSE;
    COMMIT;
END //

DELIMITER ;

CALL mostrarPedidosEnviados;
---------------------------------------------------------------------------------------------------

