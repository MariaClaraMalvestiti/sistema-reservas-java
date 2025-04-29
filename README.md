Sistema de Reservas

Este proyecto es una aplicación de escritorio desarrollada en Java que permite gestionar reservas conectándose a una base de datos MySQL. Muestra un enfoque en separación de capas y  buenas prácticas de desarrollo de software.

Funcionalidades:

- Listar todas las reservas.

- Buscar reservas por ID.

- Agregar nuevas reservas.

- Actualizar reservas existentes.

- Eliminar reservas.

Tecnologías utilizadas:

- Java 17

- MySQL

- JDBC para la conexión a la base de datos

- IDE: Eclipse / Visual Studio Code

Organización del proyecto:

El proyecto sigue una estructura de paquetes organizada:

- reservasDB.dominio → contiene la entidad Reserva.

- reservasDB.datos → contiene las clases para el acceso a datos (ReservaDAO, IReservaDAO) y servicios (ReservaService).

- reservasDB.presentacion → contiene el MenuAppReservas para interactuar con el usuario.

Configuración de la Base de Datos:

1. Crear una base de datos en MySQL, por ejemplo:
```sql
CREATE DATABASE reservasdb;
```

2. Crear la tabla correspondiente:
```sql
CREATE TABLE reservas (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre_reserva VARCHAR(50),
  apellido_reserva VARCHAR(50),
  fecha_reserva DATE,
  hora_reserva TIME
);
```

3. Actualizar los datos de conexión en el proyecto (ReservaDAO o donde corresponda):
```java
private static final String URL = "jdbc:mysql://localhost:3306/reservasdb";
private static final String USER = "tu_usuario";
private static final String PASSWORD = "tu_contraseña";
```
¿Cómo ejecutar?

1. Clonar el repositorio:
```bash
git clone https://github.com/MariaClaraMalvestiti/sistema-reservas-db.git
```

2. Importarlo en tu IDE favorito.

3. Ejecutar el programa iniciando MenuAppReservas.




Autor:

María Clara Malvestiti
