const mealsAjaxUrl = "profile/meals/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: mealsAjaxUrl
};

function filter() {
    let filterUrl = ctx.ajaxUrl + "filter?startDate=" + $('#startDate').val()
        + "&endDate=" + $('#endDate').val()
        + "&startTime=" + $('#startTime').val()
        + "&endTime=" + $('#endTime').val();

    $.get(filterUrl, function (data) {
        ctx.datatableApi.clear().rows.add(data).draw();
    });
}

function clearFilter() {
    $('form').find('input').val("")
}

// $(document).ready(function () {
$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ]
        })
    );
});