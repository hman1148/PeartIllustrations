import {
  initialUserCreateRequest,
  LoginRequest,
  User,
  UserCreateRequest,
} from '../../models/user.models';

export type UserStoreState = {
  isLoading: boolean;
  currentUser: User | null;
  currentLoginRequest: LoginRequest | null;
  currentRegistrationRequest: UserCreateRequest | null;
};

export const initialUserStoreState = (): UserStoreState => ({
  isLoading: false,
  currentUser: null,
  currentLoginRequest: null,
  currentRegistrationRequest: initialUserCreateRequest(),
});
