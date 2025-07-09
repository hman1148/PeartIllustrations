import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

import { ButtonModule } from 'primeng/button';
import { CheckboxModule } from 'primeng/checkbox';
import { InputTextModule } from 'primeng/inputtext';

import { signalState } from '@ngrx/signals';
import { UserCreateRequest } from '../../models';
import { UserStore } from '../../stores/user/user.store';
import { AppleWidget } from '../auth-extensions/apple-widget/apple-widget';
import { AuthLogo } from '../auth-extensions/auth-logo';
import { GoogleWidget } from '../auth-extensions/google-widget/google-widget';
import { VestFormDirective } from '../common';
import { initialRegisterState } from './register.state';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    CommonModule,
    GoogleWidget,
    AuthLogo,
    ButtonModule,
    InputTextModule,
    CheckboxModule,
    FormsModule,
    RouterModule,
    AppleWidget,
    VestFormDirective,
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss',
})
export class Register {
  readonly state = signalState(initialRegisterState());
  readonly userStore = inject(UserStore);

  onSubmit(event: Event) {
    event.preventDefault();

    const form = event.target as HTMLFormElement;
    const formData = new FormData(form);

    const userCreateRequest: UserCreateRequest = {
      username: formData.get('username') as string,
      password: formData.get('password') as string,
      email: formData.get('email') as string,
      role: 'USER',
    };

    this.userStore.register(userCreateRequest);
  }
}
