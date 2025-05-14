// components/toast-notification.js

/**
 * Muestra una notificación tipo "toast" en la esquina superior derecha.
 * @param {Object} options
 * @param {string} options.title    - Título de la notificación
 * @param {string} options.message  - Mensaje a mostrar
 * @param {'success'|'info'|'warning'|'danger'} [options.type='info']
 * @param {number} [options.duration=3000] - Duración en ms antes de desaparecer
 */
export function showToast(options = {}) {
    const {
        title = '',
        message = '',
        type = 'info',
        duration = 3000,
        onClose = () => {
        }
    } = options;

    // Asegurar contenedor
    let container = document.getElementById('toast-container');
    if (!container) {
        container = document.createElement('div');
        container.id = 'toast-container';
        Object.assign(container.style, {
            position: 'fixed',
            top: '1rem',
            right: '1rem',
            zIndex: 1080
        });
        document.body.appendChild(container);
    }

    // Crear toast
    const toast = document.createElement('div');
    toast.className = `toast align-items-center text-bg-${type} border-0 show`;
    toast.setAttribute('role', 'alert');
    toast.setAttribute('aria-live', 'assertive');
    toast.setAttribute('aria-atomic', 'true');
    Object.assign(toast.style, {
        minWidth: '250px',
        marginBottom: '0.5rem'
    });

    toast.innerHTML = `
    <div class="d-flex">
      <div class="toast-body">
        <strong>${title}</strong><br>${message}
      </div>
      <button type="button" class="btn-close btn-close-white me-2 m-auto" aria-label="Close"></button>
    </div>
  `;

    // Cierre manual
    const closeBtn = toast.querySelector('.btn-close');
    closeBtn.addEventListener('click', () => {
        container.removeChild(toast);
        onClose();
    });

    // Añadir al contenedor
    container.appendChild(toast);

    // Auto-dismiss
    setTimeout(() => {
        if (container.contains(toast)) {
            container.removeChild(toast);
            onClose();
        }
    }, duration);
}
