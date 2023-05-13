import { IAddressWest, NewAddressWest } from './address-west.model';

export const sampleWithRequiredData: IAddressWest = {
  id: 55686,
};

export const sampleWithPartialData: IAddressWest = {
  id: 1688,
  street: 'McCullough Dale',
  zipcode: 'Washington local synergistic',
};

export const sampleWithFullData: IAddressWest = {
  id: 64902,
  streetNumber: 'alarm interactive',
  street: 'Jackson Burgs',
  zipcode: 'e-commerce fresh-thinking B2B',
  cityId: 22264,
};

export const sampleWithNewData: NewAddressWest = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
