export interface IEstatusReqCanRec {
  id?: number;
  motivo?: string;
  estatusReqCanId?: number;
}

export class EstatusReqCanRec implements IEstatusReqCanRec {
  constructor(public id?: number, public motivo?: string, public estatusReqCanId?: number) {}
}
