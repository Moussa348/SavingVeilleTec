import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthGuardService } from 'src/app/service/auth-guard.service';
import { AuthService } from 'src/app/service/auth.service';
import { getId } from 'src/app/util/jwtUtils';
import {
  animate,
  state,
  style,
  transition,
  trigger,
} from '@angular/animations';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ResetPasswordDialogComponent } from '../reset-password-dialog/reset-password-dialog.component';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css'],
  animations: [
    trigger('fade', [
      state('void', style({ opacity: 0 })),

      transition(':enter, :leave', [animate(1000)]),
    ]),
  ]
})
export class AuthComponent implements OnInit {
  authFormGroup: FormGroup;
  modalRef

  constructor(
    private router:Router,
    private authService:AuthService,
    private authGuardService : AuthGuardService,
    private modalService: NgbModal,
  ) {}

  ngOnInit(): void {
    this.setAuthFormGroup();
  }

  setAuthFormGroup() {
    this.authFormGroup = new FormGroup({
      email: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required),
    });
  }

  
  open() {
    const modalRef = this.modalService.open(ResetPasswordDialogComponent, {
      centered: true,
      scrollable: true,
    });

  }

  onSubmit() {
    if (this.authFormGroup.valid) {
      this.authService
        .login(
          this.authFormGroup.get('email').value,
          this.authFormGroup.get('password').value
        )
        .subscribe(
          (data) => {
            if (data != null) {
             this.authGuardService.login(data);
              this.router.navigate(['/dashboard']);
            } 
          },
          (err) => {
            console.log(err);
          }
        );
    }
  }

  validatePassword(){
    return this.authFormGroup.get('password').value.length >=8;
  }

  validateField(formControlName){
    return this.authFormGroup.get(formControlName).valid;
  }

  isFieldTouched(formControlName){
    return this.authFormGroup.get(formControlName).touched;
  }

  isLoggedIn() {
    return this.authGuardService.isLoggedIn();
  }

  logout() {
    this.authGuardService.logout();
  }

  isFormValid(){
    return this.authFormGroup.valid && this.validatePassword();
  }

  
}
