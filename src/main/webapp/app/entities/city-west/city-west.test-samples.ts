import { ICityWest, NewCityWest } from './city-west.model';

export const sampleWithRequiredData: ICityWest = {
  id: 82265,
};

export const sampleWithPartialData: ICityWest = {
  id: 54828,
  name: 'Gabon matrix Computer',
  districtId: 11309,
};

export const sampleWithFullData: ICityWest = {
  id: 90944,
  name: 'models South',
  districtId: 53841,
};

export const sampleWithNewData: NewCityWest = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
