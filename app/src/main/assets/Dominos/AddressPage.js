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
var homeType = document.getElementById('Address_Type_Select');
selectOption(homeType, 'Other');
var regionSelect = document.getElementById('Region');
selectOption(regionSelect, 'DELIVERY_STATE_CODE');
document.getElementById('Street').value = 'DELIVERY_STREET_ADDRESS';
document.getElementById('Address_Line_2').value = 'DELIVERY_UNIT_NUMBER';
document.getElementById('City').value = 'DELIVERY_CITY';
document.getElementById('Postal_Code').value = 'DELIVERY_ZIP_CODE';
console.log('Set address information option');

document.evaluate( '/html/body//form//button[@type=\"submit\"]' ,document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null ).singleNodeValue.click();