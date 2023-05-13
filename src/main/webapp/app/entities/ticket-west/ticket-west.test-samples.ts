import dayjs from 'dayjs/esm';

import { ITicketWest, NewTicketWest } from './ticket-west.model';

export const sampleWithRequiredData: ITicketWest = {
  id: 10098,
};

export const sampleWithPartialData: ITicketWest = {
  id: 61317,
  quantity: 20283,
  time: dayjs('2023-05-12T15:47'),
  journeyId: 70339,
};

export const sampleWithFullData: ITicketWest = {
  id: 55682,
  finalPrice: 65388,
  quantity: 20927,
  time: dayjs('2023-05-12T15:06'),
  appUserId: 27961,
  journeyId: 26850,
};

export const sampleWithNewData: NewTicketWest = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
