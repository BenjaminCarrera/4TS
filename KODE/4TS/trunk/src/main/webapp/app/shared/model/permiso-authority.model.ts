import { Moment } from 'moment';

export interface IPermisoAuthority {
  id?: number;
  authority?: string;
  activated?: boolean;
  deleted?: boolean;
  createdBy?: string;
  createdDate?: Moment;
  lastModifiedBy?: string;
  lastModifiedDate?: Moment;
  permisoNombre?: string;
  permisoId?: number;
}

export class PermisoAuthority implements IPermisoAuthority {
  constructor(
    public id?: number,
    public authority?: string,
    public activated?: boolean,
    public deleted?: boolean,
    public createdBy?: string,
    public createdDate?: Moment,
    public lastModifiedBy?: string,
    public lastModifiedDate?: Moment,
    public permisoNombre?: string,
    public permisoId?: number
  ) {
    this.activated = this.activated || false;
    this.deleted = this.deleted || false;
  }
}
