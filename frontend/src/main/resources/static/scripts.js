"use strict";
window.onload = function () {

    const NAME_SURNAME_REGEX = new RegExp("\\b([A-ZÀ-ÿ][-,a-z. ']+ *)+");
    const EMAIL_REGEX = new RegExp("^.+@.+\\..+$");
    const INVALID_NAME_MSG = 'Invalid name';
    const INVALID_SURNAME_MSG = 'Invalid surname';
    const INVALID_EMAIL_MSG = 'Invalid email';
    const INVALID_AGE_MSG = 'Invalid age';

    const nameField = document.getElementById('name');
    const surnameField = document.getElementById('surname');
    const emailField = document.getElementById('email');
    const ageField = document.getElementById('age');
    const submitButton = document.getElementById('button');
    const formFields = [nameField, surnameField, emailField, ageField];
    let formCheck = {};
    formFields.forEach(field => {
        formCheck[field.id] = false;
    });

    const nameError = document.getElementById('name-error');
    const surnameError = document.getElementById('surname-error');
    const emailError = document.getElementById('email-error');
    const ageError = document.getElementById('age-error');
    const isEmpty = value => value.trim() === '';

    const validateForm = () => {
        submitButton.disabled = Object.values(formCheck).some(value => !value) || Object.values(formCheck).includes(undefined);
    }

    const addValidation = (field, errorElement, validationFn, errorMessage) => {
        field.addEventListener('input', function () {
            if (isEmpty(this.value) || !validationFn(this.value)) {
                errorElement.textContent = isEmpty(this.value) ? 'Field must be filled' : errorMessage;
                formCheck[field.id] = false;
            } else {
                errorElement.textContent = '';
                formCheck[field.id] = true;
            }
            validateForm();
        });
        // Symuluj zdarzenie 'input' dla każdego pola formularza po załadowaniu strony
        const event = new Event('input');
        field.dispatchEvent(event);
    }

    const nameSurnameValidation = value => NAME_SURNAME_REGEX.test(value);
    const emailValidation = value => EMAIL_REGEX.test(value);
    const ageValidation = value => value >= 18 && value <= 100;

    addValidation(nameField, nameError, nameSurnameValidation, INVALID_NAME_MSG);
    addValidation(surnameField, surnameError, nameSurnameValidation, INVALID_SURNAME_MSG);
    addValidation(emailField, emailError, emailValidation, INVALID_EMAIL_MSG);
    addValidation(ageField, ageError, ageValidation, INVALID_AGE_MSG);
}