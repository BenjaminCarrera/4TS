export interface ISkillCandidato {
  id?: number;
  calificacionSkill?: number;
  idCandidatoId?: number;
  idSkillId?: number;
  nivelSkillId?: number;
}

export class SkillCandidato implements ISkillCandidato {
  constructor(
    public id?: number,
    public calificacionSkill?: number,
    public idCandidatoId?: number,
    public idSkillId?: number,
    public nivelSkillId?: number
  ) {}
}
