export interface ISkillRequerimiento {
  id?: number;
  idRequerimientoId?: number;
  idSkillId?: number;
  tipoSkillId?: number;
}

export class SkillRequerimiento implements ISkillRequerimiento {
  constructor(public id?: number, public idRequerimientoId?: number, public idSkillId?: number, public tipoSkillId?: number) {}
}
