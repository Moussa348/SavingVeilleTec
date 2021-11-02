import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Article } from '../model/article';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {
  url = `${environment.link}/article/`;

  constructor( private http:HttpClient ) { }

  getListArticleDetailForDailyExperience(id:number,noPage:number){
    const params = new HttpParams().set("id",id.toString()).set("noPage",noPage.toString());
    const options = {params:params};
    return this.http.get<Article[]>(this.url + 'getListArticleDetailForDailyExperience',options);
  }

  getListArticleNameInDailyExpenseByPersonId(id:number){
    return this.http.get<string[]>(this.url + 'getListArticleDetailForDailyExperience/' + id.toString());
  }
}
