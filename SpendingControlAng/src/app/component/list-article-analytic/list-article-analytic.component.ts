import { DatePipe } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { Article } from 'src/app/model/article';

@Component({
  selector: 'app-list-article-analytic',
  templateUrl: './list-article-analytic.component.html',
  styleUrls: ['./list-article-analytic.component.css']
})
export class ListArticleAnalyticComponent implements OnInit {
  filter = new FormControl('');
  @Input() articleDetails :Article[] = new Array();
  filteredArticles$: Observable<Article[]>;

  constructor(private datePipe:DatePipe) { 
    this.filteredArticles$ = this.filter.valueChanges.pipe(
      startWith(''),
      map((text) => this.search(text, datePipe))
    );
  }

  ngOnInit(): void {
    console.log(this.articleDetails);
  }

  search(text: string, datePipe: DatePipe): Article[] {
    return this.articleDetails.filter((article) => {
      const term = text.toLowerCase();
      return (
        article.name.toLowerCase().includes(term) ||
        article.time.toLowerCase().includes(term) ||
        article.qty.toString().includes(term) ||
        article.price.toString().includes(term) ||
        article.degreeOfUseFullness.toString().includes(term)
      );
    });
  }

}
