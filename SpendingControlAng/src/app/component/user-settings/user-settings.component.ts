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
import { PersonService } from 'src/app/service/person.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DisableAccountDialogComponent } from '../disable-account-dialog/disable-account-dialog.component';
import { AuthGuardService } from 'src/app/service/auth-guard.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-settings',
  templateUrl: './user-settings.component.html',
  styleUrls: ['./user-settings.component.css'],
  animations: [
    trigger('fade', [
      state('void', style({ opacity: 0 })),

      transition(':enter, :leave', [animate(1000)]),
    ]),
  ],
})
export class UserSettingsComponent implements OnInit {
  registrationForm: FormGroup;
  selectedFile:File;
  id = getId();

  constructor(
    private personService: PersonService,
    private authGuardService: AuthGuardService,
    private router: Router,
    private modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.setRegistrationForm();
  }

  setRegistrationForm() {
    this.registrationForm = new FormGroup({
      picture: new FormControl('',),
      password: new FormControl('', ),
      passwordAgain: new FormControl('',),
      updatePassword: new FormControl('')
    });
  }

  disableAccount() {
    this.openDisableAccountDialog();
  }

  openDisableAccountDialog() {
    const modalRef = this.modalService.open(DisableAccountDialogComponent, {
      centered: true,
      scrollable: true,
    });

    modalRef.componentInstance.choice.subscribe((choice) => {
      if (choice) {
        this.personService.disableAccount(this.id).subscribe();
        this.authGuardService.logout();
        this.router.navigate(['/authentication']);
      }
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

  onFileChanged($event){
    this.selectedFile = $event.target.files[0];

  }
  
  uploadFile(){
    console.log(this.selectedFile + " " + this.id);
    const formData = new FormData();
    formData.append("multipartFile",this.selectedFile);

    this.personService.setPicture(this.id,formData).subscribe();
  }

  validateField(formControlName) {
    return this.registrationForm.get(formControlName).valid;
  }

  isFieldTouched(formControlName) {
    return this.registrationForm.get(formControlName).touched;
  }

  onSubmit(){
    console.log(this.id);
    this.uploadFile();
  }
}
