javascript:
/* Returns the name of the function */
function functionName(fun)
{
    var ret = fun.toString();
    ret = ret.substr('function '.length);
    ret = ret.substr(0, ret.indexOf('('));
    return ret;
}
var DEFAULT_RETRIES = 10;
var DEFAULT_SLEEP_TIME = 1000;
/* Attempts the function "func" "retries" number of times, waiting "sleepTime" ms between each try. */
function attemptFunc(func, sleepTime, retries)
{
    if(typeof retries === "undefined")
    {
        retries = DEFAULT_RETRIES;
    }
    if(typeof sleepTime === "undefined")
    {
        sleepTime = DEFAULT_SLEEP_TIME;
    }
    if (0 >= retries--)
    {
        return;
    }
    console.log('Trying ' + functionName(func));
    try
    {
        func();
    }
    catch (err)
    {
        console.log('Error: ' + err.toString() +
            "\nRetrying in " + sleepTime + 'ms. Retries left: ' + retries);
        function doFunc()
        {
            attemptFunc(func, sleepTime, retries);
        }
        setTimeout(doFunc, sleepTime);
    }
}
function selectOption(selectElement, optionValue)
{
    for (var i=0; i<selectElement.options.length; i++)
    {
        if (selectElement.options[i].value && selectElement.options[i].value == optionValue)
        {
            selectElement.selectedIndex = i;
            console.log('Set option to ' + optionValue.toString());
            break;
        }
    }
}
function pageInteractor()
{