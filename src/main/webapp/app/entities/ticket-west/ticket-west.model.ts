import dayjs from 'dayjs/esm';

export interface ITicketWest {
  id: number;
  finalPrice?: number | null;
  quantity?: number | null;
  time?: dayjs.Dayjs | null;
  appUserId?: number | null;
  journeyId?: number | null;
}

export type NewTicketWest = Omit<ITicketWest, 'id'> & { id: null };
