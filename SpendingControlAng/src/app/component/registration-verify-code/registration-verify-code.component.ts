import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { PersonService } from 'src/app/service/person.service';

@Component({
  selector: 'app-registration-verify-code',
  templateUrl: './registration-verify-code.component.html',
  styleUrls: ['./registration-verify-code.component.css']
})
export class RegistrationVerifyCodeComponent implements OnInit {
  code;
  codeInvalid = false; 

  constructor(
    private personService:PersonService,
    private route : ActivatedRoute,
    private router:Router
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe((myMap: Params) => {
      this.code = myMap.code;
    });
    console.log(this.code);
    this.verifyCode();
  }


  verifyCode(){
      this.personService.confirmVerificationCode(this.code).subscribe(
        () =>{
            this.router.navigate(['/authentication']);
        },(err) =>{
          //redirect to a page that says error404
          this.codeInvalid =true;
          console.log(this.codeInvalid);
          console.log(err);
        }
      )
  }

  resendVerifyCode(){

  }

}
