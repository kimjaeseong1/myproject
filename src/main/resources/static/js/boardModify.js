$(document).ready(function () {
    const urlParams = new URLSearchParams(window.location.search);
    const boardId = urlParams.get('boardId');
    console.log("boardId", boardId);

    boardData(boardId);

    $('#modifyButton').on('click',function () {
        console.log("boardId", boardId);
        let board = {
            boardId: boardId,
            title: $('#title').val(),
            content: $('#content').val()
        };
        console.log("board=" ,board);
        updateBoard(boardId,board);
    });
});

function boardData(boardId) {
    $.ajax({
        type: 'GET',
        url : '/board/inquiry/detail/' + boardId,
        dataType: 'json'
})
    .done(function (data){
        $('#title').val(data.title);
        $('#content').text(data.content);
    });

}

function  updateBoard(boardId,board){
    $.ajax({
        type: 'PATCH',
        url: '/board/' + boardId,
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(board)
    })
        .done(function (response) {
            alert(response.message);
            window.location.href = '/view/board/list';
        })
        .fail(function (error) {
            if (error && error.responseJSON && error.responseJSON.errors && error.responseJSON.errors.length > 0) {
                const errorMessage = error.responseJSON.errors[0].message;
                alert(errorMessage);
            }
        });
}



