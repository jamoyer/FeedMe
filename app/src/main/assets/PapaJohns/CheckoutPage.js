var tipPercentage = .2;
function fillPapaJohnForum(){
    document.getElementById('contact-firstname').value = "FIRST_NAME";
    document.getElementById('contact-lastname').value = "LAST_NAME";
    document.getElementById('contact-email').value = "EMAIL";
    document.getElementById('phone-number').value = "PHONE_NUMBER";
    document.getElementById('create-account-emailoffers').checked = false;
    document.getElementById('credit-card-number').value = "CREDIT_CARD_NUMBER";
    document.getElementById('credit-card-cvv').value = "CREDIT_CARD_CSV_NUMBER";
    document.getElementById('credit-card-name').value = "FIRST_NAME " + "LAST_NAME";

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
