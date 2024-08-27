import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { AxiosService } from '../../services/axios/axios.service';
import { User } from '../../models/user.interface';
import { SmitResponse } from '../../models/smit-response.interface';
import { Login } from '../../models/login.interface';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import {Router} from "@angular/router"
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [RouterLink, FormsModule, ReactiveFormsModule, NgIf],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  loginData: Login = {
    email: null,
    password: null
  }

  loginForm!: FormGroup;

  user: User = {
    uuid: null,
    firstName: null,
    lastName: null,
    email: null,
    token: null
  }

  smitResponse: SmitResponse = {
    success: null,
    httpStatus: null,
    message: null,
    data: null
  }

  constructor(
    private axiosService: AxiosService, 
    private router: Router,
    private fb: FormBuilder
  ) {
    this.loginForm = this.fb.group({
      email: [this.loginData.email, [Validators.required, Validators.email]],
      password: [this.loginData.password, Validators.required]
    });
  }

  ngOnInit(): void  {

  }

  login() {
    this.axiosService.request(
      "POST",
      "auth/login",
      this.loginData
    ).then(
      (response) => {
        this.smitResponse = response.data;
        
        if (this.smitResponse.success && this.smitResponse.data.token) {
          this.axiosService.setAuthToken(this.smitResponse.data.token);
          this.axiosService.setUserUuid(this.smitResponse.data.uuid);
          this.router.navigate(['/dashboard']);
        } else {
          this.axiosService.setAuthToken(null);
        }
      }
    );
  }
}
