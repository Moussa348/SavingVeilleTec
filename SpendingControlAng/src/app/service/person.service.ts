import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Dashboard } from '../model/dashboard';
import { Person } from '../model/person';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PersonService {
  url = `${environment.link}/person/`;

  constructor(private http:HttpClient) { }

  createPerson(person){
    return this.http.post<boolean>(this.url + 'createPerson',person);
  }

  setPicture(id,file){
    const params = new HttpParams().set("id",id).set("multipartFile",file);
    let options = {params:params};

    return this.http.patch(this.url + "setPicture/",options);
  }

  setPasswordWithId(id,password){
    const params = new HttpParams().set("id",id).set("password",password);
    const options = {params:params};
    return this.http.patch(this.url + 'setPasswordWithId',options);
  }

  setPasswordEmail(email,password){
    const params = new HttpParams().set("email",email).set("password",password);
    const options = {params:params};
    return this.http.patch(this.url + 'setPasswordWithEmail',options);
  }

  confirmVerificationCode(verificationCode){
     return this.http.patch(this.url + "confirmVerificationCode/" + verificationCode,"");
  }

  disableAccount(id){
    return this.http.patch(this.url + 'disableAccount/' + id,'');
  }

  getPersonDashBoard(id){
    return this.http.get<Dashboard>(this.url + 'getPersonDashBoard/' + id);
  }

}
