import { login } from '../services/auth-service.js';
import { handleResponse } from '../services/response-handler.js';
import { openNotificationModal } from "../components/modal-notification.js";

const UsuarioController = {
    init() {
        const form = document.getElementById('form-log');
        const btn = document.getElementById('btn-log');
        btn.addEventListener('click', evt => this.onLogin(evt, form));
    },

    async onLogin(evt, form) {
        evt.preventDefault();
        const formData = new FormData(form);

        try {
            const result = await login(formData);
            handleResponse(result);

            if (result.status >= 200 && result.status < 300) {
                setTimeout(() => window.location.href = 'misGrupos.jsp', 1500);
            }
        } catch (err) {
            // en caso de fallo de red
            openNotificationModal({title: 'Error', message: err.message});
        }
    }
};

export default UsuarioController;
