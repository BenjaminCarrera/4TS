export interface IEstatusReqCanRec {
  id?: number;
  motivo?: string;
  estatusReqCanEstatus?: string;
  estatusReqCanId?: number;
}

export class EstatusReqCanRec implements IEstatusReqCanRec {
  constructor(public id?: number, public motivo?: string, public estatusReqCanEstatus?: string, public estatusReqCanId?: number) {}
}
