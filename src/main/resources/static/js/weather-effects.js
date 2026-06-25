(function () {
    const canvas = document.getElementById('weather-canvas');
    if (!canvas) return;

    const ctx = canvas.getContext('2d');
    const body = document.body;
    const condition = (body.dataset.weatherCondition || 'Clouds').toLowerCase();
    const icon = body.dataset.weatherIcon || '03d';
    const isNight = icon.endsWith('n');

    let width = 0;
    let height = 0;
    let particles = [];
    let clouds = [];
    let stars = [];
    let lightningTimer = 0;
    let flashAlpha = 0;
    let animationId = null;

    function resize() {
        width = canvas.width = window.innerWidth;
        height = canvas.height = window.innerHeight;
        initParticles();
    }

    function effectType() {
        if (condition.includes('thunder')) return 'storm';
        if (condition.includes('rain') || condition.includes('drizzle')) return 'rain';
        if (condition.includes('snow')) return 'snow';
        if (condition.includes('mist') || condition.includes('fog') || condition.includes('haze')) return 'fog';
        if (condition.includes('clear')) return isNight ? 'stars' : 'sun';
        return 'clouds';
    }

    function initParticles() {
        const type = effectType();
        particles = [];
        clouds = [];
        stars = [];

        if (type === 'rain' || type === 'storm') {
            const count = Math.floor(width * height / 9000);
            for (let i = 0; i < count; i++) {
                particles.push({
                    x: Math.random() * width,
                    y: Math.random() * height,
                    speed: 8 + Math.random() * 10,
                    length: 12 + Math.random() * 18,
                    opacity: 0.2 + Math.random() * 0.5
                });
            }
        }

        if (type === 'snow') {
            const count = Math.floor(width * height / 12000);
            for (let i = 0; i < count; i++) {
                particles.push({
                    x: Math.random() * width,
                    y: Math.random() * height,
                    radius: 1 + Math.random() * 3,
                    speed: 0.5 + Math.random() * 1.5,
                    drift: -0.5 + Math.random(),
                    opacity: 0.4 + Math.random() * 0.5
                });
            }
        }

        if (type === 'clouds' || type === 'fog') {
            const count = type === 'fog' ? 6 : 10;
            for (let i = 0; i < count; i++) {
                clouds.push({
                    x: Math.random() * width,
                    y: Math.random() * height * 0.55,
                    width: 120 + Math.random() * 220,
                    height: 40 + Math.random() * 70,
                    speed: 0.15 + Math.random() * 0.35,
                    opacity: type === 'fog' ? 0.08 + Math.random() * 0.12 : 0.12 + Math.random() * 0.2
                });
            }
        }

        if (type === 'stars') {
            const count = Math.floor(width * height / 8000);
            for (let i = 0; i < count; i++) {
                stars.push({
                    x: Math.random() * width,
                    y: Math.random() * height * 0.7,
                    radius: 0.5 + Math.random() * 1.5,
                    twinkle: Math.random() * Math.PI * 2,
                    speed: 0.02 + Math.random() * 0.04
                });
            }
        }
    }

    function drawSun() {
        const cx = width * 0.78;
        const cy = height * 0.18;
        const pulse = 1 + Math.sin(Date.now() / 1200) * 0.06;

        const glow = ctx.createRadialGradient(cx, cy, 0, cx, cy, 180 * pulse);
        glow.addColorStop(0, 'rgba(255, 220, 120, 0.45)');
        glow.addColorStop(0.4, 'rgba(255, 180, 60, 0.15)');
        glow.addColorStop(1, 'rgba(255, 180, 60, 0)');
        ctx.fillStyle = glow;
        ctx.beginPath();
        ctx.arc(cx, cy, 180 * pulse, 0, Math.PI * 2);
        ctx.fill();

        ctx.fillStyle = 'rgba(255, 230, 140, 0.9)';
        ctx.beginPath();
        ctx.arc(cx, cy, 42 * pulse, 0, Math.PI * 2);
        ctx.fill();

        for (let i = 0; i < 8; i++) {
            const angle = (Math.PI * 2 * i) / 8 + Date.now() / 4000;
            ctx.strokeStyle = 'rgba(255, 220, 100, 0.25)';
            ctx.lineWidth = 3;
            ctx.beginPath();
            ctx.moveTo(cx + Math.cos(angle) * 58, cy + Math.sin(angle) * 58);
            ctx.lineTo(cx + Math.cos(angle) * 95, cy + Math.sin(angle) * 95);
            ctx.stroke();
        }
    }

    function drawClouds() {
        clouds.forEach(cloud => {
            cloud.x += cloud.speed;
            if (cloud.x - cloud.width > width) cloud.x = -cloud.width;

            ctx.fillStyle = `rgba(255, 255, 255, ${cloud.opacity})`;
            ctx.beginPath();
            ctx.ellipse(cloud.x, cloud.y, cloud.width * 0.5, cloud.height * 0.5, 0, 0, Math.PI * 2);
            ctx.ellipse(cloud.x + cloud.width * 0.25, cloud.y - cloud.height * 0.2, cloud.width * 0.35, cloud.height * 0.4, 0, 0, Math.PI * 2);
            ctx.ellipse(cloud.x - cloud.width * 0.2, cloud.y + cloud.height * 0.1, cloud.width * 0.3, cloud.height * 0.35, 0, 0, Math.PI * 2);
            ctx.fill();
        });
    }

    function drawRain() {
        ctx.strokeStyle = 'rgba(174, 214, 255, 0.7)';
        ctx.lineWidth = 1.5;
        particles.forEach(drop => {
            ctx.globalAlpha = drop.opacity;
            ctx.beginPath();
            ctx.moveTo(drop.x, drop.y);
            ctx.lineTo(drop.x - 2, drop.y + drop.length);
            ctx.stroke();
            drop.y += drop.speed;
            drop.x -= 1;
            if (drop.y > height) {
                drop.y = -drop.length;
                drop.x = Math.random() * width;
            }
        });
        ctx.globalAlpha = 1;
    }

    function drawSnow() {
        particles.forEach(flake => {
            ctx.fillStyle = `rgba(255, 255, 255, ${flake.opacity})`;
            ctx.beginPath();
            ctx.arc(flake.x, flake.y, flake.radius, 0, Math.PI * 2);
            ctx.fill();
            flake.y += flake.speed;
            flake.x += flake.drift;
            if (flake.y > height) {
                flake.y = -5;
                flake.x = Math.random() * width;
            }
        });
    }

    function drawStars() {
        stars.forEach(star => {
            star.twinkle += star.speed;
            const alpha = 0.3 + (Math.sin(star.twinkle) + 1) * 0.35;
            ctx.fillStyle = `rgba(255, 255, 255, ${alpha})`;
            ctx.beginPath();
            ctx.arc(star.x, star.y, star.radius, 0, Math.PI * 2);
            ctx.fill();
        });
    }

    function drawStorm() {
        drawRain();
        lightningTimer -= 1;
        if (lightningTimer <= 0) {
            flashAlpha = 0.35 + Math.random() * 0.25;
            lightningTimer = 120 + Math.floor(Math.random() * 200);
        }
        if (flashAlpha > 0) {
            ctx.fillStyle = `rgba(255, 255, 255, ${flashAlpha})`;
            ctx.fillRect(0, 0, width, height);
            flashAlpha *= 0.82;
        }
    }

    function drawFog() {
        drawClouds();
        const gradient = ctx.createLinearGradient(0, height * 0.5, 0, height);
        gradient.addColorStop(0, 'rgba(255, 255, 255, 0)');
        gradient.addColorStop(1, 'rgba(220, 230, 240, 0.25)');
        ctx.fillStyle = gradient;
        ctx.fillRect(0, 0, width, height);
    }

    function render() {
        ctx.clearRect(0, 0, width, height);
        const type = effectType();

        if (type === 'sun') drawSun();
        if (type === 'clouds') drawClouds();
        if (type === 'rain') drawRain();
        if (type === 'snow') drawSnow();
        if (type === 'stars') drawStars();
        if (type === 'storm') drawStorm();
        if (type === 'fog') drawFog();

        animationId = requestAnimationFrame(render);
    }

    window.addEventListener('resize', resize);
    resize();
    render();

    window.WeatherEffects = {
        destroy() {
            window.removeEventListener('resize', resize);
            if (animationId) cancelAnimationFrame(animationId);
        }
    };
})();
