export interface IUserTypeWest {
  id: number;
  code?: string | null;
  discount?: number | null;
}

export type NewUserTypeWest = Omit<IUserTypeWest, 'id'> & { id: null };
