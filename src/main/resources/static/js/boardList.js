let currentPage;
let totalPages ;
let pageSize = 10;
let currentSearchTerm = '';
let Language = "ko";


$(document).ready(function () {
    loadBoardList(0);

    attachPaginationHandlers();

    $('#logOut').on('click',function(){
        logOut();
    })

    $('#locales').change(function() {
        const selectedLanguage = $(this).val();
        changeLanguage(selectedLanguage);
    });

    $('#searchForm').submit(function (e) {
        e.preventDefault();
        currentSearchTerm = $('#searchInput').val().trim();
        loadBoardList(0);
    });


});



function changeLanguage(lang) {
    Language =lang;

   $.get('/view/board/list',{lang:lang},function(){
       window.location.reload();
   });
}



function loadBoardList(page) {
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
            if (error && error.responseJSON && error.responseJSON.errors && error.responseJSON.errors.length > 0) {
                const errorMessage = error.responseJSON.errors[0].message;
                alert(errorMessage);
            }
        });
}

function attachPaginationHandlers() {
    $('#first_page').click(function () {
       loadBoardList(0);
    });

    $('#prev_page').click(function () {
        const currentPage = parseInt($('.pages .active').text());
        if (currentPage > 0 ) {

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
        url:'/userlogout',
    })
        .done(function (response) {
            alert(response.message);
            window.location.href = "/view/board/list";
        })
        .fail(function(error){
            if (error && error.responseJSON && error.responseJSON.errors && error.responseJSON.errors.length > 0) {
                const errorMessage = error.responseJSON.errors[0].message;
                alert(errorMessage);
            }
            console.error('로그아웃 실패 오류',error)
        });
}
