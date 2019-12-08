
var filters = {
    fromPrice: 0,
    toPrice: 0,
    categoryId: "all",
};

var pagingProduct = {
    data: [],
    limit: 12,
    offset: 0
}

var canLoadMore = true;
var quickSearchTimeout = null;
var loadMoreTimeout = null;
var isLoading = false;

var $fromPrice = document.getElementById("fromPrice");
var $toPrice = document.getElementById("toPrice");
var $selectCategory = document.getElementById("selectCategory");
var $btnSearch = document.getElementById("btnSearch");
var $productList = document.getElementById("productList");
var $inputQuickSearch = document.getElementById("inputQuickSearch");
var $spinner = document.getElementById("spinner");
var $numOfProducts = document.getElementById("numOfProducts");
var $noMoreProducts = document.getElementById("noMoreProducts");

$fromPrice.value = 0;
$toPrice.value = 0;

$btnSearch.addEventListener("click", handleSearchProducts);

$inputQuickSearch.addEventListener("keyup", function (e) {
    clearTimeout(quickSearchTimeout);
    quickSearchTimeout = setTimeout(function (){
        handleQuickSearch(e);
    }, 300);
});

window.onscroll = function(ev) {
    if ((window.innerHeight + window.pageYOffset) >= document.body.offsetHeight) {
        clearTimeout(loadMoreTimeout);
        loadMoreTimeout = setTimeout(function () {
            window.scrollBy(0, -50);
            if(canLoadMore && !isLoading) {
                handleLoadMoteProducts();
            }
        }, 200);
    }
};

handleSearchProducts();

function renderNumOfProducts(numOfProducts){
    $numOfProducts.textContent = numOfProducts;
}

function handleQuickSearch(e){
    var value = e.target.value;
    var $list = $productList.getElementsByClassName('product-item');
   
    for(var i = 0; i < pagingProduct.data.length; i++){
        if(StringUtils.containsWithoutVnAccents(name, value)){
            $list[i].classList.remove('d-none');
        } else {
            $list[i].classList.add('d-none');
        }
    }
}

function handleSearchProducts(){
    
    var categoryId = $selectCategory.value;
    var fromPrice = $fromPrice.value;
    var toPrice = $toPrice.value;
    
    if(ValidationUtils.isEmpty(fromPrice) || ValidationUtils.isEmpty(toPrice)) return alert("Price is required!");
        
    if(!ValidationUtils.isNumber(fromPrice) || !ValidationUtils.isNumber(toPrice)) return alert("From price and to price must be valid number!");
    if(Number(fromPrice) > Number(toPrice)) return alert("From price must be less than to price!");
    
    filters = {
        fromPrice: fromPrice,
        toPrice: toPrice,
        categoryId: categoryId,
    };
    
    pagingProduct = {
        limit: 12,
        offset: 0
    }
    
    canLoadMore = true;
    
    showSpinner();
    
    isLoading = true;
    
    var xmlHtppOptions = {
        method: "GET",
        url: "/api/products?categoryId=" + categoryId + "&fromPrice=" + fromPrice + "&toPrice=" + toPrice + "&limit=" + 12 + "&offset=" + 0,
        success: function (res){
            var products = getProductsFromXMLString(res);
            pagingProduct.data = products;
            pagingProduct.offset = pagingProduct.offset + products.length;
            
            renderProducts(products);
            hideSpinner();
            renderNumOfProducts(pagingProduct.data.length);
            
            isLoading = false;
        }, 
    };
    
    XMLHttpUtils.makeRequest(xmlHtppOptions);
}

function showSpinner(){
    $spinner.classList.remove("d-none");
}

function hideSpinner(){
    $spinner.classList.add("d-none");
}

function renderNoMoreproductsFound() {
    $noMoreProducts.textContent = 'No More Products Found';
}

function handleLoadMoteProducts(){
    showSpinner();
    var fromPrice = filters.fromPrice;
    var toPrice = filters.toPrice;
    var categoryId = filters.categoryId;
    var limit = pagingProduct.limit;
    var offset = pagingProduct.offset;
    
    var url = "/api/products?categoryId=" + categoryId + "&fromPrice=" + fromPrice + "&toPrice=" + toPrice + "&limit=" + limit + "&offset=" + offset;
    
    isLoading = true;
    
    var xmlHtppOptions = {
        method: "GET",
        url: url,
        success: function (res){
            var products = getProductsFromXMLString(res);
            if(!ValidationUtils.isEmpty(products)){
                pagingProduct.data = pagingProduct.data.concat(products);
                pagingProduct.offset = offset + products.length;
                
                renderMoreProducts(products);
                renderNumOfProducts(pagingProduct.data.length);
            } else {
                canLoadMore = false;
                renderNoMoreproductsFound();
            }
            hideSpinner();
            isLoading = false;
        }, 
    };
    
    XMLHttpUtils.makeRequest(xmlHtppOptions);
}

function getProductsFromXMLString(xml){
    var products = [];
    var parser = new DOMParser();
    var xmlDoc = parser.parseFromString(xml, "text/xml");
    var productNodeList = xmlDoc.getElementsByTagName("product");
    for(var i = 0; i < productNodeList.length; i++){
        var productNode = productNodeList[i];
        
        var id = XMLHttpUtils.getTextNodeContent(productNode, "id");
        var name = XMLHttpUtils.getTextNodeContent(productNode, "name");
        var categoryId = XMLHttpUtils.getTextNodeContent(productNode, "categoryId");
        var price = XMLHttpUtils.getTextNodeContent(productNode, "price");
        var description = XMLHttpUtils.getTextNodeContent(productNode, "description");
        var image = XMLHttpUtils.getTextNodeContent(productNode, "image");
        var colors = XMLHttpUtils.getTextNodeContent(productNode, "colors");
        var sizes = XMLHttpUtils.getTextNodeContent(productNode, "sizes");
        var link = XMLHttpUtils.getTextNodeContent(productNode, "link");
        var avgVotes = XMLHttpUtils.getTextNodeContent(productNode, "avgVotes");
        var votes = XMLHttpUtils.getTextNodeContent(productNode, "votes");
        
        var product = {
            id: id,
            name: name,
            categoryId: categoryId,
            price: price,
            description: description,
            image: image,
            colors: colors,
            szies: sizes,
            link: link,
            avgVotes: avgVotes,
            votes: votes
        }
        
        products.push(product);
    }
    
    return products
}

function getResponseObjectFromXMLResponse(xml) {
    var parser = new DOMParser();
    var xmlDoc = parser.parseFromString(xml, "text/xml");
    var $data = xmlDoc.getElementsByTagName("data")[0];
    var success = XMLHttpUtils.getTextNodeContent($data, "success");
    var errors = XMLHttpUtils.getTextNodeContent($data, "errors");
    return { success: success, errors: errors }; 
}

function renderProducts(products){
    $productList.innerHTML = '';
    if(!ValidationUtils.isEmpty(products)){
        for(var i = 0; i < products.length; i++){
            var product = products[i];
            var $div = createSingleProductCard(product);
                    
            $productList.appendChild($div);
        }
    } else {
        $productList.innerHTML = '<div class="col-12"><h3 class="text-center">No products Found</h3></div>'
    }
}

function createSingleProductCard(product) {
    var $div = document.createElement('div');
    $div.className = 'col-lg-3 col-md-4 col-sm-6 mb-4 product-item';
    $div.innerHTML = '<div class="card" style="width: 100%">'+
                        '<img src="'+ product.image + '" class="card-img-top" alt="">'+
                        '<div class="card-body">'+
                            '<h5 class="card-title">'+ product.name +'</h5>'+
                            '<p class="card-text">'+ StringUtils.getPriceFormatted(product.price) +' vnd</p>'+
                            '<p class="card-text text-warning font-weight-bold">'+ product.avgVotes +' stars</p>'+
                            '<div class="stars-rating">'+
                                '<span class="fa fa-star star-rating"></span>'+
                                '<span class="fa fa-star star-rating"></span>'+
                                '<span class="fa fa-star star-rating"></span>'+
                                '<span class="fa fa-star star-rating"></span>'+
                                '<span class="fa fa-star star-rating"></span>'+
                            '</div>'+
                            '<a href="'+ CONTEXT_PATH + '/products?productId='+ product.id +'" class="btn btn-primary float-right">Details</a>'+
                        '</div>'+
                    '</div>';
    var $starList = $div.getElementsByClassName('star-rating');
    for(var i = 0; i < $starList.length; i++){
        (function (i) {
            $starItem = $starList[i];
            if(product.votes > i) $starItem.classList.add('checked');

            $starItem.addEventListener('click', function () {
                console.log('vote ' + (i +1) + ' star');
                handleClickStarRating($starList, i, product);
            });
        })(i)
    }
    return $div;
}

function handleClickStarRating($starList, index, product) {
    
    var url = "/api/votes?productId=" + product.id + "&votes=" + (index+1);
    
    var xmlHtppOptions = {
        method: "GET",
        url: url,
        success: function (res){
            console.log(res);
            var result = getResponseObjectFromXMLResponse(res);
            if(result.success == 'true') {
                for(var i = 0; i < $starList.length; i++){
                    $starList[i].classList.remove('checked');
                    if(i <= index) $starList[i].classList.add('checked');
                }
                setTimeout(function () {
                    alert('You have rated ' + product.name + ' ' + (index + 1) + ' star');
                }, 100);
            } else {
                return alert(result.errors);
            }
        }, 
    };
    
    XMLHttpUtils.makeRequest(xmlHtppOptions);

}

function renderMoreProducts(products){
    var currentQuixkSearchVal = $inputQuickSearch.value;
    
    for(var i = 0; i < products.length; i++){
        var product = products[i];
        var $div = createSingleProductCard(product);
        if(!StringUtils.containsWithoutVnAccents(product.name, currentQuixkSearchVal)) {
            $div.classList.add('d-none');
        }
        $productList.appendChild($div);
    }
}



