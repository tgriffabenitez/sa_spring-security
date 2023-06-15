-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS sa_empleados;

-- Usar la base de datos
USE sa_empleados;

-- Crear la tabla "empleados"
CREATE TABLE IF NOT EXISTS empleados (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(255),
  apellido VARCHAR(255),
  email VARCHAR(255),
  telefono VARCHAR(255),
  direccion VARCHAR(255),
  ciudad VARCHAR(255),
  pais VARCHAR(255),
  cargo VARCHAR(255),
  salario VARCHAR(255)
);

-- Insertar datos de ejemplo en la tabla "empleados"
INSERT INTO empleados (id, nombre, apellido, email, telefono, direccion, ciudad, pais, cargo, salario) VALUES
(1, 'John', 'Doe', 'johndoe@example.com', '555-1234', '123 Main St', 'Ciudad Ejemplo', 'País Ejemplo', 'Gerente', '5000'),
(2, 'Jane', 'Smith', 'janesmith@example.com', '555-5678', '456 Elm St', 'Ciudad Ejemplo', 'País Ejemplo', 'Analista', '3500'),
(3, 'Michael', 'Johnson', 'michaeljohnson@example.com', '555-9876', '789 Oak St', 'Ciudad Ejemplo', 'País Ejemplo', 'Desarrollador', '4000'),
(4, 'Emily', 'Williams', 'emilywilliams@example.com', '555-4321', '321 Pine St', 'Ciudad Ejemplo', 'País Ejemplo', 'Diseñador', '3800'),
(5, 'Daniel', 'Brown', 'danielbrown@example.com', '555-2468', '654 Cedar St', 'Ciudad Ejemplo', 'País Ejemplo', 'Contador', '4500'),
(6, 'Olivia', 'Jones', 'oliviajones@example.com', '555-1357', '987 Walnut St', 'Ciudad Ejemplo', 'País Ejemplo', 'Especialista de Marketing', '4200'),
(7, 'David', 'Miller', 'davidmiller@example.com', '555-8642', '852 Maple St', 'Ciudad Ejemplo', 'País Ejemplo', 'Gerente de Proyectos', '5500'),
(8, 'Sophia', 'Anderson', 'sophiaanderson@example.com', '555-5763', '753 Birch St', 'Ciudad Ejemplo', 'País Ejemplo', 'Analista de Datos', '3700'),
(9, 'James', 'Taylor', 'jamestaylor@example.com', '555-3189', '159 Spruce St', 'Ciudad Ejemplo', 'País Ejemplo', 'Desarrollador', '4100'),
(10, 'Emma', 'Thomas', 'emmathomas@example.com', '555-7420', '420 Ash St', 'Ciudad Ejemplo', 'País Ejemplo', 'Diseñador', '3800'),
(11, 'Alexander', 'Harris', 'alexanderharris@example.com', '555-9674', '746 Oak St', 'Ciudad Ejemplo', 'País Ejemplo', 'Contador', '4600'),
(12, 'Mia', 'Clark', 'miaclark@example.com', '555-5796', '697 Elm St', 'Ciudad Ejemplo', 'País Ejemplo', 'Especialista de Marketing', '4200'),
(13, 'Benjamin', 'Lewis', 'benjaminlewis@example.com', '555-2543', '435 Pine St', 'Ciudad Ejemplo', 'País Ejemplo', 'Gerente de Proyectos', '5700'),
(14, 'Charlotte', 'Hall', 'charlottehall@example.com', '555-7621', '126 Cedar St', 'Ciudad Ejemplo', 'País Ejemplo', 'Analista de Datos', '3800'),
(15, 'William', 'Young', 'williamyoung@example.com', '555-1638', '367 Walnut St', 'Ciudad Ejemplo', 'País Ejemplo', 'Desarrollador', '4100');