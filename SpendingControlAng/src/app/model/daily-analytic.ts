import { Article } from "./article";

export class DailyAnalytic {
    mostExpensiveArticlesByUseFullness:Map<string,Article> = new Map();
    lessExpensiveArticlesByUseFullness:Map<string,Article> = new Map();
    mostExpensiveArticle: Article = new Article();
    lessExpensiveArticle: Article = new Article();
    totalByUseFullness:Map<string,number> = new Map();
    totalPrice:number;
}
