javascript:
/* wait for document to be ready and loaded before doing anything */
while (document.readyState !== 'complete');
function pageInteractor()
{
    window.location.href = '/order/view-cart';
}
pageInteractor();