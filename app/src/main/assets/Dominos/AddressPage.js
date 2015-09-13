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
selectOption(regionSelect, 'IA');
document.getElementById('Street').value = '150 Campus Ave';
document.getElementById('Address_Line_2').value = '22';
document.getElementById('City').value = 'Ames';
document.getElementById('Postal_Code').value = '50014';
console.log('Set address information option');

document.evaluate( '/html/body//form//button[@type=\"submit\"]' ,document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null ).singleNodeValue.click();