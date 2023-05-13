export interface IJourneyStatusWest {
  id: number;
  code?: string | null;
  description?: string | null;
}

export type NewJourneyStatusWest = Omit<IJourneyStatusWest, 'id'> & { id: null };
