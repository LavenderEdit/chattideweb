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

    // Mensajes genéricos
    public static final String ERROR_SERVIDOR = "Ha ocurrido un error inesperado. Por favor, inténtelo más tarde.";
}
