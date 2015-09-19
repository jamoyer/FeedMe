function clearPromotion()
{
    /* Close the promotional popup if it exists */
    var closePromotionLink = document.getElementsByClassName('js-nothanks')[0];
    if (closePromotionLink && closePromotionLink.click)
    {
        closePromotionLink.click();
    }
}
function continueToPayment()
{
    clearPromotion();
    /* Submit form to continue to payment page */
    document.getElementsByClassName('submitButton')[0].click();
}
clearPromotion();
attemptFunc(continueToPayment);