function fillDeliveryInfo()
{
    document.getElementById('locations-streetaddress').value = "DELIVERY_STREET_ADDRESS";
    var addressSelect = document.getElementById('locations-aptstefloor');
    if(DELIVERY_UNIT_NUMBER != null)
    {
        for (var i=0; i<addressSelect.options.length; i++) {
            if (addressSelect.options[i].value === 'APT') {
                addressSelect.selectedIndex = i;
                break;
            }
        }
        document.getElementById('locations-aptstefloornumber').removeAttribute("disabled");
        document.getElementById('locations-aptstefloornumber').value = "DELIVERY_UNIT_NUMBER";
    }

    document.getElementById('locations-zipcode').value = "DELIVERY_ZIP_CODE";
    document.evaluate( '/html/body/div/main/div[2]/div/div[2]/form/fieldset/input' ,document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null ).singleNodeValue.click();
    setTimeout(clickDeliver, 1000);
}

function clickDeliver()
{
    document.evaluate( '/html/body/div/main/div[2]/div/div[1]/div/article[1]/div[4]/form[1]/button' ,document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null ).singleNodeValue.click();
}
fillDeliveryInfo();
