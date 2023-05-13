import { ICompanyWest, NewCompanyWest } from './company-west.model';

export const sampleWithRequiredData: ICompanyWest = {
  id: 87217,
};

export const sampleWithPartialData: ICompanyWest = {
  id: 44980,
  name: 'Guinea-Bissau ADP',
  identificationNumber: 'calculate',
};

export const sampleWithFullData: ICompanyWest = {
  id: 42428,
  name: 'Avon Profound Wooden',
  identificationNumber: 'cross-media Mandatory',
};

export const sampleWithNewData: NewCompanyWest = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
