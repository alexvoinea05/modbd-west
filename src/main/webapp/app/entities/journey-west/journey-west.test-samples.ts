import dayjs from 'dayjs/esm';

import { IJourneyWest, NewJourneyWest } from './journey-west.model';

export const sampleWithRequiredData: IJourneyWest = {
  id: 23905,
};

export const sampleWithPartialData: IJourneyWest = {
  id: 3242,
  journeyDuration: 96086,
  plannedDepartureTime: dayjs('2023-05-13T11:23'),
  plannedArrivalTime: dayjs('2023-05-13T00:02'),
  numberOfStops: 53339,
  timeOfStops: 65477,
  minutesLate: 67099,
  companyId: 47436,
  departureRailwayStationId: 15011,
  arrivalRailwayStationId: 13699,
};

export const sampleWithFullData: IJourneyWest = {
  id: 73047,
  distance: 40683,
  journeyDuration: 2492,
  actualDepartureTime: dayjs('2023-05-13T07:05'),
  plannedDepartureTime: dayjs('2023-05-13T07:30'),
  actualArrivalTime: dayjs('2023-05-12T22:06'),
  plannedArrivalTime: dayjs('2023-05-13T04:15'),
  ticketPrice: 23330,
  numberOfStops: 31790,
  timeOfStops: 23385,
  minutesLate: 40828,
  journeyStatusId: 23247,
  trainId: 4930,
  companyId: 40152,
  departureRailwayStationId: 98239,
  arrivalRailwayStationId: 28565,
};

export const sampleWithNewData: NewJourneyWest = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
