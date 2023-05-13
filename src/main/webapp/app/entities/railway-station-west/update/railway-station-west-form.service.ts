import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IRailwayStationWest, NewRailwayStationWest } from '../railway-station-west.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IRailwayStationWest for edit and NewRailwayStationWestFormGroupInput for create.
 */
type RailwayStationWestFormGroupInput = IRailwayStationWest | PartialWithRequiredKeyOf<NewRailwayStationWest>;

type RailwayStationWestFormDefaults = Pick<NewRailwayStationWest, 'id'>;

type RailwayStationWestFormGroupContent = {
  id: FormControl<IRailwayStationWest['id'] | NewRailwayStationWest['id']>;
  railwayStationName: FormControl<IRailwayStationWest['railwayStationName']>;
  railwayTypeId: FormControl<IRailwayStationWest['railwayTypeId']>;
  addressId: FormControl<IRailwayStationWest['addressId']>;
};

export type RailwayStationWestFormGroup = FormGroup<RailwayStationWestFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class RailwayStationWestFormService {
  createRailwayStationWestFormGroup(railwayStationWest: RailwayStationWestFormGroupInput = { id: null }): RailwayStationWestFormGroup {
    const railwayStationWestRawValue = {
      ...this.getFormDefaults(),
      ...railwayStationWest,
    };
    return new FormGroup<RailwayStationWestFormGroupContent>({
      id: new FormControl(
        { value: railwayStationWestRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      railwayStationName: new FormControl(railwayStationWestRawValue.railwayStationName),
      railwayTypeId: new FormControl(railwayStationWestRawValue.railwayTypeId),
      addressId: new FormControl(railwayStationWestRawValue.addressId),
    });
  }

  getRailwayStationWest(form: RailwayStationWestFormGroup): IRailwayStationWest | NewRailwayStationWest {
    return form.getRawValue() as IRailwayStationWest | NewRailwayStationWest;
  }

  resetForm(form: RailwayStationWestFormGroup, railwayStationWest: RailwayStationWestFormGroupInput): void {
    const railwayStationWestRawValue = { ...this.getFormDefaults(), ...railwayStationWest };
    form.reset(
      {
        ...railwayStationWestRawValue,
        id: { value: railwayStationWestRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): RailwayStationWestFormDefaults {
    return {
      id: null,
    };
  }
}
