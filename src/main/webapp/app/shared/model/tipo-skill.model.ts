import { ISkillRequerimiento } from 'app/shared/model/skill-requerimiento.model';

export interface ITipoSkill {
  id?: number;
  tipo?: string;
  skillRequerimientos?: ISkillRequerimiento[];
}

export class TipoSkill implements ITipoSkill {
  constructor(public id?: number, public tipo?: string, public skillRequerimientos?: ISkillRequerimiento[]) {}
}
