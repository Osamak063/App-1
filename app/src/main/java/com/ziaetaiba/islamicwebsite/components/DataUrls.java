package com.ziaetaiba.islamicwebsite.components;

public class DataUrls {

    public static String getMasterDataUrl(String language) {

        // https://www.ziaetaiba.com/mobile-apps/master.php?lang=ur
        String url = "https://www.ziaetaiba.com/mobile-apps/master.php?" +
                "lang=" + language;

        return url;
    }

    public static String getProductDataUrl(String language, int serviceNo) {

        // https://www.ziaetaiba.com/mobile-apps/services.php?lang=ur&type_id=7
        String url = "https://www.ziaetaiba.com/mobile-apps/services.php?" +
                "lang=" + language +
                "&type_id=" + serviceNo;

        return url;
    }

    public static String getDetailDataUrl(String language, int serviceNo, int productNo) {

        // https://www.ziaetaiba.com/mobile-apps/detail.php?lang=ur&type_id=4&id=8191
        String url = "https://www.ziaetaiba.com/mobile-apps/detail.php?" +
                "lang=" + language +
                "&type_id=" + serviceNo +
                "&id=" + productNo;

        return url;
    }
}
