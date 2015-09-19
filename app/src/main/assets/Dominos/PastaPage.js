var buttons = document.querySelectorAll('a.none--handheld[data-clicked-element]'); /*A list of all pasta item buttons*/
/*Make an array of all elements except the last one*/
var temp = [];
for(var i=0; i<buttons.length-1;i++){
    temp.push(buttons[i]);
}
buttons = temp;
var descriptions = document.querySelectorAll('p.none--handheld');   /*A list of all pasta item descriptions*/
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
    attemptFunc(function(){
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
            window.location.href = 'https://www.dominos.com/en/pages/order/#/section/Food/category/AllDrinks/';
        }
        else
        {
            setTimeout(addFoodForPeople, DEFAULT_TIME_BETWEEN_ACTIONS);
        }
    },DEFAULT_TIME_BETWEEN_ACTIONS / 5);
}
addFoodForPeople();