export interface IFuelTypeWest {
  id: number;
  code?: string | null;
  description?: string | null;
}

export type NewFuelTypeWest = Omit<IFuelTypeWest, 'id'> & { id: null };
