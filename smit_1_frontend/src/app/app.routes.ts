import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { SignupComponent } from './components/signup/signup.component';
import { ForgotPasswordComponent } from './components/forgot-password/forgot-password.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { authGuard } from './services/auth/auth.guard';
import { LendingBooksComponent } from './components/lending-books/lending-books.component';
import { BorrowingBooksComponent } from './components/borrowing-books/borrowing-books.component';

export const routes: Routes = [
    { path: 'signup', component: SignupComponent },
    { path: 'login', component: LoginComponent },
    { path: '', redirectTo: '/login', pathMatch: 'full' },
    { path: 'forgot-password', component: ForgotPasswordComponent },
    { path: 'dashboard', component: DashboardComponent, canActivate: [authGuard] },
    { path: 'dashboard/borrowing-books', component: BorrowingBooksComponent, canActivate: [authGuard] },
    { path: 'dashboard/lending-books', component: LendingBooksComponent, canActivate: [authGuard] }
];
