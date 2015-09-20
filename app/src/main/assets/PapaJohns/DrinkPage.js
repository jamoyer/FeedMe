console.log("grabbing drinks");
Android.putInfo('hasDrink','true');
var drinks = document.getElementsByClassName('product');
var maxIndex = drinks.length - 1;
var index = getRandomInt(0, maxIndex);
drinks[index].getElementsByClassName('button-large')[0].click();