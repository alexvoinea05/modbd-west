import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICityWest, NewCityWest } from '../city-west.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICityWest for edit and NewCityWestFormGroupInput for create.
 */
type CityWestFormGroupInput = ICityWest | PartialWithRequiredKeyOf<NewCityWest>;

type CityWestFormDefaults = Pick<NewCityWest, 'id'>;

type CityWestFormGroupContent = {
  id: FormControl<ICityWest['id'] | NewCityWest['id']>;
  name: FormControl<ICityWest['name']>;
  districtId: FormControl<ICityWest['districtId']>;
};

export type CityWestFormGroup = FormGroup<CityWestFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CityWestFormService {
  createCityWestFormGroup(cityWest: CityWestFormGroupInput = { id: null }): CityWestFormGroup {
    const cityWestRawValue = {
      ...this.getFormDefaults(),
      ...cityWest,
    };
    return new FormGroup<CityWestFormGroupContent>({
      id: new FormControl(
        { value: cityWestRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(cityWestRawValue.name),
      districtId: new FormControl(cityWestRawValue.districtId),
    });
  }

  getCityWest(form: CityWestFormGroup): ICityWest | NewCityWest {
    return form.getRawValue() as ICityWest | NewCityWest;
  }

  resetForm(form: CityWestFormGroup, cityWest: CityWestFormGroupInput): void {
    const cityWestRawValue = { ...this.getFormDefaults(), ...cityWest };
    form.reset(
      {
        ...cityWestRawValue,
        id: { value: cityWestRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CityWestFormDefaults {
    return {
      id: null,
    };
  }
}
