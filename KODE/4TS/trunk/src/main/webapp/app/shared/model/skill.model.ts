import { ISkillCandidato } from 'app/shared/model/skill-candidato.model';
import { ISkillRequerimiento } from 'app/shared/model/skill-requerimiento.model';

export interface ISkill {
  id?: number;
  nombre?: string;
  skillCandidatoes?: ISkillCandidato[];
  skillRequerimientos?: ISkillRequerimiento[];
  categoriaSkillId?: number;
}

export class Skill implements ISkill {
  constructor(
    public id?: number,
    public nombre?: string,
    public skillCandidatoes?: ISkillCandidato[],
    public skillRequerimientos?: ISkillRequerimiento[],
    public categoriaSkillId?: number
  ) {}
}
