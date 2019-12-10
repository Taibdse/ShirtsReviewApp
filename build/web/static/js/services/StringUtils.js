
var StringUtils = {
     getPriceFormatted: function(val){
        if(!ValidationUtils.isNumber(val)) return "";
        return Number(val).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$&,').slice(0, -3);
    },
//    removeNoneASCIIChars: function (val){
//        if(ValidationUtils.isEmpty(val)) return "";
//        return val.replace(/[\u{0080}-\u{FFFF}]/gu,"");
//    },
    
    removeVnAccents: function (str){
//        str = this.removeNoneASCIIChars(str);
        if(ValidationUtils.isEmpty(str)) return "";
         // remove accents
        var from = "àáãảạăằắẳẵặâầấẩẫậèéẻẽẹêềếểễệđùúủũụưừứửữựòóỏõọôồốổỗộơờớởỡợìíỉĩịäëïîöüûñç";
        var to = "aaaaaaaaaaaaaaaaaeeeeeeeeeeeduuuuuuuuuuuoooooooooooooooooiiiiiaeiiouunc";
        for (var i=0, l= from.length;i < l ;i++) {
          str = str.replace(RegExp(from[i], "gi"), to[i]);
        }

        str = str.toLowerCase().trim()

        return str;
    },
    containsWithoutVnAccents: function(str1, str2){
        str1 = StringUtils.removeVnAccents(str1).toLowerCase();
        str2 = StringUtils.removeVnAccents(str2).toLowerCase();
        return str1.indexOf(str2) > -1;
    }

};
