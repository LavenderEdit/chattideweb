import { openNotificationModal } from '../components/modal-notification.js';
import { showToast } from '../components/toast-notification.js?v=1';

/**
 * Maneja la respuesta estándar del servidor.
 * @param {{status: number, body: any}} result
 * @param {Object} [options]
 * @param {Function} [options.onClose] - Callback al cerrar la notificación/modal
 * @param {boolean} [options.useToast=false] - Si true, usa toast en vez de modal
 */
export function handleResponse( { status, body }, options = {}) {
    const {onClose = () => {
        }, useToast = false} = options;

    const notify = useToast
            ? ({ title, message, type }) => showToast({title, message, type, onClose})
    : ({ title, message }) => openNotificationModal({title, message, onClose});

    if (status >= 200 && status < 300) {
        if (body.success && body.message) {
            notify({title: 'Éxito', message: body.message, type: 'success'});
        } else {
            notify({title: 'Error', message: body.message || 'Operación no exitosa.', type: 'warning'});
        }
    } else if (status >= 400 && status < 500) {
        notify({title: 'Atención', message: body.message || 'Solicitud inválida.', type: 'warning'});
    } else {
        notify({title: 'Error servidor', message: body.message || 'Intente más tarde.', type: 'danger'});
}
}
