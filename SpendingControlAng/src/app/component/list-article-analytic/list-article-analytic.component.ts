import { DatePipe } from '@angular/common';
import { ChangeDetectionStrategy, ChangeDetectorRef, Component, Input, OnInit, SimpleChanges } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Observable, of, ReplaySubject, Subject } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { Article } from 'src/app/model/article';
import { DataExchangerService } from 'src/app/service/data-exchanger.service';

@Component({
  selector: 'app-list-article-analytic',
  templateUrl: './list-article-analytic.component.html',
  styleUrls: ['./list-article-analytic.component.css'],
})
export class ListArticleAnalyticComponent implements OnInit {
  filter = new FormControl('');
  @Input() articleDetails: Article[] = new Array();
  filteredArticles$: Observable<Article[]>;
  filteredArticles2$: ReplaySubject<Article[]>;
  initialSize ;

  constructor(
    private datePipe: DatePipe,
    private dataExchangerService: DataExchangerService,
  ) {
    this.initialSize = this.articleDetails.length;
    this.filteredArticles$ =this.filterInList('');
  }

  ngOnInit(): void {
  }

  ngDoCheck(): void {
    //Called every time that the input properties of a component or a directive are checked. Use it to extend change detection by performing a custom check.
    //Add 'implements DoCheck' to the class.
    if(this.initialSize < this.articleDetails.length){
      console.log(this.articleDetails);
      this.filteredArticles$ = this.filterInList('');
    }
  }


  filterInList(string){
    return this.filter.valueChanges.pipe(
      startWith(string),
      map((text) => this.search(text))
    );
  }

  search(text: string): Article[] {
    return this.articleDetails.filter((article) => {
      const term = text.toLowerCase();
      return (
        article.name.toLowerCase().includes(term) ||
        article.time.toLowerCase().includes(term) ||
        article.qty.toString().includes(term) ||
        article.price.toString().includes(term) ||
        article.degreeOfUseFullness.toLowerCase().includes(term)
      );
    });
  }
}
