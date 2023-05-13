import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ITrainWest, NewTrainWest } from '../train-west.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITrainWest for edit and NewTrainWestFormGroupInput for create.
 */
type TrainWestFormGroupInput = ITrainWest | PartialWithRequiredKeyOf<NewTrainWest>;

type TrainWestFormDefaults = Pick<NewTrainWest, 'id'>;

type TrainWestFormGroupContent = {
  id: FormControl<ITrainWest['id'] | NewTrainWest['id']>;
  code: FormControl<ITrainWest['code']>;
  numberOfSeats: FormControl<ITrainWest['numberOfSeats']>;
  fuelTypeId: FormControl<ITrainWest['fuelTypeId']>;
  trainTypeId: FormControl<ITrainWest['trainTypeId']>;
};

export type TrainWestFormGroup = FormGroup<TrainWestFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TrainWestFormService {
  createTrainWestFormGroup(trainWest: TrainWestFormGroupInput = { id: null }): TrainWestFormGroup {
    const trainWestRawValue = {
      ...this.getFormDefaults(),
      ...trainWest,
    };
    return new FormGroup<TrainWestFormGroupContent>({
      id: new FormControl(
        { value: trainWestRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      code: new FormControl(trainWestRawValue.code),
      numberOfSeats: new FormControl(trainWestRawValue.numberOfSeats),
      fuelTypeId: new FormControl(trainWestRawValue.fuelTypeId),
      trainTypeId: new FormControl(trainWestRawValue.trainTypeId),
    });
  }

  getTrainWest(form: TrainWestFormGroup): ITrainWest | NewTrainWest {
    return form.getRawValue() as ITrainWest | NewTrainWest;
  }

  resetForm(form: TrainWestFormGroup, trainWest: TrainWestFormGroupInput): void {
    const trainWestRawValue = { ...this.getFormDefaults(), ...trainWest };
    form.reset(
      {
        ...trainWestRawValue,
        id: { value: trainWestRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): TrainWestFormDefaults {
    return {
      id: null,
    };
  }
}
