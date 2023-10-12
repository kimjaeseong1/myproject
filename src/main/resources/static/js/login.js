$(document).ready(function () {
    $('#loginButton').click(function () {
        let memberId = $('#memberId').val();
        let password = $('#password').val();

        $.ajax({
            type: 'POST',
            url: '/login',
            data: JSON.stringify({ memberId: memberId, password: password }),
            contentType: 'application/json'
        })
            .done(function (response) {

                alert(response.message);

                window.location.href= '/view/board/list';
            })
            .fail(function (error) {

                if (error && error.responseJSON && error.responseJSON.errors && error.responseJSON.errors.length > 0) {
                    const errorMessage = error.responseJSON.errors[0].message;
                    alert(errorMessage);
                } else {
                   alert("에러 찾을 수 없음");
                }
            });
    });
});