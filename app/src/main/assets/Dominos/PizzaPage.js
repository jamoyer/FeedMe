var items = document.querySelectorAll('a.none--handheld[data-clicked-element]'); /*A list of all pizza item buttons*/

function addFoodForPeople()
{
    if (numPeople <= 0)
    {
        return;
    }

    var possibleSizes = []; /*0=small,1=medium,2=large*/
    var large = 2;
    var medium = 1;
    var small = 0;
    /* add possible choices based on how many people there are*/
    if (numPeople >= 3)
    {
        possibleSizes.push(medium);
    }
    if (numPeople >= 2)
    {
        possibleSizes.push(large);
    }
    possibleSizes.push(small);


    /*Get random item*/
    var item = randomElement(items);
    var size = randomElement(possibleSizes);
    item.click();
    setTimeout(function(){
        document.querySelectorAll('button.btn--large.js-closePizzaMessage')[0].click(); /*Choose customize to customize size*/
        document.querySelectorAll('input[type="radio"][data-flavorcode="HANDTOSS"]')[size].checked = true;  /*choose size*/

        if (size == medium)
        {
            /*Get another medium to equal 3 people*/
            var select = document.querySelectorAll('select.quantity')[0];
            selectOption(select, 2);
        }
        /*Add the item to the cart*/
        document.querySelectorAll('button.btn--large.js-addToOrder')[0].click();
        setTimeout(function(){
            try {
                /*disregard extra cheese message*/
                document.querySelectorAll('button.c-cheeseUpsell-No')[0].click();
            } catch (err) {
                /*it will throw an exception if the button isn't there which happens every time but the first. Just ignore.*/
            }
        }, 100);

        /*Decrement the number of people to feed*/
        if(size == medium)
        {
            numPeople -= 3;
        }
        else if(size == large)
        {
            numPeople -= 2;
        }
        else
        {
            numPeople -= 1;
        }

        /*If we have fed all people go to next page otherwise order food*/
        if (numPeople <= 0)
        {
            /*wait some time before going to next page to let clicks finish*/
            window.location.href = 'https://www.dominos.com/en/pages/order/#/section/Food/category/AllDrinks/';
        }
        else
        {
            setTimeout(addFoodForPeople, DEFAULT_TIME_BETWEEN_ACTIONS);
        }
    }, DEFAULT_TIME_BETWEEN_ACTIONS / 3);
}
addFoodForPeople();