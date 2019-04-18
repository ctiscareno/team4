google.charts.load('current', {'packages':['table']});
google.charts.setOnLoadCallback(
function fetchMessageData() {
	fetch("/allChart")
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
		
		/* private int id;
		    private String cause_of_death;
		    private String region_origin;
		    private int numDead;
		    private String incident_region;
		    private String date;
		    private double latitude;
		    private double longitude; */

		for (i = 0; i < msgJson.length; i++) {
			msgRow = [];
			var id = new Number (msgJson[i].id);
			var cause_of_death = new String (msgJson[i].cause_of_death);
			var region_origin = new String (msgJson[i].region_origin);
			var numDead = new Number (msgJson[i].numDead);
			var incident_region = new String (msgJson[i].incident_region);
			var date = new String (msgJson[i].date);
			var latitude = new Number (msgJson[i].latitude);
			var longitude = new Number (msgJson[i].longitude);
			
			//TODO add the formatted values to msgRow array by using JS' push method done
			msgRow.push(id);
			msgRow.push(cause_of_death);
			msgRow.push(region_origin);
			msgRow.push(numDead);
			msgRow.push(incident_region);
			msgRow.push(date);
			msgRow.push(latitude);
			msgRow.push(longitude);
			
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

	// Instantiate and draw our chart, passing in some options.
	/*var chart = new google.visualization.BarChart(document.getElementById('chart'));  */
	//chart.draw(table, chart_options);
	var chart = new google.visualization.Table(document.getElementById('table'));
	chart.draw(table, chart_options);
    //chart.draw(table, {showRowNumber: true, width: '100%', height: '100%'});
}
