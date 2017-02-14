$(document).ready(function() {


    $('form[name=parseForm]').submit(function(e){
        e.preventDefault();
        $.ajax({
            type: 'POST',
            cache: false,
            url: './test',
            data: encodeURI($(this).serialize()),
            success: function(msg) {
                alert(msg);
            }
        });
    });
});