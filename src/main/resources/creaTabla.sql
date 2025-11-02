-- Creación del esquema
CREATE DATABASE soda
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_unicode_ci;

USE soda;


-- Creación del usuario con contraseña
CREATE USER 'sodavidacr22@gmail.com'@'%' IDENTIFIED BY 'Sodavidaycafe_123';

-- Asignación de privilegios
GRANT ALL PRIVILEGES ON soda.* TO 'sodavidacr22@gmail.com'@'%';
FLUSH PRIVILEGES;

-- 1️⃣ Elimina el usuario anterior (si quieres limpiar)
DROP USER IF EXISTS 'sodavidacr22@gmail.com'@'%';

-- 2️⃣ Crea el nuevo usuario SIN arroba
CREATE USER 'sodavidacr22'@'%' IDENTIFIED BY 'Sodavidaycafe_123';

-- 3️⃣ Asigna permisos sobre la misma base de datos
GRANT ALL PRIVILEGES ON soda.* TO 'sodavidacr22'@'%';

-- 4️⃣ Aplica los cambios
FLUSH PRIVILEGES;

--   Sección de creación de tablas

-- Tabla: listado de usuarios
CREATE TABLE listado_usuarios (
  id_listadoUsuarios INT NOT NULL AUTO_INCREMENT,
  usuario VARCHAR(50),
  nombre VARCHAR(50),
  apellido VARCHAR(50),
  correo VARCHAR(100),
  fotografia VARCHAR(1024),
  PRIMARY KEY (id_listadoUsuarios)
) ENGINE = InnoDB;

INSERT INTO listado_usuarios (usuario, nombre, apellido, correo, fotografia) VALUES
('Pedro.10', 'Pedro', 'Pancho', 'Pedro@gmail.com', 'https://st4.depositphotos.com/5934840/23454/v/450/depositphotos_234542254-stock-illustration-man-profile-smiling-cartoon-vector.jpg'),
('Ana_8', 'Ana', 'Velasquez', 'Ana@gmail.com', 'https://us.123rf.com/450wm/yupiramos/yupiramos1609/yupiramos160911235/62789794-avatar-woman-cartoon-wearing-black-skirt-and-red-blouse-vector-illustration.jpg'),
('Geovanni-3', 'Geovanni', 'Berto', 'Geovanni@gmail.com', 'https://i.pinimg.com/736x/58/93/53/5893538d2888dc4e6d82ecf0994b7d18.jpg');

-- Tabla: listado de repostería y café
CREATE TABLE listado_reposteria_cafe (
  id_listadoReposteriaCafe INT NOT NULL AUTO_INCREMENT,
  descripcion VARCHAR(100),
  precio DECIMAL(10,2),
  imagen VARCHAR(1024),
  PRIMARY KEY (id_listadoReposteriaCafe)
) ENGINE = InnoDB;

INSERT INTO listado_reposteria_cafe (descripcion, precio, imagen) VALUES
('Pastel Zanahoria', 1500.00, 'https://www.splenda.com/wp-content/uploads/2020/10/carrot-cake-2000x1000.jpg'),
('Galleta Chocolate', 650.00, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTD8AqYRYhfHzSUZ_RS8nGZ_Hr8jYAgL9KIbm840yAsiT9-MM9009MdnNKLshmTtpFJsiI&usqp=CAU'),
('Britt', 300.00, 'https://cafebritt.cr/cdn/shop/files/Peaberry-Front-Molido.webp?v=1741989323');

-- Tabla: listado de productos
CREATE TABLE listado_productos (
  id_listadoProductos INT NOT NULL AUTO_INCREMENT,
  descripcion VARCHAR(100),
  precio DECIMAL(10,2),
  disponible BOOLEAN,
  categoria VARCHAR(50),
  imagen VARCHAR(1024),
  PRIMARY KEY (id_listadoProductos)
) ENGINE = InnoDB;

INSERT INTO listado_productos (descripcion, precio, disponible, categoria, imagen) VALUES
('Jugos', 1500.00, TRUE, 'Snacks', 'https://cdn.prod.website-files.com/6467bba45a17881079144c5a/64946314f472d3df511d3de9_Controversy%20of%20the%20Juices.jpg'),
('Doritos', 650.00, TRUE, 'Snacks', 'https://cdn.grupoelcorteingles.es/SGFM/dctm/MEDIA03/202509/19/00118009300841____1__600x600.jpg'),
('Barrilete', 300.00, TRUE, 'Snacks', 'https://walmartcr.vtexassets.com/arquivos/ids/564621/Caramelo-Super-Barrilete-Sabor-Fruta-400gr-1-31710.jpg?v=638458768116630000');



-- Consultas iniciales (solo se ejecutan si ya existen las tablas)
SELECT * FROM listado_usuarios;
SELECT * FROM listado_reposteria_cafe;
SELECT * FROM listado_productos;

--   Sección de administración (ejecutar una vez)
DROP DATABASE IF EXISTS sodavida;
DROP USER IF EXISTS 'sodavidacr22@gmail.com'@'%';
