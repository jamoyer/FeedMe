package rainmanproductions.feedme.userinformation;

import rainmanproductions.feedme.R;

public enum InfoType
{
    FIRST_NAME(R.id.userInformationFirstName),
    MIDDLE_NAME(R.id.userInformationMiddleName),
    MIDDLE_INITIAL(null), // can be taken as first letter of middle name
    LAST_NAME(R.id.userInformationLastName),
    BIRTH_DAY_OF_MONTH(null),
    BIRTH_MONTH_NUMBER(null), // just store the month name, use the enum to get the number
    BIRTH_MONTH(null),
    BIRTH_YEAR(null),
    EMAIL(R.id.userInformationEmail),
    PHONE_NUMBER(R.id.userInformationPhoneNumber),
    CREDIT_CARD_NUMBER(R.id.userInformationCreditCardNumber),
    CREDIT_CARD_CSV_NUMBER(R.id.userInformationCreditCardCSV),
    CREDIT_CARD_EXP_MONTH_NUM(null),
    CREDIT_CARD_EXP_YEAR(R.id.userInformationCreditCardExpYear),
    DELIVERY_STREET_ADDRESS(null),
    DELIVERY_HOME_TYPE(null),
    DELIVERY_UNIT_NUMBER(null),
    DELIVERY_ZIP_CODE(null),
    DELIVERY_CITY(null),
    DELIVERY_STATE(null),
    DELIVERY_COUNTRY(null),
    BILLING_STREET_ADDRESS(R.id.userInformationBillingStreetAddress),
    BILLING_UNIT_NUMBER(R.id.userInformationBillingUnitNumber),
    BILLING_ZIP_CODE(R.id.userInformationBillingZipCode),
    BILLING_CITY(R.id.userInformationBillingCity),
    BILLING_STATE(R.id.userInformationBillingState),
    BILLING_COUNTRY(R.id.userInformationBillingCountry);

    // null formId implies special work must be done
    private final Integer formId;

    InfoType(final Integer formId)
    {
        this.formId = formId;
    }

    public Integer getFormId()
    {
        return formId;
    }
}
