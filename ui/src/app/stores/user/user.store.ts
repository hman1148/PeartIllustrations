import { inject } from '@angular/core';
import { patchState, signalStore, withMethods, withState } from '@ngrx/signals';

import { MessageService } from 'primeng/api';

import { UserService } from '../../services';
import { initialUserStoreState } from './user-store.state';

export const UserStore = signalStore(
  { providedIn: 'root' },
  withState(initialUserStoreState()),
  withMethods(
    (
      store,
      userService = inject(UserService),
      messageService = inject(MessageService),
    ) => ({
      resolveUser: async () => {
        if (store.currentUser()) {
          return true;
        }

        patchState(store, { isLoading: true });

        const currentLoginData = store.currentLoginRequest();
      },

      login: () => {
        patchState(store, { isLoading: true });

        const currentLoginData = store.currentLoginRequest();
        userService.login(currentLoginData).subscribe({
          next: ({ item, success }) => {
            if (success) {
              patchState(store, {
                currentUser: item,
              });
            } else {
              messageService.add({
                severity: 'error',
                summary: 'Failed to Login',
              });
            }
          },
          error: ({ message }) => console.error(message),
          complete: () => patchState(store, { isLoading: false }),
        });
      },
    }),
  ),
);
