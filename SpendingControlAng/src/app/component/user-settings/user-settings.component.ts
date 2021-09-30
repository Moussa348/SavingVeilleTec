import { Component, OnInit } from '@angular/core';
import {
  animate,
  state,
  style,
  transition,
  trigger,
} from '@angular/animations';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { getId } from 'src/app/util/jwtUtils';

@Component({
  selector: 'app-user-settings',
  templateUrl: './user-settings.component.html',
  styleUrls: ['./user-settings.component.css'],
  animations: [
    trigger('fade', [
      state('void', style({ opacity: 0 })),

      transition(':enter, :leave', [animate(1000)]),
    ]),
  ]
})
export class UserSettingsComponent implements OnInit {
  registrationForm: FormGroup;
  id = getId();
  constructor() { }

  ngOnInit(): void {
    this.setRegistrationForm();
  }

  setRegistrationForm() {
    this.registrationForm = new FormGroup({
      email: new FormControl('', [Validators.required, Validators.email]),
      firstName: new FormControl('', Validators.required),
      lastName: new FormControl('', Validators.required),
      password: new FormControl('', [Validators.required]),
      passwordAgain: new FormControl('', [Validators.required]),
    });
  }

  validateSamePassword() {
    const password = this.registrationForm.get('password').value;
    const passwordAgain = this.registrationForm.get('passwordAgain').value;

    return (
      passwordAgain != '' &&
      passwordAgain != '' &&
      password == passwordAgain &&
      password.length >= 8 &&
      password.length <= 40 &&
      passwordAgain.length >= 8 &&
      passwordAgain.length <= 40
    );
  }

  validateField(formControlName) {
    return this.registrationForm.get(formControlName).valid;
  }

  isFieldTouched(formControlName) {
    return this.registrationForm.get(formControlName).touched;
  }

}
