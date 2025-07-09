export type ReigstrationModel = {
  username: string;
  password: string;
  email: string;
  stripeCustomerId: string;
  role?: string; // Optional, backend defaults to "USER"
};

export const initialRegistrationModel = (): ReigstrationModel => ({
  username: '',
  password: '',
  email: '',
  stripeCustomerId: '',
  role: 'USER', // Default role
});
