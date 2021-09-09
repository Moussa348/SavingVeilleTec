import { Component, Input, OnInit } from '@angular/core';
import Chart from 'chart.js/auto';

@Component({
  selector: 'app-artilce-utility-chart',
  templateUrl: './artilce-utility-chart.component.html',
  styleUrls: ['./artilce-utility-chart.component.css'],
})
export class ArtilceUtilityChartComponent implements OnInit {
  @Input() mapArticlesUseFullness:Map<string,number> = new Map();

  constructor() {}

  ngOnInit(): void {
    console.log(this.mapArticlesUseFullness);
    this.setChart();
  }

  setChart() {
    new Chart("articleUtilityChart", {
      type: 'bar',
      data: {
        labels: ['LOW', 'MEDIUM','HIGH'],
        datasets: [
          {
            label: '# of article by usefulness',
            data: [this.mapArticlesUseFullness['LOW'], this.mapArticlesUseFullness['MEDIUM'], this.mapArticlesUseFullness['HIGH']],
            backgroundColor: [
              'rgba(75, 192, 192, 0.2)',
              'rgba(255, 206, 86, 0.2)',
              'rgba(255, 99, 132, 0.2)'
            ],
            borderColor: [
              'rgba(75, 192, 192, 1)',
              'rgba(255, 206, 86, 1)',
              'rgba(255, 99, 132, 1)'
            ],
            borderWidth: 1,
          },
        ],
      },
      options: {
        scales: {
          y: {
            beginAtZero: true,
            ticks:{
              color:"white"
            }
          },
          x:{
            beginAtZero:true,
            ticks:{
              color:"white"
            }
          }
        },
        responsive:true
      },
    });
  }
}
