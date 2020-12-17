<script>
import { Bar } from "vue-chartjs";

export default {
  extends: Bar,
  mounted() {
    this.renderChart(
      {
        labels: [
          "January",
          "February",
          "March",
          "April",
          "May",
          "June",
          "July",
          "August",
          "September",
          "October",
          "November",
          "December"
        ],
        datasets: [
          {
            label: "Events/Anomalies",
            backgroundColor: "#1D2430",
            data: [3, 4, 5, 4, 4, 5, 3, 7, 9, 10, 5, 3]
          }
        ]
      },
      {
        responsive: true,
        maintainAspectRatio: false,
        title: {
          display: true,
          text: "Events/Anomalies by Month"
        },
        scales: {
          xAxes: [
            {
              gridLines: {
                display: false
              }
            }
          ],
          yAxes: [
            {
              gridLines: {
                display: false
              },
              ticks: {
                beginAtZero: true
              }
            }
          ]
        },
        legend: {
          onClick: e => e.stopPropagation()
        },
        showLines: false
      }
    );
  }
};
</script>
