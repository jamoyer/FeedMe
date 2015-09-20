function doClassics()
{
    var popularItems = document.getElementsByClassName('card__list-item__title');
    var greaterThan3 = [2,3,4]; /*elements 2,3,4 are medium pizzas which fill up 1.5 people*/
    var greaterThan2 = [0,1];   /*elements 0, 1 are large pizzas which fill up 2 people*/
    var greaterThan1 = [5,6,7]; /*elements 5,6,7 are single person items*/

    function addFoodForPeople()
    {
        if (numPeople <= 0)
        {
            return;
        }

        var possibleIndicies = [];
        /* add possible choices based on how many people there are*/
        if (numPeople >= 3)
        {
            possibleIndicies = possibleIndicies.concat(greaterThan3);
        }
        if (numPeople >= 2)
        {
            possibleIndicies = possibleIndicies.concat(greaterThan2);
        }
        if (numPeople >= 1)
        {
            possibleIndicies = possibleIndicies.concat(greaterThan1);
        }

        /*Get random item*/
        var index = randomElement(possibleIndicies);
        popularItems[index].click();
        console.log('Added ' + popularItems[index].innerText);

        /*Decrement the number of people to feed*/
        if(greaterThan3.indexOf(index) >= 0)
        {
            /*Get another medium to equal 3 people*/
            var secondIndex = randomElement(greaterThan3);
            setTimeout(function(){
                popularItems[secondIndex].click();
                console.log('Added ' + popularItems[secondIndex].innerText);
            },DEFAULT_TIME_BETWEEN_ACTIONS);
            numPeople -= 3;
        }
        else if(greaterThan2.indexOf(index) >= 0)
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
            setTimeout(function(){
                window.location.href = 'https://www.dominos.com/en/pages/order/#/section/Food/category/AllDrinks/';
            },DEFAULT_TIME_BETWEEN_ACTIONS * 3);
        }
        else
        {
            setTimeout(addFoodForPeople, DEFAULT_TIME_BETWEEN_ACTIONS);
        }
    }
    addFoodForPeople();
}
/*
 * Remove all previous items from the cart before adding any.
 * We don't want to screw up and loop and order more items than intended.
 */
var toRemove = document.getElementsByClassName('remove');
console.log(toRemove.length+' items to remove from the cart.');
for(var i=0; i<toRemove.length; i++)
{
    toRemove[i].children[0].click();
    console.log('Removed item ' + i);
}

var allOptions = [
    {
        name : "classics",
        avgCost : 11.53625,
        operation : doClassics
    },
    {
        name : "pizza",
        avgCost : 10.74629167,
        operation : function(){
            window.location.href = 'https://www.dominos.com/en/pages/order/#/section/Food/category/Pizza/';
        }
    },
    {
        name : "pasta",
        avgCost : 7.689,
        operation : function(){
            window.location.href = 'https://www.dominos.com/en/pages/order/#/section/Food/category/Pasta/';
        }
    },
    {
        name : "sandwiches",
        avgCost : 6.589,
        operation : function(){
            window.location.href = 'https://www.dominos.com/en/pages/order/#/section/Food/category/Sandwich/';
        }
    },
    {
        name : "wings",
        avgCost : 7.24521875,
        operation : function(){
            window.location.href = 'https://www.dominos.com/en/pages/order/#/section/Food/category/Wings/';
        }
    },
];

var averageCostPersonDeliveryFee = 2.20 / numPeople;
var averageCostPersonDrinks = 1.441;

var options = [];
for (var i = 0; i < allOptions.length; i++)
{
    /*Add delivery fee to costs*/
    var averageCost = allOptions[i].avgCost + averageCostPersonDeliveryFee + averageCostPersonDrinks;
    /*Add options only if its at most equal to the preferred cost*/
    if (averageCost <= PREFERENCE_COST_PER_PERSON)
    {
        console.log("Adding " + allOptions[i].name + " to the list of possible options.");
        options.push(allOptions[i]);
    }
}

/*pick random option*/
var chosenOption = randomElement(options);
console.log("Chose to do " + chosenOption.name);
setTimeout(chosenOption.operation,DEFAULT_TIME_BETWEEN_ACTIONS);