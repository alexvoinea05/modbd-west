import { ITrainWest, NewTrainWest } from './train-west.model';

export const sampleWithRequiredData: ITrainWest = {
  id: 93699,
};

export const sampleWithPartialData: ITrainWest = {
  id: 52854,
};

export const sampleWithFullData: ITrainWest = {
  id: 14854,
  code: 'Rubber',
  numberOfSeats: 52369,
  fuelTypeId: 15432,
  trainTypeId: 8633,
};

export const sampleWithNewData: NewTrainWest = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
