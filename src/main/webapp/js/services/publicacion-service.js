export async function createPublication(formData) {
    const ctx = window.APP_CONTEXT_PATH || '';
    const res = await fetch(`${ctx}/SvPublicar`, {
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


export async function fetchPublicacionesHtml(grupoId) {
    const ctx = window.APP_CONTEXT_PATH || '';
    const res = await fetch(`${ctx}/SvMiGrupo?aj=1&id=${grupoId}`);
    return res.text();
}
