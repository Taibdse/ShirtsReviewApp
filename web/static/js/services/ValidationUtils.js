var ValidationUtils = {
    isNumber: function (val){
        if (this.isEmpty(val)) return false;
        return !isNaN(Number(val));
    },
    isEmpty: function (val){
        if (val == null || val == undefined) return true;
        if (typeof val == "string" && val.trim().length == 0) return true;
        if (typeof val == 'object' && Object.keys(val).length == 0) return true;
        return false;
    }

}