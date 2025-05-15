import { createCommentary, fetchComentariosHtml } from '../services/comentario-service.js';
import { showToast } from '../components/toast-notification.js';

const ComentarioController = {
    init(publicacionId) {
        this.publicacionId = publicacionId;
        const form = document.getElementById('form-comentario');
        form.addEventListener('submit', e => this.onComentar(e));

        this.refreshComentarios();

        this._poller = setInterval(() => {
            this.refreshComentarios();
        }, 5000);

        window.addEventListener('beforeunload', () => clearInterval(this._poller));
    },

    async refreshComentarios() {
        try {
            const html = await fetchComentariosHtml(this.publicacionId);
            document.getElementById('comentarios-container').innerHTML = html;
        } catch (err) {
            console.error('Error refrescando comentarios: ', err);
        }
    },

    async onComentar(evt) {
        evt.preventDefault();
        const form = document.getElementById('form-comentario');
        const data = new FormData(form);

        try {
            const {status, body} = await createCommentary(data);
            if (status === 201 && body.success) {
                showToast({title: '¡Listo!', message: body.message, type: 'success'});
                form.reset();
                this.refreshComentarios();
            } else {
                showToast({title: 'Error', message: body.message || 'No se creó', type: 'danger'});
            }
        } catch (err) {
            showToast({title: 'Error red', message: err.message, type: 'danger'});
        }
    }
};

export default ComentarioController;