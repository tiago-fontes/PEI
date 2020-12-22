<script>
import { Line } from "vue-chartjs";

export default {
  extends: Line,
  mounted() {
    this.renderChart(
      {
        datasets: [
          {
            label: "Before Anomaly",
            borderColor: "#FF9800",
            data: this.beforeAnomaly,
            fill: false
          },
          {
            label: "During Anomaly",
            borderColor: "#F44336",
            data: this.duringAnomaly,
            fill: false
          },
          {
            label: "After Anomaly",
            borderColor: "#4CAF50",
            data: this.afterAnomaly,
            fill: false
          }
        ]
      },
      {
        responsive: true,
        maintainAspectRatio: false,
        title: {
          display: true,
          text: this.name
        },
        scales: {
          xAxes: [
            {
              type: "time",
              time: {
                parser: "YYYY-MM-DD HH:mm:ss",
                unit: "minute",
                displayFormats: {
                  minute: "HH:mm:ss",
                  hour: "HH:mm:ss"
                }
              },
              gridLines: {
                display: false
              },
              ticks: {
                source: "data"
              }
            }
          ],
          yAxes: [
            {
              gridLines: {
                display: false
              }
            }
          ]
        }
      }
    );
  },
  props: ["name", "beforeAnomaly", "duringAnomaly", "afterAnomaly"]
};
</script>
