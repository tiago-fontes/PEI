<script>
import { Bar } from "vue-chartjs";

export default {
  extends: Bar,
  props: ["data"],
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
            data: this.data
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
                beginAtZero: true,
                callback: function(value) {
                  if (value % 1 === 0) {
                    return value;
                  }
                }
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
