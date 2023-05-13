export interface ILicenseWest {
  id: number;
  licenseNumber?: number | null;
  licenseDescription?: string | null;
}

export type NewLicenseWest = Omit<ILicenseWest, 'id'> & { id: null };
