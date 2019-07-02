import { ISkill } from 'app/shared/model/skill.model';

export interface ICategoriaSkill {
  id?: number;
  categoria?: string;
  skills?: ISkill[];
}

export class CategoriaSkill implements ICategoriaSkill {
  constructor(public id?: number, public categoria?: string, public skills?: ISkill[]) {}
}
