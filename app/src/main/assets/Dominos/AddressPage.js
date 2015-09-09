javascript:
/* wait for document to be ready and loaded before doing anything */
while (document.readyState !== 'complete');
function pageInteractor()
{
    var serviceElements = document.getElementsByName('Service_Type');
    for (var i=0; i<serviceElements.length; i++)
    {
        if (serviceElements[i].value === 'Delivery')
        {
            serviceElements[i].click();
            break;
        }
    }
    var addressSelect = document.getElementById('Address_Type_Select');
    for (var i=0; i<addressSelect.options.length; i++)
    {
        if (addressSelect.options[i].value === 'Apartment')
        {
            addressSelect.selectedIndex = i;
            break;
        }
    }
    var regionSelect = document.getElementById('Region');
    for (var i=0; i<regionSelect.options.length; i++)
    {
        if (regionSelect.options[i].value === 'IA')
        {
            regionSelect.selectedIndex = i;
            break;
        }
    }
    document.getElementById('Street').value = '150 Campus Ave';
    document.getElementById('Address_Line_2').value = '22';
    document.getElementById('City').value = 'Ames';
    document.getElementById('Postal_Code').value = '50014';
    document.evaluate( '/html/body//form//button[@type=\"submit\"]' ,document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null ).singleNodeValue.click();
}
pageInteractor();