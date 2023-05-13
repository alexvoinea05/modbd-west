export interface ITrainTypeWest {
  id: number;
  code?: string | null;
  description?: string | null;
}

export type NewTrainTypeWest = Omit<ITrainTypeWest, 'id'> & { id: null };
