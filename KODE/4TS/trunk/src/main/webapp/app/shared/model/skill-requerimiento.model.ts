export interface ISkillRequerimiento {
  id?: number;
  idRequerimientoProyecto?: string;
  idRequerimientoId?: number;
  idSkillNombre?: string;
  idSkillId?: number;
  tipoSkillTipo?: string;
  tipoSkillId?: number;
}

export class SkillRequerimiento implements ISkillRequerimiento {
  constructor(
    public id?: number,
    public idRequerimientoProyecto?: string,
    public idRequerimientoId?: number,
    public idSkillNombre?: string,
    public idSkillId?: number,
    public tipoSkillTipo?: string,
    public tipoSkillId?: number
  ) {}
}
