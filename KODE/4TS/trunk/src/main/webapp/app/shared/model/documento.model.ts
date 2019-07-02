export interface IDocumento {
  id?: number;
  documento?: string;
}

export class Documento implements IDocumento {
  constructor(public id?: number, public documento?: string) {}
}
