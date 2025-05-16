// controllers/like-controller.js
import { createLike, deleteLike, fetchLikeCount } from '../services/like-service.js';
import { showToast } from '../components/toast-notification.js';

export function initLikeButtons(containerSelector = 'body') {
    const container = document.querySelector(containerSelector);
    if (!container)
        return;

    const abortMap = new Map();

    container.addEventListener('click', async e => {
        const btn = e.target.closest('.like-btn');
        if (!btn)
            return;
        e.preventDefault();
        if (btn.disabled)
            return;

        const pubId = btn.dataset.publicacionId;
        const likeId = btn.dataset.likeId;

        abortMap.get(pubId)?.abort();
        const ctrl = new AbortController();
        abortMap.set(pubId, ctrl);

        btn.disabled = true;
        btn.classList.add('loading');
        btn.setAttribute('aria-busy', 'true');

        try {
            const result = likeId
                    ? await deleteLike(pubId, {signal: ctrl.signal})
                    : await createLike(pubId, {signal: ctrl.signal});
            const {status, body} = result;

            if (status >= 200 && status < 300 && body.success) {
                if (body.data.newLikeId) {
                    btn.classList.replace('btn-outline-primary', 'btn-primary');
                    btn.classList.add('liked');
                    btn.dataset.likeId = body.data.newLikeId;
                } else {
                    btn.classList.replace('btn-primary', 'btn-outline-primary');
                    btn.classList.remove('liked');
                    delete btn.dataset.likeId;
                }
                btn.querySelector('.like-count').textContent = body.data.newCount;
                showToast({title: '👍', message: body.message, type: 'success'});
            } else {
                showToast({title: 'Error', message: body.message, type: 'danger'});
            }
        } catch (err) {
            if (err.name !== 'AbortError') {
                showToast({title: 'Error red', message: err.message, type: 'danger'});
            }
        } finally {
            abortMap.delete(pubId);
            btn.disabled = false;
            btn.classList.remove('loading');
            btn.removeAttribute('aria-busy');
        }
    });

    let pollIntervalId = null;
    function startPolling() {
        if (pollIntervalId)
            return;
        pollIntervalId = setInterval(async () => {
            document.querySelectorAll('.like-btn').forEach(async btn => {
                const pubId = btn.dataset.publicacionId;
                try {
                    const {status, body} = await fetchLikeCount(pubId);
                    if (status === 200 && body.success) {
                        btn.querySelector('.like-count').textContent = body.data.count;
                    }
                } catch {
                }
            });
        }, 30000);
    }
    function stopPolling() {
        clearInterval(pollIntervalId);
        pollIntervalId = null;
    }

    document.addEventListener('visibilitychange', () => {
        document.hidden ? stopPolling() : startPolling();
    });
    if (!document.hidden)
        startPolling();

    window.addEventListener('beforeunload', () => {
        stopPolling();
        abortMap.forEach(ctrl => ctrl.abort());
    });
}
