import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Apis, AuthPages, UserPages } from '../../constants/api.constants';
import { ItemResponse } from '../../models';
import {
  AuthState,
  LoginRequest,
  LoginResponse,
  User,
  UserCreateRequest,
} from '../../models/user.models';
import { EnvironemntStore } from '../../stores/environment/environment.store';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  readonly env = inject(EnvironemntStore);
  readonly http = inject(HttpClient);

  // Auth state management
  private authState = new BehaviorSubject<AuthState>({
    isAuthenticated: false,
    user: null,
    token: null,
  });

  public authState$ = this.authState.asObservable();

  constructor() {
    this.checkExistingAuth();
  }

  // Auth methods following the clean pattern
  login(loginRequest: LoginRequest): Observable<ItemResponse<User>> {
    const url = this.env.apiUrl() + Apis.Auth + AuthPages.Login;
    return this.http.post<ItemResponse<User>>(url, loginRequest);
  }

  logout(): Observable<ItemResponse<string>> {
    const url = this.env.apiUrl() + Apis.Auth + AuthPages.Logout;
    return this.http.post<ItemResponse<string>>(url, {});
  }

  // User management methods following the clean pattern
  createUser(userRequest: UserCreateRequest): Observable<ItemResponse<User>> {
    const url = this.env.apiUrl() + Apis.Users + UserPages.Create;
    return this.http.post<ItemResponse<User>>(url, userRequest);
  }

  getCurrentUser(): Observable<ItemResponse<User>> {
    const url = this.env.apiUrl() + Apis.Users + UserPages.Me;
    return this.http.get<ItemResponse<User>>(url);
  }

  updateUser(
    userId: number,
    userUpdate: Partial<User>,
  ): Observable<ItemResponse<User>> {
    const url =
      this.env.apiUrl() + Apis.Users + UserPages.Update + '/' + userId;
    return this.http.put<ItemResponse<User>>(url, userUpdate);
  }

  deleteUser(userId: number): Observable<ItemResponse<string>> {
    const url =
      this.env.apiUrl() + Apis.Users + UserPages.Delete + '/' + userId;
    return this.http.delete<ItemResponse<string>>(url);
  }

  // Auth state management helpers
  private setAuthState(loginResponse: LoginResponse): void {
    const newState: AuthState = {
      isAuthenticated: true,
      user: loginResponse.user,
      token: loginResponse.token,
    };

    // Store token in localStorage for persistence
    localStorage.setItem('auth_token', loginResponse.token);
    localStorage.setItem('user', JSON.stringify(loginResponse.user));

    this.authState.next(newState);
  }

  private clearAuthState(): void {
    const newState: AuthState = {
      isAuthenticated: false,
      user: null,
      token: null,
    };

    localStorage.removeItem('auth_token');
    localStorage.removeItem('user');

    this.authState.next(newState);
  }

  private checkExistingAuth(): void {
    const token = localStorage.getItem('auth_token');
    const userStr = localStorage.getItem('user');

    if (token && userStr) {
      try {
        const user = JSON.parse(userStr);
        this.authState.next({
          isAuthenticated: true,
          user: user,
          token: token,
        });
      } catch (error) {
        console.error('Error parsing stored user data:', error);
        this.clearAuthState();
      }
    }
  }

  // Public getters for current auth state
  getToken(): string | null {
    return this.authState.value.token;
  }

  getCurrentAuthUser(): User | null {
    return this.authState.value.user;
  }

  isAuthenticated(): boolean {
    return this.authState.value.isAuthenticated;
  }
}
