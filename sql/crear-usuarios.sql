-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS sa_usuarios;

-- Usar la base de datos
USE sa_usuarios;

-- Crear la tabla "roles"
CREATE TABLE IF NOT EXISTS roles (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(255),
  descripcion VARCHAR(255)
);

-- Crear la tabla "usuario"
CREATE TABLE IF NOT EXISTS usuario (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(255),
  password VARCHAR(255),
  nombre VARCHAR(255)
);

-- Crear la tabla intermedia "usuario_rol"
CREATE TABLE IF NOT EXISTS usuario_rol (
  usuario_id BIGINT,
  rol_id BIGINT,
  PRIMARY KEY (usuario_id, rol_id),
  FOREIGN KEY (usuario_id) REFERENCES usuario(id),
  FOREIGN KEY (rol_id) REFERENCES roles(id)
);

-- Insertar datos en la tabla "roles"
INSERT INTO roles (nombre, descripcion) VALUES
('usuario', 'Rol de usuario'),
('admin', 'Rol de administrador');

-- Insertar datos en la tabla "usuario"
-- la contrasena de admin es admin (encriptada con bcrypt)
-- la contrasena de usuario es usuario (encriptada con bcrypt)
INSERT INTO usuario (email, password, nombre) VALUES
('admin', '$2y$10$VlXGApMEp.SWTgNrPujYse9sYtb97NqxmPjGmno3ts3dqkaI5Y3LW', 'admin'),
('usuario', '$2y$10$LePiaCkAYQQRxQmLMEEYNeejVtRjdjMc1O9AXrT23s.o3k3wRbjYq', 'usuario');

-- Insertar relación entre usuario y roles
INSERT INTO usuario_rol (usuario_id, rol_id)
SELECT usuario.id, roles.id
FROM usuario, roles
WHERE usuario.email = 'admin' AND roles.nombre = 'admin';

-- Insertar relación entre usuario y roles
INSERT INTO usuario_rol (usuario_id, rol_id)
SELECT usuario.id, roles.id
FROM usuario, roles
WHERE usuario.email = 'usuario' AND roles.nombre = 'usuario';
