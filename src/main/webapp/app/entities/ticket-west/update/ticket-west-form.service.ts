import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ITicketWest, NewTicketWest } from '../ticket-west.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITicketWest for edit and NewTicketWestFormGroupInput for create.
 */
type TicketWestFormGroupInput = ITicketWest | PartialWithRequiredKeyOf<NewTicketWest>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ITicketWest | NewTicketWest> = Omit<T, 'time'> & {
  time?: string | null;
};

type TicketWestFormRawValue = FormValueOf<ITicketWest>;

type NewTicketWestFormRawValue = FormValueOf<NewTicketWest>;

type TicketWestFormDefaults = Pick<NewTicketWest, 'id' | 'time'>;

type TicketWestFormGroupContent = {
  id: FormControl<TicketWestFormRawValue['id'] | NewTicketWest['id']>;
  finalPrice: FormControl<TicketWestFormRawValue['finalPrice']>;
  quantity: FormControl<TicketWestFormRawValue['quantity']>;
  time: FormControl<TicketWestFormRawValue['time']>;
  appUserId: FormControl<TicketWestFormRawValue['appUserId']>;
  journeyId: FormControl<TicketWestFormRawValue['journeyId']>;
};

export type TicketWestFormGroup = FormGroup<TicketWestFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TicketWestFormService {
  createTicketWestFormGroup(ticketWest: TicketWestFormGroupInput = { id: null }): TicketWestFormGroup {
    const ticketWestRawValue = this.convertTicketWestToTicketWestRawValue({
      ...this.getFormDefaults(),
      ...ticketWest,
    });
    return new FormGroup<TicketWestFormGroupContent>({
      id: new FormControl(
        { value: ticketWestRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      finalPrice: new FormControl(ticketWestRawValue.finalPrice),
      quantity: new FormControl(ticketWestRawValue.quantity),
      time: new FormControl(ticketWestRawValue.time),
      appUserId: new FormControl(ticketWestRawValue.appUserId),
      journeyId: new FormControl(ticketWestRawValue.journeyId),
    });
  }

  getTicketWest(form: TicketWestFormGroup): ITicketWest | NewTicketWest {
    return this.convertTicketWestRawValueToTicketWest(form.getRawValue() as TicketWestFormRawValue | NewTicketWestFormRawValue);
  }

  resetForm(form: TicketWestFormGroup, ticketWest: TicketWestFormGroupInput): void {
    const ticketWestRawValue = this.convertTicketWestToTicketWestRawValue({ ...this.getFormDefaults(), ...ticketWest });
    form.reset(
      {
        ...ticketWestRawValue,
        id: { value: ticketWestRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): TicketWestFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      time: currentTime,
    };
  }

  private convertTicketWestRawValueToTicketWest(
    rawTicketWest: TicketWestFormRawValue | NewTicketWestFormRawValue
  ): ITicketWest | NewTicketWest {
    return {
      ...rawTicketWest,
      time: dayjs(rawTicketWest.time, DATE_TIME_FORMAT),
    };
  }

  private convertTicketWestToTicketWestRawValue(
    ticketWest: ITicketWest | (Partial<NewTicketWest> & TicketWestFormDefaults)
  ): TicketWestFormRawValue | PartialWithRequiredKeyOf<NewTicketWestFormRawValue> {
    return {
      ...ticketWest,
      time: ticketWest.time ? ticketWest.time.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
