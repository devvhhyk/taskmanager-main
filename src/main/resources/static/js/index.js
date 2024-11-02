$(document).ready(function () {
    function updateDateTime() {
        const now = new Date();
        const dateTimeString = now.toLocaleDateString() + ' ' + now.toLocaleTimeString();
        $('#date-time').text(dateTimeString);
    }

    function fetchWeather(latitude, longitude) {
        const apiKey = 'cc6d98dd86b02537c60ea7c325a340eb';
        const url = `https://api.openweathermap.org/data/2.5/weather?lat=${latitude}&lon=${longitude}&appid=${apiKey}&units=metric&lang=kr`;

        $.getJSON(url, function (data) {
            const temperature = data.main.temp.toFixed(1);
            const icon = data.weather[0].icon;
            const location = data.name;

            $('#weather-location').text(location);
            $('#weather-temp').text(`${temperature}°C`);
            $('#weather-icon').attr('src', `http://openweathermap.org/img/wn/${icon}.png`);
        }).fail(function () {
            $('#weather').text('날씨 정보를 가져오는데 실패했습니다.');
        });
    }

    function getLocationAndFetchWeather() {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(function (position) {
                const latitude = position.coords.latitude;
                const longitude = position.coords.longitude;
                fetchWeather(latitude, longitude);
            }, function () {
                $('#weather').text('위치 정보를 가져오는데 실패했습니다.');
            });
        } else {
            $('#weather').text('Geolocation을 지원하지 않는 브라우저입니다.');
        }
    }

    updateDateTime();
    getLocationAndFetchWeather();

    setInterval(updateDateTime, 1000);
});
