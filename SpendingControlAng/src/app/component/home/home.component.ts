import { Component, OnInit } from '@angular/core';
import { isTokenExpired } from 'src/app/util/jwtUtils';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
    console.log(isTokenExpired());
  }

}
