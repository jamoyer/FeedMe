javascript:
/* wait for document to be ready and loaded before doing anything */
while (document.readyState !== 'complete');
function pageInteractor()
{
    function selectPizza()
    {
        document.evaluate( '/html/body/div/main/section[1]/div[2]/ul/li[1]/a' ,document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null ).singleNodeValue.click();
        setTimeout(addToCart, 500);
    }
    function addToCart()
    {
		document.evaluate( '/html/body/div/main/section[1]/div[2]/div[2]/form/ul/li[1]/button' ,document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null ).singleNodeValue.click();
		setTimeout(fillDeliveryInfo, 500);
    }
    selectPizza();
}
pageInteractor();