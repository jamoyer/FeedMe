javascript:
/* wait for document to be ready and loaded before doing anything */
while (document.readyState !== 'complete');
function pageInteractor()
{
    /* Click on a large 14" Pepperoni Pizza */
    document.getElementsByClassName('card__list-item__title')[1].click();
}
pageInteractor();