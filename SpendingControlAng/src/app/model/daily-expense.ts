import { Article } from "./article";

export class DailyExpense {
    id:number;
    date:string;
    total:number;
    articleDetails:Article[] = new Array();
    mapArticlesUseFullness:Map<string,number> = new Map();
}
