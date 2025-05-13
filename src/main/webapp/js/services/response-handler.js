// services/response-handler.js
import { openNotificationModal } from '../components/modal-notification.js';

/**
 * Maneja la respuesta estándar del servidor y muestra un modal de notificación.
 * 
 * @param {Object} response - La respuesta del servidor.
 * @param {Object} [options] - Opcional. Permite manejar callbacks como onClose.
 */
export function handleResponse( { status, body }, options = {}) {
    const {onClose} = options;

    if (status >= 200 && status < 300) {
        if (body.success && body.message) {
            openNotificationModal({
                title: 'Éxito',
                message: body.message,
                onClose
            });
        } else {
            openNotificationModal({
                title: 'Error',
                message: body.message || 'Operación no exitosa.',
                onClose
            });
        }
    } else if (status >= 400 && status < 500) {
        openNotificationModal({
            title: 'Atención',
            message: body.message || 'Solicitud inválida.',
            onClose
        });
    } else {
        openNotificationModal({
            title: 'Error servidor',
            message: body.message || 'Intente más tarde.',
            onClose
        });
}
}
