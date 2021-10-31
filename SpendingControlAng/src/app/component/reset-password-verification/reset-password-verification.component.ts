import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { PersonService } from 'src/app/service/person.service';
import {
  animate,
  state,
  style,
  transition,
  trigger,
} from '@angular/animations';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-reset-password-verification',
  templateUrl: './reset-password-verification.component.html',
  styleUrls: ['./reset-password-verification.component.css'],
  animations: [
    trigger('fade', [
      state('void', style({ opacity: 0 })),

      transition(':enter, :leave', [animate(1000)]),
    ]),
  ],
})
export class ResetPasswordVerificationComponent implements OnInit {
  code;
  @Input() email;
  codeInvalid = undefined;
  registrationForm: FormGroup;

  constructor(
    private personService: PersonService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe((myMap: Params) => {
      this.code = myMap.code;
    });
    console.log(this.code);
    this.setRegistrationForm();
  }

  setRegistrationForm() {
    this.registrationForm = new FormGroup({
      password: new FormControl('', Validators.required),
      passwordAgain: new FormControl('', Validators.required),
    });
  }

  verifyCode() {
    this.personService.confirmVerificationCode(this.code).subscribe(
      () => {
        this.codeInvalid = false;
        console.log(localStorage.getItem('email'));

        if (!this.codeInvalid) {
          this.personService
            .setPasswordEmail(
              localStorage.getItem('email').toString(),
              this.registrationForm.get('password').value
            )
            .subscribe(
              () => {
                this.router.navigate(['/authentificaiton']);
              },
              (err) => {
                this.codeInvalid = true;
              }
            );
        }
      },
      (err) => {
        //redirect to a page that says error404
        this.codeInvalid = true;
        console.log(this.codeInvalid);
        console.log(err);
      }
    );
  }

  validateField(formControlName) {
    return this.registrationForm.get(formControlName).valid;
  }

  isFieldTouched(formControlName) {
    return this.registrationForm.get(formControlName).touched;
  }

  onSubmit() {
    if(this.registrationForm.valid){
      this.verifyCode();
    }
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
}
