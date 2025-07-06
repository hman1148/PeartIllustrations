export type Response = {
  success: boolean;
  message: string;
};

export type ItemResponse<T> = Response & {
  item: T;
};

export type ItemsResponse<T> = Response & {
  items: T[];
};
