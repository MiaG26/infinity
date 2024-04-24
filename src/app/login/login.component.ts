import { Component, OnInit} from '@angular/core';
import { Router, ActivatedRoute} from '@angular/router';
import { MessageService } from 'src/services/message.service';
import { UsersService } from 'src/services/users.service';
import { first } from 'rxjs/operators';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CanComponentDeactivate } from 'src/guards/can-deactivate.guard';
import { Observable, of } from 'rxjs';
import { Auth } from 'src/entities/auth';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  providers: [UsersService]
})
export class LoginComponent implements OnInit, CanComponentDeactivate {
  authForm: FormGroup;
  isSubmitted  =  false;
  loading = false;
  private auth: Auth = new Auth();


  constructor(
    private usersService: UsersService, 
    private router: Router,
    private route: ActivatedRoute,
    private formBuilder: FormBuilder,
    private messageService: MessageService ) { }

  ngOnInit() {
    this.authForm = this.formBuilder.group({
      login: ['', Validators.required],
      password: ['', Validators.required]
  });
}
  

  get formControls() { return this.authForm.controls; }

  onSubmit(){
    this.isSubmitted = true;
    if(this.authForm.invalid){
      return;
    }
   // this.usersService.logIn(this.authForm.value);

    this.loading = true;
    this.usersService.logIn(this.authForm.value)
            .pipe(first())
            .subscribe(
                data => {
                    this.messageService.successMessage('Login successful');
                    this.router.navigate(['../dashboard'], { relativeTo: this.route });
                },
                error => {
                    this.messageService.errorMessage(error);
                    this.loading = false;
                });
  }

  canDeactivate(): boolean | Observable<boolean> | Promise<boolean> {
    const canLeave = !(this.auth.login || this.auth.password);
    if (canLeave) return true;
   
    const confirmation = window.confirm(
    'Are you sure to leave? The form is partially filled and will be discarded.');
    return of(confirmation);
    }
   

}


