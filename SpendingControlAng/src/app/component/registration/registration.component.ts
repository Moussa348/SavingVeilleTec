import { Component, OnInit } from '@angular/core';
import { PersonService } from 'src/app/service/person.service';
import {
  animate,
  state,
  style,
  transition,
  trigger,
} from '@angular/animations';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Person } from 'src/app/model/person';
import { Router } from '@angular/router';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css'],
  animations: [
    trigger('fade', [
      state('void', style({ opacity: 0 })),

      transition(':enter, :leave', [animate(1000)]),
    ]),
  ],
})
export class RegistrationComponent implements OnInit {
  registrationForm: FormGroup;
  person: Person = new Person();
  registered = false;

  constructor(private personService: PersonService, private router: Router) {}

  ngOnInit(): void {
    this.setRegistrationForm();
  }

  setRegistrationForm() {
    this.registrationForm = new FormGroup({
      username: new FormControl('', Validators.required),
      email: new FormControl('', Validators.required),
      firstName: new FormControl('', Validators.required),
      lastName: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required),
      passwordAgain: new FormControl('', Validators.required),
    });
  }

  createPerson() {
    this.person.firstName = this.registrationForm.get('firstName').value;
    this.person.lastName = this.registrationForm.get('lastName').value;
    this.person.email = this.registrationForm.get('email').value;
    this.person.password = this.registrationForm.get('password').value;

    //console.log(this.person);
    console.log(this.person);

    if (this.validateSamePassword() && this.registrationForm.valid) {
      this.personService.createPerson(this.person).subscribe((data) => {
        this.registered = data;
        if (this.registered) {
          this.router.navigate(['/authentication']);
        }
      });
    }
  }

  validateSamePassword() {
    return (
      this.registrationForm.get('password').value ==
      this.registrationForm.get('passwordAgain').value
    );
  }
}
