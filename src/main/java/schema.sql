CREATE DATABASE IF NOT EXISTS foodStore;
USE foodStore;

CREATE TABLE IF NOT EXISTS categoria(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre      VARCHAR(100) UNIQUE,
    descripcion TEXT,
    eliminado   BOOLEAN DEFAULT FALSE,
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME DEFAULT NULL,
    deleted_at  DATETIME DEFAULT NULL
    );


CREATE TABLE IF NOT EXISTS usuarios (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre      VARCHAR(100) NOT NULL,
    apellido    VARCHAR(100) NOT NULL,
    mail        VARCHAR(100) UNIQUE NOT NULL,
    celular     VARCHAR(100) NOT NULL,
    contrasenia VARCHAR(100) NOT NULL,
    rol         VARCHAR(50) NOT NULL,
    eliminado   BOOLEAN DEFAULT FALSE,
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME DEFAULT NULL,
    deleted_at  DATETIME DEFAULT NULL
    );