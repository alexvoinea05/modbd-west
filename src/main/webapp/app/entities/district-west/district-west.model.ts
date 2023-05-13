export interface IDistrictWest {
  id: number;
  name?: string | null;
  region?: string | null;
}

export type NewDistrictWest = Omit<IDistrictWest, 'id'> & { id: null };
