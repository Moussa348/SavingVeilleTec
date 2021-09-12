import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Dashboard } from '../model/dashboard';
import { Person } from '../model/person';

@Injectable({
  providedIn: 'root'
})
export class PersonService {
  url = "http://localhost:4444/person/"

  constructor(private http:HttpClient) { }

  createPerson(person){
    return this.http.post<boolean>(this.url + 'createPerson',person);
  }

  setPicture(id){
  }

  setPassword(id,password){
    const params = new HttpParams().set("id",id).set("password",password);
    const options = {params:params};
    return this.http.patch(this.url + 'setPassword',options);
  }

  disableAccount(id){
    return this.http.patch(this.url + 'disableAccount/' + id,'');
  }

  getPersonDashBoard(id){
    return this.http.get<Dashboard>(this.url + 'getPersonDashBoard/' + id);
  }

}
