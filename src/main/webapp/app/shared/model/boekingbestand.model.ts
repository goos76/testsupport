import { IRecord } from 'app/shared/model/record.model';
import { Label } from 'app/shared/model/enumerations/label.model';

export interface IBoekingbestand {
  id?: number;
  kenmerk?: string;
  label?: Label;
  records?: IRecord[];
}

export const defaultValue: Readonly<IBoekingbestand> = {};
