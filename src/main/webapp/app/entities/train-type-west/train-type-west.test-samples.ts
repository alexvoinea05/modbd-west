import { ITrainTypeWest, NewTrainTypeWest } from './train-type-west.model';

export const sampleWithRequiredData: ITrainTypeWest = {
  id: 62515,
};

export const sampleWithPartialData: ITrainTypeWest = {
  id: 44865,
  code: 'Borders Metal',
};

export const sampleWithFullData: ITrainTypeWest = {
  id: 66939,
  code: 'cross-platform Loan',
  description: 'enable',
};

export const sampleWithNewData: NewTrainTypeWest = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
