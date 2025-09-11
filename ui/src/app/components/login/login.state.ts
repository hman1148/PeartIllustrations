import { create } from 'vest';
import { LoginRequest } from '../../models';
import { loginSuite } from './login.validations';

export type LoginState = {
  loginData: LoginRequest;
  suite: ReturnType<typeof create>;
};

export const initialLoginState = (): LoginState => ({
  loginData: {
    username: '',
    password: '',
  },
  suite: loginSuite,
});
