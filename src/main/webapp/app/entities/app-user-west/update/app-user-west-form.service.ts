import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAppUserWest, NewAppUserWest } from '../app-user-west.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAppUserWest for edit and NewAppUserWestFormGroupInput for create.
 */
type AppUserWestFormGroupInput = IAppUserWest | PartialWithRequiredKeyOf<NewAppUserWest>;

type AppUserWestFormDefaults = Pick<NewAppUserWest, 'id'>;

type AppUserWestFormGroupContent = {
  id: FormControl<IAppUserWest['id'] | NewAppUserWest['id']>;
  password: FormControl<IAppUserWest['password']>;
  cnp: FormControl<IAppUserWest['cnp']>;
};

export type AppUserWestFormGroup = FormGroup<AppUserWestFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AppUserWestFormService {
  createAppUserWestFormGroup(appUserWest: AppUserWestFormGroupInput = { id: null }): AppUserWestFormGroup {
    const appUserWestRawValue = {
      ...this.getFormDefaults(),
      ...appUserWest,
    };
    return new FormGroup<AppUserWestFormGroupContent>({
      id: new FormControl(
        { value: appUserWestRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      password: new FormControl(appUserWestRawValue.password),
      cnp: new FormControl(appUserWestRawValue.cnp),
    });
  }

  getAppUserWest(form: AppUserWestFormGroup): IAppUserWest | NewAppUserWest {
    return form.getRawValue() as IAppUserWest | NewAppUserWest;
  }

  resetForm(form: AppUserWestFormGroup, appUserWest: AppUserWestFormGroupInput): void {
    const appUserWestRawValue = { ...this.getFormDefaults(), ...appUserWest };
    form.reset(
      {
        ...appUserWestRawValue,
        id: { value: appUserWestRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): AppUserWestFormDefaults {
    return {
      id: null,
    };
  }
}
