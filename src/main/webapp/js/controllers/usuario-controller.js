import { login }           from '../services/auth-service.js';
import { updateProfile }   from '../services/profile-service.js?v=1';
import { handleResponse }  from '../services/response-handler.js?v=3';
import { openNotificationModal } from '../components/modal-notification.js';
import { showToast }            from '../components/toast-notification.js?v=1';

const UsuarioController = {
    init() {
        // Login
        const formLog = document.getElementById('form-log');
        const btnLog = document.getElementById('btn-log');
        const ctx = window.APP_CONTEXT_PATH || '';
        if (btnLog && formLog) {
            btnLog.addEventListener('click', evt => this.onLogin(evt, formLog, ctx));
        }

        // Perfil (update)
        const formUser = document.getElementById('form-user');
        if (formUser) {
            formUser.addEventListener('submit', evt => this.onUpdate(evt));
        }
    },

    // -----------------
    // LOGIN (modal)
    // -----------------
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
                // useToast: false por defecto, usa modal
            });
        } catch (err) {
            openNotificationModal({title: 'Error', message: err.message});
        }
    },

    // -----------------
    // UPDATE PROFILE (toast)
    // -----------------
    async onUpdate(evt) {
        evt.preventDefault();
        const form = document.getElementById('form-user');
        const data = new FormData(form);

        try {
            const res = await updateProfile(data);

            handleResponse(res, {
                useToast: true,
                onClose: () => window.location.reload()
            });
        } catch (err) {
            console.log("Error => ", err.message);
            showToast({title: 'Error red', message: err.message, type: 'danger'});
        }
    }
};

export default UsuarioController;
