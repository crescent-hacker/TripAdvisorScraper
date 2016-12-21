/**
 * initialize after document loaded
 */
$(function () {
    $("#launch-button").on("click", function () {
        launchScrape();
    });
    $("#view-history-button").on("click", function () {
        viewHistory();
    })
});


/**
 * start crawling
 */
function launchScrape() {
    //check
    var url = $("#trip-advisor-url").val();
    if (!url) {
        popAlert({
            title: "<span style='color: #1272a9;'>Type in url </span>",
            content: "Please type in url before you launch scraping.",
            needConfirm: false
        })
        return;
    }
    //start ajax
    waitingDialog.show();
    $.ajax({
        type: "post",
        url: "/launchScrape",
        data: {
            url: url
        },
        dataType: "json",
        success: function (data) {
            if (data.success) {
                popAlert({
                    title: "<span style='color: #17a913;'>Scrape successfully</span>",
                    content: "Scrapping succeeded.",
                    needConfirm: false
                })
                viewReviews(data.insertId);
            } else {
                popAlert({
                    title: "<span style='color: #a94442;'>Scrape failed</span>",
                    content: data.msg,
                    needConfirm: false
                })
            }
            waitingDialog.hide();
        },
        error: function (err) {
            console.error("launchScrape error!!!", err);
            waitingDialog.hide();
        }
    });
}

/**
 * view the scrape history
 */
function viewHistory() {
    waitingDialog.show();
    $.ajax({
        type: "get",
        url: "/getHistories",
        data: {},
        dataType: "json",
        success: function (data) {
            if (data.success) {
                $.each(data.list, function (idx, elem) {
                    var operationHtml = "<a href='javascript:void(0)' onclick='viewReviews(" + elem.id + ")'>View</a>";
                    data.list[idx].operation = operationHtml;
                });
                //draw table
                $('#show-table').bootstrapTable('destroy');
                $('#show-table').bootstrapTable({
                    columns: [
                        {
                            field: 'scrapeDate',
                            title: 'Date'
                        }, {
                            field: 'url',
                            title: 'Url'
                        }, {
                            field: 'operation',
                            title: 'Operation'
                        }
                    ],
                    data: data.list
                });
            } else {
                popAlert({
                    title: "<span style='color: #a94442;'>View history failed</span>",
                    content: "Sorry, you haven't launched a scrape yet.",
                    needConfirm: false
                })
            }
            waitingDialog.hide();
        },
        error: function (err) {
            console.error("viewHistory error!!!", err);
            waitingDialog.hide();
        }
    });
}

/**
 * view the reviews by history id
 */
function viewReviews(id) {
    waitingDialog.show();
    $.ajax({
        type: "get",
        url: "/getReviews",
        data: {
            shId: id,
            pageNum: 0
        },
        dataType: "json",
        success: function (data) {
            if (data.success) {
                $.each(data.list,function(idx,elem){
                    data.list[idx].dateOfReview = new Date(elem.dateOfReview).toDateString();
                });
                $('#show-table').bootstrapTable('destroy');
                //draw table
                $('#show-table').bootstrapTable({
                    columns: [{
                        field: 'dateOfReview',
                        title: 'Reviewed Date'
                    }, {
                        field: 'nameOfReviewer',
                        title: 'Reviewer Name'
                    }, {
                        field: 'title',
                        title: 'Title'
                    }, {
                        field: 'description',
                        title: 'Description'
                    }],
                    data: data.list
                });
            } else {
                popAlert({
                    title: "<span style='color: #a94442;'>Query reviews failed</span>",
                    content: "Sorry, there is no entry in this history scrape.",
                    needConfirm: false
                })
            }
            waitingDialog.hide();
        },
        error: function (err) {
            console.error("viewReviews error!!!", err);
            waitingDialog.hide();
        }
    });
}