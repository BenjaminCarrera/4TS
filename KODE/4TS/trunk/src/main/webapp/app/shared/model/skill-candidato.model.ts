export interface ISkillCandidato {
  id?: number;
  calificacionSkill?: number;
  idCandidatoNombre?: string;
  idCandidatoId?: number;
  idSkillNombre?: string;
  idSkillId?: number;
  nivelSkillDominio?: string;
  nivelSkillId?: number;
}

export class SkillCandidato implements ISkillCandidato {
  constructor(
    public id?: number,
    public calificacionSkill?: number,
    public idCandidatoNombre?: string,
    public idCandidatoId?: number,
    public idSkillNombre?: string,
    public idSkillId?: number,
    public nivelSkillDominio?: string,
    public nivelSkillId?: number
  ) {}
}
