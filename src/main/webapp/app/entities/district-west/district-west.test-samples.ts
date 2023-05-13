import { IDistrictWest, NewDistrictWest } from './district-west.model';

export const sampleWithRequiredData: IDistrictWest = {
  id: 11461,
};

export const sampleWithPartialData: IDistrictWest = {
  id: 51781,
  region: 'Home deposit',
};

export const sampleWithFullData: IDistrictWest = {
  id: 94045,
  name: 'markets killer Health',
  region: 'Small Brooks',
};

export const sampleWithNewData: NewDistrictWest = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
