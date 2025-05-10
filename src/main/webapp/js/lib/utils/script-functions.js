import { openNotificationModal } from "../../components/modal-notification.js";
import { openConfirmModal      } from "../../components/modal-confirm.js";

export function togglePasswordVisibility() {
    const toggleButtons = document.querySelectorAll(".toggle-password");
    toggleButtons.forEach(button => {
        button.addEventListener("click", function () {
            const input = this.parentElement.querySelector(
                    "input[type='password'], input[type='text']"
                    );
            if (!input)
                return;
            const newType = input.type === "password" ? "text" : "password";
            input.type = newType;
            const iconClass = newType === "password" ? "fa-eye" : "fa-eye-slash";
            this.innerHTML = `<i class="fas ${iconClass}"></i>`;
        });
    });
}

export function checkArchiveSizeAndType() {
    const fileInputs = document.querySelectorAll('.check-file');

    fileInputs.forEach(input => {
        input.addEventListener('change', function () {
            const file = input.files[0];
            const allowedTypes = ['image/jpeg', 'image/png', 'image/webp'];
            const maxSize = 2 * 1024 * 1024; // 2 MB

            if (!file)
                return;

            if (!allowedTypes.includes(file.type)) {
                openNotificationModal({
                    title: 'Formato inválido',
                    message: 'Solo se permiten archivos JPG, PNG o WEBP.'
                });
                input.value = '';
                return;
            }

            if (file.size > maxSize) {
                openNotificationModal({
                    title: 'Tamaño excedido',
                    message: 'El archivo no debe superar los 2 MB.'
                });
                input.value = '';
                return;
            }

            // Ejemplo de confirmación, si quieres pedir “¿Subir este archivo?”
            openConfirmModal({
                title: 'Subir archivo',
                message: `¿Deseas subir “${file.name}” (${(file.size / 1024).toFixed(1)} KB)?`,
                confirmText: 'Sí, subir',
                cancelText: 'No, cancelar',
                onConfirm: () => openNotificationModal({message: 'Archivo listo para subir.'}),
                onCancel: () => input.value = ''
            });
        });
    });
}
