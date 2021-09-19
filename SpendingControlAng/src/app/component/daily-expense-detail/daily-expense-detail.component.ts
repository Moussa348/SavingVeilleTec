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
  dailyExpense : DailyExpense = new DailyExpense();
  model: NgbDate = this.calendar.getToday();
  date: NgbDate;

  constructor(
    private dailyExpenseService : DailyExpenseService,
    private calendar: NgbCalendar,
    private datePipe:DatePipe
  ) { }

  ngOnInit(): void {
    this.getDailyExpenseDetail(this.model);
  }
  selectToday() {
    this.model = this.calendar.getToday();
  }

  getDailyExpenseDetail(ngbDate:NgbDate){
   const date = this.datePipe.transform(
      new Date(ngbDate.year,ngbDate.month-1,ngbDate.day),
      'yyyy-MM-dd'
    );
    this.dailyExpenseService.getDailyExpenseByDateForPerson(this.id,date).subscribe(
      (data) =>{
        this.dailyExpense = data;
        console.log(this.dailyExpense);
      },(err) =>{
        console.log(err);
      }
    );
   console.log(date);
  }

}
