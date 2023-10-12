$(document).ready(function () {

    const urlParams = new URLSearchParams(window.location.search);
    const boardId = urlParams.get('boardId');
    console.log("boardId", boardId);

        boardDetail(boardId);


    $('#deleteButton').on('click', function () {
        deleteBoard(boardId); // 게시물 삭제
    });
});


function boardDetail(boardId){

        $.ajax({
        type: 'GET',
        url : '/board/inquiry/detail/' + boardId,
        dataType: 'json'

    })
        .done(function (data){

            $('#title').text(data.title);
            $('#content').text(data.content);
            $('#modify').attr('href','/view/board/modify?boardId=' + data.boardId);

    })
        .fail(function (error){
            console.error('게시물 상세 조회 오류',error);
        });
}

function  deleteBoard(boardId){

    $.ajax({
        type: 'DELETE',
        url: '/board/' + boardId,
        dataType: 'json'
    })
        .done(function () {
            alert('게시물이 삭제되었습니다.');
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