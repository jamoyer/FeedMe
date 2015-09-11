javascript:
/* wait for document to be ready and loaded before doing anything */
while (document.readyState !== 'complete');
function pageInteractor()
{
    function fillDeliveryInfo()
    {
        document.getElementById('locations-streetaddress').value = "150 Campus Ave";
        var addressSelect = document.getElementById('locations-aptstefloor');
        for (var i=0; i<addressSelect.options.length; i++) {
            if (addressSelect.options[i].value === 'APT') {
                addressSelect.selectedIndex = i;
                break;
            }
        }

        document.getElementById('locations-aptstefloornumber').removeAttribute("disabled");
        document.getElementById('locations-aptstefloornumber').value = "22";
        document.getElementById('locations-zipcode').value = "50014-2836";
        document.evaluate( '/html/body/div/main/div[2]/div/div[2]/form/fieldset/input' ,document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null ).singleNodeValue.click();
        setTimeout(clickDeliver, 1000);
    }

    function clickDeliver()
    {
        document.evaluate( '/html/body/div/main/div[2]/div/div[1]/div/article[1]/div[4]/form[1]/button' ,document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null ).singleNodeValue.click();
    }
    fillDeliveryInfo();
}
pageInteractor();