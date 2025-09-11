import { create, enforce, test } from 'vest';
import { LoginRequest } from '../../models';

export const loginSuite = create('loginSuite', (data: LoginRequest) => {
  test('username', 'Username is required', () => {
    enforce(data.username).isNotBlank();
  });

  test('password', 'Password is required', () => {
    enforce(data.password).isNotBlank();
  });
});
