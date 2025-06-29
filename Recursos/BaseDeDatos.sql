
-- Crear base de datos
CREATE DATABASE corebancario;
use corebancario;


-- Tabla persona
CREATE TABLE persona (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    genero VARCHAR(10) NOT NULL,
    edad INT NOT NULL,
    identificacion VARCHAR(20) NOT NULL UNIQUE,
    direccion VARCHAR(200),
    telefono VARCHAR(20)
);

-- Tabla cliente
CREATE TABLE cliente (
    id BIGINT PRIMARY KEY,
    contrasena VARCHAR(100) NOT NULL,
    estado BOOLEAN NOT NULL,
    FOREIGN KEY (id) REFERENCES persona(id)
);

-- Tabla cuenta
CREATE TABLE cuenta (
    numero_cuenta BIGINT PRIMARY KEY,
    tipo_cuenta VARCHAR(20) NOT NULL,
    saldo_inicial DECIMAL(12,2) NOT NULL,
    estado BOOLEAN NOT NULL,
    cliente_id BIGINT NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES cliente(id)
);

-- Tabla movimiento
CREATE TABLE movimiento (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    tipo_movimiento VARCHAR(20) NOT NULL,
    valor DECIMAL(12,2) NOT NULL,
    saldo DECIMAL(12,2) NOT NULL,
    cuenta_id BIGINT NOT NULL,
    FOREIGN KEY (cuenta_id) REFERENCES cuenta(numero_cuenta)
);


SELECT * from corebancario.persona;

SELECT * from corebancario.cliente;

SELECT * from corebancario.cuenta;

SELECT * from corebancario.movimiento;
