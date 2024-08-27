import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { PasswordReminder } from '../../models/password.reminder';
import { AxiosService } from '../../services/axios/axios.service';
import { SmitResponse } from '../../models/smit-response.interface';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-forgot-password',
  standalone: true,
  imports: [RouterLink, FormsModule, NgIf, ReactiveFormsModule],
  templateUrl: './forgot-password.component.html',
  styleUrl: './forgot-password.component.css'
})
export class ForgotPasswordComponent {

  passwordReminder: PasswordReminder = {
    email: null
  }

  smitResponse: SmitResponse = {
    success: null,
    httpStatus: null,
    message: null,
    data: null
  }

  passwordReminderForm!: FormGroup;

  constructor(private axiosService: AxiosService, private fb: FormBuilder) {
    this.passwordReminderForm = this.fb.group({
      email: [this.passwordReminder.email, [Validators.required, Validators.email]],
    })
  }

  sendPasswordReminder() {
    this.axiosService.request(
      "POST",
      "auth/remind_password",
      this.passwordReminder
    ).then(
      (response) => {
        this.smitResponse = response.data;
      }
    );
  }

}
