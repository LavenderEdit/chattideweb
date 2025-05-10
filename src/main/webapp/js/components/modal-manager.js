/**
 * Crea, inyecta y muestra un modal basado en el HTML pasado.
 *
 * @param {string} modalHtml El string HTML del modal.
 * @param {object} options Opciones para el modal (opcional, se pasan a bootstrap.Modal).
 * @returns {bootstrap.Modal} La instancia del modal.
 */
export function openModal(modalHtml, options = {}) {
    const container = document.createElement("div");
    container.innerHTML = modalHtml;
    const modalElement = container.firstElementChild;
    document.body.appendChild(modalElement);

    const modalInstance = new bootstrap.Modal(modalElement, options);

    modalElement.addEventListener("hidden.bs.modal", () => {
        modalElement.remove();
    });

    modalInstance.show();
    return modalInstance;
}
