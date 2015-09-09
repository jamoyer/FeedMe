javascript:
/* wait for document to be ready and loaded before doing anything */
while (document.readyState !== 'complete');
function pageInteractor()
{
    function clearPromotion()
    {
        /* Close the promotional popup if it exists */
        var closePromotionLink = document.getElementsByClassName('js-nothanks')[0];
        if (closePromotionLink && closePromotionLink.click)
        {
            closePromotionLink.click();
        }
        /* Chain functions together to give the page a bit to load */
        setTimeout(clickCoke, 4000);
    }
    function clickCoke()
    {
        /* Get a 2 liter Coke */
        document.getElementsByName('Beverage_Selection')[0].click();
        /* Chain functions together to give the page a bit to load */
        setTimeout(clickAddCoke, 1000);
    }
    function clickAddCoke()
    {
        document.getElementsByClassName('js-isNew')[0].click();
        /* Chain functions together to give the page a bit to load */
        setTimeout(continueToPayment, 1000);
    }
    function continueToPayment()
    {
        /* Submit form to continue to payment page */
        document.getElementsByClassName('submitButton')[0].click();
    }
    clearPromotion();
}
pageInteractor();