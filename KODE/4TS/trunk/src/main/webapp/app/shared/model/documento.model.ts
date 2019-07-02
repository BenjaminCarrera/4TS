export interface IDocumento {
  id?: number;
}

export class Documento implements IDocumento {
  constructor(public id?: number) {}
}
