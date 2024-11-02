document.getElementById('loginForm').addEventListener('submit', function(event) {
    const memberId = document.getElementById('memberId').value.trim();
    const memberPassword = document.getElementById('memberPassword').value.trim();

    if (memberId === '') {
        alert('아이디를 입력하세요');
        event.preventDefault();
    } else if (memberPassword === '') {
        alert('비밀번호를 입력하세요');
        event.preventDefault();
    }
});
