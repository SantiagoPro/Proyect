//barras
function createConfig(gridlines, title) {
    return {
        type: 'bar',
        data: {
            labels: ["January", "February", "March", "April", "May", "June", "July"],
            datasets: [{
                    label: "Snatch",
                    backgroundColor: color(window.chartColors.red).alpha(0.2).rgbString(),
                    borderColor: window.chartColors.red,
                    data: [67, 70, 60, 0, 75, 70, 90],
                    fill: false,
                }, {
                    label: "Clean and Jerk",
                    fill: false,
                    backgroundColor: window.chartColors.blue,
                    borderColor: window.chartColors.blue,
                    data: [72, 80, 67, 0, 87, 80, 100],
                }]
        },
        options: {
            responsive: true,
            title: {
                display: true,
                text: title
            },
            scales: {
                xAxes: [{
                        gridLines: gridlines
                    }],
                yAxes: [{
                        gridLines: gridlines,
                        ticks: {
                            min: 0,
                            max: 120,
                            stepSize: 30
                        }
                    }]
            }
        }
    };
}
;

window.onload = function () {
    var container = document.querySelector('.container-fluid');

    [{
            title: 'Checkup of the year',
            gridLines: {
                display: true
            }
        },
    ].forEach(function (details) {
        var div = document.createElement('div');
        div.classList.add('chart-container');

        var canvas = document.createElement('canva');
        div.appendChild(canva);
        container.appendChild(div);

        var ctx = canva.getContext('2d');
        var config = createConfig(details.gridLines, details.title);
        new Chart(ctx, config);
    });
};