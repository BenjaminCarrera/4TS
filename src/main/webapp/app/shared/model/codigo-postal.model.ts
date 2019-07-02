import { IColonia } from 'app/shared/model/colonia.model';

export interface ICodigoPostal {
  id?: number;
  codigoPostal?: string;
  colonias?: IColonia[];
  municipioId?: number;
}

export class CodigoPostal implements ICodigoPostal {
  constructor(public id?: number, public codigoPostal?: string, public colonias?: IColonia[], public municipioId?: number) {}
}
