import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ILicenseWest, NewLicenseWest } from '../license-west.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ILicenseWest for edit and NewLicenseWestFormGroupInput for create.
 */
type LicenseWestFormGroupInput = ILicenseWest | PartialWithRequiredKeyOf<NewLicenseWest>;

type LicenseWestFormDefaults = Pick<NewLicenseWest, 'id'>;

type LicenseWestFormGroupContent = {
  id: FormControl<ILicenseWest['id'] | NewLicenseWest['id']>;
  licenseNumber: FormControl<ILicenseWest['licenseNumber']>;
  licenseDescription: FormControl<ILicenseWest['licenseDescription']>;
};

export type LicenseWestFormGroup = FormGroup<LicenseWestFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class LicenseWestFormService {
  createLicenseWestFormGroup(licenseWest: LicenseWestFormGroupInput = { id: null }): LicenseWestFormGroup {
    const licenseWestRawValue = {
      ...this.getFormDefaults(),
      ...licenseWest,
    };
    return new FormGroup<LicenseWestFormGroupContent>({
      id: new FormControl(
        { value: licenseWestRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      licenseNumber: new FormControl(licenseWestRawValue.licenseNumber),
      licenseDescription: new FormControl(licenseWestRawValue.licenseDescription),
    });
  }

  getLicenseWest(form: LicenseWestFormGroup): ILicenseWest | NewLicenseWest {
    return form.getRawValue() as ILicenseWest | NewLicenseWest;
  }

  resetForm(form: LicenseWestFormGroup, licenseWest: LicenseWestFormGroupInput): void {
    const licenseWestRawValue = { ...this.getFormDefaults(), ...licenseWest };
    form.reset(
      {
        ...licenseWestRawValue,
        id: { value: licenseWestRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): LicenseWestFormDefaults {
    return {
      id: null,
    };
  }
}
