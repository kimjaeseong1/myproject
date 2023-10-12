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
        url : '/board/inquiry/' + boardId,
        dataType: 'json'
})
    .done(function (data){
        $('#title').text(data.title);
        $('#content').text(data.content);
    });

}

function  updateBoard(boardId,board){
    $.ajax({
        type: 'PUT',
        url: '/board/' + boardId,
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(board)
    })
        .done(function () {
            alert('게시물이 수정되었습니다.');
            window.location.href = '/view/board/list';
        })
        .fail(function (error) {
            const errors = error.responseJSon.errors;
            if (errors && errors.length > 0) {
                const errorMessage = errors[0].message;
                alert(errorMessage);
            }
        });
}



