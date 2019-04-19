google.charts.load('current', {'packages':['corechart']});
google.charts.setOnLoadCallback(
		function fetchMessageData() {
			fetch("/pieChart")
			.then((response) => {
				return response.json();
			})
			.then((msgJson) => {
				var data = new google.visualization.DataTable();
				//defining columns for the DataTable instances
				data.addColumn('string', 'Type of Death');
				data.addColumn('number', 'Quantity');
				data.addRows([
					['Drowning', msgJson[0].drown],
					['Vehicle Death', msgJson[0].vehicle],
					['Death due to exposure', msgJson[0].exposure],
					['Violence', msgJson[0].violence], // Below limit.
					['Unknown/Unusual cases', msgJson[0].other] // Below limit.
					]);

			console.log(msgJson[0].drown);
			console.log(msgJson[0].vehicle);
			console.log(msgJson[0].exposure);
			console.log(msgJson[0].violence);
			console.log(msgJson[0].other);

			console.log(data);
			drawChart(data);
			});
		} );

		function drawChart(table) {

			// Set chart options
			var options = {
					title: 'Types of Death',
					sliceVisibilityThreshold: .01,
					pieSliceText: 'label',
					is3D: true,
			};


			var chart = new google.visualization.PieChart(document.getElementById('chart_div'));
			chart.draw(table, options);
		}
