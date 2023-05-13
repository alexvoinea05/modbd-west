import { ILicenseWest, NewLicenseWest } from './license-west.model';

export const sampleWithRequiredData: ILicenseWest = {
  id: 29735,
};

export const sampleWithPartialData: ILicenseWest = {
  id: 94162,
  licenseNumber: 38387,
  licenseDescription: 'blue Maldives',
};

export const sampleWithFullData: ILicenseWest = {
  id: 82141,
  licenseNumber: 99257,
  licenseDescription: 'Industrial',
};

export const sampleWithNewData: NewLicenseWest = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
