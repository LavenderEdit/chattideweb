import { togglePasswordVisibility, checkArchiveSizeAndType } from './lib/utils/script-functions.js?v=1';
import UsuarioController from './controllers/usuario-controller.js?v=1';
import { initExitGroupButtons } from './controllers/grupo-controller.js';
import PublicacionController from './controllers/publicacion-controller.js';
import ComentarioController from './controllers/comentario-controller.js?v=1';


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
        case 'SvMiGrupo':
            initExitGroupButtons();
            const gid = document.body.dataset.grupoId;
            PublicacionController.init(gid);
        case 'misGrupos.jsp':
        case 'SvMisGrupos':
            initExitGroupButtons();
            // Agrega funciones para misGrupos.jsp si es necesario
            break;
        case 'miCuenta.jsp':
        case 'SvMiCuenta':
            checkArchiveSizeAndType();
            UsuarioController.init();
            // Agrega funciones para miCuenta.jsp si es necesario
            break;
        case 'publicacion.jsp':
        case 'SvPublicacion':
            const pid = document.body.dataset.grupoId;
            ComentarioController.init(pid);
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

