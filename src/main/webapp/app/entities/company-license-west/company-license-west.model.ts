import { ICompanyWest } from 'app/entities/company-west/company-west.model';
import { ILicenseWest } from 'app/entities/license-west/license-west.model';

export interface ICompanyLicenseWest {
  id: number;
  idCompany?: Pick<ICompanyWest, 'id'> | null;
  idLicense?: Pick<ILicenseWest, 'id'> | null;
}

export type NewCompanyLicenseWest = Omit<ICompanyLicenseWest, 'id'> & { id: null };
