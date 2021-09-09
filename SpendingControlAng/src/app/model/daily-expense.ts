
export class DailyExpense {
    id:number;
    date:string;
    total:number;
    mapArticlesUseFullness:Map<string,number> = new Map();
}
