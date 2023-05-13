import { ICompanyLicenseWest, NewCompanyLicenseWest } from './company-license-west.model';

export const sampleWithRequiredData: ICompanyLicenseWest = {
  id: 59487,
};

export const sampleWithPartialData: ICompanyLicenseWest = {
  id: 53929,
};

export const sampleWithFullData: ICompanyLicenseWest = {
  id: 56218,
};

export const sampleWithNewData: NewCompanyLicenseWest = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
