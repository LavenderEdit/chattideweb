# Chattide

Chattide es una aplicación web basada en Java (Jakarta EE/Servlets + JSP) que simula una red social básica. Permite a los usuarios registrarse, iniciar sesión, crear y unirse a grupos, publicar contenido, comentar y dar “me gusta” a publicaciones dentro de los grupos a los que pertenecen. El proyecto utiliza JPA (EclipseLink) para la persistencia en una base de datos MySQL, y Bootstrap para el diseño de la interfaz.

## Tabla de contenidos
1. [Descripción general](#descripción-general)  
2. [Características](#características)  
3. [Tecnologías y dependencias](#tecnologías-y-dependencias)  
4. [Estructura del proyecto](#estructura-del-proyecto)  
5. [Arquitectura y flujo de trabajo](#arquitectura-y-flujo-de-trabajo)  
   - [Modelos (Entidades JPA)](#modelos-entidades-jpa)  
   - [DTOs y Mappers](#dtos-y-mappers)  
   - [Controladores JPA (Persistence)](#controladores-jpa-persistence)  
   - [Servlets de negocio](#servlets-de-negocio)  
   - [Capas de presentación (JSP & Fragments)](#capas-de-presentación-jsp--fragments)  
   - [Configuración de JAX-RS (Recursos REST)](#configuración-de-jax-rs-recursos-rest)  
   - [Persistencia (persistence.xml)](#persistencia-persistencexml)  
6. [Instalación y despliegue](#instalación-y-despliegue)  
   - [Requisitos previos](#requisitos-previos)  
   - [Clonar y compilar](#clonar-y-compilar)  
   - [Configurar la base de datos](#configurar-la-base-de-datos)  
   - [Ejecutar en Apache Tomcat](#ejecutar-en-apache-tomcat)  
7. [Uso de la aplicación](#uso-de-la-aplicación)  
   - [Registro e inicio de sesión](#registro-e-inicio-de-sesión)  
   - [Gestión de grupos](#gestión-de-grupos)  
   - [Publicaciones, comentarios y “me gusta”](#publicaciones-comentarios-y-me-gusta)  
   - [Perfil de usuario](#perfil-de-usuario)  
8. [Licencia](#licencia)  

---

## Descripción general

Chattide es un prototipo de red social donde los usuarios pueden:
- **Registrarse** y **autenticarse** con correo y contraseña.
- **Crear** y **buscar grupos** temáticos.
- **Unirse** a grupos y **participar** en ellos.
- **Publicar** mensajes/textos dentro de un grupo.
- **Comentar** publicaciones de otros miembros del grupo.
- **Dar “me gusta”** a publicaciones y ver el recuento de “likes”.
- **Ver y editar** su propio perfil (avatar, nombre, correo).
- **Salir** de un grupo y ver la lista de los grupos en los que participa.

La interfaz está construida con JSPs y fragmentos (header.jsp, footer.jsp, fragmentos de tarjeta de publicacion y lista de comentarios). El estilo utiliza Bootstrap 5 y un CSS propio (`main-style.css`). El backend está desarrollado con Servlets y JPA para interactuar con MySQL. Se incluye un pequeño recurso REST de prueba (`JakartaEE10Resource`) para exponer un endpoint `GET /resources/jakartaee10` que responde “ping Jakarta EE”.

---

## Características

- **Autenticación y registro de usuarios**  
- **Gestión de grupos**: crear, buscar, unirse y salir  
- **Publicaciones de texto** en grupos  
- **Comentarios** a publicaciones  
- **“Me gusta”** a publicaciones  
- **Perfil de usuario** con avatar, nombre y correo  
- **Interfaz responsiva** usando Bootstrap  
- **Persistencia con JPA/EclipseLink** y base de datos MySQL  
- **Servlets organizados por funcionalidades** (Autenticación, Gestión de Grupos, Publicaciones/Comentarios, Perfil)  
- **Mapeo Entidad ⇄ DTO** con clases Mapper para no exponer directamente las entidades en capa de presentación  
- **Un recurso REST mínimo** (JakartaRestConfiguration + JakartaEE10Resource) para demostrar la configuración de Jakarta RESTful Web Services.  

---

## Tecnologías y dependencias

- **Lenguaje**: Java 11 (Jakarta EE 10)  
- **Servidor de aplicaciones**: Apache Tomcat 10.1.40 (compatible con Jakarta EE 10/Servlet 6.0)  
- **Maven**: sistema de gestión y construcción (pom.xml)  
- **Jakarta EE / Java EE**  
  - **Servlets** (mapeo en `web.xml`)  
  - **Jakarta Server Pages (JSP)**  
  - **Jakarta Taglibs** (`jakarta.tags.core`, `jakarta.tags.fmt`)  
  - **JAX-RS** (jerarquía `javax.ws.rs.*`)  
  - **JPA (EclipseLink)** para la persistencia de datos  
- **Base de datos**: MySQL (JDBC + EclipseLink)  
- **Front-end**:  
  - Bootstrap 5 (CSS y grid)  
  - CSS personalizado (`main-style.css`)  
  - Imágenes (iconos y logos)  
- **Herramientas auxiliares**:  
  - **nb-configuration.xml** (configuración de NetBeans)  
  - **.gitignore**  
  - **Apache License 2.0** (archivo LICENSE)  

---

## Estructura del proyecto

```text
ChattideWeb/                  ← Carpeta raíz del proyecto
├─ .gitignore
├─ LICENSE                   ← Licencia Apache 2.0
├─ nb-configuration.xml      ← Configuración de NetBeans
├─ pom.xml                   ← Definición de dependencias y plugins Maven
│
├─ src/
│   ├─ main/
│   │   ├─ java/
│   │   │   └─ com/
│   │   │      └─ chattide/
│   │   │         └─ web/
│   │   │            ├─ JakartaRestConfiguration.java       ← Habilita JAX-RS en /resources
│   │   │            ├─ resources/
│   │   │            │   └─ JakartaEE10Resource.java        ← Ejemplo de recurso REST
│   │   │            │
│   │   │            ├─ DTO/                                 ← Clases DTO (Data Transfer Object)
│   │   │            │   ├─ ComentarioDTO.java
│   │   │            │   ├─ GrupoDTO.java
│   │   │            │   ├─ PublicacionDTO.java
│   │   │            │   └─ UsuarioDTO.java
│   │   │            │
│   │   │            ├─ Mapper/                              ← Clases para mapear Entidad ⇄ DTO
│   │   │            │   ├─ ComentarioMapper.java
│   │   │            │   ├─ GrupoMapper.java
│   │   │            │   ├─ PublicacionMapper.java
│   │   │            │   └─ UsuarioMapper.java
│   │   │            │
│   │   │            ├─ Modelo/                              ← Entidades JPA
│   │   │            │   ├─ Comentario.java
│   │   │            │   ├─ Grupo.java
│   │   │            │   ├─ Likes.java
│   │   │            │   ├─ Publicacion.java
│   │   │            │   ├─ Usuario.java
│   │   │            │   └─ Usuario_Grupo.java
│   │   │            │
│   │   │            ├─ Persistence/                         ← Controladores JPA generados (EclipseLink)
│   │   │            │   ├─ AbstractJpaController.java
│   │   │            │   ├─ ComentarioJpaController.java
│   │   │            │   ├─ GrupoJpaController.java
│   │   │            │   ├─ LikesJpaController.java
│   │   │            │   ├─ PublicacionJpaController.java
│   │   │            │   ├─ UsuarioJpaController.java
│   │   │            │   └─ Usuario_GrupoJpaController.java
│   │   │            │
│   │   │            └─ Servlet/                             ← Lógica de negocio (servlets)
│   │   │               ├─ AuthUser/
│   │   │               │   ├─ SvLogin.java                  ← Servlet de inicio de sesión
│   │   │               │   └─ SvRegistro.java               ← Servlet de registro
│   │   │               │
│   │   │               ├─ GestGrup/                         
│   │   │               │   ├─ SvBuscarGrupos.java           ← Servlet para buscar grupos
│   │   │               │   ├─ SvCrearGrupo.java             ← Servlet para crear grupo
│   │   │               │   ├─ SvMisGrupos.java              ← Servlet que lista grupos del usuario
│   │   │               │   └─ SvSalirGrupo.java             ← Servlet para salir de un grupo
│   │   │               │
│   │   │               ├─ PubCom/                           
│   │   │               │   ├─ SvComentario.java             ← Servlet para agregar comentario
│   │   │               │   ├─ SvLike.java                   ← Servlet para dar “me gusta”
│   │   │               │   └─ SvPublicar.java                ← Servlet para crear nueva publicación
│   │   │               │
│   │   │               ├─ Perfil/
│   │   │               │   └─ SvMiCuenta.java               ← Servlet para gestionar perfil personal
│   │   │               │
│   │   │               └─ UsuariosGrupos/                   
│   │   │                   ├─ SvUnirseGrupo.java             ← Servlet para unirse a un grupo
│   │   │                   └─ SvPublicacion.java              ← Servlet para listar publicaciones del grupo
│   │   │
│   │   ├─ resources/
│   │   │   └─ META-INF/
│   │   │       └─ persistence.xml     ← Configuración de unidad de persistencia JPA
│   │   │
│   │   └─ webapp/
│   │       ├─ WEB-INF/
│   │       │   ├─ web.xml             ← Mapeo de servlets y sesiones
│   │       │   └─ (archivos compilados en target)
│   │       │
│   │       ├─ css/                     ← Archivos CSS (Bootstrap + main-style.css)
│   │       │   ├─ bootstrap/           ← CSS de Bootstrap 5
│   │       │   └─ main-style.css       ← Estilos personalizados
│   │       │
│   │       ├─ images/                  ← Imágenes y logos
│   │       │   └─ Logos/              
│   │       │       ├─ Logo-Chattide-FondoClaro.ico
│   │       │       └─ Logo-Chattide.webp
│   │       │
│   │       ├─ fragments/               ← JSP fragments reutilizables
│   │       │   ├─ comentario-list.jsp  ← Listado de comentarios de una publicación
│   │       │   └─ publicacion-card.jsp ← Tarjeta de presentación de una publicación
│   │       │
│   │       ├─ header.jsp               ← Encabezado común (navbar, meta tags)
│   │       ├─ footer.jsp               ← Pie de página común
│   │       ├─ index.jsp                ← Página de bienvenida / login
│   │       ├─ login.jsp                ← Formulario de inicio de sesión
│   │       ├─ registro.jsp             ← Formulario de registro de usuario
│   │       ├─ logout.jsp               ← Cierre de sesión
│   │       ├─ miCuenta.jsp             ← Página para ver/editar perfil
│   │       ├─ misGrupos.jsp            ← Listado de grupos del usuario
│   │       ├─ crearGrupo.jsp           ← Formulario para crear un nuevo grupo
│   │       ├─ buscarGrupos.jsp         ← Formulario/listado para buscar grupos
│   │       ├─ miGrupo.jsp              ← Detalle de un grupo en particular (publicaciones, miembros)
│   │       ├─ publicacion.jsp          ← Detalle de una publicación (comentarios, “me gusta”)
│   │       └─ perfil.jsp               ← Página de perfil de otro usuario
│   │
│   └─ test/ (vacío o no incluido)      ← Pruebas unitarias, si las hubiera
│
└─ target/                              ← Artefactos generados por Maven (clasess, WAR, etc.)
````

---

## Arquitectura y flujo de trabajo

A continuación se describe cómo está organizado el proyecto y cómo cada componente colabora para ofrecer la funcionalidad de la red social:

### Modelos (Entidades JPA)

* **`Usuario.java`**
  Representa la tabla `Usuario` en la base de datos. Campos principales: `usuarioID`, `nombre`, `email`, `password`, `avatar`, etc.
* **`Grupo.java`**
  Representa un grupo temático en la red social. Campos: `grupoID`, `nombre`, `descripcion`, fecha de creación, etc.
* **`Publicacion.java`**
  Corresponde a las publicaciones hechas por los usuarios dentro de un grupo. Campos: `publicacionID`, `contenido`, referencia a `Usuario`, referencia a `Grupo`, fecha, etc.
* **`Comentario.java`**
  Almacena los comentarios que un usuario hace sobre una publicación. Campos: `comentarioID`, `texto`, enlace a `Usuario` y a `Publicacion`.
* **`Likes.java`**
  Registra los “me gusta” que un usuario da a una publicación. Campos: `likeID`, `usuarioID`, `publicacionID`.
* **`Usuario_Grupo.java`**
  Entidad intermedia que modela la relación N\:M entre `Usuario` y `Grupo` (miembro de grupo). Campos: `usuarioID`, `grupoID` y fecha de unión.

Cada una de estas clases está anotada con `@Entity`, se le asigna un nombre de tabla y se definen sus relaciones (por ejemplo, `@ManyToOne`, `@OneToMany`, `@JoinColumn`, etc.) para reflejar las claves foráneas en MySQL.

### DTOs y Mappers

Para no exponer directamente las entidades JPA en la capa de presentación, se creó un paquete **DTO/** con clases inmutables o con getters y setters básicos. Por ejemplo:

* **`UsuarioDTO.java`**
  Contiene solo los campos que se mostrarán públicamente (ID, nombre, correo, avatar), sin exponer la contraseña ni las colecciones de relaciones.
* **`GrupoDTO.java`, `PublicacionDTO.java`, `ComentarioDTO.java`** de forma similar.

Los mapeos se realizan en clases de **Mapper/** (por ejemplo, `UsuarioMapper.toDTO(Usuario u)`), que reciben una entidad y devuelven el correspondiente DTO. Esto permite desacoplar la lógica de la base de datos de la lógica de presentación.

### Controladores JPA (Persistence)

En el paquete **Persistence/** hay varias clases generadas (por NetBeans o manualmente) que extienden de `AbstractJpaController`. Estas clases permiten operaciones CRUD sobre cada entidad:

* **`UsuarioJpaController.java`**
* **`GrupoJpaController.java`**
* **`PublicacionJpaController.java`**
* **`ComentarioJpaController.java`**
* **`LikesJpaController.java`**
* **`Usuario_GrupoJpaController.java`**

Cada controlador JPA crea instancias de `EntityManager` según la unidad de persistencia definida en `persistence.xml`. Permiten métodos como `create()`, `edit()`, `destroy()` y consultas básicas (`findXXX`, `findXXXEntities`, etc.). La capa de Servlets invoca a estos controladores para consultar, insertar o actualizar datos.

### Servlets de negocio

En **Servlet/** se agrupan las clases que extienden de `HttpServlet` y contienen la lógica de la aplicación. Se organizan en subpaquetes según la funcionalidad:

1. **AuthUser/**

   * **`SvRegistro.java`**: Procesa el formulario de registro (obtiene parámetros `nombre`, `email`, `password`), valida que no exista un usuario con ese correo, cifra la contraseña (si se implementó cifrado) y guarda la nueva entidad `Usuario` vía `UsuarioJpaController.create()`. Luego redirige a la página de login o a la lista de grupos.
   * **`SvLogin.java`**: Recibe credenciales, busca un usuario por `email` (p.ej., con `UsuarioJpaController.findUsuarioByEmail()`), compara contraseñas y, si es válido, almacena el objeto `UsuarioDTO` o el objeto `Usuario` en la sesión. Redirige a `SvMisGrupos` (lista de grupos, página principal después del login).

2. **GestGrup/**

   * **`SvCrearGrupo.java`**: Muestra o procesa el formulario de creación de grupo (`nombre`, `descripcion`). Al crear el grupo, guarda la entidad `Grupo`, y luego inserta una fila en `Usuario_Grupo` para que el creador sea el primer miembro.
   * **`SvBuscarGrupos.java`**: Permite hacer búsquedas en la tabla `Grupo` (por nombre o descripción). Retorna una lista de `GrupoDTO` a `buscarGrupos.jsp`.
   * **`SvMisGrupos.java`**: Consulta a `Usuario_GrupoJpaController` para obtener los grupos a los que pertenece el usuario autenticado. Presenta `misGrupos.jsp` con tarjetas de cada grupo.
   * **`SvSalirGrupo.java`**: Elimina la relación `Usuario_Grupo` para que el usuario deje de pertenecer al grupo.

3. **PubCom/**

   * **`SvPublicar.java`**: Procesa la publicación de contenido (texto) en un grupo. Recibe como parámetros `contenido`, `grupoID` y `usuarioID` (de la sesión). Crea la entidad `Publicacion` y la persiste. Redirige a la página de detalle del grupo (`miGrupo.jsp`).
   * **`SvComentario.java`**: Recoge parámetros `textoComentario`, `publicacionID` y `usuarioID`. Crea un `Comentario` y lo guarda por medio de `ComentarioJpaController`. Luego recarga la vista de la publicación (`publicacion.jsp`).
   * **`SvLike.java`**: Cuando un usuario hace “me gusta”, la petición AJAX o el formulario envía `publicacionID` y `usuarioID`. El servlet crea la entidad `Likes` (o elimina si ya existe) para actualizar el conteo. Redirige o responde con redirección a la misma página.

4. **Perfil/**

   * **`SvMiCuenta.java`**: Muestra y procesa cambios en el perfil de usuario. Permite actualizar nombre, avatar (subida de imagen) o contraseña. Invoca a `UsuarioJpaController.edit()`.

5. **UsuariosGrupos/**

   * **`SvUnirseGrupo.java`**: Cuando un usuario decide unirse a un grupo, crea un registro `Usuario_Grupo` en la tabla intermedia.
   * **`SvPublicacion.java`**: (Debe llamarse algo como `SvDetalleGrupo` o similar) Consulta todas las publicaciones del grupo y las pasa a `miGrupo.jsp` para mostrarlas con fragmentos de tarjeta.

Cada servlet está mapeado en **`web.xml`** con un nombre (`<servlet-name>`) y la ruta de acceso (`<url-pattern>/SvNombreServlet</url-pattern>`). Al recibir una petición, el servlet realiza validaciones (p.ej., verifica que haya sesión iniciada), recupera o modifica datos y finalmente hace `forward` a una JSP con atributos en el `request` (p.ej. `request.setAttribute("listaGrupos", listaDTO)`) o hace `response.sendRedirect()`.

### Capas de presentación (JSP & Fragments)

* **`header.jsp`** y **`footer.jsp`**
  Componentes comunes insertados en cada página. Incluyen la barra de navegación, enlaces a Bootstrap, meta tags, scripts de JavaScript y pie de página.
* **`index.jsp`**
  Página pública de bienvenida. Si hay sesión iniciada (atributo `"usuario"` en sesión), redirige a `/SvMisGrupos`; de lo contrario, muestra enlace a formulario de login o registro.
* **`login.jsp`** y **`registro.jsp`**
  Formularios para autenticación y creación de usuario.
* **`misGrupos.jsp`**
  Lista todos los grupos del usuario autenticado con tarjetas Bootstrap. Cada tarjeta incluye el nombre del grupo, descripción breve y enlace a “Entrar” al grupo.
* **`crearGrupo.jsp`** y **`buscarGrupos.jsp`**
  Formularios para crear un nuevo grupo o buscar grupos existentes.
* **`miGrupo.jsp`**
  Dentro del grupo, muestra:

  * Nombre y descripción del grupo.
  * Formulario para crear nueva publicación (textarea + botón).
  * Listado de publicaciones (cada una representada por el fragmento `publicacion-card.jsp`).
* **`publicacion-card.jsp`** (fragment)
  Tarjeta individual que muestra el autor, contenido, fecha y botones para “Me gusta” y “Comentar”. Contiene lógica para cargar el número de “likes” y enlazar a `publicacion.jsp` para ver comentarios.
* **`publicacion.jsp`**
  Vista detallada de una sola publicación. Muestra:

  * Contenido completo de la publicación.
  * Lista de comentarios (fragmento `comentario-list.jsp`).
  * Formulario para agregar un nuevo comentario.
  * Botón/juego para dar o quitar “Me gusta”.
* **`comentario-list.jsp`** (fragment)
  Recorre una lista de `ComentarioDTO` y la renderiza con avatar del autor, texto y fecha.
* **`perfil.jsp`** y **`miCuenta.jsp`**
  Muestran información del perfil de un usuario: avatar, nombre, correo. Permiten editar datos y/o cambiar imagen de avatar.
* **`logout.jsp`**
  Invalida la sesión y redirige a `index.jsp`.

Todas las JSP emplean expresiones JSP (`<%= %>`) y JSTL (`<c:if>`, `<c:forEach>`) para iterar colecciones y mostrar condicionales según rol o estado de la sesión.

### Configuración de JAX-RS (Recursos REST)

Aunque la funcionalidad principal se basa en Servlets y JSP, se incluyó una configuración mínima de JAX-RS:

* **`JakartaRestConfiguration.java`**
  Sobrescribe `javax.ws.rs.core.Application` y define `@ApplicationPath("resources")`. Con esto, cualquier recurso anotado con `@Path` se expone bajo la ruta `/resources/*`.

* **`JakartaEE10Resource.java`** (en `com.chattide.web.resources`)
  Ejemplo básico que responde a `GET /ChattideWeb/resources/jakartaee10` con un texto “ping Jakarta EE”. Sirve como comprobación de que JAX-RS está funcionando.

Este módulo REST está apartado del flujo de la red social; podría expandirse para convertir la aplicación en una API RESTful completa en el futuro.

### Persistencia (`persistence.xml`)

En `src/main/resources/META-INF/persistence.xml` se define la unidad de persistencia:

```xml
<persistence-unit name="chattide" transaction-type="RESOURCE_LOCAL">
    <provider>org.eclipse.persistence.jpa.PersistenceProvider</provider>
    <class>com.chattide.web.Modelo.Comentario</class>
    <class>com.chattide.web.Modelo.Grupo</class>
    <class>com.chattide.web.Modelo.Likes</class>
    <class>com.chattide.web.Modelo.Publicacion</class>
    <class>com.chattide.web.Modelo.Usuario</class>
    <class>com.chattide.web.Modelo.Usuario_Grupo</class>
    <properties>
        <property name="jakarta.persistence.jdbc.url"
                  value="jdbc:mysql://localhost:3306/bdchattide?useSSL=false&amp;serverTimezone=America/Lima"/>
        <property name="jakarta.persistence.jdbc.user" value="tu_usuario"/>
        <property name="jakarta.persistence.jdbc.password" value="tu_contraseña"/>
        <property name="jakarta.persistence.jdbc.driver" value="com.mysql.cj.jdbc.Driver"/>
    </properties>
</persistence-unit>
```

* **URL de conexión**: `jdbc:mysql://localhost:3306/bdchattide` (asegúrate de crear la base de datos `bdchattide` en MySQL).
* **Usuario/contraseña**: `tu_usuario` / `tu_contraseña`.
* **Proveedor JPA**: EclipseLink.
* **Clases registradas**: todas las entidades JPA del paquete `com.chattide.web.Modelo`.

---

## Instalación y despliegue

### Requisitos previos

1. **Java 11 (JDK 11)** instalado y configurado en la variable `JAVA_HOME`.
2. **Maven** (versión 3.6 o superior).
3. **MySQL Server** instalado (versión 8.x recomendada).
4. **Apache Tomcat 10** (compatible con Jakarta EE 10; configurar el “Context” si es necesario).

### Clonar y compilar

```bash
# Clonar el repositorio
git clone https://github.com/<usuario>/ChattideWeb.git
cd ChattideWeb

# Compilar y generar el WAR
mvn clean package
```

Al ejecutar `mvn package`, Maven descargará dependencias (jakarta-servlet, jakarta-jpa, eclipselink, etc.) y generará un archivo `ChattideWeb-1.0.war` dentro de `target/`.

### Configurar la base de datos

1. Iniciar sesión en MySQL y crear la base de datos:

   ```sql
   CREATE DATABASE bdchattide CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```
2. Verificar que exista el usuario `tu_usuario` con contraseña `tu_contraseña` (o modificar `persistence.xml` con tus propias credenciales). Ejemplo para crear usuario:

   ```sql
   CREATE USER 'tu_usuario'@'localhost' IDENTIFIED BY 'tu_contraseña';
   GRANT ALL PRIVILEGES ON bdchattide.* TO 'tu_usuario'@'localhost';
   FLUSH PRIVILEGES;
   ```
3. Dejar MySQL corriendo en `localhost:3306` (puerto por defecto) y comprobar que el URL en `persistence.xml` coincide con tu configuración (`useSSL=false` y zona horaria `America/Lima`).

Cuando la aplicación inicie, el proveedor JPA (EclipseLink) creará automáticamente las tablas según las entidades. Si prefieres hacerlo manualmente, puedes exportar el script DDL generado o habilitar la propiedad `<property name="jakarta.persistence.schema-generation.database.action" value="create"/>` para que las genere al desplegar.

### Ejecutar en Apache Tomcat

1. Copiar el archivo WAR (`target/ChattideWeb-1.0.war`) a la carpeta `webapps/` de tu instalación de Tomcat.
2. Iniciar Tomcat:

   ```bash
   cd /ruta/a/tomcat/bin
   ./startup.sh
   ```
3. Abrir en el navegador:

   ```
   http://localhost:8080/ChattideWeb-1.0/
   ```

   Deberías ver la página de bienvenida (`index.jsp`). Si la aplicación se desplegó bajo otro contexto, ajusta la URL.

---

## Uso de la aplicación

### Registro e inicio de sesión

1. Desde la página principal (`index.jsp`), haz clic en “Registrarse” o “Iniciar sesión”.
2. En **Registro** (`registro.jsp`), ingresa tu nombre completo, correo electrónico y contraseña. Tras enviar, el servlet `SvRegistro` guardará el nuevo usuario y redirigirá a la página de login.
3. En **Login** (`login.jsp`), ingresa tu correo y contraseña. El servlet `SvLogin` validará tus credenciales y, si son correctas, guardará el objeto `UsuarioDTO` en la sesión con la clave `"usuario"`. Luego redirigirá a `SvMisGrupos`.

### Gestión de grupos

* En **Mis Grupos** (`misGrupos.jsp`) — servlet `SvMisGrupos` — verás todos los grupos a los que perteneces, cada uno con un enlace para entrar (`miGrupo.jsp`).
* Para **crear un nuevo grupo**, haz clic en “Crear grupo” (`crearGrupo.jsp`), ingresa nombre y descripción. El servlet `SvCrearGrupo` almacenará un nuevo registro en `Grupo` y automáticamente añadirá la relación en `Usuario_Grupo` para que tú seas miembro.
* Para **buscar grupos**, ve a `buscarGrupos.jsp`. Ingresa un texto de búsqueda; el servlet `SvBuscarGrupos` consultará los grupos cuyo nombre o descripción contenga ese texto. Aparecerán resultados con botón “Unirse” que invoca a `SvUnirseGrupo`.
* Para **salir de un grupo**, dentro de la lista de mis grupos (o la vista del grupo), haz clic en “Salir de grupo”; el servlet `SvSalirGrupo` eliminará la relación de la tabla intermedia.

### Publicaciones, comentarios y “me gusta”

* Una vez dentro de un grupo (`miGrupo.jsp`):

  1. **Publicar contenido**: en la parte superior hay un formulario (textarea + botón). Envía la publicación al servlet `SvPublicar`.
  2. **Ver publicaciones existentes**: cada publicación se muestra con el fragmento `publicacion-card.jsp`, que incluye autor, texto, fecha, contador de “me gusta” y enlace para ver detalles o comentar.
  3. Para **dar “me gusta”** a una publicación, haz clic en el ícono de “like”; el servlet `SvLike` registra o elimina un registro en `Likes`.
  4. Para **ver detalles de una publicación** y sus comentarios, haz clic en la tarjeta; se abre `publicacion.jsp`. Ahí verás la publicación completa y, debajo, el listado de comentarios en `comentario-list.jsp`.
  5. Para **comentar**, utiliza el formulario de comentario en `publicacion.jsp`; el servlet `SvComentario` lo guardará y recargará la misma vista.

### Perfil de usuario

* En cualquier página, la barra de navegación (header) muestra tu nombre/ícono de perfil. Al hacer clic, accedes a `miCuenta.jsp`.
* En **Mi Cuenta** (`miCuenta.jsp`), el servlet `SvMiCuenta` carga tu información (usuario, email, avatar). Puedes:

  * Cambiar tu **avatar**: subir un archivo de imagen (Guarda en servidor o carpeta designada).
  * Modificar tu **nombre** o **correo** (validando que no exista otro usuario con el mismo email).
  * Cambiar tu **contraseña** (previo ingreso de la contraseña actual).
* Para **cerrar sesión**, haz clic en “Logout” (`logout.jsp`), que invalida la sesión y redirige a `index.jsp`.

---

## Licencia

Este proyecto está licenciado bajo la **Apache License, Version 2.0**.
Consulta el archivo [LICENSE](LICENSE) para más detalles sobre derechos de uso y distribución.
(Copyright © 2025 Juan S. Pimentel Lalangui / Luis M. Vallejos Evangelista)

---

**¡Gracias por usar Chattide!**
Si encuentras bugs o quieres aportar mejoras, siéntete libre de abrir issues o pull requests en el repositorio de GitHub.
