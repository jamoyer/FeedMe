document.getElementById('First_Name').value = 'FIRST_NAME';
document.getElementById('Last_Name').value = 'LAST_NAME';
document.getElementById('Email').value = 'EMAIL';
document.getElementById('Callback_Phone').value = 'PHONE_NUMBER';
document.getElementById('Email_Opt_In').checked = false;
document.evaluate( '/html/body//form//input[@type=\"radio\" and @name=\"Payment_Type\" and @value=\"Credit\"]' ,document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null ).singleNodeValue.checked = true;
document.getElementById('Credit_Card_Number').value = 'CREDIT_CARD_NUMBER';
var expMonthSelect = document.getElementById('Expiration_Month');
selectOption(expMonthSelect, CREDIT_CARD_EXP_MONTH_NUM);
var expYearSelect = document.getElementById('Expiration_Year');
selectOption(expYearSelect, CREDIT_CARD_EXP_YEAR);
document.getElementById('Credit_Card_Security_Code').value = 'CREDIT_CARD_CSV_NUMBER';
document.getElementById('Billing_Postal_Code').value = 'BILLING_ZIP_CODE';
/*document.getElementsByClassName('submitButton')[0].click();*/