import { openConfirmModal } from '../components/modal-confirm.js';
import { openNotificationModal } from '../components/modal-notification.js';
import { createGrupo, exitGrupo } from '../services/grupo-service.js';

export function initExitGroupButtons() {
    const buttons = document.querySelectorAll('.exit-group-btn');

    buttons.forEach(btn => {
        btn.addEventListener('click', () => {
            const grupoId = btn.dataset.groupId;
            openConfirmModal({
                title: 'Confirmar salida',
                message: '¿Seguro que quieres salir de este grupo?',
                confirmText: 'Sí, salir',
                cancelText: 'Cancelar',
                onConfirm: async () => {
                    try {
                        const {status, body} = await exitGrupo(grupoId);
                        if (status === 200) {
                            openNotificationModal({
                                message: 'Has salido del grupo.',
                                onClose: () => {
                                    window.location.href = `${window.APP_CONTEXT_PATH || ''}/SvMisGrupos`;
                                }
                            });
                        } else {
                            openNotificationModal({
                                title: 'Error',
                                message: body.message || 'No se pudo salir del grupo.'
                            });
                        }
                    } catch (err) {
                        console.error(err);
                        openNotificationModal({
                            title: 'Error',
                            message: 'Error de red.'
                        });
                    }
                }
            });
        });
    });
}

export function initCreateGroupForm() {
    const form = document.getElementById('crearGrupoForm');
    if (form) {
        form.addEventListener('submit', async (event) => {
            event.preventDefault();
            const formData = new FormData(form);
            try {
                const {status, body} = await createGrupo(formData);
                if (status === 200 && body.success) {
                    openNotificationModal({
                        message: 'Grupo creado exitosamente.',
                        onClose: () => {
                            window.location.href = `${window.APP_CONTEXT_PATH || ''}/SvMisGrupos`;
                        }
                    });
                } else {
                    openNotificationModal({
                        title: 'Error',
                        message: body.message || 'No se pudo crear el grupo.'
                    });
                }
            } catch (err) {
                console.error(err);
                openNotificationModal({
                    title: 'Error',
                    message: 'Error de red.'
                });
            }
        });
    }
}

export function init() {
    initExitGroupButtons();
    initCreateGroupForm();
}