import { createLike, deleteLike, fetchLikeCount } from '../services/like-service.js';
import { showToast } from '../components/toast-notification.js';

export function initLikeButtons() {
    const buttons = document.querySelectorAll('.like-btn');

    buttons.forEach(btn => {
        btn.addEventListener('click', async () => {
            const pubId = btn.dataset.publicacionId;
            const likeId = btn.dataset.likeId;

            try {
                const result = likeId
                        ? await deleteLike(pubId)
                        : await createLike(pubId);

                const {status, body} = result;
                if (status >= 200 && status < 300 && body.success) {
                    if (body.data.newLikeId) {
                        btn.classList.remove('btn-outline-primary');
                        btn.classList.add('btn-primary', 'liked');
                        btn.dataset.likeId = body.data.newLikeId;
                    } else {
                        btn.classList.remove('btn-primary', 'liked');
                        btn.classList.add('btn-outline-primary');
                        delete btn.dataset.likeId;
                    }
                    btn.querySelector('.like-count').textContent = body.data.newCount;
                    showToast({title: '👍', message: body.message, type: 'success'});
                } else {
                    showToast({title: 'Error', message: body.message, type: 'danger'});
                }

            } catch (err) {
                showToast({title: 'Error red', message: err.message, type: 'danger'});
            }
        });
    });

    setInterval(() => {
        buttons.forEach(async btn => {
            const pubId = btn.dataset.publicacionId;
            try {
                const {status, body} = await fetchLikeCount(pubId);
                if (status === 200 && body.success) {
                    btn.querySelector('.like-count').textContent = body.data.count;
                }
            } catch (_) {
            }
        });
    }, 5000);
}
