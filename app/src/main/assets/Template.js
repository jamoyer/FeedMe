javascript:
/* wait for document to be ready and loaded before doing anything */
while (document.readyState !== 'complete');
function functionName(fun) 
{
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
function pageInteractor()
{

}
attemptFunc(pageInteractor, 1000, 5);
