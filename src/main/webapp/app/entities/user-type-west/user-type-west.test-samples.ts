import { IUserTypeWest, NewUserTypeWest } from './user-type-west.model';

export const sampleWithRequiredData: IUserTypeWest = {
  id: 11564,
};

export const sampleWithPartialData: IUserTypeWest = {
  id: 44251,
  code: 'Beauty circuit',
  discount: 80938,
};

export const sampleWithFullData: IUserTypeWest = {
  id: 66743,
  code: 'withdrawal Incredible Louisiana',
  discount: 95871,
};

export const sampleWithNewData: NewUserTypeWest = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
