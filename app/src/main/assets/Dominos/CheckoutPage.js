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
    attemptFunc(clickAddCoke);
}
function clickAddCoke()
{
    clearPromotion();
    document.getElementsByClassName('js-isNew')[0].click();
    /* Chain functions together to give the page a bit to load */
    attemptFunc(continueToPayment);
}
function continueToPayment()
{
    clearPromotion();
    /* Submit form to continue to payment page */
    document.getElementsByClassName('submitButton')[0].click();
}
clearPromotion();
attemptFunc(clickCoke);