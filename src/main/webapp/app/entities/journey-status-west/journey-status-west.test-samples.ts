import { IJourneyStatusWest, NewJourneyStatusWest } from './journey-status-west.model';

export const sampleWithRequiredData: IJourneyStatusWest = {
  id: 66300,
};

export const sampleWithPartialData: IJourneyStatusWest = {
  id: 32498,
  code: 'Ouguiya',
};

export const sampleWithFullData: IJourneyStatusWest = {
  id: 80588,
  code: 'Buckinghamshire Berkshire blue',
  description: 'overriding',
};

export const sampleWithNewData: NewJourneyStatusWest = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
