import { Component, OnInit } from '@angular/core';
import { DailyExpense } from 'src/app/model/daily-expense';
import { DailyExpenseService } from 'src/app/service/daily-expense.service';
import { getId } from 'src/app/util/jwtUtils';
import {
  animate,
  state,
  style,
  transition,
  trigger,
} from '@angular/animations';
import { NgbCalendar, NgbDate, NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { DailyAnalytic } from 'src/app/model/daily-analytic';
import { ChartType } from 'chart.js/auto';

@Component({
  selector: 'app-daily-expense-detail',
  templateUrl: './daily-expense-detail.component.html',
  styleUrls: ['./daily-expense-detail.component.css'],
  animations: [
    trigger('fade', [
      state('void', style({ opacity: 0 })),

      transition(':enter, :leave', [animate(1000)]),
    ]),
  ]
})
export class DailyExpenseDetailComponent implements OnInit {
  id = getId();
  label = "Total Price Of Article By Usefulness $";
  type:ChartType = "bar";
  chartId = "totalByUseFullnessChart";
  chartId2 = "mapArticle";
  hasAnAnalytic = true;
  dailyAnalytic : DailyAnalytic = new DailyAnalytic();
  model: NgbDate = this.calendar.getToday();
  date: NgbDate;

  constructor(
    private dailyExpenseService : DailyExpenseService,
    private calendar: NgbCalendar,
    private datePipe:DatePipe
  ) { }

  ngOnInit(): void {
    this.getDailyAnalytic(this.model);
  }
  selectToday() {
    this.model = this.calendar.getToday();
  }

  getDailyAnalytic(ngbDate:NgbDate){
   const date = this.datePipe.transform(
      new Date(ngbDate.year,ngbDate.month-1,ngbDate.day),
      'yyyy-MM-dd'
    );
    this.dailyExpenseService.getDailyAnalytic(this.id,date).subscribe(
      (data) =>{
        this.hasAnAnalytic = true;
        this.dailyAnalytic = data;
        console.log(this.dailyAnalytic);
      },(err) =>{
        this.hasAnAnalytic = false;
        console.log(err);
      }
    );
   console.log(date);
  }

  isDailyAnalyticTotalUseFullnessCharged(){
    return this.dailyAnalytic.totalByUseFullness.size != 0 ;
  }

  isMostExpensiveArticlesByUseFullnessCharged(){
    return this.dailyAnalytic.mostExpensiveArticlesByUseFullness.size != 0 ;
  }

  isLEssExpensiveArticlesByUseFullnessCharged(){
    return this.dailyAnalytic.lessExpensiveArticlesByUseFullness.size != 0 ;
  }


}
