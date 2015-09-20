var buttons = document.querySelectorAll('a.none--handheld[data-clicked-element]'); /*A list of all pizza item buttons*/
var descriptions = document.querySelectorAll('p.none--handheld');   /*A list of all pizza item descriptions*/
var items = []; /*A list to hold non-conflicting item buttons*/
for (var i=0; i<descriptions.length; i++)
{
    /*Check all keywords against each item to form a list of each items ingredients*/
    if (!containsPreferenceConflicts(descriptions[i].innerText))
    {
        console.log("Adding sandwich " + i);
        items.push(buttons[i]);
    }
}
function addFoodForPeople()
{
    if (numPeople <= 0)
    {
        return;
    }

    /*Get random item*/
    randomElement(items).click();

    /*Randomize bowl type*/
    attemptFunc(function(){
        /*Add to order*/
        document.querySelectorAll('button.btn.js-isNew')[0].click();
        numPeople--;

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
    },DEFAULT_TIME_BETWEEN_ACTIONS / 3);
}
addFoodForPeople();