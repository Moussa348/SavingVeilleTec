import {
  animate,
  state,
  style,
  transition,
  trigger,
} from '@angular/animations';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.css'],
  animations: [
    // Each unique animation requires its own trigger. The first argument of the trigger function is the name
    trigger('rotatedState', [
      state('default', style({ transform: 'rotate(0)' })),
      state('rotated', style({ transform: 'rotate(2160deg)' })),
      transition('rotated => default', animate('1000ms ease-out')),
      transition('default => rotated', animate('1000ms ease-in')),
    ]),
  ],
})
export class WelcomeComponent implements OnInit {
  state: string = 'default';

  constructor(private router: Router) {}

  ngOnInit(): void {}

  rotate() {
    this.state = this.state === 'default' ? 'rotated' : 'default';
    setTimeout(() => {
      this.router.navigate(['/home']);
    }, 1800);
  }
}
