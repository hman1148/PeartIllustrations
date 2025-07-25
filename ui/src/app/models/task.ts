import { Member } from './member';

export interface Task {
  id: number;
  name?: string;
  description?: string;
  startDate?: Date;
  endDate?: Date;
  members?: Member[];
  completed?: boolean;
  status?: string;
  comments?: number;
  attachments?: number;
}

export interface DialogConfig {
  visible: boolean;
  header?: string;
  newTask?: boolean;
}
