export interface IAddressWest {
  id: number;
  streetNumber?: string | null;
  street?: string | null;
  zipcode?: string | null;
  cityId?: number | null;
}

export type NewAddressWest = Omit<IAddressWest, 'id'> & { id: null };
