
var XMLHttpUtils = {
    getXMLHttpRequestObject: function() {
        var xmlhttp;
        if (window.XMLHttpRequest) {
            xmlhttp = new XMLHttpRequest();
        } else {
            // code for older browsers
            xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
        }
        return xmlhttp;
    },
    
    makeRequest: function (options) {
        var xmlhttp = this.getXMLHttpRequestObject();
        options.url = CONTEXT_PATH + options.url;

        xmlhttp.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                options.success(this.responseText);
            }
        };

        xmlhttp.open(options.method, options.url, true);
        xmlhttp.send();
    },
    
    getTextNodeContent: function(parent, tagName) {
        var node = parent.getElementsByTagName(tagName)[0];
        if (node == null)
            return null;
        return node.textContent;
    }
}