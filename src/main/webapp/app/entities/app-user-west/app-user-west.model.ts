export interface IAppUserWest {
  id: number;
  password?: string | null;
  cnp?: string | null;
}

export type NewAppUserWest = Omit<IAppUserWest, 'id'> & { id: null };
