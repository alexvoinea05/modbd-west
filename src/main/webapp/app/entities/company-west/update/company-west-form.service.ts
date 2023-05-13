import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICompanyWest, NewCompanyWest } from '../company-west.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICompanyWest for edit and NewCompanyWestFormGroupInput for create.
 */
type CompanyWestFormGroupInput = ICompanyWest | PartialWithRequiredKeyOf<NewCompanyWest>;

type CompanyWestFormDefaults = Pick<NewCompanyWest, 'id'>;

type CompanyWestFormGroupContent = {
  id: FormControl<ICompanyWest['id'] | NewCompanyWest['id']>;
  name: FormControl<ICompanyWest['name']>;
  identificationNumber: FormControl<ICompanyWest['identificationNumber']>;
};

export type CompanyWestFormGroup = FormGroup<CompanyWestFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CompanyWestFormService {
  createCompanyWestFormGroup(companyWest: CompanyWestFormGroupInput = { id: null }): CompanyWestFormGroup {
    const companyWestRawValue = {
      ...this.getFormDefaults(),
      ...companyWest,
    };
    return new FormGroup<CompanyWestFormGroupContent>({
      id: new FormControl(
        { value: companyWestRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(companyWestRawValue.name),
      identificationNumber: new FormControl(companyWestRawValue.identificationNumber),
    });
  }

  getCompanyWest(form: CompanyWestFormGroup): ICompanyWest | NewCompanyWest {
    return form.getRawValue() as ICompanyWest | NewCompanyWest;
  }

  resetForm(form: CompanyWestFormGroup, companyWest: CompanyWestFormGroupInput): void {
    const companyWestRawValue = { ...this.getFormDefaults(), ...companyWest };
    form.reset(
      {
        ...companyWestRawValue,
        id: { value: companyWestRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CompanyWestFormDefaults {
    return {
      id: null,
    };
  }
}
