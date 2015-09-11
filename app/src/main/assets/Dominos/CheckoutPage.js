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
    if (0 >= retries--)
    {
        return;
    }
    console.log('Trying ' + functionName(func));
    try
    {
        func();
    }
    catch (err)
    {
        console.log('Error: ' + err.toString() +
            '. Retrying in ' + sleepTime + 'ms. Retries left: ' + retries);
        function doFunc()
        {
            attemptFunc(func, sleepTime, retries);
        }
        setTimeout(doFunc, sleepTime);
    }
}
function pageInteractor()
{
    var numRetries = 10;
    function clearPromotion()
    {
        /* Close the promotional popup if it exists */
        var closePromotionLink = document.getElementsByClassName('js-nothanks')[0];
        if (closePromotionLink && closePromotionLink.click)
        {
            closePromotionLink.click();
        }
    }
    function clickCoke()
    {
        clearPromotion();
        /* Get a 2 liter Coke */
        document.getElementsByName('Beverage_Selection')[0].click();
        /* Chain functions together to give the page a bit to load */
        attemptFunc(clickAddCoke, 1000, numRetries);
    }
    function clickAddCoke()
    {
        clearPromotion();
        document.getElementsByClassName('js-isNew')[0].click();
        /* Chain functions together to give the page a bit to load */
        attemptFunc(continueToPayment, 1000, numRetries);
    }
    function continueToPayment()
    {
        clearPromotion();
        /* Submit form to continue to payment page */
        document.getElementsByClassName('submitButton')[0].click();
    }
    clearPromotion();
    attemptFunc(clickCoke, 1000, numRetries);
}
pageInteractor();