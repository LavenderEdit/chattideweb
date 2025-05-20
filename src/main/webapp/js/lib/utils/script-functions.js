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


export function injectHeroBackground(imageUrl, { overlayOpacity = 0.5, fixed = true } = {}) {
    const body = document.body;
    body.style.minHeight = '100vh';
    body.style.margin = '0';

    body.style.backgroundImage = `url(${imageUrl})`;
    body.style.backgroundSize = 'cover';
    body.style.backgroundPosition = 'center center';
    body.style.backgroundRepeat = 'no-repeat';
    if (fixed)
        body.style.backgroundAttachment = 'fixed';

    let overlay = document.getElementById('hero-overlay');
    if (!overlay) {
        overlay = document.createElement('div');
        overlay.id = 'hero-overlay';
        Object.assign(overlay.style, {
            position: 'fixed',
            top: 0,
            left: 0,
            width: '100%',
            height: '100%',
            pointerEvents: 'none',
            zIndex: '-1'
        });
        document.body.appendChild(overlay);
    }
    overlay.style.backgroundColor = `rgba(0, 0, 0, ${overlayOpacity})`;
}

export function valideEmail() {
    const emailInput = document.getElementById("email");
    const result = document.getElementById("result");
    const icon = document.getElementById("icon");

    const regex = /^[\w.-]+@[a-zA-Z\d.-]+\.[a-zA-Z]{2,}$/;


    function validateEmailLive() {
        const email = emailInput.value.trim();

        if (email === "") {
            emailInput.classList.remove("valid", "invalid");
            icon.textContent = "";
            result.textContent = "";
            return;
        }

        if (regex.test(email)) {
            emailInput.classList.add("valid");
            emailInput.classList.remove("invalid");
            icon.textContent = "✅";
            icon.style.color = "#4caf50";
            result.textContent = "Correo válido";
            result.style.color = "lightgreen";
        } else {
            emailInput.classList.add("invalid");
            emailInput.classList.remove("valid");
            icon.textContent = "❌";
            icon.style.color = "tomato";
            result.textContent = "Correo inválido";
            result.style.color = "tomato";
        }
    }

    emailInput.addEventListener("input", validateEmailLive);
}