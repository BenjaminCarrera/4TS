export interface IReferenciasLaborales {
  id?: number;
  empresa?: string;
  nombreContacto?: string;
  puestoContacto?: string;
  emailContaco?: string;
  telefonoContacto?: string;
  candidatoNombre?: string;
  candidatoId?: number;
}

export class ReferenciasLaborales implements IReferenciasLaborales {
  constructor(
    public id?: number,
    public empresa?: string,
    public nombreContacto?: string,
    public puestoContacto?: string,
    public emailContaco?: string,
    public telefonoContacto?: string,
    public candidatoNombre?: string,
    public candidatoId?: number
  ) {}
}
