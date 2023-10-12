let currentPage;
let totalPages ;
let pageSize = 10;
let currentSearchTerm = '';


$(document).ready(function () {
    loadBoardList(0);

    attachPaginationHandlers();

    $('#logOut').on('click',function(){
        logOut();
    })


});


function loadBoardList(page) {
    $('#searchForm').submit(function (e) {
        e.preventDefault();
        currentSearchTerm = $('#searchInput').val().trim();
        loadBoardList(0);
    });

    const data = {
        page: page,
        size: pageSize,
        searchTerm: currentSearchTerm
    }


    const url = currentSearchTerm
                ? '/board/search?title=' + currentSearchTerm
                :  '/board/inquiry/' + page ;
    $.ajax({
        type: 'GET',
        url: url ,
        data: data,
        dataType: 'json'
    })
        .done(function (data) {
            console.log(data);
            const boardData = data.content;
            totalPages = data.totalPages;
            currentPage = data.number;

            $('#boardTableBody').empty();
            $('#write').attr('href', '/view/board/write');
            $('#login').attr('href','/view/login')
            $.each(boardData, function (index, board) {
                $('#boardTableBody').append(
                    '<tr>' +
                    '<td>' + board.boardId + '</td>' +
                    '<td><a href="/view/board/detail?boardId=' + board.boardId + '">' + board.title + '</a></td>' +
                    '</tr>'
                );
            });

            updatePagination(totalPages, currentPage);
        })
        .fail(function (error) {
            console.error('게시물 목록을 불러오는 중 오류 발생: ', error);
        });
}

function attachPaginationHandlers() {
    $('#first_page').click(function () {
       loadBoardList(0);
    });

    $('#prev_page').click(function () {
        const currentPage = parseInt($('.pages .active').text());
        if (currentPage > 0) {

            loadBoardList(currentPage -1);
        }
    });

    $('#next_page').click(function () {
        const currentPage = parseInt($('.pages .active').text());
        const totalPages = parseInt($('.pages span').last().text());

        if (currentPage < totalPages) {

            loadBoardList(currentPage +1);
        }
    });

    $('#last_page').click(function () {
        const totalPages = parseInt($('.pages span').last().text());
            loadBoardList(totalPages);
    });

    $('.pages').on('click', 'span', function () {

        const page = parseInt($(this).text());
        loadBoardList(page);
    });
}

function updatePagination(totalPages, currentPage) {
    const pages = $('.pages');
    pages.empty();

    for (let i = 0; i < totalPages; i++) {
        const pageLink = $('<span>').text(i);
        if (i === currentPage) {
            pageLink.addClass('active');
        }
        pages.append(pageLink);
    }

}

function logOut(){
    $.ajax({
        type:'POST',
        url: '/logout',
    })
        .done(function () {
            alert('로그아웃 되었습니다.');
            window.location.href = "/view/board/list";
        })
        .fail(function(error){
            alert('로그인 한 유저가 없습니다. ');
            console.error('로그아웃 실패 오류',error)
        });
}
