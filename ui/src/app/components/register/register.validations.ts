import { create, enforce, test } from 'vest';
import { ReigstrationModel } from '../../models';

export const registrationSuite = create(
  'registrationSuite',
  (data: ReigstrationModel) => {
    test('username', 'Username is required', () => {
      enforce(data.username).isNotBlank();
    });

    test('password', 'Password is required', () => {
      enforce(data.password).isNotBlank();
    });

    test('password', 'Password must be at least 8 characters', () => {
      enforce(data.password).longerThan(8);
    });

    test('email', 'Email is required', () => {
      enforce(data.email).isNotBlank();
    });

    test('email', 'Email must be a valid email address', () => {
      enforce(data.email).matches(/^[^\s@]+@[^\s@]+\.[^\s@]+$/);
    });

    test('stripeCustomerId', 'Stripe Customer ID is required', () => {
      enforce(data.stripeCustomerId).isNotBlank();
    });
  },
);
