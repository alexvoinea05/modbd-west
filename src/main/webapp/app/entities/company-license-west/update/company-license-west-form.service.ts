import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICompanyLicenseWest, NewCompanyLicenseWest } from '../company-license-west.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICompanyLicenseWest for edit and NewCompanyLicenseWestFormGroupInput for create.
 */
type CompanyLicenseWestFormGroupInput = ICompanyLicenseWest | PartialWithRequiredKeyOf<NewCompanyLicenseWest>;

type CompanyLicenseWestFormDefaults = Pick<NewCompanyLicenseWest, 'id'>;

type CompanyLicenseWestFormGroupContent = {
  id: FormControl<ICompanyLicenseWest['id'] | NewCompanyLicenseWest['id']>;
  idCompany: FormControl<ICompanyLicenseWest['idCompany']>;
  idLicense: FormControl<ICompanyLicenseWest['idLicense']>;
};

export type CompanyLicenseWestFormGroup = FormGroup<CompanyLicenseWestFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CompanyLicenseWestFormService {
  createCompanyLicenseWestFormGroup(companyLicenseWest: CompanyLicenseWestFormGroupInput = { id: null }): CompanyLicenseWestFormGroup {
    const companyLicenseWestRawValue = {
      ...this.getFormDefaults(),
      ...companyLicenseWest,
    };
    return new FormGroup<CompanyLicenseWestFormGroupContent>({
      id: new FormControl(
        { value: companyLicenseWestRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      idCompany: new FormControl(companyLicenseWestRawValue.idCompany),
      idLicense: new FormControl(companyLicenseWestRawValue.idLicense),
    });
  }

  getCompanyLicenseWest(form: CompanyLicenseWestFormGroup): ICompanyLicenseWest | NewCompanyLicenseWest {
    return form.getRawValue() as ICompanyLicenseWest | NewCompanyLicenseWest;
  }

  resetForm(form: CompanyLicenseWestFormGroup, companyLicenseWest: CompanyLicenseWestFormGroupInput): void {
    const companyLicenseWestRawValue = { ...this.getFormDefaults(), ...companyLicenseWest };
    form.reset(
      {
        ...companyLicenseWestRawValue,
        id: { value: companyLicenseWestRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CompanyLicenseWestFormDefaults {
    return {
      id: null,
    };
  }
}
