document.querySelectorAll('[data-theme]').forEach(btn => {
    btn.addEventListener('click', () => {
        const theme = btn.getAttribute('data-theme');
        document.body.classList.remove('theme-dark', 'theme-orange');
        if (theme === 'dark')
            document.body.classList.add('theme-dark');
        if (theme === 'orange')
            document.body.classList.add('theme-orange');
        localStorage.setItem('chattide-theme', theme);
    });
});