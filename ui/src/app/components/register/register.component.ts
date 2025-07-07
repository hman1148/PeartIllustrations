import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

import { ButtonModule } from 'primeng/button';
import { CheckboxModule } from 'primeng/checkbox';
import { InputTextModule } from 'primeng/inputtext';

import { AuthLogo } from '../auth-extensions/auth-logo';
import { GoogleWidget } from '../auth-extensions/google-widget/google-widget';

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
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss',
})
export class Register {}
