import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { SignUp } from '../../models/signup.interface';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { AxiosService } from '../../services/axios/axios.service';
import { SmitResponse } from '../../models/smit-response.interface';
import { NgIf } from '@angular/common';
import { confirmPasswordValidator } from '../../validators/password-match.validator';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [RouterLink, FormsModule, NgIf, ReactiveFormsModule],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css'
})
export class SignupComponent {
  signUpData: SignUp = {
    firstName: null,
    lastName: null,
    email: null,
    password: null,
    passwordRepeat: null
  }

  smitResponse: SmitResponse = {
    success: null,
    httpStatus: null,
    message: null,
    data: null
  }

  registerForm!: FormGroup;

  constructor(
    private axiosService: AxiosService, 
    private fb: FormBuilder
  ) {
    this.registerForm = this.fb.group({
      firstName: [this.signUpData.firstName, Validators.required],
      lastName: [this.signUpData.lastName, Validators.required],
      email: [this.signUpData.email, [Validators.required, Validators.email]],
      password: [this.signUpData.password, Validators.required],
      passwordRepeat: [this.signUpData.passwordRepeat, Validators.required],
    },
    {
        validators: confirmPasswordValidator
    })
  }

  ngOnInit() {

  }

  register() {
    this.axiosService.request(
      "POST",
      "auth/register",
      this.signUpData
    ).then(
      (response) => {
        this.smitResponse = response.data;
      }
    );
  }
}
