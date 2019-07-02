import { ITarea } from 'app/shared/model/tarea.model';

export interface IEstatusTarea {
  id?: number;
  estatus?: string;
  tareas?: ITarea[];
}

export class EstatusTarea implements IEstatusTarea {
  constructor(public id?: number, public estatus?: string, public tareas?: ITarea[]) {}
}
