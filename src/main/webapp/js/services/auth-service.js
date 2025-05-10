export async function login(formData) {
    try {
        const res = await fetch('/ChattideWeb/SvLogin', {
            method: 'POST',
            body: new URLSearchParams(formData)
        });
        const body = await res.json();
        return {status: res.status, body};
    } catch (err) {
        throw new Error(`Network error: ${err.message}`);
    }
}
