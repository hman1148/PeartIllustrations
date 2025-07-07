import { signalStore, withMethods, withState } from '@ngrx/signals';
import { initialEnvironmentState } from './environment.state';

export const EnvironemntStore = signalStore(
  { providedIn: 'root' },
  withState(initialEnvironmentState()),
  withMethods((store) => ({
    apiUrl: () => {
      return store.apiUrl();
    },
  })),
);
