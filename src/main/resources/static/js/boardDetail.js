// 게시글 목록
const listBoard = () => {
    const page = /*[[${page}]]*/ '';
    location.href = `/board?page=${page}`;
}

// 게시글 수정
const updateBoard = () => {
    const id = document.getElementById("boardId").value;
    location.href = `/board/update/${id}`;
}

// 게시글 삭제
const deleteBoard = () => {
    if (confirm("정말로 이 게시글을 삭제하시겠습니까?")) {
        const id = document.getElementById("boardId").value;
        location.href = `/board/delete/${id}`;
    }
}

// 댓글 작성
const commentWrite = () => {
    const writer = document.getElementById("commentWriter").value;
    const contents = document.getElementById("commentContents").value;
    const id = document.getElementById("boardId").value;

    if (!contents.trim()) {
        alert("댓글 내용을 입력하세요.");
        return;
    }

    $.ajax({
        type: "post",
        url: "/comment/save",
        data: {
            "commentWriter": writer,
            "commentContents": contents,
            "boardId": id
        },
        success: function(res) {
            let output = '';
            for (let i in res) {
                // 시간 포맷 적용
                const date = new Date(res[i].commentCreatedTime);
                const formattedDate =
                    date.getFullYear() + '-' +
                    ('0' + (date.getMonth() + 1)).slice(-2) + '-' +
                    ('0' + date.getDate()).slice(-2) + ' ' +
                    ('0' + date.getHours()).slice(-2) + ':' +
                    ('0' + date.getMinutes()).slice(-2) + ':' +
                    ('0' + date.getSeconds()).slice(-2);

                // 댓글 추가
                output += "<div class='comment-item'>";
                output += "<div class='comment-meta'>";
                output += "<span>" + res[i].commentWriter + "</span>";
                output += "<span>" + formattedDate + "</span>";
                output += "</div>";
                output += "<div class='comment-content'>" + res[i].commentContents + "</div>";
                output += "</div>";
            }
            document.getElementById('comment-list').innerHTML = output;
            document.getElementById('commentContents').value = '';
        },
        error: function(err) {
            console.log("요청 실패", err);
        }
    })
}
