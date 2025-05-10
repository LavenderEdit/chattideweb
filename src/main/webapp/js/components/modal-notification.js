import { openModal } from "./modal-manager.js";

/**
 * Abre un modal de notificación para informar al usuario.
 * @param {object} options
 * @param {string} [options.title="Notificación"]
 * @param {string} [options.message=""]
 * @param {Function} [options.onClose=() => {}]
 */
export function openNotificationModal(options = {}) {
    const {
        title = "Notificación",
        message = "",
        onClose = () => { }
    } = options;

    const modalHtml = `
    <div class="modal fade" id="notificationModal" tabindex="-1" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">${title}</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
          </div>
          <div class="modal-body">
            <p>${message}</p>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-primary" data-bs-dismiss="modal">Aceptar</button>
          </div>
        </div>
      </div>
    </div>`;

    const modalInstance = openModal(modalHtml);
    const modalElement = document.getElementById("notificationModal");

    modalElement.addEventListener("hidden.bs.modal", () => {
        onClose();
    });

    return modalInstance;
}
