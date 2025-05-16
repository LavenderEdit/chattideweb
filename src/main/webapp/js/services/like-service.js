const ctx = window.APP_CONTEXT_PATH || '';

async function parseResponse(res) {
    let body;
    try {
        body = await res.json();
    } catch {
        body = {success: false, message: res.statusText || 'Error inesperado'};
    }
    return {status: res.status, body};
}

export async function createLike(publicacionId) {
    const res = await fetch(`${ctx}/SvLike?idPub=${publicacionId}`, {
        method: 'POST',
        headers: {'X-Requested-With': 'XMLHttpRequest'}
    });
    return parseResponse(res);
}

export async function deleteLike(publicacionId) {
    const res = await fetch(`${ctx}/SvLike?idPub=${publicacionId}`, {
        method: 'DELETE',
        headers: {'X-Requested-With': 'XMLHttpRequest'}
    });
    return parseResponse(res);
}

export async function fetchLikeCount(publicacionId) {
    const res = await fetch(`${ctx}/SvLikeCount?idPub=${publicacionId}`, {
        headers: {'X-Requested-With': 'XMLHttpRequest'}
    });
    return parseResponse(res);
}
