function makePizzas()
{
    var active = document.getElementsByClassName('active')[1].getAttribute('class');

    if (active.indexOf("crust") > -1)
    {
        var selectElement = document.querySelectorAll('select');
        /*TODO: If preferences are impossible (no square or wedge cut, for example) handles it somehow*/
        /*TODO: Parse instead of hard coding ints*/

        /*Select Crust*/
        if(PREFERENCE_ORIGINAL_CRUST)
        {
            selectOption(selectElement[0], 2);
        }
        else if(PREFERENCE_THIN_CRUST)
        {
            selectOption(selectElement[0], 1);
        }
        else
        {
            selectOption(selectElement[0], getRandomInt(1,2));
        }


        /*Select Cut*/
        if(PREFERENCE_NORMAL_CUT)
        {
            selectOption(selectElement[1], 11);
        }
        else if(PREFERENCE_SQUARE_CUT)
        {
            selectOption(selectElement[1], 10);
        }
        else
        {
            selectOption(selectElement[1], getRandomInt(10,11));
        }

        /*Select Cut*/
        if(PREFERENCE_NORMAL_CUT)
        {
            selectOption(selectElement[2], 8);
        }
        else if(PREFERENCE_SQUARE_CUT)
        {
            selectOption(selectElement[2], 9);
        }
        else
        {
            selectOption(selectElement[2], getRandomInt(8,9));
        }
    }
}

