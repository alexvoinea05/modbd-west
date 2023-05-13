import { IAppUserWest, NewAppUserWest } from './app-user-west.model';

export const sampleWithRequiredData: IAppUserWest = {
  id: 13667,
};

export const sampleWithPartialData: IAppUserWest = {
  id: 59090,
  password: 'Intelligent Granite',
  cnp: 'hybrid',
};

export const sampleWithFullData: IAppUserWest = {
  id: 8584,
  password: 'Guinea Route Peru',
  cnp: 'JBOD feed',
};

export const sampleWithNewData: NewAppUserWest = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
