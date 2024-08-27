import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AxiosService } from '../axios/axios.service';

export const authGuard: CanActivateFn = (route, state) => {
    const token = inject(AxiosService).getAuthToken();
    if (!token) {
      inject(Router).navigate(['login']);
    };

  return true;
};
