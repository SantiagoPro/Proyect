//radar
var color = Chart.helpers.color;
var se = {
    type: 'radar',
    data: {
        labels: ["Velocidad", "Fuerza", "Resistencia", "Agilidad", "Coordinacion", "Flexibilidad", "Salto"],
        datasets: [{
                label: "Más altas",
                backgroundColor: color(window.chartColors.red).alpha(0.2).rgbString(),
                borderColor: window.chartColors.red,
                pointBackgroundColor: window.chartColors.red,
                data: [
                    78,
                    85,
                    56,
                    89,
                    82,
                    70,
                    80
                ]
            }, {
                label: "Más bajas",
                backgroundColor: color(window.chartColors.blue).alpha(0.2).rgbString(),
                borderColor: window.chartColors.blue,
                pointBackgroundColor: window.chartColors.blue,
                data: [
                    12,
                    14,
                    34,
                    23,
                    56,
                    45,
                    23
                ]
            } ]
    },
    options: {
        legend: {
            position: 'top'
        },
        title: {
            display: true,
            text: 'Evaluacion de capacidades'
        },
        scale: {
            ticks: {
                beginAtZero: true
            }
        }
    }
};
window.onload = function () {
    window.myRadar = new Chart(document.getElementById("canvas"), se);
};
