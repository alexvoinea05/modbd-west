import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDistrictWest, NewDistrictWest } from '../district-west.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDistrictWest for edit and NewDistrictWestFormGroupInput for create.
 */
type DistrictWestFormGroupInput = IDistrictWest | PartialWithRequiredKeyOf<NewDistrictWest>;

type DistrictWestFormDefaults = Pick<NewDistrictWest, 'id'>;

type DistrictWestFormGroupContent = {
  id: FormControl<IDistrictWest['id'] | NewDistrictWest['id']>;
  name: FormControl<IDistrictWest['name']>;
  region: FormControl<IDistrictWest['region']>;
};

export type DistrictWestFormGroup = FormGroup<DistrictWestFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DistrictWestFormService {
  createDistrictWestFormGroup(districtWest: DistrictWestFormGroupInput = { id: null }): DistrictWestFormGroup {
    const districtWestRawValue = {
      ...this.getFormDefaults(),
      ...districtWest,
    };
    return new FormGroup<DistrictWestFormGroupContent>({
      id: new FormControl(
        { value: districtWestRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(districtWestRawValue.name),
      region: new FormControl(districtWestRawValue.region),
    });
  }

  getDistrictWest(form: DistrictWestFormGroup): IDistrictWest | NewDistrictWest {
    return form.getRawValue() as IDistrictWest | NewDistrictWest;
  }

  resetForm(form: DistrictWestFormGroup, districtWest: DistrictWestFormGroupInput): void {
    const districtWestRawValue = { ...this.getFormDefaults(), ...districtWest };
    form.reset(
      {
        ...districtWestRawValue,
        id: { value: districtWestRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DistrictWestFormDefaults {
    return {
      id: null,
    };
  }
}
