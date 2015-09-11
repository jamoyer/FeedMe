javascript:
/* Returns the name of the function */
function functionName(fun)
{
    var ret = fun.toString();
    ret = ret.substr('function '.length);
    ret = ret.substr(0, ret.indexOf('('));
    return ret;
}
/* Attempts the function "func" "retries" number of times, waiting "sleepTime" ms between each try. */
function attemptFunc(func, sleepTime, retries)
{
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
function pageInteractor()
{