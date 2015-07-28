$(document).ready(function() {



    var body_id = $("body").attr("id");

    if(body_id === 'login-page') {

        (function() {



        $(".login-input-email").keyup(function() {
            var input = $(this);
            if(input.val() !== "") {
                $(".login-input-email-clear").removeClass("is-hidden");
            }
            else {
                $(".login-input-email-clear").addClass("is-hidden");
            }
        });
        $(".login-input-password").keyup(function() {
            var input = $(this);
            if(input.val() !== "") {
                $(".login-input-password-clear").removeClass("is-hidden");
            }
            else {
                $(".login-input-password-clear").addClass("is-hidden");
            }
        });
        $(".login-input-email-clear").click(function() {
            $(".login-input-email").val("").trigger("focus");
            $(".login-input-email-clear").addClass("is-hidden");
        });
        $(".login-input-password-clear").click(function() {
            $(".login-input-password").val("").trigger("focus");
            $(".login-input-password-clear").addClass("is-hidden");
        });

        /* test */
        var count = 0;
        $(".login-button").click(function() {
            if(count === 1) {
                window.location.href = 'select-location.html';
            }
            $(".modal-alert").modalBox('open');
            count++;
        });
        $(".modal-alert-close").click(function() {
            $(".modal-alert").modalBox('close');
        });
        /* end of test */

        })();
    }

    if(body_id === 'select-location-page') {
        (function() {

        $(".select-option").click(function() {
            $(".select-option").removeClass("is-selected");
            $(this).addClass("is-selected");
        });
        $(".select-option-toggle").click(function() {
            $(this).hide();
            $(".select.is-hidden").slideDown();
        });

        })();
    }

    if(body_id === 'add-location-page') {
        (function() {

        var SLIDE_DURATION = 250;
        var articleSlideView = $(".layout-container").simpleSlideView({
            views: ".layout-slide",
            duration: SLIDE_DURATION
        });

        var active_select = null;
        $(".form-line-select").click(function() {
            var a = $(this);
            if(a.hasClass("is-disabled")) return false;
            var view = a.data("view_name");
            articleSlideView.pushView(view);
            active_select = a;
        });

        $(".header-link-back").click(function() {
             articleSlideView.popView('.slide-add-location');
        });


        $(".select-option").click(function() {
            var a = $(this);
            var select = a.closest(".select");
            $(".select-option", select).removeClass("is-selected");
            $(this).addClass("is-selected");

            var val = a.text();
            $(".form-line-select-text", active_select).text(val).addClass("is-filled");
            $(".form-line-select").removeClass("is-disabled");
        });

        })();
    }

    if(body_id === 'find-product-page') {
        (function() {

        var SLIDE_DURATION = 250;
        var articleSlideView = $(".layout-container").simpleSlideView({
            views: ".layout-slide",
            duration: SLIDE_DURATION
        });

        $("[data-popview='.slide-product-scan']").click(function() {
            $(".scan-ready").show();
            $(".scan-retrieving").hide();
            $(".scan-button-enter-code").removeClass("is-disabled");
        });


        var active_select = null;
        $(".form-line-select").click(function() {
            var a = $(this);
            if(a.hasClass("is-disabled")) return false;
            var view = a.data("view_name");
            articleSlideView.pushView(view);
            active_select = a;
        });
        $(".select-option").click(function() {
            var a = $(this);
            var select = a.closest(".select");
            $(".select-option", select).removeClass("is-selected");
            $(this).addClass("is-selected");

            var val = a.text();
            $(".form-line-select-text", active_select).text(val).addClass("is-filled");
        });

        /* test */
        var count = 0;
        $(".scan-area").click(function() {
            var area = $(this).find("img");
            area.data("unclear", area.attr("src"));
            area.attr("src", area.data("clear"));
            if(count === 0) {
                $(".scan-ready").hide();
                $(".scan-error").show();
            }
            else {
                $(".scan-ready").hide();
                $(".scan-retrieving").show();
                $(".scan-button-enter-code").addClass("is-disabled");
                setTimeout(function() {
                    articleSlideView.pushView(".slide-product-info");
                }, 3000);
            }
            count++;
        });
        $(".scan-button-rescan").click(function() {
            var area = $(".scan-area").find("img");
            area.attr("src", area.data("unclear"));
            $(".scan-ready").show();
            $(".scan-error").hide();
        });
        /* end of test */

        })();
    }

    if(body_id === 'survey-list-page') {
        $(".form-line").click(function() {
            var line = $(this);
            var link = $(".form-line-select", line).attr("href");
            window.location.href = link;
        });
    }


    if(body_id === 'questions-page') {
        (function() {


        var window_height = $(window).height();
        $("article").css("min-height", window_height-85);

        var SLIDE_DURATION = 250;
        var articleSlideView = $(".layout-container").simpleSlideView({
            views: ".layout-slide",
            duration: SLIDE_DURATION
        });

        })();
    }


    if(body_id === 'review-page') {
        (function() {

        var window_height = $(window).height();
        $("article").css("min-height", window_height-60);

        var SLIDE_DURATION = 250;
        var articleSlideView = $(".layout-container").simpleSlideView({
            views: ".layout-slide",
            duration: SLIDE_DURATION
        });

        $(window).scroll(function() {
            var wrapper = $(".layout-container")[0];
            var scrollY = wrapper.scrollHeight - wrapper.offsetHeight - $(window).scrollTop();
            if(scrollY > 30) {
                $(".js-review-button-wrapper").addClass("is-content-scrollable");
            }
            else {
                $(".js-review-button-wrapper").removeClass("is-content-scrollable");
            }
        });

        })();
    }


    $(".layout-container").on("viewChangeEnd", function() {
        $(window).scrollTop(0);
    });

});