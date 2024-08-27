import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export const confirmPasswordValidator: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {
  const password = control.get('password');
  const passwordRepeat = control.get('passwordRepeat');

  if (password && passwordRepeat && password?.value != passwordRepeat?.value) {
    passwordRepeat.setErrors({ passwordConfirmError: true });
  } else {
    if (passwordRepeat?.hasError('passwordMismatch')) {
      passwordRepeat.setErrors(null);
    }
  }

  return null;
};

