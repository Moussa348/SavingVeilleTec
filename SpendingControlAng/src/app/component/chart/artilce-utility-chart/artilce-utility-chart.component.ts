import { Component, Input, OnInit } from '@angular/core';
import Chart from 'chart.js/auto';
import { DegreeOfUtility } from 'src/app/model/enum/degree-of-utility.enum';

@Component({
  selector: 'app-artilce-utility-chart',
  templateUrl: './artilce-utility-chart.component.html',
  styleUrls: ['./artilce-utility-chart.component.css'],
})
export class ArtilceUtilityChartComponent implements OnInit {
  @Input() mapArticlesUtility:Map<string,number> = new Map();

  constructor() {}

  ngOnInit(): void {
    console.log(this.mapArticlesUtility);
    this.setChart();
  }

  setChart() {
    new Chart("articleUtilityChart", {
      type: 'pie',
      data: {
        labels: ['LOW', 'MEDIUM','HIGH'],
        datasets: [
          {
            label: '# of Votes',
            data: [this.mapArticlesUtility['LOW'], this.mapArticlesUtility['MEDIUM'], this.mapArticlesUtility['HIGH']],
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
