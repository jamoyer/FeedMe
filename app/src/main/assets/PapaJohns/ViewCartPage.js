/*adds a second 2 liter if only 1 in cart*/
var products = document.getElementsByClassName("cart-items")[0].getElementsByClassName('product-name');
var numDrinks = 0;
for(var i = 0; i < products.length; i++)
{
    if(products[i].innerText.toLowerCase().indexOf('liter') > -1 || products[i].innerText.toLowerCase().indexOf('20-oz') > -1)
    {
        numDrinks++;
    }
    console.log("num 2 liters = " + numDrinks);
}
if (numDrinks < 2 && PARTY_SIZE ==5 )
{
    window.location.href = '/order/menu?category=Drinks';
}
else
{
    document.evaluate( '//*[@id="cartsummary"]/form[1]/button' ,document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null ).singleNodeValue.click();
}



