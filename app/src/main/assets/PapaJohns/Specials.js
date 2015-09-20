var knownMeals = [];
var knownMealsNum = 0;
var pricePerPerson = PREFERENCE_COST_PER_PERSON;
var partySize = PARTY_SIZE;

var quantities = ['One', 'Two', 'Three', 'Four', 'Five'];
var sizes = ['Extra Large', 'Large', 'Medium', 'Small'];
var specialList = document.getElementsByClassName('product-specials product');
for (var z = 0; z < specialList.length; z++)
{
	var currentElement = specialList[z];

	/*get quantities and sizes of pizzas*/
	specialText = currentElement.children[1].children[1].innerHTML;
	var numExLarge = occurrences(specialText, 'Extra Large');
	var tempLarge = occurrences(specialText, 'Large')-numExLarge;
	var numLarge = tempLarge > 0 ? tempLarge : 0;
	var numMedium = occurrences(specialText, 'Medium');
	var numSmall = occurrences(specialText,'Small');

    /*calculate number of each size*/
	for(var i = 0; i < quantities.length; i++)
	{
		for(var j = 0; j < sizes.length; j++)
		{
			var pizzaSize = sizes[j];
			var tempString = quantities[i]+' '+pizzaSize;
			var newNumber = occurrences(specialText, tempString) * (i+1);
			switch(pizzaSize)
			{
				case 'Extra Large':
					numExLarge = newNumber > numExLarge ? newNumber : numExLarge;
					break;
				case 'Large':
					numLarge = newNumber > numLarge ? newNumber : numLarge;
					break;
				case 'Medium':
					numMedium = newNumber > numMedium ? newNumber : numMedium;
					break;
				case 'Small':
					numSmall = newNumber > numSmall ? newNumber : numSmall;
					break;
			}
		}
	}

	var sumOfPizzas = numExLarge + numLarge + numMedium + numSmall;

	/*Get other attributes and add to list if this is a pizza meal*/
	if (currentElement.getElementsByClassName('price feature-red')[0] != null && sumOfPizzas != 0)
	{
		var priceWithDollar = currentElement.getElementsByClassName('price feature-red')[0].innerText;
		var price = priceWithDollar.substring(1, priceWithDollar.length);
		var link = currentElement.getElementsByClassName('button-small button')[0];
		var name = currentElement.getElementsByClassName('product-image')[0].getAttribute('alt');

		var numOneTopping = 0;
		var numTwoTopping = 0;
		var numThreeTopping = 0;
		var numFourTopping = 0;
		var numFiveTopping = 0;

		/* Calculate Number of Toppings */
		for(var i = 0; i < quantities.length; i++)
        {
            for(var j = 0; j < sizes.length; j++)
            {
                var numToppings = quantities[i];
                var tempString = sizes[j]+' '+quantities[i];
                var newNumber = occurrences(specialText, tempString);
				if (newNumber == 0)
				{
					var tempString = sizes[j]+' '+quantityToInt(quantities[i]);
					newNumber = occurrences(specialText, tempString);
				}
                switch(numToppings)
                {
                    case 'One':
                        numOneTopping += newNumber;
                        break;
                    case 'Two':
                        numTwoTopping += newNumber;
                        break;
                    case 'Three':
                        numThreeTopping += newNumber;
                        break;
                    case 'Four':
                        numFourTopping += newNumber;
                        break;
                    case 'Five':
                       numFiveTopping += newNumber;
                        break;
                }
            }
        }

		for(var i = 0; i < quantities.length; i++)
		{
			for(var j = 0; j < sizes.length; j++)
			{
				for(var k = 0; k < quantities.length; k++)
				{
					var toppingNum = quantities[k];
					var tempString = quantities[i]+' '+sizes[j]+' '+quantities[k];
					var newNumber = occurrences(specialText, tempString) * (i+1);
					switch(toppingNum)
					{
						case 'One':
							numOneTopping = newNumber > numOneTopping ? newNumber : numOneTopping;
							break;
						case 'Two':
							numTwoTopping = newNumber > numTwoTopping ? newNumber : numTwoTopping;
							break;
						case 'Three':
							numThreeTopping = newNumber > numThreeTopping ? newNumber : numThreeTopping;
							break;
						case 'Four':
							numFourTopping = newNumber > numFourTopping ? newNumber : numFourTopping;
							break;
						case 'Five':
							numFiveTopping = newNumber > numFiveTopping ? newNumber : numFiveTopping;
							break;
					}
				}
			}
		}

        for(var i = 0; i < sizes.length; i++)
        {
            var pizzaSize = sizes[i];
            var tempString = 'Any '+pizzaSize;
            var newNumber = occurrences(specialText, tempString);
            numFiveTopping += newNumber;
        }

		var newMeal = {name:name, numExLarge:numExLarge, numLarge:numLarge, numMedium:numMedium, numSmall:numSmall, hasDrink:hasDrink(specialText), hasDessert:hasDessert(specialText), price:price, feeds:feeds(numExLarge, numLarge, numMedium, numSmall), link:link, numOneTopping:numOneTopping, numTwoTopping:numTwoTopping, numThreeTopping:numThreeTopping, numFourTopping:numFourTopping, numFiveTopping:numFiveTopping};
		knownMeals.push(newMeal);
		knownMealsNum++;
	}
}

var misses = 0;
var lightenUp = false;
var pizza = randomElement(knownMeals);
if (partySize != 1)
{
    while(pizza.feeds != partySize || pizza.price > pricePerPerson*partySize)
    {
        pizza = randomElement(knownMeals);
        misses++;
        if(misses > 1000)
        {
            lightenUp = true;
        }
    }
}
else
{
    for (var i = 0; i < knownMeals.length; i++)
    {
        console.log("name is " + knownMeals[i].name);
        if(knownMeals[i].name.indexOf('Medium Pizza') > -1)
        {
            pizza = knownMeals[i];
            break;
        }
    }
    if (pizza.price > pricePerPerson)
    {
        lightenUp = true;
    }
}

if(lightenUp)
{
    Android.showToast('Try less restriced options');
}
else
{
    storeRandomPizza(pizza);
    pizza.link.click();
}


/*find specific special for debugging*/
/*var pizza;
for (var i = 0; i < knownMeals.length; i++)
{
	console.log("name is " + knownMeals[i].name);
	if(knownMeals[i].name.indexOf('Any Lg') > -1)
	{
		pizza = knownMeals[i];
		break;
	}
}*/

function storeRandomPizza(pizza)
{
	Android.putInfo('name',pizza.name);
	Android.putInfo('numExLarge',pizza.numExLarge);
	Android.putInfo('numLarge',pizza.numLarge);
	Android.putInfo('numMedium',pizza.numMedium);
	Android.putInfo('numSmall',pizza.numSmall);
	Android.putInfo('hasDrink',pizza.hasDrink);
	Android.putInfo('hasDessert',pizza.hasDessert);
	Android.putInfo('price',pizza.price);
	Android.putInfo('feeds',pizza.feeds);
	Android.putInfo('link',pizza.link);
	Android.putInfo('numOneTopping',pizza.numOneTopping);
	Android.putInfo('numTwoTopping',pizza.numTwoTopping);
	Android.putInfo('numThreeTopping',pizza.numThreeTopping);
	Android.putInfo('numFourTopping',pizza.numFiveTopping);
	Android.putInfo('numFiveTopping',pizza.numFiveTopping);
	Android.putInfo('numPizzas', pizza.numSmall + pizza.numMedium + pizza.numLarge + pizza.numExLarge);
}

/*maybe update this function to include desserts and sides*/
function feeds(numExLarge, numLarge, numMedium, numSmall)
{
	return Math.round(numExLarge*2.61 + numLarge*2 + numMedium*1.5 + numSmall);
}

function hasDrink(specialText)
{
	return (occurrences(specialText,'liter') > 0);
}

function hasDessert(specialText)
{
	return ( (occurrences(specialText,'chocolate') > 0) || (occurrences(specialText,'cinnamon') > 0) );
}

function occurrences(string, subString, allowOverlapping){
	/*make both upper case to ignore case*/
	string = string.toUpperCase();
	subString = subString.toUpperCase();

    string+=''; subString+='';
    if(subString.length<=0) return string.length+1;

    var n=0, pos=0;
    var step=allowOverlapping?1:subString.length;

    while(true){
        pos=string.indexOf(subString,pos);
        if(pos>=0){ ++n; pos+=step; } else break;
    }
    return n;
}

function quantityToInt(quantity)
{
	switch(quantity)
	{
		case 'One':
			return 1;
		case 'Two':
			return 2;
		case 'Three':
			return 3;
		case 'Four':
			return 4;
		case 'Five':
			return 5;
	}

}