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
    DELIVERY_STREET_ADDRESS(R.id.deliveryStreetAddress),
    DELIVERY_UNIT_NUMBER(R.id.deliveryUnitNumber),
    DELIVERY_ZIP_CODE(R.id.deliveryZipCode),
    DELIVERY_CITY(R.id.deliveryCity),
    DELIVERY_STATE_NAME(R.id.deliveryState),
    DELIVERY_STATE_CODE(null),
    DELIVERY_COUNTRY(R.id.deliveryCountry),
    BILLING_STREET_ADDRESS(R.id.userInformationBillingStreetAddress),
    BILLING_UNIT_NUMBER(R.id.userInformationBillingUnitNumber),
    BILLING_ZIP_CODE(R.id.userInformationBillingZipCode),
    BILLING_CITY(R.id.userInformationBillingCity),
    BILLING_STATE_NAME(R.id.userInformationBillingState),
    BILLING_STATE_CODE(null),
    BILLING_COUNTRY(R.id.userInformationBillingCountry),
    PREFERENCE_COST_PER_PERSON(R.id.preferenceCostPerPerson),
    PREFERENCE_CONFIRM_ORDERS(R.id.preferenceConfirmOrders),
    PREFERENCE_DISPLAY_BROWSER(R.id.preferenceDisplayBrowser),
    PREFERENCE_ALERT_SOUND(R.id.preferenceAlertSound),
    PREFERENCE_PAPA_JOHNS(R.id.preferencePapaJohns),
    PREFERENCE_JIMMY_JOHNS(R.id.preferenceJimmyJohns),
    PREFERENCE_DOMINOS(R.id.preferenceDominos),
    PREFERENCE_888_CHINESE(R.id.preference888Chinese),
    PREFERENCE_PEPPERONI(R.id.preferencePepperoni),
    PREFFERENCE_GRILLED_CHICKEN(R.id.preferenceGrilledChicken),
    PREFERENCE_BEEF(R.id.preferenceBeef),
    PREFERENCE_SPICY_ITALIAN_SAUSAGE(R.id.preferenceSpicyItalianSausage),
    PREFERENCE_BACON(R.id.preferenceBacon),
    PREFERENCE_SAUSAGE(R.id.preferenceSausage),
    PREFERENCE_CANDADIAN_BACON(R.id.preferenceCanadianBacon),
    PREFERENCE_ANCHOVIES(R.id.preferenceAnchovies),
    PREFERENCE_PINEAPPLE(R.id.preferencePineapple),
    PREFERENCE_ROMA_TOMATOES(R.id.preferenceRomaTomatoes),
    PREFERENCE_GREEN_OLIVES(R.id.preferenceGreenOlives),
    PREFERENCE_MUSHROOMS(R.id.preferenceMushrooms),
    PREFERENCE_SAUERKRAUT(R.id.preferenceSauerkraut),
    PREFERENCE_ONIONS(R.id.preferenceOnions),
    PREFERENCE_BLACK_OLIVES(R.id.preferenceBlackOlives),
    PREFERENCE_JALAPENO_PEPPERS(R.id.preferenceJalapenoPeppers),
    PREFERENCE_EXTRA_CHEESE(R.id.preferenceExtraCheese),
    PREFERENCE_THREE_CHEESE_BLEND(R.id.preferenceThreeCheeseBlend),
    PREFERENCE_PARMESAN(R.id.preferenceParmesan),
    PREFERENCE_ORIGINAL_CRUST(R.id.preferenceOriginalCrust),
    PREFERENCE_THIN_CRUST(R.id.preferenceThinCrust),
    PREFERENCE_NORMAL_CUT(R.id.preferenceNormalCut),
    PREFERENCE_SQUARE_CUT(R.id.preferenceSquareCut),
    PREFERENCE_NORMAL_BAKE(R.id.preferenceNormalBake),
    PREFERENCE_WELL_DONE_BAKE(R.id.preferenceWellDoneBake),
    PREFERENCE_NORMAL_SAUCE(R.id.preferenceNormalSauce),
    PREFERENCE_ORIGINAL_SAUCE(R.id.preferenceOriginalSauce),
    PREFERENCE_BBQ_SAUCE(R.id.preferenceBBQSauce),
    PREFERENCE_RANCH_SAUCE(R.id.preferenceRanchSauce),
    PREFERENCE_LIGHT_SAUCE(R.id.preferenceLightSauce),
    PREFERENCE_EXTRA_SAUCE(R.id.preferenceExtraSauce),
    PREFERENCE_NO_SAUCE(R.id.preferenceNoSauce),
    PREFERENCE_NORMAL_CHEESE(R.id.preferenceNormalCheese),
    PREFERENCE_LIGHT_CHEESE(R.id.preferenceLightCheese),
    PREFERENCE_NO_CHEESE(R.id.preferenceNoCheese);

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
