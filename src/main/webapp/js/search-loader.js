google.charts.load('current', {'packages':['table']});
google.charts.setOnLoadCallback(
function fetchMessageData() {
	fetch("/searchMigrant")
	.then((response) => {
		return response.json();
	})
	.then((msgJson) => {
		var data = new google.visualization.DataTable();
		//defining columns for the DataTable instances
		data.addColumn('number','id');
		data.addColumn('string','Cause of Death');
		data.addColumn('string','Region of Origin');
		data.addColumn('number','#Dead');
		data.addColumn('string','Region of Incident');
		data.addColumn('string','Date');
		data.addColumn('number','Latitude');
		data.addColumn('number','Longitude');

		for (i = 0; i < msgJson.length; i++) {
			msgRow = [];
			msgRow.push(msgJson[i].id);
			msgRow.push(msgJson[i].cause_of_death);
			msgRow.push(msgJson[i].region_origin);
			msgRow.push(msgJson[i].numDead);
			msgRow.push(msgJson[i].incident_region);
			msgRow.push(msgJson[i].date);
			msgRow.push(msgJson[i].latitude);
			msgRow.push(msgJson[i].longitude);
			
			console.log(msgRow);
			data.addRow(msgRow);

		}
		console.log(data);
		drawChart(data);
	});
} );

//fetchMessageData();

function drawChart(table) {

	// Set chart options
	var chart_options = {
			'title':'Number of messages per user',
			//'is3D':true,
			'width':900,
			'height':900,
			 bar: {groupWidth: "95%"},
			};

	var chart = new google.visualization.Table(document.getElementById('table_div'));
    chart.draw(table, {showRowNumber: false, width: '100%', height: '100%'});
}