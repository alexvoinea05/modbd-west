export interface ICompanyWest {
  id: number;
  name?: string | null;
  identificationNumber?: string | null;
}

export type NewCompanyWest = Omit<ICompanyWest, 'id'> & { id: null };
