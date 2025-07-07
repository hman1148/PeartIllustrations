export type EnvironmentStoreState = {
  apiUrl: string;
  production: boolean;
};

export const initialEnvironmentState = (): EnvironmentStoreState => ({
  apiUrl: 'http://http://app.local',
  production: false,
});
