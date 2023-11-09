import { type IEquipe } from '@/shared/model/equipe.model';

export interface IJoueur {
  id?: number;
  nom?: string | null;
  poste?: string | null;
  numeroMaillot?: number | null;
  telephone?: string | null;
  dateNaissance?: Date | null;
  prixTransfer?: number | null;
  equipe?: IEquipe | null;
}

export class Joueur implements IJoueur {
  constructor(
    public id?: number,
    public nom?: string | null,
    public poste?: string | null,
    public numeroMaillot?: number | null,
    public telephone?: string | null,
    public dateNaissance?: Date | null,
    public prixTransfer?: number | null,
    public equipe?: IEquipe | null,
  ) {}
}
