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
  actAdmin?: boolean,
  public requirements?: number,
  actRequirements?: boolean,
  public candidates?: number,
  actCandidates?: boolean,
  public entity?: number,
  actEntity?: boolean
  ) {}
}
