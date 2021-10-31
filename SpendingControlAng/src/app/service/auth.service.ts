import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  url = "http://localhost:4444/auth"

  constructor(private http: HttpClient) { }

  login(email:string,password:string){
    const params = new HttpParams()
    .append("email",email)
    .append("password",password);
    let options ={responseType: 'text'}
    return this.http.get(this.url + "/login",{params:params,responseType:'text'})
  }

  resetPassword(email){
    return this.http.get(this.url + '/resetPassword/' + email);
  }
}
