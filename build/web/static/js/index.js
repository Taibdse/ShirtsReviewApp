
var filters = {
    fromPrice: 0,
    toPrice: 0,
    categoryId: 0,
};

var pagingProduct = {
    data: [],
    limit: 12,
    offset: 0
}
var canLoadMore = true;
var quickSearchTimeout = null;

var $fromPrice = document.getElementById("fromPrice");
var $toPrice = document.getElementById("toPrice");
var $selectCategory = document.getElementById("selectCategory");
var $btnSearch = document.getElementById("btnSearch");
var $productList = document.getElementById("productList");
var $inputQuickSearch = document.getElementById("inputQuickSearch");
var $spinner = document.getElementById("spinner");

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
        if(canLoadMore) handleLoadMoteProducts();
//        clearTimeout(loadMoreTimeout);
//        loadMoreTimeout = setTimeout(function () {
//            handleLoadMoteProducts();
//        }, 200);
    }
};

handleSearchProducts();

function handleQuickSearch(e){
    var value = e.target.value;
    var $list = $productList.getElementsByClassName('product-item');
    console.log($list[0]);
    for(var i = 0; i < pagingProduct.data.length; i++){
        var name = pagingProduct.data[i].name;
        name = StringUtils.removeVnAccents(name).toLowerCase();
        value = StringUtils.removeVnAccents(value).toLowerCase();
        if(name.indexOf(value) > -1){
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
    
    var xmlHtppOptions = {
        method: "GET",
        url: "/api/products?categoryId=" + categoryId + "&fromPrice=" + fromPrice + "&toPrice=" + toPrice + "&limit=" + 12 + "&offset=" + 0,
        success: function (res){
            var products = getProductsFromXMLString(res);
            pagingProduct.data = products;
            renderProducts(products);
            hideSpinner();
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

function handleLoadMoteProducts(){
    showSpinner();
    var fromPrice = filters.fromPrice;
    var toPrice = filters.toPrice;
    var categoryId = filters.categoryId;
    var limit = pagingProduct.limit;
    var offset = pagingProduct.offset;
    
    var url = "/api/products?categoryId=" + categoryId + "&fromPrice=" + fromPrice + "&toPrice=" + toPrice + "&limit=" + limit + "&offset=" + offset;
    
    var xmlHtppOptions = {
        method: "GET",
        url: url,
        success: function (res){
            var products = getProductsFromXMLString(res);
            if(!ValidationUtils.isEmpty(products)){
                renderMoreProducts(products);
                pagingProduct.data = pagingProduct.data.concat(products);
                pagingProduct.offset = offset + products.length;
            } else {
                canLoadMore = false;
            }
            hideSpinner();
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
        }
        
        products.push(product);
    }
    
    return products
}

function renderProducts(products){
    var output = '';
    if(!ValidationUtils.isEmpty(products)){
        for(var i = 0; i < products.length; i++){
            var product = products[i];
            output += '<div class="col-lg-3 col-md-4 col-sm-6 mb-4 product-item">'+
                        '<div class="card" style="width: 100%">'+
                            '<img src="'+ product.image + '" class="card-img-top" alt="">'+
                            '<div class="card-body">'+
                                '<h5 class="card-title">'+ product.name +'</h5>'+
                                '<p class="card-text">'+ StringUtils.getPriceFormatted(product.price) +' vnd</p>'+
                                '<a href="'+ CONTEXT_PATH + '/products?productId='+ product.id +'" class="btn btn-primary float-right">Details</a>'+
                            '</div>'+
                        '</div>'+
                    '</div>';
        }
    }
    $productList.innerHTML = output;
}

function renderMoreProducts(products){
    for(var i = 0; i < products.length; i++){
        var product = products[i];
        var div = document.createElement('div');
        div.className = 'col-lg-3 col-md-4 col-sm-6 mb-4 product-item';
        div.innerHTML = '<div class="card" style="width: 100%">'+
                            '<img src="'+ product.image +'" class="card-img-top" alt="">'+
                            '<div class="card-body">'+
                                '<h5 class="card-title">'+ product.name +'</h5>'+
                                '<p class="card-text">'+ StringUtils.getPriceFormatted(product.price) +' vnd</p>'+
                                '<a href="'+ CONTEXT_PATH + '/products?productId='+ product.id +'" class="btn btn-primary float-right">Details</a>'+
                            '</div>'+
                        '</div>'
        $productList.appendChild(div);
    }
}



