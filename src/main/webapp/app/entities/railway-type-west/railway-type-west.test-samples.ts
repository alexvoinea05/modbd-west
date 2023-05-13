import { IRailwayTypeWest, NewRailwayTypeWest } from './railway-type-west.model';

export const sampleWithRequiredData: IRailwayTypeWest = {
  id: 18500,
};

export const sampleWithPartialData: IRailwayTypeWest = {
  id: 49631,
  description: 'Courts',
};

export const sampleWithFullData: IRailwayTypeWest = {
  id: 10025,
  code: 'Sports transmit',
  description: 'applications redundant',
};

export const sampleWithNewData: NewRailwayTypeWest = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
