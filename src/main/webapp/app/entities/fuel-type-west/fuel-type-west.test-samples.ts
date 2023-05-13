import { IFuelTypeWest, NewFuelTypeWest } from './fuel-type-west.model';

export const sampleWithRequiredData: IFuelTypeWest = {
  id: 5596,
};

export const sampleWithPartialData: IFuelTypeWest = {
  id: 93515,
  code: 'Tala Vista Kip',
  description: 'Sausages',
};

export const sampleWithFullData: IFuelTypeWest = {
  id: 17574,
  code: 'Director Hong compressing',
  description: 'Virginia',
};

export const sampleWithNewData: NewFuelTypeWest = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
