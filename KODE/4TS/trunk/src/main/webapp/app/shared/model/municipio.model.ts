import { IColonia } from 'app/shared/model/colonia.model';
import { ICodigoPostal } from 'app/shared/model/codigo-postal.model';

export interface IMunicipio {
  id?: number;
  municipio?: string;
  colonias?: IColonia[];
  estadoId?: number;
  codigoPostals?: ICodigoPostal[];
}

export class Municipio implements IMunicipio {
  constructor(
    public id?: number,
    public municipio?: string,
    public colonias?: IColonia[],
    public estadoId?: number,
    public codigoPostals?: ICodigoPostal[]
  ) {}
}
