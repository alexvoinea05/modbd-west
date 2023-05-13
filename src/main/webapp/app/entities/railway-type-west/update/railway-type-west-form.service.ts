import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IRailwayTypeWest, NewRailwayTypeWest } from '../railway-type-west.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IRailwayTypeWest for edit and NewRailwayTypeWestFormGroupInput for create.
 */
type RailwayTypeWestFormGroupInput = IRailwayTypeWest | PartialWithRequiredKeyOf<NewRailwayTypeWest>;

type RailwayTypeWestFormDefaults = Pick<NewRailwayTypeWest, 'id'>;

type RailwayTypeWestFormGroupContent = {
  id: FormControl<IRailwayTypeWest['id'] | NewRailwayTypeWest['id']>;
  code: FormControl<IRailwayTypeWest['code']>;
  description: FormControl<IRailwayTypeWest['description']>;
};

export type RailwayTypeWestFormGroup = FormGroup<RailwayTypeWestFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class RailwayTypeWestFormService {
  createRailwayTypeWestFormGroup(railwayTypeWest: RailwayTypeWestFormGroupInput = { id: null }): RailwayTypeWestFormGroup {
    const railwayTypeWestRawValue = {
      ...this.getFormDefaults(),
      ...railwayTypeWest,
    };
    return new FormGroup<RailwayTypeWestFormGroupContent>({
      id: new FormControl(
        { value: railwayTypeWestRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      code: new FormControl(railwayTypeWestRawValue.code),
      description: new FormControl(railwayTypeWestRawValue.description),
    });
  }

  getRailwayTypeWest(form: RailwayTypeWestFormGroup): IRailwayTypeWest | NewRailwayTypeWest {
    return form.getRawValue() as IRailwayTypeWest | NewRailwayTypeWest;
  }

  resetForm(form: RailwayTypeWestFormGroup, railwayTypeWest: RailwayTypeWestFormGroupInput): void {
    const railwayTypeWestRawValue = { ...this.getFormDefaults(), ...railwayTypeWest };
    form.reset(
      {
        ...railwayTypeWestRawValue,
        id: { value: railwayTypeWestRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): RailwayTypeWestFormDefaults {
    return {
      id: null,
    };
  }
}
