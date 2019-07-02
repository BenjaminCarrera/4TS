import { IMunicipio } from 'app/shared/model/municipio.model';

export interface IEstado {
  id?: number;
  estado?: string;
  municipios?: IMunicipio[];
}

export class Estado implements IEstado {
  constructor(public id?: number, public estado?: string, public municipios?: IMunicipio[]) {}
}
