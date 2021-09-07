import { DegreeOfUtility } from "./enum/degree-of-utility.enum";

export class DailyExpense {
    id:number;
    date:string;
    total:number;
    mapArticlesUtility:Map<DegreeOfUtility,number>
}
