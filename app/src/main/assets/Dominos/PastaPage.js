var items = document.querySelectorAll('a.none--handheld[data-clicked-element]'); /*A list of all pizza item buttons*/
/*Make an array of all elements except the last one*/
var temp = [];
for(var i=0; i<items.length-1;i++){
    temp.push(items[i]);
}
items = temp;
var numPeople = PARTY_SIZE;

function addFoodForPeople()
{
    if (numPeople <= 0)
    {
        return;
    }

    /*Get random item*/
    var item = randomElement(items);
    item.click();

    /*Randomize bowl type*/
    setTimeout(function(){
        var noBreadBowl = document.querySelectorAll('input[type="radio"][data-flavorcode="PASTA"]')[0];
        var breadBowl = document.querySelectorAll('input[type="radio"][data-flavorcode="BBOWL"]')[0];
        var bowls = [noBreadBowl, breadBowl];
        randomElement(bowls).checked = true;

        /*Add to order*/
        document.querySelectorAll('button.btn.js-isNew')[0].click();
        numPeople--;

        /*If we have fed all people go to next page otherwise order food*/
        if (numPeople <= 0)
        {
            /*wait some time before going to next page to let clicks finish*/
            window.location.href = 'https://www.dominos.com/en/pages/order/#/checkout/';
        }
        else
        {
            setTimeout(addFoodForPeople, 500);
        }
    },300);
}
addFoodForPeople();