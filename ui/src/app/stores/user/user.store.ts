import { inject } from '@angular/core';
import { patchState, signalStore, withMethods, withState } from '@ngrx/signals';

import { MessageService } from 'primeng/api';

import { firstValueFrom } from 'rxjs';
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

        const token = localStorage.getItem('token');

        try {
          if (!token) {
            messageService.add({
              severity: 'error',
              summary: 'No Login Token exists',
              detail: 'Please login to get token',
            });
            return false;
          }
          const { item, success } = await firstValueFrom(
            userService.getCurrentUser(token),
          );

          if (success) {
            patchState(store, {
              currentUser: item,
            });
            return true;
          } else {
            messageService.add({
              severity: 'error',
              summary: 'Failed to fetch current user',
              detail: 'Please try again later',
            });
            return false;
          }
        } catch (error) {
          console.error('Error fetching current user:', error);
          messageService.add({
            severity: 'error',
            summary: 'Failed to fetch current user',
            detail: 'Please try again later',
          });
        } finally {
          patchState(store, { isLoading: false });
        }

        return true;
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
