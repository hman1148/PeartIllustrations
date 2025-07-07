import {
  initialLoginRequest,
  initialUser,
  LoginRequest,
  User,
} from '../../models/user.models';

export type UserStoreState = {
  isLoading: boolean;
  currentUser: User;
  currentLoginRequest: LoginRequest;
};

export const initialUserStoreState = (): UserStoreState => ({
  isLoading: false,
  currentUser: initialUser(),
  currentLoginRequest: initialLoginRequest(),
});
