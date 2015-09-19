function doClassics()
{
    var popularItems = document.getElementsByClassName('card__list-item__title');
    var numPeople = PARTY_SIZE;
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
            },500);
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
                /*window.location.href = 'https://www.dominos.com/en/pages/order/#/section/Food/category/AllSides/'; want to go to sides next*/
                window.location.href = 'https://www.dominos.com/en/pages/order/#/checkout/';
            },2000);
        }
        else
        {
            setTimeout(addFoodForPeople, 500);
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
/*doClassics();*/
window.location.href = 'https://www.dominos.com/en/pages/order/#/section/Food/category/Pizza/';
/*var randomOption = getRandomInt(0, 5);
switch (randomOption)
{
    case 0:
        doClassics();
        break;
    case 1:
        window.location.href = 'https://www.dominos.com/en/pages/order/#/section/Food/category/Pizza/';
        break;
    case 2:
        window.location.href = 'https://www.dominos.com/en/pages/order/#/section/Food/category/Pasta/';
        break;
    case 3:
        window.location.href = 'https://www.dominos.com/en/pages/order/#/section/Food/category/Sandwich/';
        break;
    case 4:
        window.location.href = 'https://www.dominos.com/en/pages/order/#/section/Food/category/Wings/';
        break;
}*/