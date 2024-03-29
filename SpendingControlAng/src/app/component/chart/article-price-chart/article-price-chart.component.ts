import { Component, Input, OnInit } from '@angular/core';
import Chart, { ChartType } from 'chart.js/auto';
import { Article } from 'src/app/model/article';

@Component({
  selector: 'app-article-price-chart',
  templateUrl: './article-price-chart.component.html',
  styleUrls: ['./article-price-chart.component.css'],
})
export class ArticlePriceChartComponent implements OnInit {
  @Input() labelName = '';
  @Input() chartId = '';
  @Input() type: ChartType = 'line';
  @Input() mapArticles: Map<string, Article> = new Map();
  @Input() colors = [];
  myChart;

  constructor() {}

  ngOnInit(): void {
    console.log(this.colors);
  }
  ngOnDestroy(): void {
    this.myChart.destroy();
  }

  ngAfterViewInit(): void {
    this.setChart();
  }

  setChart() {
    this.myChart = new Chart(this.chartId, {
      type: this.type,
      data: {
        labels: [
          `${this.mapArticles['LOW'].name} «LOW» $`,
          `${this.mapArticles['MEDIUM'].name} «MEDIUM» $`,
          `${this.mapArticles['HIGH'].name} «HIGH» $`,
        ],
        datasets: [
          {
            label: this.labelName,
            data: [
              this.mapArticles['LOW'].price,
              this.mapArticles['MEDIUM'].price,
              this.mapArticles['HIGH'].price,
            ],
            backgroundColor: [this.colors[0], this.colors[1], this.colors[2]],
            borderColor: [this.colors[3], this.colors[4], this.colors[5]],
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
            labels: [
              `${this.mapArticles['LOW'].name} «LOW» $`,
              `${this.mapArticles['MEDIUM'].name} «MEDIUM» $`,
              `${this.mapArticles['HIGH'].name} «HIGH» $`,
            ],
          },
        },
        responsive: true,
      },
    });
  }
}
