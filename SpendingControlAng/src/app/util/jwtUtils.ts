import { STORAGE_KEY } from "./constant";
import decode from 'jwt-decode';

export function getId(){
    return sessionStorage.getItem(STORAGE_KEY) != null ? decode<any>(sessionStorage.getItem(STORAGE_KEY)).personId:null;
}