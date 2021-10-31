import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { AuthService } from 'src/app/service/auth.service';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password-dialog.component.html',
  styleUrls: ['./reset-password-dialog.component.css'],
})
export class ResetPasswordDialogComponent implements OnInit {
  authFormGroup: FormGroup;
  hasBeenSent = undefined;
  status = 'nothing';

  constructor(
    public activeModal: NgbActiveModal,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.setAuthFormGroup();
  }

  setAuthFormGroup() {
    this.authFormGroup = new FormGroup({
      email: new FormControl('',[Validators.required, Validators.email]),
    });
  }

  sendConfirmation() {
    this.activeModal.close();
  }

  validateField(formControlName) {
    return this.authFormGroup.get(formControlName).valid;
  }

  isFieldTouched(formControlName) {
    return this.authFormGroup.get(formControlName).touched;
  }

  onSubmit() {
    if (this.authFormGroup.valid) {
      this.status = 'pending';
      this.authService
        .resetPassword(this.authFormGroup.get('email').value)
        .subscribe(
          () => {
            this.status = 'nothing';
            this.hasBeenSent = true;
            localStorage.setItem('email',this.authFormGroup.get('email').value);
            setTimeout(() => this.activeModal.close(), 1000);
          },
          (err) => {
            console.log(err);
            this.status = 'nothing';
            this.hasBeenSent = false;
            setTimeout(() => this.activeModal.close(), 1000);
          }
        );
    }
  }
}
