import { Component, Input } from '@angular/core';

@Component({
  standalone: true,
  selector: 'app-auth-logo',
  imports: [],
  templateUrl: './auth-logo.html',
  styleUrl: './auth-logo.scss',
})
export class AuthLogo {
  @Input() className: string = '';
}
