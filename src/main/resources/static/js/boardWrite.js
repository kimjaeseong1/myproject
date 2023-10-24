let main = {
    init : function (){
        let _this = this;
        $('#sub-save').on('click',function (){
            _this.save();
        });
    },
    save : function () {
        let data = {
            title: $('#title').val(),
            content: $('#content').val(),
        };

        $.ajax({
            type: 'POST',
            url: '/board',
            datatype: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function (response){
            alert(response.message);
            window.location.href='/view/board/list';
        }).fail(function (error){
            if (error && error.responseJSON && error.responseJSON.errors && error.responseJSON.errors.length > 0) {
                const errorMessage = error.responseJSON.errors[0].message;
                alert(errorMessage);
            }
        });

        return false;
    }
};

main.init();