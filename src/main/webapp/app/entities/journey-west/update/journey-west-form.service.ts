import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IJourneyWest, NewJourneyWest } from '../journey-west.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IJourneyWest for edit and NewJourneyWestFormGroupInput for create.
 */
type JourneyWestFormGroupInput = IJourneyWest | PartialWithRequiredKeyOf<NewJourneyWest>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IJourneyWest | NewJourneyWest> = Omit<
  T,
  'actualDepartureTime' | 'plannedDepartureTime' | 'actualArrivalTime' | 'plannedArrivalTime'
> & {
  actualDepartureTime?: string | null;
  plannedDepartureTime?: string | null;
  actualArrivalTime?: string | null;
  plannedArrivalTime?: string | null;
};

type JourneyWestFormRawValue = FormValueOf<IJourneyWest>;

type NewJourneyWestFormRawValue = FormValueOf<NewJourneyWest>;

type JourneyWestFormDefaults = Pick<
  NewJourneyWest,
  'id' | 'actualDepartureTime' | 'plannedDepartureTime' | 'actualArrivalTime' | 'plannedArrivalTime'
>;

type JourneyWestFormGroupContent = {
  id: FormControl<JourneyWestFormRawValue['id'] | NewJourneyWest['id']>;
  distance: FormControl<JourneyWestFormRawValue['distance']>;
  journeyDuration: FormControl<JourneyWestFormRawValue['journeyDuration']>;
  actualDepartureTime: FormControl<JourneyWestFormRawValue['actualDepartureTime']>;
  plannedDepartureTime: FormControl<JourneyWestFormRawValue['plannedDepartureTime']>;
  actualArrivalTime: FormControl<JourneyWestFormRawValue['actualArrivalTime']>;
  plannedArrivalTime: FormControl<JourneyWestFormRawValue['plannedArrivalTime']>;
  ticketPrice: FormControl<JourneyWestFormRawValue['ticketPrice']>;
  numberOfStops: FormControl<JourneyWestFormRawValue['numberOfStops']>;
  timeOfStops: FormControl<JourneyWestFormRawValue['timeOfStops']>;
  minutesLate: FormControl<JourneyWestFormRawValue['minutesLate']>;
  journeyStatusId: FormControl<JourneyWestFormRawValue['journeyStatusId']>;
  trainId: FormControl<JourneyWestFormRawValue['trainId']>;
  companyId: FormControl<JourneyWestFormRawValue['companyId']>;
  departureRailwayStationId: FormControl<JourneyWestFormRawValue['departureRailwayStationId']>;
  arrivalRailwayStationId: FormControl<JourneyWestFormRawValue['arrivalRailwayStationId']>;
};

export type JourneyWestFormGroup = FormGroup<JourneyWestFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class JourneyWestFormService {
  createJourneyWestFormGroup(journeyWest: JourneyWestFormGroupInput = { id: null }): JourneyWestFormGroup {
    const journeyWestRawValue = this.convertJourneyWestToJourneyWestRawValue({
      ...this.getFormDefaults(),
      ...journeyWest,
    });
    return new FormGroup<JourneyWestFormGroupContent>({
      id: new FormControl(
        { value: journeyWestRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      distance: new FormControl(journeyWestRawValue.distance),
      journeyDuration: new FormControl(journeyWestRawValue.journeyDuration),
      actualDepartureTime: new FormControl(journeyWestRawValue.actualDepartureTime),
      plannedDepartureTime: new FormControl(journeyWestRawValue.plannedDepartureTime),
      actualArrivalTime: new FormControl(journeyWestRawValue.actualArrivalTime),
      plannedArrivalTime: new FormControl(journeyWestRawValue.plannedArrivalTime),
      ticketPrice: new FormControl(journeyWestRawValue.ticketPrice),
      numberOfStops: new FormControl(journeyWestRawValue.numberOfStops),
      timeOfStops: new FormControl(journeyWestRawValue.timeOfStops),
      minutesLate: new FormControl(journeyWestRawValue.minutesLate),
      journeyStatusId: new FormControl(journeyWestRawValue.journeyStatusId),
      trainId: new FormControl(journeyWestRawValue.trainId),
      companyId: new FormControl(journeyWestRawValue.companyId),
      departureRailwayStationId: new FormControl(journeyWestRawValue.departureRailwayStationId),
      arrivalRailwayStationId: new FormControl(journeyWestRawValue.arrivalRailwayStationId),
    });
  }

  getJourneyWest(form: JourneyWestFormGroup): IJourneyWest | NewJourneyWest {
    return this.convertJourneyWestRawValueToJourneyWest(form.getRawValue() as JourneyWestFormRawValue | NewJourneyWestFormRawValue);
  }

  resetForm(form: JourneyWestFormGroup, journeyWest: JourneyWestFormGroupInput): void {
    const journeyWestRawValue = this.convertJourneyWestToJourneyWestRawValue({ ...this.getFormDefaults(), ...journeyWest });
    form.reset(
      {
        ...journeyWestRawValue,
        id: { value: journeyWestRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): JourneyWestFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      actualDepartureTime: currentTime,
      plannedDepartureTime: currentTime,
      actualArrivalTime: currentTime,
      plannedArrivalTime: currentTime,
    };
  }

  private convertJourneyWestRawValueToJourneyWest(
    rawJourneyWest: JourneyWestFormRawValue | NewJourneyWestFormRawValue
  ): IJourneyWest | NewJourneyWest {
    return {
      ...rawJourneyWest,
      actualDepartureTime: dayjs(rawJourneyWest.actualDepartureTime, DATE_TIME_FORMAT),
      plannedDepartureTime: dayjs(rawJourneyWest.plannedDepartureTime, DATE_TIME_FORMAT),
      actualArrivalTime: dayjs(rawJourneyWest.actualArrivalTime, DATE_TIME_FORMAT),
      plannedArrivalTime: dayjs(rawJourneyWest.plannedArrivalTime, DATE_TIME_FORMAT),
    };
  }

  private convertJourneyWestToJourneyWestRawValue(
    journeyWest: IJourneyWest | (Partial<NewJourneyWest> & JourneyWestFormDefaults)
  ): JourneyWestFormRawValue | PartialWithRequiredKeyOf<NewJourneyWestFormRawValue> {
    return {
      ...journeyWest,
      actualDepartureTime: journeyWest.actualDepartureTime ? journeyWest.actualDepartureTime.format(DATE_TIME_FORMAT) : undefined,
      plannedDepartureTime: journeyWest.plannedDepartureTime ? journeyWest.plannedDepartureTime.format(DATE_TIME_FORMAT) : undefined,
      actualArrivalTime: journeyWest.actualArrivalTime ? journeyWest.actualArrivalTime.format(DATE_TIME_FORMAT) : undefined,
      plannedArrivalTime: journeyWest.plannedArrivalTime ? journeyWest.plannedArrivalTime.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
