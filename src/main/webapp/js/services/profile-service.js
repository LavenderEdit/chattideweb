export async function updateProfile(formData) {
    const ctx = window.APP_CONTEXT_PATH || '';
    try {
        const res = await fetch(`${ctx}/SvMiCuenta`, {
            method: 'PUT',
            body: formData
        });
        const body = await res.json();
        return {status: res.status, body};
    } catch (err) {
        throw new Error(`Network error: ${err.message}`);
    }
}
