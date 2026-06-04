CREATE DATABASE IF NOT EXISTS foodstore;
USE foodstore;

CREATE TABLE IF NOT EXISTS categoria(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre      VARCHAR(100) UNIQUE,
    descripcion TEXT,
    eliminado   BOOLEAN DEFAULT FALSE,
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME DEFAULT NULL,
    deleted_at  DATETIME DEFAULT NULL
    );