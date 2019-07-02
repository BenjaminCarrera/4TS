export interface IReqCan {
  id?: number;
  candidatoId?: number;
  requerimientoId?: number;
  estatusReqCanId?: number;
  estatusReqCanRecId?: number;
}

export class ReqCan implements IReqCan {
  constructor(
    public id?: number,
    public candidatoId?: number,
    public requerimientoId?: number,
    public estatusReqCanId?: number,
    public estatusReqCanRecId?: number
  ) {}
}
