import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IJourneyStatusWest, NewJourneyStatusWest } from '../journey-status-west.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IJourneyStatusWest for edit and NewJourneyStatusWestFormGroupInput for create.
 */
type JourneyStatusWestFormGroupInput = IJourneyStatusWest | PartialWithRequiredKeyOf<NewJourneyStatusWest>;

type JourneyStatusWestFormDefaults = Pick<NewJourneyStatusWest, 'id'>;

type JourneyStatusWestFormGroupContent = {
  id: FormControl<IJourneyStatusWest['id'] | NewJourneyStatusWest['id']>;
  code: FormControl<IJourneyStatusWest['code']>;
  description: FormControl<IJourneyStatusWest['description']>;
};

export type JourneyStatusWestFormGroup = FormGroup<JourneyStatusWestFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class JourneyStatusWestFormService {
  createJourneyStatusWestFormGroup(journeyStatusWest: JourneyStatusWestFormGroupInput = { id: null }): JourneyStatusWestFormGroup {
    const journeyStatusWestRawValue = {
      ...this.getFormDefaults(),
      ...journeyStatusWest,
    };
    return new FormGroup<JourneyStatusWestFormGroupContent>({
      id: new FormControl(
        { value: journeyStatusWestRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      code: new FormControl(journeyStatusWestRawValue.code),
      description: new FormControl(journeyStatusWestRawValue.description),
    });
  }

  getJourneyStatusWest(form: JourneyStatusWestFormGroup): IJourneyStatusWest | NewJourneyStatusWest {
    return form.getRawValue() as IJourneyStatusWest | NewJourneyStatusWest;
  }

  resetForm(form: JourneyStatusWestFormGroup, journeyStatusWest: JourneyStatusWestFormGroupInput): void {
    const journeyStatusWestRawValue = { ...this.getFormDefaults(), ...journeyStatusWest };
    form.reset(
      {
        ...journeyStatusWestRawValue,
        id: { value: journeyStatusWestRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): JourneyStatusWestFormDefaults {
    return {
      id: null,
    };
  }
}
