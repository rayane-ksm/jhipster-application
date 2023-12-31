import { type IEntraineur } from '@/shared/model/entraineur.model';
import { type IStade } from '@/shared/model/stade.model';
import { type IJoueur } from '@/shared/model/joueur.model';

export interface IEquipe {
  id?: number;
  nom?: string | null;
  pays?: string | null;
  nbJoueurs?: number | null;
  classement?: number | null;
  entraineur?: IEntraineur | null;
  stade?: IStade | null;
  joueurs?: IJoueur[] | null;
}

export class Equipe implements IEquipe {
  constructor(
    public id?: number,
    public nom?: string | null,
    public pays?: string | null,
    public nbJoueurs?: number | null,
    public classement?: number | null,
    public entraineur?: IEntraineur | null,
    public stade?: IStade | null,
    public joueurs?: IJoueur[] | null,
  ) {}
}
