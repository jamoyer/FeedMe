javascript:
/* wait for document to be ready and loaded before doing anything */
while (document.readyState !== 'complete');
function pageInteractor()
{
    document.evaluate( '//*[@id="cartsummary"]/form[1]/button' ,document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null ).singleNodeValue.click();
}
pageInteractor();