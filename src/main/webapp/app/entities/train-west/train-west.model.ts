export interface ITrainWest {
  id: number;
  code?: string | null;
  numberOfSeats?: number | null;
  fuelTypeId?: number | null;
  trainTypeId?: number | null;
}

export type NewTrainWest = Omit<ITrainWest, 'id'> & { id: null };
