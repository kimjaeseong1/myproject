$(document).ready(function () {
 AccessDenied();
});

function AccessDenied (){
    const nextPage = null;
    const message = null;

    if(message){
        alert(message);
    }

    if(nextPage){
        window.location.href = nextPage;
    }
}