import { togglePasswordVisibility, checkArchiveSizeAndType } from './lib/utils/script-functions.js?v=1';
import UsuarioController from './controllers/usuario-controller.js'

export function runComponentRegistry() {
    const path = window.location.pathname;
    const pageName = path.substring(path.lastIndexOf('/') + 1);

    switch (pageName) {
        case 'registro.jsp':
            // Ejecuta funciones específicas para la página de registro
            togglePasswordVisibility();
            checkArchiveSizeAndType();
            break;
        case 'login.jsp':
            // Ejecuta funciones específicas para la página de login
            UsuarioController.init();
            togglePasswordVisibility();
            break;
        case 'miGrupo.jsp':
            // Agrega funciones para miGrupo.jsp si es necesario
            break;
        case 'misGrupos.jsp':
            // Agrega funciones para misGrupos.jsp si es necesario
            break;
        case 'miCuenta.jsp':
            // Agrega funciones para miCuenta.jsp si es necesario
            break;
        case 'publicacion.jsp':
            // Agrega funciones para publicacion.jsp si es necesario
            break;
        case 'crearGrupo.jsp':
            // Agrega funciones para crearGrupo.jsp si es necesario
            break;
        default:
            // Opcional: alguna función por defecto
            break;
    }
}

