let idSuccess = false;
let emailSuccess = false;

function idCheck() {
    let id = document.getElementById("memberId").value;

    if (id.trim() === '') {
        alert("아이디를 입력하세요");
        return;
    }

    $.ajax({
        url: '/user/checkId',
        data: { memberId: id },
        type: 'POST',
        dataType: 'json',
        success: function(result) {
            if (result) {
                alert("사용 가능한 ID입니다");
                idSuccess = true;
            } else {
                alert("사용이 불가능한 ID입니다");
                document.getElementById("memberId").value = '';
                idSuccess = false;
            }
        },
        error: function() {
            alert("중복 체크 중 오류가 발생했습니다.");
        }
    });
}

function emailCheck() {
    let email = document.getElementById("memberEmail").value;
    let emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

    if (email.trim() === '') {
        alert("이메일을 입력하세요");
        return;
    }

    if (!emailPattern.test(email)) {
        alert("올바른 이메일 형식을 입력하세요");
        return;
    }

    $.ajax({
        url: '/user/checkEmail',
        data: { memberEmail: email },
        type: 'POST',
        dataType: 'json',
        success: function(result) {
            if (result) {
                alert("사용 가능한 이메일입니다");
                emailSuccess = true;
            } else {
                alert("사용이 불가능한 이메일입니다");
                document.getElementById("memberEmail").value = '';
                emailSuccess = false;
            }
        },
        error: function() {
            alert("중복 체크 중 오류가 발생했습니다.");
        }
    });
}

    function validateForm() {
        var name = document.getElementById("memberName").value;
        var id = document.getElementById("memberId").value;
        var password = document.getElementById("memberPassword").value;
        var passwordConfirm = document.getElementById("passwordConfirm").value;
        var email = document.getElementById("memberEmail").value;
        var emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

        if (name.trim() === '') {
            alert("이름을 입력하세요");
            return false;
        }

        if (id.trim() === '') {
            alert("아이디를 입력하세요");
            return false;
        }

        if (password.trim() === '') {
            alert("비밀번호를 입력하세요");
            return false;
        }

        if (password.length < 8) {
            alert("비밀번호는 최소 8자 이상이어야 합니다");
            return false;
        }

        if (password !== passwordConfirm) {
            alert("비밀번호가 일치하지 않습니다");
            return false;
        }

        if (email.trim() === '') {
            alert("이메일을 입력하세요");
            return false;
        }

        if (!emailPattern.test(email)) {
            alert("올바른 이메일 형식을 입력하세요");
            return false;
        }

        if (!idSuccess) {
            alert("아이디 중복 체크를 완료해주세요.");
            return false;
        }

        if (!emailSuccess) {
            alert("이메일 중복 체크를 완료해주세요.");
            return false;
        }

        return true;
    }

