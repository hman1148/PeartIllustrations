// User related interfaces
export type User = {
  id?: number;
  username: string;
  email: string;
  role: string;
  stripeCustomerId?: string;
  createdAt?: string;
  updatedAt?: string;
};

export const initialUser = (): User => ({
  id: undefined,
  username: '',
  email: '',
  role: 'USER', // Default role
  stripeCustomerId: undefined,
  createdAt: undefined,
  updatedAt: undefined,
});

export type UserCreateRequest = {
  username: string;
  password: string;
  email: string;
  role?: string; // Will default to "USER" on backend
  stripeCustomerId?: string;
};

// Login related interfaces
export type LoginRequest = {
  username: string;
  password: string;
};

export const initialLoginRequest = (): LoginRequest => ({
  username: '',
  password: '',
});

export interface LoginResponse {
  token: string;
  user: User;
}
// Auth related types
export interface AuthState {
  isAuthenticated: boolean;
  user: User | null;
  token: string | null;
}
