var hasDrink = Android.getInfo('hasDrink');
if(hasDrink == 'true')
{
    window.location.href = '/order/view-cart';
}
else
{
    window.location.href = '/order/menu?category=Drinks';
}


