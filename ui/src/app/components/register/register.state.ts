import { create } from 'vest';
import { initialRegistrationModel, ReigstrationModel } from '../../models';
import { registrationSuite } from './register.validations';

export type RegisterState = {
  registrationData: ReigstrationModel;
  suite: ReturnType<typeof create>;
};

export const initialRegisterState = (): RegisterState => ({
  registrationData: initialRegistrationModel(),
  suite: registrationSuite,
});
