# Proyecto Final - Spring Security

## Aclaraciones
- En la carpeta `postman` esta la coleccion de requests y las variables de ambiente para probar el proyecto. En el nombre de los request, esta especificado el rol del usuario que tiene permisos para realizar ese request ([admin], [usuario] [admin/usuario]). Tambien es posible crear nuevos usarios utilizando el request `register`, a este usuario se le asignara por defecto el rol `usuario`. Para facilitar y agilizar el testeo de los endpoints se crearon las variables de entorno `Bearer` y `Roles` (variables de ambiente) las cuales obtienen automaticamente el bearer token y el rol del header, de esta forma se evita tener que estar copiando y pegando el token en los requests.

- En la carpeta `sql` estan los scrips necesarios para crear las tablas que se usan en los microservicios ms-usuarios y ms-empleados. A demas de crear las tablas se cargan valores iniciales para poder probar el proyecto. __Nota__: se recomienda usar base de datos MySQL.

- Dentro de los miscroservicios, en la carpeta `./target/site/apidocs` se encuentra en archivo `index.html` que contiene la documentacion de javadocs de todas las clases y funciones.


## Cuestionario

1. ¿Cuáles son los mecanismos que dispone SpringBoot para la generación y manipulación de los datos contenidos en el header?

    Para manipular los headers y sus datos se pueden utilizar las clases HttpHeaders y la clase HttpEntity. La clase HttpHeaders permite agregar, obtener y manipular los headers HTTP, mientras que la clase HttpEntity te permite "encapsular" una solicitud o una respuesta HTTP, incluyendo los encabezados.

    _Para este proyecto se utilizo la clase HttpHeaders para obtener y agregar informacion al header. Luego esa informacion es almacenada en una cache._

2. ¿Una vez que me autentico exitosamente en el sistema y este me envía el token, como hace SpringBoot para saber que el token es válido?

    La validación de un token generalmente implica la verificación de la firma y la comprobación de la validez y la integridad del token. La forma en que Spring Boot realiza esta validación depende de la configuración y las bibliotecas utilizadas.

    - Configuración del proveedor de tokens: En Spring Boot, se puede configurar un proveedor de tokens que se encargue de la generación, validación y administración de los tokens. Por lo general, se utiliza una biblioteca como "jjwt" para manejar los tokens JWT.
    
    - Firma y clave secreta: Durante la generación del token, se utiliza una clave secreta conocida solo por el servidor para firmar el token. Esta firma garantiza que el token no ha sido alterado por terceros. Durante la validación, Spring Boot utiliza la misma clave secreta para verificar la firma del token recibido.

    - Verificación de la firma: Spring Boot valida la firma del token utilizando la clave secreta configurada. Si la firma no coincide, se considera que el token es inválido.

    - Fecha de expiración: El token JWT generalmente contiene una fecha de expiración. Spring Boot verifica si el token ha expirado comparando la fecha actual con la fecha de expiración especificada en el token. Si el token ha expirado, se considera inválido.

    _Esto fue implementado en el ms-bff. Una vez que recibo el token, con la misma key con la que fue creado valido si el token es valido o no. Tambien verifico la fecha de expiracion del token, por defecto y para probar la funcionalidad en postman, la vida util del token es de 60 segundos._

3. ¿Si gestiona su expiración, como lo hace?

    Para gestionar la expiración de los tokens en Spring Boot, se utilizan típicamente los JSON Web Tokens (JWT) y se incluye una fecha de expiración en el propio token. La expiración se establece al momento de generar el token y se verifica durante su validación.

    - Generación del token: Cuando un usuario se autentica exitosamente en el sistema, se genera un token JWT que contiene información relevante, como el identificador del usuario y los roles asignados. Al generar el token, se establece una fecha de expiración en el campo "exp" (expiry) del token. Esta fecha de expiración se representa como un timestamp en segundos o en formato de fecha y hora.

    - Validación del token: Cuando el servidor recibe una solicitud que incluye un token en el encabezado de autorización, se realiza la validación del token. Durante este proceso, se comprueba la firma del token y se verifica la fecha de expiración.

    Si el token ha expirado o no cumple con alguna de las validaciones, el servidor puede rechazar la solicitud y responder con un código de estado HTTP adecuado, como "401 Unauthorized" o "403 Forbidden".

    _En el ms-usuarios (encargado de crear el token) genera el token con una vida util de 60 segundos. En el ms-bff cuando obtengo el token del header primero valido si el token es valido, si lo es lo almaceno en una cache. Cuando un usuario hace un request a un endpoint, verifico si el token del header esta dentro de la cache, si lo esta, verifico que el token no haya expirado. Si el token expiro, le indico por el body del response que vuelva a iniciar sesion. Si el token no expiro, proceso el request._