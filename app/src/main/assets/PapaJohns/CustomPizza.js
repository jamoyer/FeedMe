var step = 0;

function makePizza()
{
    var active = document.getElementsByClassName('active')[0].getAttribute('class');

    if (active.indexOf("crust") > -1)
    {
        var selectElement = document.querySelectorAll('select');
        /*TODO: If preferences are impossible (no square or wedge cut, for example) handles it somehow*/

        /*Select Crust*/
        var crust = selectElement[0];
        if(crust.length>1)
        {
            var origCrust = crust.children[0].value;
            var thinCrust = crust.children[1].value;
            var values = [];
            values.push(origCrust);
            values.push(thinCrust);
            if(PREFERENCE_ORIGINAL_CRUST)
            {
                selectOption(crust, thinCrust);
            }
            else if(PREFERENCE_THIN_CRUST)
            {
                selectOption(selectElement[0], origCrust);
            }
            else
            {
                selectOption(selectElement[0], randomElement(values));
            }
        }

        /*Select Cut*/
        var cut = selectElement[1];
        if(cut.length>1)
        {
            var normalCut = cut.children[0].value;
            var squareCut = cut.children[1].value;
            var values = [];
            values.push(normalCut);
            values.push(squareCut);
            if(PREFERENCE_NORMAL_CUT)
            {
                selectOption(cut, squareCut);
            }
            else if(PREFERENCE_SQUARE_CUT)
            {
                selectOption(cut, normalCut);
            }
            else
            {
                selectOption(cut, randomElement(values));
            }
        }

        /*Select Bake*/
        var bake = selectElement[2];
        if(bake.length > 1)
        {
            var normalBake = bake.children[0].value;
            var wellBake = bake.children[1].value;
            var values = [];
            values.push(normalBake);
            values.push(wellBake);
            if(PREFERENCE_NORMAL_BAKE)
            {
                selectOption(bake, wellBake);
            }
            else if(PREFERENCE_WELL_DONE_BAKE)
            {
                selectOption(bake, normalBake);
            }
            else
            {
                selectOption(bake, randomElement(values));
            }
        }

    document.getElementsByClassName('stepButton')[step].click();
    step++;
    makePizza();
    }

    /*Select Sauces*/
    else if(active.indexOf("sauce") > -1)
    {
        /*TODO*/
        console.log("Adding sauce!");
        document.getElementsByClassName('stepButton')[step].click();
        step++;
        makePizza();
    }

    /*Select Toppings*/
    else if(active.indexOf("topping") > -1)
    {
        setTimeout(addToppings, 1000);
    }
}

function addToppings()
{
    console.log("Adding toppings!");
    Android.generateToppings();
    var nextTopping = Android.getNextTopping();
    while(nextTopping != null)
    {
        console.log("Adding next topping = " + nextTopping);
        selectTopping(nextTopping);
        nextTopping = Android.getNextTopping();
    }
}

function selectTopping(nextTopping)
{
    console.log("CustomPizza adding topping: "+ nextTopping);
    var toppings = document.querySelectorAll('img');
    for(var i = 0; i < toppings.length; i++)
    {
        /*mutate nextTopping for now to avoid preprocessing*/
        if (correctTopping(toppings[i].getAttribute('alt'), nextTopping.substring(1)))
        {
            console.log("MADE IT HERE --- CLICKING");
            toppings[i].click();
        }
    }
}

function correctTopping(tenativeTopping, neededTopping)
{
    console.log('attribute is ' + tenativeTopping);
    console.log('neededTopping is ' + neededTopping);
    if (tenativeTopping == '3-Cheese Blend')
    {
        return neededTopping == 'REFERENCE_THREE_CHEESE_BLEND';
    }
    else if(tenativeTopping == 'Parmesan Romano')
    {
        return neededTopping == 'REFERENCE_PARMESAN';
    }
    else if(tenativeTopping == 'Extra Cheese')
    {
        return neededTopping == 'REFERENCE_EXTRA_CHEESE';
    }
    else if(tenativeTopping == 'Pineapple')
    {
        return neededTopping == 'REFERENCE_PINEAPPLE';
    }
    else if(tenativeTopping == 'Roma Tomatoes')
    {
        return neededTopping == 'REFERENCE_ROMA_TOMATOES';
    }
    else if(tenativeTopping == 'Green Olives')
    {
        return neededTopping == 'REFERENCE_GREEN_OLIVES';
    }
    else if(tenativeTopping == 'Mushrooms')
    {
        return neededTopping == 'REFERENCE_MUSHROOMS';
    }
    else if(tenativeTopping == 'Sauerkraut')
    {
        return neededTopping == 'REFERENCE_SAUERKRAUT';
    }
    else if(tenativeTopping == 'Onions')
    {
        return neededTopping == 'REFERENCE_ONIONS';
    }
    else if(tenativeTopping == 'Black Olives')
    {
        return neededTopping == 'REFERENCE_BLACK_OLIVES';
    }
    else if(tenativeTopping == 'Jalapeño Peppers')
    {
        return neededTopping == 'REFERENCE_JALAPENO_PEPPERS';
    }
    else if(tenativeTopping == 'Pepperoni')
    {
        return neededTopping == 'REFERENCE_PEPPERONI';
    }
    else if(tenativeTopping == 'Bacon')
    {
        return neededTopping == 'REFERENCE_BACON';
    }
    else if(tenativeTopping == 'Grilled Chicken')
    {
        return neededTopping == 'REFFERENCE_GRILLED_CHICKEN';
    }
    else if(tenativeTopping == 'Sausage')
    {
        return neededTopping == 'REFERENCE_SAUSAGE';
    }
    else if(tenativeTopping == 'Beef')
    {
        return neededTopping == 'REFERENCE_BEEF';
    }
    else if(tenativeTopping == 'Canadian Bacon')
    {
        return neededTopping == 'REFERENCE_CANDADIAN_BACON';
    }
    else if(tenativeTopping == 'Spicy Italian Sausage')
    {
        return neededTopping == 'REFERENCE_SPICY_ITALIAN_SAUSAGE';
    }
    else if(tenativeTopping == 'Anchovies')
    {
        return neededTopping == 'REFERENCE_ANCHOVIES';
    }
    return false;
}

makePizza();