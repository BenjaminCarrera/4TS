export interface IReqCan {
  id?: number;
  candidatoNombre?: string;
  candidatoId?: number;
  requerimientoProyecto?: string;
  requerimientoId?: number;
  estatusReqCanEstatus?: string;
  estatusReqCanId?: number;
  estatusReqCanRecMotivo?: string;
  estatusReqCanRecId?: number;
}

export class ReqCan implements IReqCan {
  constructor(
    public id?: number,
    public candidatoNombre?: string,
    public candidatoId?: number,
    public requerimientoProyecto?: string,
    public requerimientoId?: number,
    public estatusReqCanEstatus?: string,
    public estatusReqCanId?: number,
    public estatusReqCanRecMotivo?: string,
    public estatusReqCanRecId?: number
  ) {}
}
