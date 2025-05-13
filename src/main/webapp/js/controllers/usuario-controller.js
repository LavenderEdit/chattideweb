import { login } from '../services/auth-service.js';
import { handleResponse } from '../services/response-handler.js?v=1';
import { openNotificationModal } from "../components/modal-notification.js";

const UsuarioController = {
    init() {
        const form = document.getElementById('form-log');
        const btn = document.getElementById('btn-log');
        const ctx = window.APP_CONTEXT_PATH || '';
        btn.addEventListener('click', evt => this.onLogin(evt, form, ctx));
    },

    async onLogin(evt, form, ctx) {
        evt.preventDefault();
        const formData = new FormData(form);

        try {
            const result = await login(formData);
            handleResponse(result, {
                onClose: () => {
                    if (result.status >= 200 && result.status < 300) {
                        window.location.href = `${ctx}/SvMisGrupos`;
                    }
                }
            });
        } catch (err) {
            openNotificationModal({title: 'Error', message: err.message});
        }
    }
};

export default UsuarioController;
