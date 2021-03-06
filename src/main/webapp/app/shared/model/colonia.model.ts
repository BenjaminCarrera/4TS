import { ICandidato } from 'app/shared/model/candidato.model';

export interface IColonia {
  id?: number;
  colonia?: string;
  candidatoes?: ICandidato[];
  municipioMunicipio?: string;
  municipioId?: number;
  codigoPostalCodigoPostal?: string;
  codigoPostalId?: number;
}

export class Colonia implements IColonia {
  constructor(
    public id?: number,
    public colonia?: string,
    public candidatoes?: ICandidato[],
    public municipioMunicipio?: string,
    public municipioId?: number,
    public codigoPostalCodigoPostal?: string,
    public codigoPostalId?: number
  ) {}
}
