<!DOCTYPE html>
<html class="full" lang="en">
<head>

    <title>TripAdvisor Scraper</title>
    <%@include file="component/common_include.jsp" %>
    <!--customize css-->
    <link href="/static/css/lib/boostrap-table/bootstrap-table.min.css" rel="stylesheet"/>
    <link href="/static/css/domain/scraper.css" rel="stylesheet"/>
    <!--customize js-->
    <script src="/static/js/lib/boostrap-table/bootstrap-table.min.js" type="application/javascript"></script>
    <script src="/static/js/domain/scraper.js" type="application/javascript"></script>
    <script src="/static/js/tool/process-bar.js" type="application/javascript"></script>
    <script src="/static/js/tool/popup-alert.js" type="application/javascript"></script>
</head>

<body>
<!--************* main content *****************-->
<div class="main main-bg">
    <div class="cover black" data-color="black"></div>

    <div class="container center-container">
        <h1 class="logo cursive">
            <span>TripAdvisor Scraper</span>
        </h1>
        <div class="content">
            <h4 class="motto"></h4>
            <div class="subscribe">
                <!-- operation panel-->
                <div class="row">
                    <div class="col-lg-12">
                        <div class="col-lg-8 col-lg-offset-2">
                            <div class="input-group">
                                <input id="trip-advisor-url" type="text" class="form-control transparent"
                                       placeholder="TripAdvisor review url..." value="">
                                <span class="input-group-btn">
                                    <button id="launch-button" class="btn btn-ghost launch-button"
                                            type="button">Scrape</button>
                                </span>
                            </div>
                        </div>
                        <div class="col-lg-2">
                            <div class="input-group">
                            <span class="input-group-btn">
                                    <button id="view-history-button" class="btn btn-ghost radius launch-button"
                                            type="button">History</button>
                                </span>
                            </div>
                        </div>
                    </div>
                </div>
                <br/>
                <br/>
                <br/>
                <div class="row">
                    <div class="col-lg-12">
                        <!-- table -->
                        <div class="table-responsive">
                            <table id="show-table" class="table" data-height="auto"
                                   data-pagination="true"
                                   sidePagination="client"
                                   data-page-number="1" data-page-size="10" data-smart-display="true"
                                   data-sortable="true">

                            </table>
                        </div>
                    </div>
                </div>
                <br/>
                <br/>
                <br/>
            </div>
        </div>
    </div>
</div>

<!--************* alert modal *****************-->
<div class="modal fade" tabindex="-1" role="dialog" id="alert-modal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="alert-title"></h4>
            </div>
            <div class="modal-body" style="font-size:16px">
                <p id="alert-content"></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" id="alert-confirm-button">Confirm</button>
            </div>
        </div>
    </div>
</div>
</body>

</html>
