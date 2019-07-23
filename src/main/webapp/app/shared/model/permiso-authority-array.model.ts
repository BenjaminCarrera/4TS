import { Moment } from 'moment';

export interface IArrePermisoAuthority {
  authority?: string;
  admin?: number;
  actAdmin?: boolean;
  requirements?: number;
  actRequirements?: boolean;
  candidates?: number;
  actCandidates?: boolean;
  entity?: number;
  actEntity?: boolean;
}

export class ArrePermisoAuthority implements IArrePermisoAuthority {
  constructor(
  public authority?: string,
  public admin?: number,
  public requirements?: number,
  public candidates?: number,
  public entity?: number
  ) {}
}
