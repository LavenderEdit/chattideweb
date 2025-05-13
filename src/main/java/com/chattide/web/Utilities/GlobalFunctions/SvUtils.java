package com.chattide.web.Utilities.GlobalFunctions;

import com.chattide.web.Modelo.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

/**
 *
 * @author Juan - Luis
 */
public class SvUtils {

    /* Metodos para los campos */
    // Método para campos vacios
    public static boolean isNullOrEmpty(String... strs) {
        if (strs == null) {
            return true;
        }
        for (String str : strs) {
            if (str == null || str.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    // Método para validación de correos electrónicos
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        return email != null && pat.matcher(email).matches();
    }

    /**
     * Convierte una List<T> en un ArrayList<T>. Si la lista pasada es null,
     * retorna una lista vacía.
     *
     * @param <T> Tipo de elementos
     * @param list Lista a convertir
     * @return Un ArrayList con los mismos elementos
     */
    public static <T> ArrayList<T> toArrayList(List<? extends T> list) {
        if (list == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(list);
    }

    /* Métodos para listas */
    //Método para encontrar un usuario por su nombre de usuario o email con tres parametros
    public static Optional<Usuario> findUsersByEmail(String email, ArrayList<Usuario> listaUsuarios) {
        return listaUsuarios.stream()
                .filter(user -> (email.equalsIgnoreCase(user.getEmail())))
                .findFirst();
    }

    /* Métodos para las clases */
    public static String guardarArchivo(HttpServletRequest request, Part filePart, String carpetaDestino) throws IOException {
        if (filePart == null || filePart.getSize() <= 0) {
            return "images/Usuario/DefaultUserAvatar.webp";
        }

        String uploadPath = request.getServletContext().getRealPath("") + File.separator + carpetaDestino;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String originalFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        String extension = "";
        int dotIndex = originalFileName.lastIndexOf('.');
        if (dotIndex >= 0) {
            extension = originalFileName.substring(dotIndex);
        }
        String baseName = originalFileName.substring(0, dotIndex >= 0 ? dotIndex : originalFileName.length());
        String finalFileName = baseName + "_" + System.currentTimeMillis() + extension;

        String fileSavePath = uploadDir.getAbsolutePath() + File.separator + finalFileName;
        filePart.write(fileSavePath);

        return carpetaDestino + "/" + finalFileName;
    }

    /* Respuestas JSON */
    // Respuesta JSON - General - Sin data
    public static void respondWithJson(HttpServletResponse response, int statusCode, boolean success, String message, Object data) throws IOException {
        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put("success", success);
        jsonResponse.put("message", message);
        jsonResponse.put("status", statusCode);
        if (data != null) {
            jsonResponse.put("data", data);
        }
        response.setStatus(statusCode);
        response.setContentType("application/json;charset=UTF-8");
        Gson gson = new Gson();
        String jsonResponseString = gson.toJson(jsonResponse);
        response.getWriter().write(jsonResponseString);
    }

    // Respuesta JSON - General - Con data
    public static void respondWithJsonList(HttpServletResponse respuesta, int codigoEstado, boolean checkSuccess, String mensaje, Object dataObjeto) throws IOException {
        Map<String, Object> responseInfo = new HashMap<>();

        responseInfo.put("success", checkSuccess);
        responseInfo.put("message", mensaje);
        responseInfo.put("data", dataObjeto instanceof Collection<?> ? dataObjeto : Collections.singletonList(dataObjeto));

        respuesta.setStatus(codigoEstado);
        respuesta.setContentType("application/json");
        respuesta.setCharacterEncoding("UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(responseInfo);

        respuesta.getWriter().write(jsonResponse);
        System.out.println(jsonResponse);
    }

    // Respuesta JSON - General - Con objeto específico
    public static void respondWithJsonObject(HttpServletResponse respuesta, int codigoEstado, boolean checkSuccess, String mensaje, Map<String, Object> additionalData, Object mainData) throws IOException {
        Map<String, Object> responseInfo = new HashMap<>();

        responseInfo.put("success", checkSuccess);
        responseInfo.put("message", mensaje);

        responseInfo.put("data", mainData);

        if (additionalData != null && !additionalData.isEmpty()) {
            responseInfo.putAll(additionalData);
        }

        respuesta.setStatus(codigoEstado);
        respuesta.setContentType("application/json");
        respuesta.setCharacterEncoding("UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(responseInfo);

        respuesta.getWriter().write(jsonResponse);
        System.out.println(jsonResponse);
    }

    // Respuesta JSON - Añadido - Data
    public static List<Map<String, Object>> anadirDataLista(List<?> listaOriginal, Map<String, Object> dataAdicional) {
        List<Map<String, Object>> listaEnriquecida = new ArrayList<>();

        listaOriginal.forEach(item -> {
            Map<String, Object> itemData = new HashMap<>();
            itemData.putAll(new ObjectMapper().convertValue(item, Map.class));
            itemData.putAll(dataAdicional);
            listaEnriquecida.add(itemData);
        });
        return listaEnriquecida;
    }

    // Respuesta JSON - Información - Éxito
    public static void respondWithSuccess(HttpServletResponse response, int statusCode, String message) throws IOException {
        respondWithJson(response, statusCode, true, message, null);
    }

    public static void respondWithSuccessDataObject(HttpServletResponse response, int statusCode, boolean checkSuccess, String mensaje, Map<String, Object> additionalData, Object mainData) throws IOException {
        respondWithJsonObject(response, statusCode, checkSuccess, mensaje, additionalData, mainData);
    }

    // Respuesta JSON - Información - Éxito con Datos
    public static void respondWithSuccessData(HttpServletResponse response, int statusCode, String message, Object data) throws IOException {
        respondWithJsonList(response, statusCode, true, message, data);
    }

    // Respuesta JSON - Información - Error
    public static void respondWithError(HttpServletResponse response, int statusCode, String errorMessage) throws IOException {
        respondWithJson(response, statusCode, false, errorMessage, null);
    }
}
