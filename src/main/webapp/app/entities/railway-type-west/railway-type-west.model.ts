export interface IRailwayTypeWest {
  id: number;
  code?: string | null;
  description?: string | null;
}

export type NewRailwayTypeWest = Omit<IRailwayTypeWest, 'id'> & { id: null };
