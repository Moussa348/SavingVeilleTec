import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { isTokenExpired } from '../util/jwtUtils';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService {

  constructor(private router : Router) { }

  canActivate(route: ActivatedRouteSnapshot) {
    const url = route.url.join('');
    
    if(!this.isLoggedIn() && url != 'home'){
      this.router.navigate(['/authentication']);
      return false;
    }
    
    if(!this.isLoggedIn() && url != 'registration'){
      this.router.navigate(['/authentication']);
      return false;

    }
    if(this.isLoggedIn() && url == 'registration'){
      this.router.navigate(['/home'])
      return false;
    }
    return true;
  }
  
  isLoggedIn() {
    if(sessionStorage.getItem('token')!=null && !isTokenExpired())
      return true;
    this.logout();
    return false;
  }

  login(token){
    sessionStorage.setItem('token',token);
  }

  logout(){
    sessionStorage.clear();
  }
}
