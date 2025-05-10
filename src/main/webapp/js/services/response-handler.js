import { openNotificationModal } from '../components/modal-notification.js';

export function handleResponse( { status, body }) {
    if (status >= 200 && status < 300) {
        if (body.success && body.message) {
            openNotificationModal({title: 'Éxito', message: body.message});
        } else {
            openNotificationModal({title: 'Error', message: body.message || 'Operación no exitosa.'});
        }
    } else if (status >= 400 && status < 500) {
        openNotificationModal({title: 'Atención', message: body.message || 'Solicitud inválida.'});
    } else /* 5XX */ {
        openNotificationModal({title: 'Error servidor', message: body.message || 'Intente más tarde.'});
}
}
