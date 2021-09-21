import { Component, Input, OnInit } from '@angular/core';
import Chart, { ChartType } from 'chart.js/auto';
import { Article } from 'src/app/model/article';

@Component({
  selector: 'app-article-price-chart',
  templateUrl: './article-price-chart.component.html',
  styleUrls: ['./article-price-chart.component.css']
})
export class ArticlePriceChartComponent implements OnInit {
  @Input() labelName = "";
  @Input() chartId = "";
  @Input() type:ChartType = "line";
  @Input() mapArticles:Map<string,Article> = new Map();
   myChart;
  constructor() { }

  ngOnInit(): void {
    console.log(this.chartId);
    console.log(this.type);
  }

  ngOnDestroy(): void {
    this.myChart.destroy();
    
  }

  ngAfterViewInit(): void {
    //Called after ngAfterContentInit when the component's view has been initialized. Applies to components only.
    this.setChart();
    //Add 'implements AfterViewInit' to the class.
  }

  setChart() {
    this.myChart= new Chart(this.chartId, {
       type: this.type,
       data: {
         labels: ['LOW', 'MEDIUM','HIGH'],
         datasets: [
           {
             label: this.labelName,
             data: [this.mapArticles['LOW'].price, this.mapArticles['MEDIUM'].price, this.mapArticles['HIGH'].price],
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
         plugins: {
           legend:{
             labels:{
               color: "white"
             }
           }
         },
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
