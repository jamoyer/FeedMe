var knownMeals = [];
var knownMealsNum = 0;

var quantities = ['One', 'Two', 'Three', 'Four'];
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

		var newMeal = {name:name, numExLarge:numExLarge, numLarge:numLarge, numMedium:numMedium, numSmall, numSmall, hasDrink:hasDrink(specialText), hasDessert:hasDessert(specialText), price:price, feeds:feeds(numExLarge, numLarge, numMedium, numSmall), link:link};
		knownMeals.push(newMeal);
		knownMealsNum++;
	}
}

var pizza = randomElement(knownMeals);
storeRandomPizza(pizza);
pizza.link.click();

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