import { Moment } from 'moment';

export interface IPermiso {
  id?: number;
  nombre?: string;
  descripcion?: string;
  activated?: boolean;
  deleted?: boolean;
  createdBy?: string;
  createdDate?: Moment;
  lastModifiedBy?: string;
  lastModifiedDate?: Moment;
}

export class Permiso implements IPermiso {
  constructor(
    public id?: number,
    public nombre?: string,
    public descripcion?: string,
    public activated?: boolean,
    public deleted?: boolean,
    public createdBy?: string,
    public createdDate?: Moment,
    public lastModifiedBy?: string,
    public lastModifiedDate?: Moment
  ) {
    this.activated = this.activated || false;
    this.deleted = this.deleted || false;
  }
}
