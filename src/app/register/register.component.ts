import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { MessageService } from 'src/services/message.service';
import { UsersService } from 'src/services/users.service';
import { first } from 'rxjs/operators';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  registerForm: FormGroup;
    loading = false;
    submitted = false;

    constructor(
        private formBuilder: FormBuilder,
        private route: ActivatedRoute,
        private router: Router,
        private usersService: UsersService,
        private messageService: MessageService
    ) { }

    ngOnInit() {
        this.registerForm = this.formBuilder.group({
            firstName: ['', Validators.required],
            lastName: ['', Validators.required],
            email: ['', [Validators.required, Validators.email]],
            age: ['', Validators.required],
            password: ['', [Validators.required, Validators.minLength(6)]],
            rePassword: ['', [Validators.required, Validators.minLength(6)]]
        },
        [RegisterComponent.matchValidator('password', 'rePassword')]
        );
    }

    // convenience getter for easy access to form fields
    get f() { return this.registerForm.controls; }

    onSubmit() {
        this.submitted = true;

        // stop here if form is invalid
        if (this.registerForm.invalid) {
            return;
        }
        RegisterComponent.matchValidator('password', 'rePassword')
        this.loading = true;
        this.usersService.register(this.registerForm.value)
            .pipe(first())
            .subscribe(
                data => {
                    this.messageService.successMessage('Registration successful');
                    this.router.navigate(['../login'], { relativeTo: this.route });
                },
                error => {
                    this.messageService.errorMessage(error);
                    this.loading = false;
                });
    }

    static matchValidator(source: string, target: string): ValidatorFn {
      return (control: AbstractControl): ValidationErrors | null => {
        const sourceCtrl = control.get(source);
        const targetCtrl = control.get(target);
  
        return sourceCtrl && targetCtrl && sourceCtrl.value !== targetCtrl.value
          ? { mismatch: true }
          : null;
      };
    }

    get passwordMatchError() {
      return (
        this.registerForm.getError('mismatch') &&
        this.registerForm.get('confirmPassword')?.touched
      );
    }
   
}
