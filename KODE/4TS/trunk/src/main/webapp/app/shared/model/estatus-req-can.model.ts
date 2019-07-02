import { IEstatusReqCanRec } from 'app/shared/model/estatus-req-can-rec.model';

export interface IEstatusReqCan {
  id?: number;
  estatus?: string;
  estatusReqCanRecs?: IEstatusReqCanRec[];
}

export class EstatusReqCan implements IEstatusReqCan {
  constructor(public id?: number, public estatus?: string, public estatusReqCanRecs?: IEstatusReqCanRec[]) {}
}
