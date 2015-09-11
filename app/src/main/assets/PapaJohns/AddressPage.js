function startOrder()
{
    document.evaluate( '/html/body//a[@class=\"start-order-link\"]' ,document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null ).singleNodeValue.click();
    setTimeout(enterDelivery, 500);
}
function enterDelivery()
{
    document.evaluate( '/html/body//form//input[@value=\"Enter Your Delivery Address\"]' ,document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null ).singleNodeValue.click();
}
startOrder();
