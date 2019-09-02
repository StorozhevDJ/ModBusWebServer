
/*$.ajax({
	dataType : 'json',
	url : path + 'devices',
	success : function(jsondata) {
		// $('.results').html('# = ' + jsondata[1].devNo + ' Name = ' +
		// jsondata[1].devName + ', Nickname = ' + jsondata[1].position);
		var tr;
		for (var i = 0; i < jsondata.length; i++) {
			tr = $('<tr/>');
			tr.append("<td>" + jsondata[i].address + "</td>");
			tr.append("<td>" + jsondata[i].type + "</td>");
			tr.append("<td>" + jsondata[i].serial + "</td>");
			tr.append("<td>" + jsondata[i].comment + "</td>");
			$('.results').append(tr);
		}
	}
});*/



$(document).ready(function() {
	/**
	 *  Find device button
	 */
	$("#but_search").click(function() {
		$('.results').empty();
		var n = $('#search_from').val();
		get();
		function get() {
			$.ajax({
				dataType : 'json',
				url : path + 'device/search/' + $("#selected_port option:selected").text() + '/' + n,
				/*
				 * data : { search : 'true', //addr : n },
				 */
				beforeSend : function() {
					// Show image container
					$("#loader").show();
					$('#findDevNum').html(n + "/" + $('#search_to').val());
					$("#but_search").attr("disabled", true);
				},
				success : function(jsondata) {
					// $('.results').empty();
					// $('.results').html('# = ' + jsondata[1].devNo + ' Name =
					// ' + jsondata[1].devName + ', Nickname = ' +
					// jsondata[1].position);
					var tr;
					/*
					 * for (var i = 0; i < jsondata.length; i++) { tr = $('<tr/>');
					 * tr.append("<td>" + jsondata[i].devNo + "</td>");
					 * tr.append("<td>" + jsondata[i].devName + "</td>");
					 * tr.append("<td>" + jsondata[i].position + "</td>");
					 * $('.results').append(tr); }
					 */
					if (jsondata != null) {
						tr = $('<tr/>');
						tr.append("<td>" + jsondata.address + "</td>");
						tr.append("<td>" + jsondata.type + "</td>");
						tr.append("<td>" + jsondata.serial + "</td>");
						tr.append("<td>" + jsondata.comment + "</td>");
						$('.results').append(tr);
					}
				},
				error : function(request, error) {
					console.log(arguments);
					alert(" Error: " + error);
				},
				complete : function(data) {
					if (n < $('#search_to').val()) {
						n++;
						get();
					} else{
						$("#loader").hide(); // Hide image container
						$("#but_search").attr("disabled", false);
					}
						
				}
			});
			
		}
	});
	
	
	

		
});
	
	


/**
 * Delete Device from DataBase
 * @param devId
 * @returns
 */
function DeleteDevice (devId) {
	if (confirm("Удалить устройство из базы данных?")){
			$.ajax({
				dataType : 'json',
				type: 'DELETE',
				url : path + 'device/' + devId + '/',
				success : function(jsondata) {
					$('#'+devId).remove();
				},
				error : function(request, error) {
					console.log(arguments);
					alert(" Error: " + error);
				},
				complete : function(data) {
					
				}
			});
		}
	}
	


/**
 *  Get MLP device data
 */
function getMLPData(devId) {
	$('.mlpdata').empty();
	//var n = $('#search_from').val();
	$.ajax({
		dataType : 'json',
		url : path + 'data/mlp/'+devId,
		data: { from: $('#dateFrom').val(), to: $('#dateTo').val() },
		//data: { from: "2019-08-22", to: "2019-09-01" },
		beforeSend : function() {
			// Show image container
			$("#loader").show();
		},
		success : function(jsondatas) {
			var tr;
			//for (jsondata != null) {
			jsondatas.forEach(function(jsondata) {
				tr = $('<tr/>');
				tr.append("<td>" + jsondata.dateTime + "</td>");
				tr.append("<td>" + jsondata.accel/10000 + "</td>");
				tr.append("<td>" + jsondata.angleAXDeg + "</td>");
				tr.append("<td>" + jsondata.angleAYDeg + "</td>");
				tr.append("<td>" + jsondata.angleAZDeg + "</td>");
				tr.append("<td>" + jsondata.tempCase/100 + "</td>");
				$('.mlpdata').append(tr);
			});
		},
		error : function(request, error) {
			console.log(arguments);
			alert(" Error: " + error);
		},
		complete : function(data) {
				$("#loader").hide(); // Hide image loader container
			}
				
		//}
	});
	
}



