<script>
import { Line } from "vue-chartjs";

export default {
  extends: Line,
  mounted() {
    this.renderChart(
      {
        datasets: [
          {
            label: "Gas",
            borderColor: "#D7F9F1",
            data: [
              112302,
              111382,
              110824,
              109860,
              108379,
              108048,
              106359,
              104970,
              78147,
              56198
            ],
            fill: false
          },
          {
            label: "Humidity",
            borderColor: "#7AA095",
            data: [
              62.7877953468,
              62.8140506745,
              62.8270510578,
              62.8400522224,
              62.8595840004,
              62.8723014228,
              62.8784032623,
              62.917732618,
              63.6112965592,
              64.3593990447
            ],
            fill: false
          },
          {
            label: "PM 10",
            borderColor: "#40531B",
            data: [14.2, 14.4, 14.4, 14.5, 12.4, 12.3, 13.4, 12.7, 11.5, 11.5],
            fill: false
          },
          {
            label: "PM 25",
            borderColor: "#618B4A",
            data: [6.3, 6.4, 6.4, 6.4, 6.3, 6.3, 6.4, 6.4, 6.3, 6.1],
            fill: false
          },
          {
            label: "Pressure",
            borderColor: "#AFBC88",
            data: [
              1019.64309576,
              1019.64297457,
              1019.64662878,
              1019.6485576,
              1019.64746217,
              1019.653045,
              1019.65895243,
              1019.65105727,
              1019.63683007,
              1019.63049645
            ],
            fill: false
          },
          {
            label: "Temperature",
            borderColor: "#494850",
            data: [
              20.234296875,
              20.2321484375,
              20.2301953125,
              20.2282421875,
              20.2255078125,
              20.2216015625,
              20.217890625,
              20.2141796875,
              20.131953125,
              19.99640625
            ],
            fill: false
          }
        ]
      },
      {
        responsive: true,
        maintainAspectRatio: false,
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
        }
      }
    );
  }
};
</script>
