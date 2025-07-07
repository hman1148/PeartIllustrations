// API endpoint constants
export const Apis = {
  Auth: '/api/auth',
  Users: '/api/users'
} as const;

export const AuthPages = {
  Login: '/login',
  Logout: '/logout'
} as const;

export const UserPages = {
  Me: '/me',
  Create: '',
  Update: '', // Will be used with /{id}
  Delete: '' // Will be used with /{id}
} as const;
