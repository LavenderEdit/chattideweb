export async function createGrupo(formData) {
    const ctx = window.APP_CONTEXT_PATH || '';
    const res = await fetch(`${ctx}/SvCrearGrupo`, {
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

export async function exitGrupo(grupoId) {
    const ctx = window.APP_CONTEXT_PATH || '';
    const res = await fetch(`${ctx}/SvSalirGrupo?id=${grupoId}`, {
        method: 'DELETE',
        headers: {'X-Requested-With': 'XMLHttpRequest'}
    });
    let body;
    try {
        body = await res.json();
    } catch {
        body = {success: false, message: res.statusText || 'Error al salir del grupo'};
    }
    return {status: res.status, body};
}