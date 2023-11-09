import { type IEquipe } from '@/shared/model/equipe.model';

export interface IEntraineur {
  id?: number;
  nom?: string | null;
  numIdent?: number | null;
  dateNaissance?: Date | null;
  ancienneEquipe?: string | null;
  equipe?: IEquipe | null;
}

export class Entraineur implements IEntraineur {
  constructor(
    public id?: number,
    public nom?: string | null,
    public numIdent?: number | null,
    public dateNaissance?: Date | null,
    public ancienneEquipe?: string | null,
    public equipe?: IEquipe | null,
  ) {}
}
