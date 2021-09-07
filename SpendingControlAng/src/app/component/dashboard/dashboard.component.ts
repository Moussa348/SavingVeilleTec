import { Component } from '@angular/core';
import { map } from 'rxjs/operators';
import { Breakpoints, BreakpointObserver } from '@angular/cdk/layout';
import {
  animate,
  state,
  style,
  transition,
  trigger,
} from '@angular/animations';
import { PersonService } from 'src/app/service/person.service';
import { Dashboard } from 'src/app/model/dashboard';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
  animations: [
    trigger('fade', [
      state('void', style({ opacity: 0 })),

      transition(':enter, :leave', [animate(1000)]),
    ]),
  ]
})
export class DashboardComponent {
  /** Based on the screen size, switch from standard to one column per row */
  id=1;
  dashboard: Dashboard = new Dashboard();
  constructor(private personService:PersonService) {}

  ngOnInit(): void {
    //Called after the constructor, initializing input properties, and the first call to ngOnChanges.
    //Add 'implements OnInit' to the class.
    
  }

  getDashboard(){
    this.personService.getPersonDashBoard(this.id).subscribe(
      (data) =>{
        this.dashboard = data;
        console.log(this.dashboard);
      },(err)=>{
        console.log(err);
      }
    )
  }
}
