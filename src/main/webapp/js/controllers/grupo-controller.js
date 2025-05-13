import { openConfirmModal } from '../components/modal-confirm.js';
import { openNotificationModal } from '../components/modal-notification.js';

export function initExitGroupButtons() {
    const ctx = window.APP_CONTEXT_PATH || '';
    const buttons = document.querySelectorAll('.exit-group-btn');

    buttons.forEach(btn => {
        btn.addEventListener('click', () => {
            const grupoId = btn.dataset.groupId;
            openConfirmModal({
                title: 'Confirmar salida',
                message: '¿Seguro que quieres salir de este grupo?',
                confirmText: 'Sí, salir',
                cancelText: 'Cancelar',
                onConfirm: () => {
                    fetch(`${ctx}/SvSalirGrupo?id=${grupoId}`, {method: 'DELETE'})
                            .then(res => {
                                if (res.status === 204) {
                                    openNotificationModal({
                                        message: 'Has salido del grupo.',
                                        onClose: () => {
                                            window.location.reload();
                                        }
                                    });
                                } else {
                                    openNotificationModal({
                                        title: 'Error',
                                        message: 'No se pudo salir del grupo.'
                                    });
                                }
                            })
                            .catch(err => {
                                console.error(err);
                                openNotificationModal({
                                    title: 'Error',
                                    message: 'Error de red.'
                                });
                            });
                }
            });
        });
    });
}
