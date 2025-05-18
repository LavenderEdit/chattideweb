package com.chattide.web.Utilities;

/**
 * Clase que contiene todos los mensajes de feedback para el usuario.
 *
 * @author Juan - Luis
 */
public class Mensajes {

    // Validación de formularios
    public static final String CAMPOS_VACIOS = "¡Los campos están vacíos!";
    public static final String USUARIO_EMAIL_INVALIDO = "¡El email insertado no es válido!";

    // Autenticación
    public static final String USUARIO_NO_AUTENTICADO = "Ha ocurrido un problema con la sesión del usuario. ¡Vuelva a iniciar sesión!";
    public static final String USUARIO_LOGEADO = "¡Usuario autenticado! Redirigiendo al inicio... Bienvenido ";
    public static final String USUARIO_CONTRA_INCORRECTA = "¡El email o la contraseña son incorrectas!";
    public static final String USUARIO_INEXISTENTE = "¡El email del usuario no existe!";

    // Registro
    public static final String USUARIO_REGISTRADO = "¡Usuario registrado! Redirigiendo al inicio de sesión...";
    public static final String REGISTRO_EXITOSO = USUARIO_REGISTRADO;
    public static final String USUARIO_EMAIL_EXISTE = "¡El email ya está en uso!";
    public static final String EMAIL_DUPLICADO = USUARIO_EMAIL_EXISTE;
    public static final String ERROR_REGISTRO = "Error al registrar el usuario. Por favor, inténtelo de nuevo.";

    // Actualización de perfil
    public static final String USUARIO_DATOS_MODIFICADOS = "¡Los datos han sido modificados con éxito!";
    public static final String USUARIO_DATOS_DUPLICADOS = "No se han detectado cambios en los datos ingresados.";

    // Mensajes de creación/eliminación de grupo
    public static final String GRUPO_CREADO = "Grupo creado exitosamente";
    public static final String GRUPO_ELIMINADO = "Grupo eliminado correctamente";
    public static final String GRUPO_ACTUALIZADO = "Grupo actualizado con éxito";

    // Mensajes de membresía
    public static final String USUARIO_AGREGADO = "Usuario agregado al grupo";
    public static final String USUARIO_ELIMINADO = "Usuario eliminado del grupo";
    public static final String USUARIO_PROMOVIDO = "Usuario promovido a administrador";
    public static final String USUARIO_DEGRADADO = "Usuario degradado a miembro regular";
    public static final String SALIO_DEL_GRUPO = "Has abandonado el grupo";

    // Mensajes de error
    public static final String ERROR_CREAR_GRUPO = "Error al crear el grupo";
    public static final String ERROR_ELIMINAR_GRUPO = "Error al eliminar el grupo";
    public static final String ERROR_ACCESO_DENEGADO = "No tienes permisos para esta acción";
    public static final String ERROR_USUARIO_NO_ENCONTRADO = "Usuario no encontrado en el grupo";
    public static final String ERROR_GRUPO_NO_ENCONTRADO = "Grupo no encontrado";
    public static final String ERROR_GRUPO_LLENO = "El grupo ha alcanzado su límite de miembros";
    public static final String ERROR_YA_EN_GRUPO = "El usuario ya está en este grupo";

    // Mensajes de validación
    public static final String NOMBRE_INVALIDO = "El nombre del grupo no es válido";
    public static final String DESCRIPCION_INVALIDA = "La descripción del grupo no es válida";
    public static final String LIMITE_INVALIDO = "El límite de miembros no es válido";

    // Mensajes varios
    public static final String GRUPO_VACIO = "El grupo no tiene miembros";
    public static final String LISTA_MIEMBROS = "Lista de miembros obtenida";
    public static final String SIN_GRUPOS = "No perteneces a ningún grupo";
    public static final String INFO_GRUPO = "Información del grupo obtenida";

    // Mensajes de LIKE
    public static final String LIKE = "¡Diste un like!";
    public static final String NO_LIKE = "¡Like denegado!";
    public static final String LIKE_YA_PUESTO = "¡Ya diste tu like!";
    public static final String LIKE_QUITADO_YA = "¡Ya quitaste el like!";

    // Mensajes genéricos
    public static final String ERROR_SERVIDOR = "Ha ocurrido un error inesperado. Por favor, inténtelo más tarde.";
}
