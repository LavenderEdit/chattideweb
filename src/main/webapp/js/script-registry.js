import { togglePasswordVisibility, checkArchiveSizeAndType } from './lib/utils/script-functions.js?v=1';
import UsuarioController from './controllers/usuario-controller.js?v=1';
import { initExitGroupButtons }     from './controllers/grupo-controller.js';
import PublicacionController        from './controllers/publicacion-controller.js?v=1';
import ComentarioController         from './controllers/comentario-controller.js?v=2';
import { initLikeButtons }          from './controllers/like-controller.js';

function getPageName() {
    const withoutQuery = window.location.href.split('?')[0];
    return withoutQuery.substring(withoutQuery.lastIndexOf('/') + 1);
}

export function runComponentRegistry() {
    const pageName = getPageName();

    switch (pageName) {
        case 'registro.jsp':
            togglePasswordVisibility();
            checkArchiveSizeAndType();
            break;

        case 'login.jsp':
            UsuarioController.init();
            togglePasswordVisibility();
            break;

        case 'miGrupo.jsp':
        case 'SvMiGrupo':
            initExitGroupButtons();
            initLikeButtons();
            const gid = new URLSearchParams(window.location.search).get('id');
            if (gid)
                PublicacionController.init(gid);
            break;

        case 'misGrupos.jsp':
        case 'SvMisGrupos':
            initExitGroupButtons();
            break;

        case 'miCuenta.jsp':
        case 'SvMiCuenta':
            checkArchiveSizeAndType();
            UsuarioController.init();
            break;

        case 'publicacion.jsp':
        case 'SvPublicacion':
            const pid = new URLSearchParams(window.location.search).get('id');
            if (pid) {
                ComentarioController.init(pid);
                initLikeButtons();
            }
            break;
        default:
        // nada
    }
}
