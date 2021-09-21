import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Article } from '../model/article';
import { DailyExpense } from '../model/daily-expense';
import { DailyAnalytic } from '../model/daily-analytic';

@Injectable({
  providedIn: 'root'
})
export class DailyExpenseService {
  url = "http://localhost:4444/dailyExpense/"

  constructor( private http:HttpClient ) { }

  addArticleToDailyExpense(article:Article){
    return this.http.patch(this.url + 'addArticleToDailyExpense',article);
  }

  getDailyExpenseByDateForPerson(id:number,date:string){
    const params = new HttpParams().set("id",id.toString()).set("date",date);
    return this.http.get<DailyExpense>(this.url + 'getDailyExpenseByDateForPerson',{params:params});
  }

  getDailyAnalytic(id:number,date:string){
    const params = new HttpParams().set("id",id.toString()).set("date",date);
    return this.http.get<DailyAnalytic>(this.url + 'getDailyAnalytic',{params:params});
  }
}
