import { AbstractControl, ValidationErrors, ValidatorFn } from "@angular/forms";

export class FormValidations {
    // validate whether the selection is from the list or not
    static selectedFromListValidator(checkProperty: string): ValidatorFn {

        return (control: AbstractControl): ValidationErrors | null => {
            const selectedObj = control.value;
            const selectedProertyValue = control.value[checkProperty];

            if ((selectedObj && selectedProertyValue) || selectedObj === '') {
                return null;
            } else {
                return { 'notSelectedFromList': true };
            }

        }
    }

    // validate whether the input stats with the given character or not
    static startsWithValidator(checkCharacter: string): ValidatorFn {
        return (control: AbstractControl): ValidationErrors | null => {
            const inputValue = control.value;
            if (inputValue) {
                const isStartsWithChar = inputValue.startsWith(checkCharacter);

                if (isStartsWithChar) {
                    return { 'startWithInvalidChar': { 'invalidCharacter': checkCharacter } };
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }
    }

    static selectedAgenciesValidator(selectedAgencies: any[]): ValidatorFn {
        return (control: AbstractControl): ValidationErrors | null => {
            return selectedAgencies.length === 0 ? { required: true } : null;
        };
    }
}