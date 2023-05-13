export interface ICityWest {
  id: number;
  name?: string | null;
  districtId?: number | null;
}

export type NewCityWest = Omit<ICityWest, 'id'> & { id: null };
