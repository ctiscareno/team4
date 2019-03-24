google.charts.load('current', {'packages':['corechart']});
google.charts.setOnLoadCallback(
function fetchMessageData() {
	fetch("/messageChart")
	.then((response) => {
		return response.json();
	})
	.then((msgJson) => {
		var msgData = new google.visualization.DataTable();
		//defining columns for the DataTable instances
		msgData.addColumn('date','Data');
		msgData.addColumn('number','Message Count');

		for (i = 0; i < msgJson.length; i++) {
			msgRow = [];
			var timestampAsDate = new Date (msgJson[i].timestamp);
			var totalMessages = i + 1;
			//TODO add the formatted values to msgRow array by using JS' push method done
			msgRow.push(timestampAsDate);
			msgRow.push(totalMessages);
			//console.log(msgRow);
			msgData.addRow(msgRow);

		}
		//console.log(msgData);
		drawChart(msgData);
	});
} );

//fetchMessageData();

function drawChart(table) {

	// Set chart options
	var chart_options = {
			'title':'Number of messages per user',
			'is3D':true,
			'width':800,
			'height':400};

	// Instantiate and draw our chart, passing in some options.
	var chart = new google.visualization.BarChart(document.getElementById('chart'));
	chart.draw(table, chart_options);
}
