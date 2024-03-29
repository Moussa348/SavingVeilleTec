import { Component } from '@angular/core';
import { map } from 'rxjs/operators';
import { Breakpoints, BreakpointObserver } from '@angular/cdk/layout';
import {
  animate,
  state,
  style,
  transition,
  trigger,
} from '@angular/animations';
import { PersonService } from 'src/app/service/person.service';
import { Dashboard } from 'src/app/model/dashboard';
import { getId } from 'src/app/util/jwtUtils';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AddingArticleComponent } from '../adding-article/adding-article.component';
import { ChartType } from 'chart.js/auto';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
  animations: [
    trigger('fade', [
      state('void', style({ opacity: 0 })),

      transition(':enter, :leave', [animate(1000)]),
    ]),
  ],
})
export class DashboardComponent {
  id = getId();
  label = '# of article by usefulness';
  type: ChartType = 'pie';
  chartId = 'articleUtilityChart';
  todayDate = new Date();
  dashboard: Dashboard = new Dashboard();
  url = environment.link;

  constructor(
    private personService: PersonService,
    private modalService: NgbModal,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.getDashboard();
  }

  getDashboard() {
    this.personService.getPersonDashBoard(this.id).subscribe(
      (data) => {
        this.dashboard = data;
        console.log(this.dashboard);
      },
      (err) => {
        console.log(err);
      }
    );
  }

  getArticlesMap(): Map<string, number> {
    return this.dashboard.dailyExpenseDetail.mapArticlesUseFullness;
  }

  isArticlesMapCharged(): boolean {
    return this.dashboard.dailyExpenseDetail.mapArticlesUseFullness.size != 0;
  }

  isListArticlesCharged() {
    return this.dashboard.dailyExpenseDetail.articleDetails.length != 0;
  }

  getListArticles() {
    return this.dashboard.dailyExpenseDetail.articleDetails;
  }

  openAddingArticle() {
    const modalRef = this.modalService.open(AddingArticleComponent, {
      centered: true,
      scrollable: true,
    });

    modalRef.componentInstance.personId = this.dashboard.personDetail.id;

    modalRef.componentInstance.addedArticle.subscribe((addedArticle) => {
      const degreeOfUseFullness = addedArticle.degreeOfUseFullness;

      this.dashboard.dailyExpenseDetail.articleDetails.push(addedArticle);
      this.dashboard.dailyExpenseDetail.articleDetails.reverse();
      this.dashboard.dailyExpenseDetail.total += addedArticle.price;
      this.dashboard.dailyExpenseDetail.mapArticlesUseFullness[
        degreeOfUseFullness
      ] =
        this.dashboard.dailyExpenseDetail.mapArticlesUseFullness[
          degreeOfUseFullness
        ] + addedArticle.qty;
    });
  }

  goToUserSettings(){
    this.router.navigate(['/userSettings']);
  }
}
