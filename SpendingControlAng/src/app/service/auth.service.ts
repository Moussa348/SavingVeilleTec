import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  url = `${environment.link}/auth/`;

  constructor(private http: HttpClient) { }

  login(email:string,password:string){
    const params = new HttpParams()
    .append("email",email)
    .append("password",password);
    let options ={responseType: 'text'}
    return this.http.get(this.url + "login",{params:params,responseType:'text'})
  }

  resetPassword(email){
    return this.http.get(this.url + 'resetPassword/' + email);
  }
}
