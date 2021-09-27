import { Injectable } from '@angular/core';
import { Article } from '../model/article';

@Injectable({
  providedIn: 'root'
})
export class DataExchangerService {
  articles:Article[]

  constructor() { }

  setArticles(articles){
    this.articles = articles;
  }

  getArticles(){
    return this.articles;
  }
}
