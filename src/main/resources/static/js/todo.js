$(document).ready(function() {
    loadTodos();

    $('#todoForm').submit(function(event) {
        event.preventDefault();
        todoSave();
    });
});

// 할일추가
function todoSave() {
    const todoTask = $('#todoInput').val();

    if (todoTask.trim() === "") {
        alert("할 일을 입력하세요.");
        return;
    }

    $.ajax({
        url: '/todo/save',
        method: 'POST',
        data: {
            todoTask: todoTask,
            todoCompleted: false
        },
        success: function() {
            $('#todoInput').val('');
            loadTodos();
        }
    });
}

// 할일 목록
function loadTodos() {
    $.get('/todo/list', function(todos) {
        const todoList = $('#todoList');
        todoList.empty();

         todos.sort((a, b) => b.id - a.id);

        todos.reverse().forEach(function(todo) {
            const todoItem = $('<li class="' + (todo.todoCompleted ? 'completed' : '') + '">' +
                '<span onclick="toggleComplete(' + todo.id + ', ' + todo.todoCompleted + ')">' + todo.todoTask + '</span>' +
                '<div class="button-group">' +
                '<button class="edit-btn" onclick="editTodo(' + todo.id + ', \'' + todo.todoTask + '\')" ' + (todo.todoCompleted ? 'disabled' : '') + '>수정</button>' +
                '<button class="delete-btn" onclick="deleteTodo(' + todo.id + ')">삭제</button>' +
                '</div></li>');
            todoList.prepend(todoItem);
        });
    });
}

// 할일 완료
function toggleComplete(id, currentStatus) {
    const newStatus = !currentStatus;
    $.ajax({
        url: '/todo/complete',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({ id: id, todoCompleted: newStatus }),
        success: function(response) {
            loadTodos();
        },
        error: function(xhr, status, error) {
            alert("Error: " + error);
        }
    });
}

// 할일 삭제
function deleteTodo(id) {
    $.ajax({
        url: '/todo/delete',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({ id: id }),
        success: function(response) {
            loadTodos();
        },
        error: function(xhr, status, error) {
            alert("Error: " + error);
        }
    });
}

// 할일 수정
function editTodo(id, task) {
    const newTask = prompt("할 일을 수정하세요:", task);
    if (newTask === null || newTask.trim() === "") {
        return;
    }

    const todoItem = $('#todoList').find('li').filter(function() {
        return $(this).find('span').attr('onclick').includes(`toggleComplete(${id}`);
    });

    const todoCompleted = todoItem.hasClass('completed');

    $.ajax({
        url: '/todo/update',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({ id: id, todoTask: newTask, todoCompleted: todoCompleted }),
        success: function(response) {
            loadTodos();
        },
        error: function(xhr, status, error) {
            alert("Error: " + error);
        }
    });
}
