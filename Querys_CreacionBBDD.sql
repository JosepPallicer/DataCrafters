/***************************************************************************************************/

CREATE TABLE TiposClientes (
    tipo VARCHAR(255) PRIMARY KEY,
    cuotaAnual DECIMAL(10, 2),
    descuentoEnvio DECIMAL(4, 2)
);

CREATE TABLE Clientes (
    email VARCHAR(255) PRIMARY KEY,
    nombre VARCHAR(255),
    domicilio VARCHAR(255),
    nif VARCHAR(255),
    tipo VARCHAR(255),
    FOREIGN KEY (tipo) REFERENCES TiposClientes (tipo)
);

CREATE TABLE Articulos (
    codigo VARCHAR(255) PRIMARY KEY,
    descripcion VARCHAR(255),
    precio DECIMAL(10, 2),
    tiempoPreparacion INT,
    gastosEnvio DECIMAL(10, 2)
);

CREATE TABLE Pedidos (
    numeroPedido INT PRIMARY KEY,
    emailCliente VARCHAR(255),
    codigoArticulo VARCHAR(255),
    cantidad INT,
    fechaHora DATETIME,
    enviado BOOLEAN,
    FOREIGN KEY (emailCliente) REFERENCES Clientes (email),
    FOREIGN KEY (codigoArticulo) REFERENCES Articulos (codigo)
);

/*************************************************************************************************/

/*Realizacion de los procedures necesarios*/

DROP PROCEDURE IF EXISTS agregarArticulo; /*Para borrar el procedure*/
drop Procedure if exists agregar_pedido_nuevo_cliente; 
DELIMITER //
CREATE PROCEDURE agregarArticulo(
IN p_codigo VARCHAR(255),
IN p_descripcion VARCHAR(255),
IN p_precio DECIMAL(10, 2),
IN p_tiempoPreparacion INT,
IN p_gastosEnvio DECIMAL(10, 2)
)
BEGIN
INSERT INTO Articulos (codigo, descripcion, precio, tiempoPreparacion, gastosEnvio) 
    VALUES (p_codigo, p_descripcion, p_precio, p_tiempoPreparacion, p_gastosEnvio);
END //
DELIMITER ;

CALL agregarArticulo('1', 'Libreta', 4.95, 10, 5.95);
SELECT * FROM Articulos;


DELIMITER //
CREATE PROCEDURE agregarCliente(
    IN p_email VARCHAR(255),
    IN p_nombre VARCHAR(255),
    IN p_domicilio VARCHAR(255),
    IN p_nif VARCHAR(20),
    IN p_tipo VARCHAR(255)
)
BEGIN
    INSERT INTO Clientes (nombre, domicilio, nif, email) 
    VALUES (p_nombre, p_domicilio, p_nif, p_email);
END //
DELIMITER ;

CALL agregarCliente('AnaRam@email.com', 'Ana Ramirez', 'Calle Sacromonte 1', '123456A', 'Cliente Estandar');
SELECT * FROM Clientes;
SELECT * FROM Clientes WHERE tipo = 'Estándar';
SELECT * FROM Clientes WHERE tipo = 'Premium';


DELIMITER //
CREATE PROCEDURE agregarPedido(
    IN p_numero_pedido INT,
    IN p_nombre_cliente VARCHAR(255),
    IN p_domicilio_cliente VARCHAR(255),
    IN p_nif_cliente VARCHAR(20),
    IN p_email_cliente VARCHAR(255),
    IN p_codigo_articulo VARCHAR(255),
    IN p_cantidad INT,
    IN fechaHora DATETIME,
    IN p_enviado BOOLEAN
)
BEGIN
    -- Verificar si el cliente ya existe
    DECLARE cliente_existente INT;
    SELECT COUNT(*) INTO cliente_existente FROM Clientes WHERE nif = p_nif_cliente;
    
    IF cliente_existente = 0 THEN
        -- Si el cliente no existe, agregarlo
        INSERT INTO Clientes (nombre, domicilio, nif, email, tipo) 
        VALUES (p_nombre_cliente, p_domicilio_cliente, p_nif_cliente, p_email_cliente, 'Estándar');
    END IF;
    
    -- Añadir el pedido
    INSERT INTO Pedidos (numeroPedido, emailCliente, codigoArticulo, cantidad, fechaHora, enviado) 
    VALUES (p_numero_pedido, p_email_cliente, p_codigo_articulo, p_cantidad, NOW(), p_enviado);
END //
DELIMITER ;


CALL agregarPedido(1, 'Ana Ramirez', 'Calle Sacromonte 1', '123456A', 'AnaRam@email.com', '1', 2, NOW(), TRUE);
CALL agregarPedido(2, 'Ana Ramirez', 'Calle Sacromonte 1', '123456A', 'AnaRam@email.com', '1', 4, NOW(), FALSE);


SELECT * FROM Clientes;
SELECT * FROM Pedidos;


DELIMITER //
CREATE PROCEDURE eliminar_pedido(
    IN p_numero_pedido INT
)
BEGIN
    DELETE FROM Pedidos WHERE numeroPedido = p_numero_pedido AND enviado = 0;
END //
DELIMITER ;

CALL eliminar_pedido(1);


DELIMITER //
CREATE PROCEDURE mostrar_pedidos_enviados(
    IN p_nif_cliente VARCHAR(20)
)
BEGIN
    SELECT 
        p.numeroPedido,
        p.fechaHora,
        c.nif AS nif_cliente,
        c.nombre AS nombre_cliente,
        a.codigo AS codigo_articulo,
        a.descripcion AS descripcion_articulo,
        p.cantidad,
        p.enviado
    FROM 
        Pedidos p
    INNER JOIN Clientes c ON p.emailCliente = c.email
    INNER JOIN Articulos a ON p.codigoArticulo = a.codigo
    WHERE 
        c.nif = p_nif_cliente AND p.enviado = TRUE;
END //

CALL mostrar_pedidos_enviados('123456A');


DELIMITER //
CREATE PROCEDURE mostrar_pedidos_pendientes(
    IN p_nif_cliente VARCHAR(20)
)
BEGIN
    SELECT 
        p.numeroPedido,
        p.fechaHora,
        c.nif AS nif_cliente,
        c.nombre AS nombre_cliente,
        a.codigo AS codigo_articulo,
        a.descripcion AS descripcion_articulo,
        p.cantidad,
        p.enviado
    FROM 
        Pedidos p
    INNER JOIN Clientes c ON p.emailCliente = c.email
    INNER JOIN Articulos a ON p.codigoArticulo = a.codigo
    WHERE 
        c.nif = p_nif_cliente AND p.enviado = FALSE;
END //
DELIMITER ;


CALL mostrar_pedidos_pendientes('123456A');