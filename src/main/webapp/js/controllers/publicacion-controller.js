import { createPublication, fetchPublicacionesHtml } from '../services/publicacion-service.js';
import { showToast } from '../components/toast-notification.js';

const PublicacionController = {
    init(grupoId) {
        this.grupoId = grupoId;
        const form = document.getElementById('form-publicar');
        form.addEventListener('submit', e => this.onPublicar(e));

        this.refreshPublicaciones();

        this._poller = setInterval(() => {
            this.refreshPublicaciones();
        }, 5000);

        window.addEventListener('beforeunload', () => clearInterval(this._poller));
    },

    async refreshPublicaciones() {
        try {
            const html = await fetchPublicacionesHtml(this.grupoId);
            document.getElementById('publicaciones-container').innerHTML = html;
        } catch (err) {
            console.error('Error refrescando publicaciones:', err);
        }
    },

    async onPublicar(evt) {
        evt.preventDefault();
        const form = document.getElementById('form-publicar');
        const data = new FormData(form);

        try {
            const {status, body} = await createPublication(data);
            if (status === 200 && body.success) {
                showToast({title: '¡Listo!', message: body.message, type: 'success'});
                form.reset();
                this.refreshPublicaciones();
            } else {
                showToast({title: 'Error', message: body.message || 'No se creó', type: 'danger'});
            }
        } catch (err) {
            showToast({title: 'Error red', message: err.message, type: 'danger'});
        }
    }
};

export default PublicacionController;
