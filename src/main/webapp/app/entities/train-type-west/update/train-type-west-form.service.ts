import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ITrainTypeWest, NewTrainTypeWest } from '../train-type-west.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITrainTypeWest for edit and NewTrainTypeWestFormGroupInput for create.
 */
type TrainTypeWestFormGroupInput = ITrainTypeWest | PartialWithRequiredKeyOf<NewTrainTypeWest>;

type TrainTypeWestFormDefaults = Pick<NewTrainTypeWest, 'id'>;

type TrainTypeWestFormGroupContent = {
  id: FormControl<ITrainTypeWest['id'] | NewTrainTypeWest['id']>;
  code: FormControl<ITrainTypeWest['code']>;
  description: FormControl<ITrainTypeWest['description']>;
};

export type TrainTypeWestFormGroup = FormGroup<TrainTypeWestFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TrainTypeWestFormService {
  createTrainTypeWestFormGroup(trainTypeWest: TrainTypeWestFormGroupInput = { id: null }): TrainTypeWestFormGroup {
    const trainTypeWestRawValue = {
      ...this.getFormDefaults(),
      ...trainTypeWest,
    };
    return new FormGroup<TrainTypeWestFormGroupContent>({
      id: new FormControl(
        { value: trainTypeWestRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      code: new FormControl(trainTypeWestRawValue.code),
      description: new FormControl(trainTypeWestRawValue.description),
    });
  }

  getTrainTypeWest(form: TrainTypeWestFormGroup): ITrainTypeWest | NewTrainTypeWest {
    return form.getRawValue() as ITrainTypeWest | NewTrainTypeWest;
  }

  resetForm(form: TrainTypeWestFormGroup, trainTypeWest: TrainTypeWestFormGroupInput): void {
    const trainTypeWestRawValue = { ...this.getFormDefaults(), ...trainTypeWest };
    form.reset(
      {
        ...trainTypeWestRawValue,
        id: { value: trainTypeWestRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): TrainTypeWestFormDefaults {
    return {
      id: null,
    };
  }
}
