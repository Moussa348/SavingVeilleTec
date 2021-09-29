import { DatePipe } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { Article } from 'src/app/model/article';
import { Observable } from 'rxjs';
import { FormControl } from '@angular/forms';
import { map, startWith } from 'rxjs/operators';

@Component({
  selector: 'app-list-article-detail',
  templateUrl: './list-article-detail.component.html',
  styleUrls: ['./list-article-detail.component.css']
})
export class ListArticleDetailComponent implements OnInit {
  @Input() articleDetails :Article[] = new Array();
  filter = new FormControl('');


  constructor( private datePipe:DatePipe) {
   }

  ngOnInit(): void {
    console.log(this.articleDetails)
  }



  isListArticlesCharged(){
    return this.articleDetails.length != 0;
  }

}
