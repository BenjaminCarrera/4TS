import { ISkillCandidato } from 'app/shared/model/skill-candidato.model';

export interface IDominioSkill {
  id?: number;
  dominio?: string;
  skillCandidatoes?: ISkillCandidato[];
}

export class DominioSkill implements IDominioSkill {
  constructor(public id?: number, public dominio?: string, public skillCandidatoes?: ISkillCandidato[]) {}
}
