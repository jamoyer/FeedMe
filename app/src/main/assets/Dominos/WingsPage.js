var items = document.querySelectorAll('a.none--handheld[data-clicked-element]'); /*A list of all wings item buttons*/
var specialties = [0,1,2,3];    /*Index of 12 piece specialties*/
var boneless = 4;   /*Index of boneless options*/
var boneIn = 5;     /*Index of bone in options*/

/*Add indexes to options array*/
var options = [];
options = options.concat(specialties);
options.push(boneless);
options.push(boneIn);

function doManyWings(sizeButtons)
{
    var dippingCupSelects = document.querySelectorAll('select.side-select');

    var onePersonIndex = 0;          /*8 pieces*/
    /*TODO: the 14 piece option*/
    var fourPeopleIndex = 2;         /*40 pieces*/

    var possibleSizes = [onePersonIndex];
    var numDipCups;

    if (numPeople >= 4)
    {
        possibleSizes.push(fourPeopleIndex);
    }
    /*Randomly choose a size*/
    var size = randomElement(possibleSizes);
    sizeButtons[size].checked = true;
    if (size == onePersonIndex)
    {
        numDipCups = 1;
    }
    else
    {
        numDipCups = 5;
    }

    /*Initialize dipping cups choices to 0 for each*/
    var dippingCupChoices = [];
    for(var i=0; i<dippingCupSelects.length; i++)
    {
        dippingCupChoices.push(0);
    }

    /*Choose what dipping cups we want*/
    for(var i=0; i<numDipCups; i++)
    {
        var dipCup = getRandomInt(0, dippingCupChoices.length);
        dippingCupChoices[dipCup]++;
    }

    /*Set the dipping cup choices*/
    for(var i=0; i<dippingCupChoices.length; i++)
    {
        selectOption(dippingCupSelects[i], dippingCupChoices[i]);
    }

    /*Return the number of people to feed*/
    if (size == onePersonIndex)
    {
        return 1;
    }
    return 4;
}

function addFoodForPeople()
{
    if (numPeople <= 0)
    {
        return;
    }

    /*Get random item*/
    var index = randomElement(options);
    items[index].click();

    /*Continue with the customization of the item to add.*/
    /*Set timeout to let page load some.*/
    attemptFunc(function(){
        var peopleFed;
        if (index == boneless)
        {
            var sizeButtons = document.querySelectorAll('input[type="radio"][data-flavorcode="BCHICK"]');
            peopleFed = doManyWings(sizeButtons);
        }
        else if (index == boneIn)
        {
            var wingTypeHeaders = document.querySelectorAll('h3.card--list-accordion__acc-header');
            var wingSizeRadios = document.querySelectorAll('div.card--list-accordion__acc-body input[type="radio"]');

            /*Randomly pick type of wing*/
            var typeIndex = getRandomInt(0, wingTypeHeaders.length);
            wingTypeHeaders[typeIndex].click();
            /*Add the different sizes*/
            var sizes = [];
            sizes.push(wingSizeRadios[typeIndex*3]);
            sizes.push(wingSizeRadios[typeIndex*3+1]);
            sizes.push(wingSizeRadios[typeIndex*3+2]);
            peopleFed = doManyWings(sizes);
        }
        else
        {
            peopleFed = 1;
        }

        /*Add to order*/
        document.querySelectorAll('button.btn.js-isNew')[0].click();
        numPeople -= peopleFed;

        /*If we have fed all people go to next page otherwise order food*/
        if (numPeople <= 0)
        {
            /*wait some time before going to next page to let clicks finish*/
            window.location.href = 'https://www.dominos.com/en/pages/order/#/section/Food/category/AllDrinks/';
        }
        else
        {
            attemptFunc(addFoodForPeople, DEFAULT_TIME_BETWEEN_ACTIONS);
        }
    },DEFAULT_TIME_BETWEEN_ACTIONS / 3);
}
addFoodForPeople();