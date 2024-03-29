import { Component, Input, OnInit } from '@angular/core';
import Chart, { ChartType } from 'chart.js/auto';
import * as $ from 'jquery';

@Component({
  selector: 'app-artilce-utility-chart',
  templateUrl: './artilce-utility-chart.component.html',
  styleUrls: ['./artilce-utility-chart.component.css'],
})
export class ArtilceUtilityChartComponent implements OnInit {
  @Input() labelName = '';
  @Input() chartId = '';
  @Input() type: ChartType = 'line';
  @Input() mapArticlesUseFullness: Map<string, number> = new Map();
  mapArticlesClone: Map<string, number> = new Map();
  myChart;

  constructor() {}
  ngOnInit(): void {
    this.setMapClone();
  }

  ngDoCheck(): void {
    if (
      this.mapArticlesClone['LOW'] != this.mapArticlesUseFullness['LOW'] ||
      this.mapArticlesClone['MEDIUM'] !=
        this.mapArticlesUseFullness['MEDIUM'] ||
      this.mapArticlesClone['HIGH'] != this.mapArticlesUseFullness['HIGH']
    ) {
      console.log("CHART DATA HAS CHANGED")
      this.setFieldAndUpdate();
      this.setMapClone();
    }
  }

  ngAfterViewInit(): void {
    this.setChart();
  }

  ngOnDestroy(): void {
    this.myChart.destroy();
  }

  setFieldAndUpdate() {
    this.myChart.data.datasets[0].data[0] = this.mapArticlesUseFullness['LOW'];
    this.myChart.data.datasets[0].data[1] = this.mapArticlesUseFullness['MEDIUM'];
    this.myChart.data.datasets[0].data[2] = this.mapArticlesUseFullness['HIGH'];

    this.myChart.update();
  }

  setMapClone() {
    this.mapArticlesClone['LOW'] = this.mapArticlesUseFullness['LOW'];
    this.mapArticlesClone['MEDIUM'] = this.mapArticlesUseFullness['MEDIUM'];
    this.mapArticlesClone['HIGH'] = this.mapArticlesUseFullness['HIGH'];
  }

  setChart() {
    this.myChart = new Chart(this.chartId, {
      type: this.type,
      data: {
        labels: ['LOW', 'MEDIUM', 'HIGH'],
        datasets: [
          {
            label: this.labelName,
            data: [
              this.mapArticlesUseFullness['LOW'],
              this.mapArticlesUseFullness['MEDIUM'],
              this.mapArticlesUseFullness['HIGH'],
            ],
            backgroundColor: [
              'rgba(75, 192, 192, 0.2)',
              'rgba(255, 206, 86, 0.2)',
              'rgba(255, 99, 132, 0.2)',
            ],
            borderColor: [
              'rgba(75, 192, 192, 1)',
              'rgba(255, 206, 86, 1)',
              'rgba(255, 99, 132, 1)',
            ],
            borderWidth: 1,
          },
        ],
      },
      options: {
        plugins: {
          legend: {
            labels: {
              color: 'white',
            },
          },
        },
        scales: {
          y: {
            beginAtZero: true,
            ticks: {
              color: 'white',
            },
          },
          x: {
            beginAtZero: true,
            ticks: {
              color: 'white',
            },
          },
        },
        responsive: true,
      },
    });
  }
}
