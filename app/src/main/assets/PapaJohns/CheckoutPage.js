javascript:
/* wait for document to be ready and loaded before doing anything */
while (document.readyState !== 'complete');
function pageInteractor()
{
    var tipPercentage = .2;
    function fillPapaJohnForum(){
        document.getElementById('contact-firstname').value = "Puff";
    	document.getElementById('contact-lastname').value = "Daddy";
    	document.getElementById('contact-email').value = "pdiddy@gmail.com";
    	document.getElementById('phone-number').value = "5553537651";
    	document.getElementById('create-account-emailoffers').checked = false;
    	document.getElementById('credit-card-number').value = "1111111111111111";
    	document.getElementById('credit-card-cvv').value = "111";
    	document.getElementById('credit-card-name').value = "Puff Daddy";

    	var addressSelect = document.getElementById('credit-card-expiration-month');
        for (var i=0; i<addressSelect.options.length; i++) {
            if (addressSelect.options[i].value === '08') {
                addressSelect.selectedIndex = i;
                break;
            }
        }

    	var addressSelect = document.getElementById('credit-card-expiration-year');
        for (var i=0; i<addressSelect.options.length; i++) {
            if (addressSelect.options[i].value === '2020') {
                addressSelect.selectedIndex = i;
                break;
            }
        }

    	document.getElementById('input').checked = true;

    	var costString = document.getElementById('estimated-total-summary').innerHTML;
    	var costInt = costString.substring(1, costString.length);
    	var finalTip = (costInt*tipPercentage).toFixed(2);
    	document.getElementById('tip-amount').value = finalTip;
    	document.getElementById('validate-order').click();

    }
    fillPapaJohnForum();
}
pageInteractor();