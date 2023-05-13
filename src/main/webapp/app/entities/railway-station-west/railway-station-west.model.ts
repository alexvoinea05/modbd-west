export interface IRailwayStationWest {
  id: number;
  railwayStationName?: string | null;
  railwayTypeId?: number | null;
  addressId?: number | null;
}

export type NewRailwayStationWest = Omit<IRailwayStationWest, 'id'> & { id: null };
