
var $fromPrice = document.getElementById("fromPrice");
var $toPrice = document.getElementById("toPrice");
var $selectCategory = document.getElementById("selectCategory");
var $btnSearch = document.getElementById("btnSearch");
var $productList = document.getElementById("productList");
var $inputQuickSearch = document.getElementById("inputQuickSearch");
var $spinner = document.getElementById("spinner");
var $numOfProducts = document.getElementById("numOfProducts");
var $noMoreProducts = document.getElementById("noMoreProducts");

var model = {
    filters: {
        fromPrice: 0,
        toPrice: 0,
        categoryId: "all",
    },
    
    pagingProduct: {
        data: [],
        limit: 12,
        offset: 0
    },
    
    canLoadMore: true,
    quickSearchTimeout: null,
    loadMoreTimeout: null,
    isLoading: false,
    currentNumOfProducts: 0,
    
    getProductsFromXMLString: function (xml) {
        var products = [];
        var parser = new DOMParser();
        var xmlDoc = parser.parseFromString(xml, "text/xml");
        var productNodeList = xmlDoc.getElementsByTagName("product");
        for (var i = 0; i < productNodeList.length; i++) {
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
    },
    
    getResponseObjectFromXMLResponse: function (xml) {
        var parser = new DOMParser();
        var xmlDoc = parser.parseFromString(xml, "text/xml");
        var $data = xmlDoc.getElementsByTagName("data")[0];
        var success = XMLHttpUtils.getTextNodeContent($data, "success");
        var errors = XMLHttpUtils.getTextNodeContent($data, "errors");
        return {success: success, errors: errors};
    },
}

var view = {
    init: function () {
        $fromPrice.value = 0;
        $toPrice.value = 0;

        $btnSearch.addEventListener("click", octopus.handleSearchProducts);

        $inputQuickSearch.addEventListener("keyup", function (e) {
            window.clearTimeout(model.quickSearchTimeout);
            model.quickSearchTimeout = setTimeout(function () {
                octopus.handleQuickSearch(e);
            }, 300);
        });

        this.addEventOnscrollToWindow();
        octopus.handleSearchProducts();
    },

    renderMoreProducts: function (products) {
        var currentQuixkSearchVal = $inputQuickSearch.value;
        
        for (var i = 0; i < products.length; i++) {
            var product = products[i];
            var $div = this.createSingleProductCard(product);
            if (!StringUtils.containsWithoutVnAccents(product.name, currentQuixkSearchVal)) {
                this.addClassToElement($div, 'd-none');
            } else {
                model.currentNumOfProducts++;
            }
            $productList.appendChild($div);
        }

        this.renderNumOfProducts(model.currentNumOfProducts);
    },

    addEventOnscrollToWindow: function () {
        window.onscroll = function (ev) {
            if ((window.innerHeight + window.pageYOffset) >= document.body.offsetHeight) {
                window.scrollBy(0, -50);
                if (model.canLoadMore && !model.isLoading) {
                    view.removeEventOnscrollToWindow();
                    octopus.handleLoadMoteProducts();
                }
            }
        };
    },

    removeEventOnscrollToWindow: function () {
        window.onscroll = null;
    },

    showSpinner: function () {
        $spinner.classList.remove("d-none");
    },

    hideSpinner: function () {
        $spinner.classList.add("d-none");
    },

    renderNoMoreproductsFound: function () {
        $noMoreProducts.textContent = 'No More Products Found';
    },

    clearSearchInput: function () {
        $inputQuickSearch.value = '';
    },

    renderNumOfProducts: function (numOfProducts) {
        $numOfProducts.textContent = numOfProducts;
    },

    renderProducts: function (products) {
        $productList.innerHTML = '';
        if (!ValidationUtils.isEmpty(products)) {
            for (var i = 0; i < products.length; i++) {
                var product = products[i];
                var $div = view.createSingleProductCard(product);
                $productList.appendChild($div);
            }
        } else {
            $productList.innerHTML = '<div class="col-12"><h3 class="text-center">No products Found</h3></div>'
        }
    },
    
    addClassToElement: function ($ele, className) {
        if($ele != null) $ele.classList.add(className);
    },
    
    removeClassFromElement: function ($ele, className) {
        if($ele != null) $ele.classList.remove(className);
    },
    
    createSingleProductCard: function (product) {
        var $div = document.createElement('div');
        
        $div.className = 'col-lg-3 col-md-4 col-sm-6 mb-4 product-item';
        $div.innerHTML = '<div class="card" style="width: 100%">' +
                '<img src="' + product.image + '" class="card-img-top" alt="">' +
                '<div class="card-body">' +
                '<h5 class="card-title">' + product.name + '</h5>' +
                '<p class="card-text">' + StringUtils.getPriceFormatted(product.price) + ' vnd</p>' +
                '<p class="card-text text-warning font-weight-bold">' + product.avgVotes + ' stars</p>' +
                '<div class="stars-rating">' +
                '<span class="fa fa-star star-rating"></span>' +
                '<span class="fa fa-star star-rating"></span>' +
                '<span class="fa fa-star star-rating"></span>' +
                '<span class="fa fa-star star-rating"></span>' +
                '<span class="fa fa-star star-rating"></span>' +
                '</div>' +
                '<a href="' + CONTEXT_PATH + '/products?productId=' + product.id + '" class="btn btn-primary float-right">Details</a>' +
                '</div>' +
                '</div>';
        
        var $starList = $div.getElementsByClassName('star-rating');
        
        for (var i = 0; i < $starList.length; i++) {
            (function (i) {
                $starItem = $starList[i];
                if (product.votes > i)
                    view.addClassToElement($starItem, 'checked');
                $starItem.addEventListener('click', function () {
                    octopus.handleClickStarRating($starList, i, product);
                });
            })(i)
        }
        
        
        return $div;
    },

}

var octopus = {
    handleQuickSearch: function (e) {
        view.removeEventOnscrollToWindow();
        
        var value = e.target.value;
        var $list = $productList.getElementsByClassName('product-item');
        var numOfProducts = 0;
        for (var i = 0; i < model.pagingProduct.data.length; i++) {
            var product = model.pagingProduct.data[i];
            if (ValidationUtils.isEmpty(value) || StringUtils.containsWithoutVnAccents(product.name, value)) {
                view.removeClassFromElement($list[i], 'd-none');
                numOfProducts++;
            } else {
                view.addClassToElement($list[i], 'd-none');
            }
        }
        model.currentNumOfProducts = numOfProducts;
        view.renderNumOfProducts(numOfProducts);
        setTimeout(function () {
            view.addEventOnscrollToWindow();
        }, 300);
    },

    handleSearchProducts: function () {

        var categoryId = $selectCategory.value;
        var fromPrice = $fromPrice.value;
        var toPrice = $toPrice.value;
        
        if (ValidationUtils.isEmpty(fromPrice) || ValidationUtils.isEmpty(toPrice))
            return alert("Price is required!");
        if (!ValidationUtils.isNumber(fromPrice) || !ValidationUtils.isNumber(toPrice))
            return alert("From price and to price must be valid number!");
        if (Number(fromPrice) > Number(toPrice))
            return alert("From price must be less than to price!");
        
        model.filters = {
            fromPrice: fromPrice,
            toPrice: toPrice,
            categoryId: categoryId,
        };
        
        model.pagingProduct = {
            limit: 12,
            offset: 0
        }

        model.canLoadMore = true;
        view.showSpinner();
        model.isLoading = true;
        
        var xmlHtppOptions = {
            method: "GET",
            url: "/api/products?categoryId=" + categoryId + "&fromPrice=" + fromPrice + "&toPrice=" + toPrice + "&limit=" + 12 + "&offset=" + 0,
            success: function (res) {
                var products = model.getProductsFromXMLString(res);
                model.pagingProduct.data = products;
                model.pagingProduct.offset = model.pagingProduct.offset + products.length;
                model.currentNumOfProducts = model.pagingProduct.data.length;
                
                view.renderProducts(products);
                view.hideSpinner();
                view.renderNumOfProducts(model.currentNumOfProducts);
                view.clearSearchInput();
                
                model.isLoading = false;
            },
        };
        XMLHttpUtils.makeRequest(xmlHtppOptions);
    },

    handleLoadMoteProducts: function () {
        view.showSpinner();
        
        var fromPrice = model.filters.fromPrice;
        var toPrice = model.filters.toPrice;
        var categoryId = model.filters.categoryId;
        var limit = model.pagingProduct.limit;
        var offset = model.pagingProduct.offset;
        var url = "/api/products?categoryId=" + categoryId + "&fromPrice=" + fromPrice + "&toPrice=" + toPrice + "&limit=" + limit + "&offset=" + offset;
        
        model.isLoading = true;
        
        var xmlHtppOptions = {
            method: "GET",
            url: url,
            success: function (res) {
                var products = model.getProductsFromXMLString(res);
                if (!ValidationUtils.isEmpty(products)) {
                    model.pagingProduct.data = model.pagingProduct.data.concat(products);
                    model.pagingProduct.offset = offset + products.length;
                    model.currentNumOfProducts = model.pagingProduct.data.length;
                    
                    view.renderMoreProducts(products);
                    view.renderNumOfProducts(model.currentNumOfProducts);
                } else {
                    model.canLoadMore = false;
                    view.renderNoMoreproductsFound();
                }
                
                view.hideSpinner();
                view.addEventOnscrollToWindow();
                model.isLoading = false;
            },
        };
        XMLHttpUtils.makeRequest(xmlHtppOptions);
    },

    handleClickStarRating: function ($starList, index, product) {
        
        var url = "/api/votes?productId=" + product.id + "&votes=" + (index + 1);
        
        var xmlHtppOptions = {
            method: "GET",
            url: url,
            success: function (res) {
                var result = model.getResponseObjectFromXMLResponse(res);
                if (result.success == 'true') {
                    for (var i = 0; i < $starList.length; i++) {
                        view.removeClassFromElement($starList[i], 'checked');
                        if (i <= index) view.addClassToElement($starList[i], 'checked');
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
    },
}


view.init();












