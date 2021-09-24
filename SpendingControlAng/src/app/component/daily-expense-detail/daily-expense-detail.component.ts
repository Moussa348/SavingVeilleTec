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
import {
  NgbCalendar,
  NgbDate,
  NgbDateStruct,
} from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { DailyAnalytic } from 'src/app/model/daily-analytic';
import { ChartType } from 'chart.js/auto';
import { ArticleService } from 'src/app/service/article.service';
import { Article } from 'src/app/model/article';

@Component({
  selector: 'app-daily-expense-detail',
  templateUrl: './daily-expense-detail.component.html',
  styleUrls: ['./daily-expense-detail.component.css'],
  animations: [
    trigger('fade', [
      state('void', style({ opacity: 0 })),

      transition(':enter, :leave', [animate(1000)]),
    ]),
  ],
})
export class DailyExpenseDetailComponent implements OnInit {
  id = getId();

  noPage = 0;
  articles: Article[] = new Array();

  label = 'Total Price Of Article By Usefulness $';
  label2 = 'Most Expensive Article By Usefulness $';
  label3 = 'Less Expensive Article By Usefulness $';

  type: ChartType = 'bar';

  chartId = 'totalByUseFullnessChart';
  chartId2 = 'mapArticle';

  colors1 = [
    'rgba(255, 99, 71, 0.6)',
    'rgba(255, 167, 0, 0.5)',
    'rgba(17, 16, 17, 0.5)',
    'rgba(255, 99, 71, 1)',
    'rgba(255, 167, 0, 1)',
    'rgba(17, 16, 17, 1)',
  ];
  colors2 = [
    'rgba(120, 0, 255, 0.1)',
    'rgba(255, 167, 0, 0.5)',
    'rgba(17, 16, 17, 0.5)',
    'rgba(120, 0, 255, 1)',
    'rgba(255, 167, 0, 1)',
    'rgba(17, 16, 17, 1)',
  ];

  hasAnAnalytic = true;
  dailyAnalytic: DailyAnalytic = new DailyAnalytic();
  model: NgbDate = this.calendar.getToday();
  date: NgbDate;

  constructor(
    private dailyExpenseService: DailyExpenseService,
    private calendar: NgbCalendar,
    private datePipe: DatePipe,
    private articleService: ArticleService
  ) {}

  ngOnInit(): void {
    this.getDailyAnalytic(this.model);
  }
  selectToday() {
    this.model = this.calendar.getToday();
  }

  getDailyAnalytic(ngbDate: NgbDate) {
    const date = this.datePipe.transform(
      new Date(ngbDate.year, ngbDate.month - 1, ngbDate.day),
      'yyyy-MM-dd'
    );
    this.dailyExpenseService.getDailyAnalytic(this.id, date).subscribe(
      (data) => {
        this.hasAnAnalytic = true;
        this.dailyAnalytic = data;
        console.log(this.dailyAnalytic);
      },
      (err) => {
        this.hasAnAnalytic = false;
        console.log(err);
      }
    );
    console.log(date);
  }

  isDailyAnalyticTotalUseFullnessCharged() {
    return this.dailyAnalytic.totalByUseFullness.size != 0;
  }

  isMostExpensiveArticlesByUseFullnessCharged() {
    return this.dailyAnalytic.mostExpensiveArticlesByUseFullness.size != 0;
  }

  isLEssExpensiveArticlesByUseFullnessCharged() {
    return this.dailyAnalytic.lessExpensiveArticlesByUseFullness.size != 0;
  }

  isDailyAnalyticCharged() {
    return this.dailyAnalytic.totalPrice > 0;
  }

  getListArticles() {
    this.articleService
      .getListArticleDetailForDailyExperience(
        this.dailyAnalytic.id,
        this.noPage
      )
      .subscribe(
        (data) => {
          this.articles = data;
        },
        (err) => {
          console.log(err);
        }
      );
  }

  isArticlesCharged() {
    return this.articles.length != 0;
  }
}
