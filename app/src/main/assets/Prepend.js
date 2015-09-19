javascript:
/* Returns the name of the function */
function functionName(fun)
{
    var ret = fun.toString();
    ret = ret.substr('function '.length);
    ret = ret.substr(0, ret.indexOf('('));
    return ret;
}
var DEFAULT_RETRIES = 10;
var DEFAULT_SLEEP_TIME = 1000;
var DEFAULT_TIME_BETWEEN_ACTIONS = 1000;
/* Attempts the function "func" "retries" number of times, waiting "sleepTime" ms between each try. */
function attemptFunc(func, sleepTime, retries)
{
    if(typeof retries === "undefined")
    {
        retries = DEFAULT_RETRIES;
    }
    if(typeof sleepTime === "undefined")
    {
        sleepTime = DEFAULT_SLEEP_TIME;
    }
    if (0 >= retries--)
    {
        return;
    }
    console.log('Trying ' + functionName(func));
    try
    {
        func();
    }
    catch (err)
    {
        console.log('Error: ' + err.toString() +
            "\nRetrying in " + sleepTime + 'ms. Retries left: ' + retries);
        function doFunc()
        {
            attemptFunc(func, sleepTime, retries);
        }
        setTimeout(doFunc, sleepTime);
    }
}
function selectOption(selectElement, optionValue)
{
    for (var i=0; i<selectElement.options.length; i++)
    {
        if (selectElement.options[i].value && selectElement.options[i].value == optionValue)
        {
            selectElement.selectedIndex = i;
            console.log('Set option to ' + optionValue.toString());
            break;
        }
    }
}
function randomElement(arr)
{
    /* check if arr is valid array with some elements */
    if (typeof arr !== "undefined" && arr !== null && arr.length > 0)
    {
        return arr[getRandomInt(0, arr.length)];
    }
    return null;
}
function getRandomInt(min, max)
{
    return Math.floor(Math.random() * (max - min)) + min;
}
var numPeople = PARTY_SIZE;
var preferenceKeyWords = [
    {
        preferenceType : PREFERENCE_PEPPERONI,
        keywords : [
            "pepperoni"
        ]
    },
    {
        preferenceType : PREFFERENCE_GRILLED_CHICKEN,
        keywords : [
            "chicken"
        ]
    },
    {
        preferenceType : PREFERENCE_BEEF,
        keywords : [
            "beef"
        ]
    },
    {
        preferenceType : PREFERENCE_BACON,
        keywords : [
            "bacon"
        ]
    },
    {
        preferenceType : PREFERENCE_SAUSAGE,
        keywords : [
            "sausage"
        ]
    },
    {
        preferenceType : PREFERENCE_ANCHOVIES,
        keywords : [
            "anchovies"
        ]
    },
    {
        preferenceType : PREFERENCE_PINEAPPLE,
        keywords : [
            "pineapple"
        ]
    },
    {
        preferenceType : PREFERENCE_MUSHROOMS,
        keywords : [
            "mushroom"
        ]
    },
    {
        preferenceType : PREFERENCE_SAUERKRAUT,
        keywords : [
            "sauerkraut"
        ]
    },
    {
        preferenceType : PREFERENCE_ONIONS,
        keywords : [
            "onion"
        ]
    },
    {
        preferenceType : PREFERENCE_BLACK_OLIVES,
        keywords : [
            "olives"
        ]
    },
    {
        preferenceType : PREFERENCE_JALAPENO_PEPPERS,
        keywords : [
            "jalapeno", "jalapeño"
        ]
    }
];
function containsPreferenceConflicts(text)
{
    for (var i=0; i<preferenceKeyWords.length; i++)
    {
        var doesExclude = preferenceKeyWords[i].preferenceType;
        var keywords = preferenceKeyWords[i].keywords;
        for (var j=0; j<keywords.length; j++)
        {
            if (text.indexOf(keywords[j]) > -1 && doesExclude)
            {
                return true;
            }
        }
    }
    return false;
}
function pageInteractor()
{