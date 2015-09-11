javascript:
/* wait for document to be ready and loaded before doing anything */
while (document.readyState !== 'complete');
function functionName(fun) {
    var ret = fun.toString();
    ret = ret.substr('function '.length);
    ret = ret.substr(0, ret.indexOf('('));
    return ret;
}
function attemptFunc(func, sleepTime, retries)
{
    if (0 >= retries)
    {
        return;
    }
    try
    {
        func();
    }
    catch (err)
    {
        console.log('Error: ' + err.toString() + ' received while attempting ' +
            functionName(func) + '. Retrying in ' + sleepTime + 'ms. Retries left: ' + retries);
        function doFunc()
        {
            attemptFunc(func, sleepTime, --retries);
        }
        setTimeout(doFunc, sleepTime);
    }
}
var numRetries = 10;
function pageInteractor()
{
    document.getElementById('First_Name').value = 'Bob';
    document.getElementById('Last_Name').value = 'Bobenstein';
    document.getElementById('Email').value = 'bob@gmail.com';
    document.getElementById('Callback_Phone').value = '5558675309';
    document.getElementById('Email_Opt_In').checked = false;
    document.evaluate( '/html/body//form//input[@type=\"radio\" and @name=\"Payment_Type\" and @value=\"Credit\"]' ,document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null ).singleNodeValue.checked = true;
    document.getElementById('Credit_Card_Number').value = '1234123412341234';
    var expMonthSelect = document.getElementById('Expiration_Month');
    for (var i=0; i<expMonthSelect.options.length; i++)
    {
        if (expMonthSelect.options[i].value && expMonthSelect.options[i].value == 1)
        {
            expMonthSelect.selectedIndex = i;
            break;
        }
    }
    var expYearSelect = document.getElementById('Expiration_Year');
    for (var i=0; i<expYearSelect.options.length; i++)
    {
        if (expYearSelect.options[i].value && expYearSelect.options[i].value == 2019)
        {
            expYearSelect.selectedIndex = i;
            break;
        }
    }
    document.getElementById('Credit_Card_Security_Code').value = '456';
    document.getElementById('Billing_Postal_Code').value = '50014';
    document.getElementsByClassName('submitButton')[0].click();
}
attemptFunc(pageInteractor, 1000, numRetries);