import { Task } from './task';

export interface KanbanCardType {
  id: string;
  title?: string;
  description?: string;
  progress?: number;
  assignees?: Assignee[];
  attachments?: number;
  comments?: KabanComment[];
  startDate?: string;
  dueDate?: string;
  completed?: boolean;
  priority?: Object;
  taskList: TaskList;
}

export interface KanbanListType {
  listId: string;
  title?: string;
  cards: KanbanCardType[];
}

export interface KabanComment {
  id?: string;
  name: string;
  image?: string;
  text: string;
}

export interface ListName {
  listId?: string;
  title: string;
}

export interface TaskList {
  id?: string;
  title: string;
  tasks: Task[];
}

export interface Assignee {
  name: string;
  image: string;
}
