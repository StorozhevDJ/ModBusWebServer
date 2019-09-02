$(function() {
	$.datepicker.setDefaults($.datepicker.regional['ru']);
	
    $('#dateFrom').datepicker({
        constrainInput: true,
        minDate: "-1m",
        maxDate: "0",
        dateFormat: 'yy-mm-dd',
	});
    $('#dateTo').datepicker({
        constrainInput: true,
        minDate: "-1m",
        maxDate: "0",
        dateFormat: 'yy-mm-dd',

	});
    
    $('#dateFrom').datepicker('setDate', '-1');
    $('#dateTo').datepicker('setDate', 'today');

});


