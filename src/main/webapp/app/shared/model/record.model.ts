import { IBoekingbestand } from 'app/shared/model/boekingbestand.model';

export interface IRecord {
  id?: number;
  debiteur?: string;
  veroorzaker?: string;
  overeenkomst?: string;
  datumIngang?: string;
  datumEinde?: string;
  boekingbestand?: IBoekingbestand;
}

export const defaultValue: Readonly<IRecord> = {};
