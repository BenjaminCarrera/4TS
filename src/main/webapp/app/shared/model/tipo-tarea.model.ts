import { ITarea } from 'app/shared/model/tarea.model';

export interface ITipoTarea {
  id?: number;
  tipo?: string;
  tareas?: ITarea[];
}

export class TipoTarea implements ITipoTarea {
  constructor(public id?: number, public tipo?: string, public tareas?: ITarea[]) {}
}
