import { IRailwayStationWest, NewRailwayStationWest } from './railway-station-west.model';

export const sampleWithRequiredData: IRailwayStationWest = {
  id: 76012,
};

export const sampleWithPartialData: IRailwayStationWest = {
  id: 62201,
};

export const sampleWithFullData: IRailwayStationWest = {
  id: 9145,
  railwayStationName: 'intangible Account Azerbaijanian',
  railwayTypeId: 34410,
  addressId: 17814,
};

export const sampleWithNewData: NewRailwayStationWest = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
