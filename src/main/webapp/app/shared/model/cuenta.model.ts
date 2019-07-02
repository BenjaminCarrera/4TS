import { ICandidato } from 'app/shared/model/candidato.model';

export interface ICuenta {
  id?: number;
  clave?: string;
  nombre?: string;
  candidatoInteres?: ICandidato[];
  candidatoRechazadas?: ICandidato[];
}

export class Cuenta implements ICuenta {
  constructor(
    public id?: number,
    public clave?: string,
    public nombre?: string,
    public candidatoInteres?: ICandidato[],
    public candidatoRechazadas?: ICandidato[]
  ) {}
}
