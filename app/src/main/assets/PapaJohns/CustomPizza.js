var step = 0;

function makePizza()
{
    var tester = Android.getInfo('name');
    Android.showToast(tester);
    var active = document.getElementsByClassName('active')[1].getAttribute('class');

    if (active.indexOf("crust") > -1)
    {
        Android.showToast('TRUEEE');
        var selectElement = document.querySelectorAll('select');
        /*TODO: If preferences are impossible (no square or wedge cut, for example) handles it somehow*/
        /*TODO: Parse instead of hard coding ints*/

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
        document.getElementsByClassName('stepButton')[step].click();
        step++;
        makePizza();
    }

    /*Select Toppings*/
    else if(active.indexOf("topping") > -1)
    {

    }
}

makePizza();