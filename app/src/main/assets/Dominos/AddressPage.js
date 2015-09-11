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
    var serviceElements = document.getElementsByName('Service_Type');
    for (var i=0; i<serviceElements.length; i++)
    {
        if (serviceElements[i].value === 'Delivery')
        {
            serviceElements[i].click();
            console.log('Set delivery option');
            break;
        }
    }
    var addressSelect = document.getElementById('Address_Type_Select');
    for (var i=0; i<addressSelect.options.length; i++)
    {
        if (addressSelect.options[i].value === 'Apartment')
        {
            addressSelect.selectedIndex = i;
            console.log('Set apartment option');
            break;
        }
    }
    var regionSelect = document.getElementById('Region');
    for (var i=0; i<regionSelect.options.length; i++)
    {
        if (regionSelect.options[i].value === 'IA')
        {
            regionSelect.selectedIndex = i;
            console.log('Set region to IA');
            break;
        }
    }
    document.getElementById('Street').value = '150 Campus Ave';
    document.getElementById('Address_Line_2').value = '22';
    document.getElementById('City').value = 'Ames';
    document.getElementById('Postal_Code').value = '50014';
    console.log('Set address information option');

    document.evaluate( '/html/body//form//button[@type=\"submit\"]' ,document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null ).singleNodeValue.click();
}
attemptFunc(pageInteractor, 1000, numRetries);