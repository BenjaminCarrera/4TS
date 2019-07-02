import { IColonia } from 'app/shared/model/colonia.model';
import { IMunicipio } from 'app/shared/model/municipio.model';

export interface ICodigoPostal {
  id?: number;
  codigoPostal?: string;
  colonias?: IColonia[];
  municipios?: IMunicipio[];
}

export class CodigoPostal implements ICodigoPostal {
  constructor(public id?: number, public codigoPostal?: string, public colonias?: IColonia[], public municipios?: IMunicipio[]) {}
}
