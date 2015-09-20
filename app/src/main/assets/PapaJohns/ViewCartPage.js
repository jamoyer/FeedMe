/*adds a second 2 liter if only 1 in cart*/
var products = document.getElementsByClassName("cart-items")[0].getElementsByClassName('product-name');
var numTwoLiters = 0;
for(var i = 0; i < products.length; i++)
{
    if(products[i].innerText.indexOf('Liter') > -1)
    {
        numTwoLiters++;
    }
    console.log("num 2 liters = " + numTwoLiters);
}
if (numTwoLiters < 2 && PARTY_SIZE ==5 )
{
    window.location.href = '/order/menu?category=Drinks';
}
else
{
    document.evaluate( '//*[@id="cartsummary"]/form[1]/button' ,document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null ).singleNodeValue.click();
}



