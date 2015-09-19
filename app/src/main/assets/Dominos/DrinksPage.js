var buttons = document.querySelectorAll('a.none--handheld[data-clicked-element]'); /*A list of all pizza item buttons*/
var descriptions = document.querySelectorAll('p.none--handheld');   /*A list of all pizza item descriptions*/
var items = []; /*A list to hold non-conflicting item buttons*/
for (var i=0; i<descriptions.length; i++)
{
    /*Check all keywords against each item to form a list of each items ingredients*/
    if (!containsPreferenceConflicts(descriptions[i].innerText))
    {
        console.log("Adding pizza specialty " + i);
        items.push(buttons[i]);
    }
}
var peoplePer20Oz = 1;
var peoplePer2Liter = 3;

function addDrinksForPeople()
{
    if (numPeople <= 0)
    {
        return;
    }

    /*Get random item*/
    randomElement(items).click();

    /*Randomize drink size*/
    attemptFunc(function(){
        var drinks = [];
        var peoplePerDrink = [];

        var twentyOzOptions = document.querySelectorAll('input[type="radio"][data-sizecode="20OZB"]');  /*Should only be size 1 or 0*/
        if (twentyOzOptions.length > 0)
        {
            drinks.push(twentyOzOptions[0]);
            peoplePerDrink.push(peoplePer20Oz);
        }
        /*Don't add two liter option if it would be unnecessary and we don't have enough people.*/
        if(!(drinks.length == 1 && numPeople < 3))
        {
            var twoLiterOptions = document.querySelectorAll('input[type="radio"][data-sizecode="2LTB"]');  /*Should only be size 1 or 0*/
            if (twoLiterOptions.length > 0)
            {
                drinks.push(twoLiterOptions[0]);
                peoplePerDrink.push(peoplePer2Liter);
            }
        }

        var index = getRandomInt(0, drinks.length);

        drinks[index].checked = true;

        /*Add to order*/
        document.querySelectorAll('button.btn.js-isNew')[0].click();
        numPeople -= peoplePerDrink[index];

        /*If we have fed all people go to next page otherwise order food*/
        if (numPeople <= 0)
        {
            /*wait some time before going to next page to let clicks finish*/
            window.location.href = 'https://www.dominos.com/en/pages/order/#/checkout/';
        }
        else
        {
            setTimeout(addDrinksForPeople, DEFAULT_TIME_BETWEEN_ACTIONS);
        }
    }, DEFAULT_TIME_BETWEEN_ACTIONS/5);
}
addDrinksForPeople();