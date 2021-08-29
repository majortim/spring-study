//IIFE
(function () {
    const birth = document.querySelector('#birth');
    flatpickr(birth, {"locale" : "ko"});

    const onChangeInputHandler = function(evt) {
        const cl = (evt.target.type === "radio") ? evt.target.parentElement.classList : evt.target.classList;

        if(cl.contains("field_error"))
            cl.remove("field_error");
    }

    document.querySelectorAll('.field_error, .field_error input')
        .forEach(
            function(element){
                element.addEventListener('change', onChangeInputHandler)
            }
        );
})();
