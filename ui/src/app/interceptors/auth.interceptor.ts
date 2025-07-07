import { HttpInterceptorFn, HttpErrorResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';
import { UserService } from '../services/user/user.service';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const userService = inject(UserService);
  const router = inject(Router);

  // Clone the request and add authorization header if token exists
  const token = userService.getToken();
  const authReq = token 
    ? req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      })
    : req;

  return next(authReq).pipe(
    catchError((error: HttpErrorResponse) => {
      // Handle 401 Unauthorized responses
      if (error.status === 401) {
        userService.logout().subscribe({
          complete: () => {
            router.navigate(['/login']);
          }
        });
      }

      // Handle other HTTP errors
      console.error('HTTP Error:', error);
      return throwError(() => error);
    })
  );
};
