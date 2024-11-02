const listBoard = () => {
    location.href = "/board";
}

const validateForm = () => {
    const title = document.getElementById('boardTitle').value.trim();
    const contents = document.getElementById('boardContents').value.trim();

    if (title === '') {
        alert('제목을 입력해주세요');
        return false;
    }
    if (contents === '') {
        alert('내용을 입력해주세요');
        return false;
    }
    return true;
}
