import { type IEquipe } from '@/shared/model/equipe.model';

export interface IStade {
  id?: number;
  nom?: string | null;
  lieu?: string | null;
  equipe?: IEquipe | null;
}

export class Stade implements IStade {
  constructor(
    public id?: number,
    public nom?: string | null,
    public lieu?: string | null,
    public equipe?: IEquipe | null,
  ) {}
}
