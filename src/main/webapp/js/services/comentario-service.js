export async function createCommentary(formData) {
    const ctx = window.APP_CONTEXT_PATH || '';
    const res = await fetch(`${ctx}/SvComentario`, {
        method: 'POST',
        body: formData,
        headers: {'X-Requested-With': 'XMLHttpRequest'}
    });
    let body;
    try {
        body = await res.json();
    } catch {
        body = {success: false, message: res.statusText || 'Error inesperado'};
    }
    return {status: res.status, body};
}

export async function fetchComentariosHtml(publicacionId) {
    const ctx = window.APP_CONTEXT_PATH || '';
    const res = await fetch(`${ctx}/SvPublicacion?aj=1&id=${publicacionId}`, {
        headers: {'X-Requested-With': 'XMLHttpRequest'}
    });
    if (!res.ok) {
        throw new Error(`HTTP ${res.status}`);
    }
    return res.text();
}
