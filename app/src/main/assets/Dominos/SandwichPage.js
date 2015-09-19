var items = document.querySelectorAll('a.none--handheld[data-clicked-element]'); /*A list of all sandwich item buttons*/
var numPeople = PARTY_SIZE;

function addFoodForPeople()
{
    if (numPeople <= 0)
    {
        return;
    }

    /*Get random item*/
    randomElement(items).click();

    /*Randomize bowl type*/
    setTimeout(function(){
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
            setTimeout(addFoodForPeople, DEFAULT_TIME_BETWEEN_ACTIONS);
        }
    },DEFAULT_TIME_BETWEEN_ACTIONS/3);
}
addFoodForPeople();