javascript:
/* wait for document to be ready and loaded before doing anything */
while (document.readyState !== 'complete');
function functionName(fun) {
    var ret = fun.toString();
    ret = ret.substr('function '.length);
    ret = ret.substr(0, ret.indexOf('('));
    return ret;
}
function attemptFunc(func, sleepTime, retries)
{
    if (0 >= retries)
    {
        return;
    }
    try
    {
        func();
    }
    catch (err)
    {
        console.log('Error: ' + err.toString() + ' received while attempting ' +
            functionName(func) + '. Retrying in ' + sleepTime + 'ms. Retries left: ' + retries);
        function doFunc()
        {
            attemptFunc(func, sleepTime, --retries);
        }
        setTimeout(doFunc, sleepTime);
    }
}
var numRetries = 10;
function pageInteractor()
{
    /*
     * Remove all previous items from the cart before adding any.
     * We don't want to screw up and loop and order more items than intended.
     */
    var toRemove = document.getElementsByClassName('remove');
    console.log(toRemove.length+' items to remove from the cart.');
    for(var i=0; i<toRemove.length; i++)
    {
        toRemove[i].children[0].click();
        console.log('Removed item ' + i);
    }

    /* Click on a large 14" Pepperoni Pizza */
    document.getElementsByClassName('card__list-item__title')[1].click();
    console.log('Added a pizza');
    /* Go to checkout */
    window.location.href = 'https://www.dominos.com/en/pages/order/#/checkout/';
}
attemptFunc(pageInteractor, 1000, numRetries);