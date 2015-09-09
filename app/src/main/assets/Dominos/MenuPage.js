javascript:
/* wait for document to be ready and loaded before doing anything */
while (document.readyState !== 'complete');
function pageInteractor()
{
    /* Click on a large 14" Pepperoni Pizza */
    document.getElementsByClassName('card__list-item__title')[1].click();
    /* Go to checkout */
    window.location.href = 'https://www.dominos.com/en/pages/order/#/checkout/';
}
setTimeout(pageInteractor, 1000);