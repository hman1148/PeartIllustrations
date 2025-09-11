import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { signalState } from '@ngrx/signals';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { AuthLayout } from "../../layout/components/app.authlayout";
import { UserStore } from '../../stores/user/user.store';
import { AppleWidget } from '../auth-extensions/apple-widget/apple-widget';
import { AuthLogo } from '../auth-extensions/auth-logo';
import { GoogleWidget } from '../auth-extensions/google-widget/google-widget';
import { VestFormDirective } from '../common';
import { initialLoginState } from './login.state';

@Component({
  selector: 'app-login',
  imports: [
    CommonModule,
    ButtonModule,
    InputTextModule,
    VestFormDirective,
    RouterModule,
    FormsModule,
    AuthLogo,
    GoogleWidget,
    AppleWidget,
    AuthLayout
],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class Login {
  readonly state = signalState(initialLoginState());
  readonly userStore = inject(UserStore);

  onSubmit(event: Event) {
  }
}
