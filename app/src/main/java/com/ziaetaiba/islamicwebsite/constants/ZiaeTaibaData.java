package com.ziaetaiba.islamicwebsite.constants;


import com.ziaetaiba.islamicwebsite.R;
import com.ziaetaiba.islamicwebsite.datamodels.ProductModel;
import com.ziaetaiba.islamicwebsite.datamodels.SettingsModel;
import com.ziaetaiba.islamicwebsite.database.DBAdapter;

public class ZiaeTaibaData {

    private static final int NO_SETTINGS     = 1 ;
    private static final int NO_LANGUAGES    = 0 ;
    private static final int NO_SERVICES     = 0 ;
    private static final int NO_MASTERS      = 0 ;
    private static final int NO_SUB_MASTERS  = 0 ;
    private static final int NO_DATA_TABLES  = 13 ;
    private static final int NO_AUDIO_VIDEO  = 0 ;
    private static final int NO_BOOKS        = 0 ;
    private static final int NO_MEDIA        = 0 ;
    private static final int NO_SCHOLAR      = 0 ;
    private static final int NO_GALLERY      = 0 ;
    private static final int NO_NEWS         = 0 ;
    private static final int NO_PLACES       = 0 ;
    private static final int NO_HISTORY      = 0 ;
    private static final int NO_DAYS         = 0 ;
    private static final int NO_DEPARTMENTS  = 0 ;
    private static final int NO_APPS         = 0 ;
    private static final int NO_ARTICLES     = 0 ;
    private static final int NO_BLOGS        = 0 ;
    private static final int NO_POETRY       = 0 ;

    public static final int SERVICE_NO_BOOKS        = 2 ;
    public static final int SERVICE_NO_MEDIA        = 3 ;
    public static final int SERVICE_NO_SCHOLAR      = 4 ;
    public static final int SERVICE_NO_GALLERY      = 5 ;
    public static final int SERVICE_NO_NEWS         = 6 ;
    public static final int SERVICE_NO_PLACES       = 7 ;
    public static final int SERVICE_NO_HISTORY      = 8 ;
    public static final int SERVICE_NO_DAYS         = 10 ;
    public static final int SERVICE_NO_DEPARTMENTS  = 13 ;
    public static final int SERVICE_NO_APPS         = 14 ;
    public static final int SERVICE_NO_ARTICLES     = 16 ;
    public static final int SERVICE_NO_BLOGS        = 17 ;
    public static final int SERVICE_NO_POETRY       = 21 ;

    public static final String SERVICE_SHORT_NAME_BOOKS       = "book" ;
    public static final String SERVICE_SHORT_NAME_MEDIA       = "media" ;
    public static final String SERVICE_SHORT_NAME_SCHOLAR     = "scholar" ;
    public static final String SERVICE_SHORT_NAME_GALLERY     = "gallery" ;
    public static final String SERVICE_SHORT_NAME_NEWS        = "news" ;
    public static final String SERVICE_SHORT_NAME_PLACES      = "place" ;
    public static final String SERVICE_SHORT_NAME_HISTORY     = "history" ;
    public static final String SERVICE_SHORT_NAME_DAYS        = "day" ;
    public static final String SERVICE_SHORT_NAME_DEPARTMENTS = "department" ;
    public static final String SERVICE_SHORT_NAME_APPS        = "app" ;
    public static final String SERVICE_SHORT_NAME_ARTICLES    = "article" ;
    public static final String SERVICE_SHORT_NAME_BLOGS       = "blog" ;
    public static final String SERVICE_SHORT_NAME_POETRY      = "poetry" ;

    public static int getNoSettings () { return NO_SETTINGS ; }

    public static int getNoLanguages () { return NO_LANGUAGES ; }

    public static int getNoServices () { return NO_SERVICES ; }

    public static int getNoMasters () { return NO_MASTERS ; }

    public static int getNoSubMasters () { return NO_SUB_MASTERS ; }

    public static int getNoDataTables() { return NO_DATA_TABLES; }

    public static int getNoAudioVideo() { return NO_AUDIO_VIDEO; }

    public static int getNoProducts (int serviceNo) {

        int noProducts = 0 ;

        switch (serviceNo) {

            case SERVICE_NO_BOOKS:       noProducts = NO_BOOKS ;        break;
            case SERVICE_NO_MEDIA:       noProducts = NO_MEDIA ;        break;
            case SERVICE_NO_SCHOLAR:     noProducts = NO_SCHOLAR ;      break;
            case SERVICE_NO_GALLERY:     noProducts = NO_GALLERY ;      break;
            case SERVICE_NO_NEWS:        noProducts = NO_NEWS ;         break;
            case SERVICE_NO_PLACES:      noProducts = NO_PLACES ;       break;
            case SERVICE_NO_HISTORY:     noProducts = NO_HISTORY ;      break;
            case SERVICE_NO_DAYS:        noProducts = NO_DAYS ;         break;
            case SERVICE_NO_DEPARTMENTS: noProducts = NO_DEPARTMENTS ;  break;
            case SERVICE_NO_APPS:        noProducts = NO_APPS ;         break;
            case SERVICE_NO_ARTICLES:    noProducts = NO_ARTICLES ;     break;
            case SERVICE_NO_BLOGS:       noProducts = NO_BLOGS ;        break;
            case SERVICE_NO_POETRY:      noProducts = NO_POETRY ;       break;
        }

        return noProducts ;
    }

    public static void addSettingsData() {

        DBAdapter.addSettingsData(new SettingsModel(1, Constants.AWAKE_NO, Constants.VERSION_LITE,
                Constants.DATA_01, Constants.AUTO_UPDATE_YES));
    }

    public static void addDataTablesData() {

        DBAdapter.addDataTablesData(new ProductModel(1,  SERVICE_NO_BOOKS,       DBAdapter.TABLE_BOOKS,       SERVICE_SHORT_NAME_BOOKS));
        DBAdapter.addDataTablesData(new ProductModel(2,  SERVICE_NO_MEDIA,       DBAdapter.TABLE_MEDIA,       SERVICE_SHORT_NAME_MEDIA));
        DBAdapter.addDataTablesData(new ProductModel(3,  SERVICE_NO_SCHOLAR,     DBAdapter.TABLE_SCHOLAR,     SERVICE_SHORT_NAME_SCHOLAR));
        DBAdapter.addDataTablesData(new ProductModel(4,  SERVICE_NO_GALLERY,     DBAdapter.TABLE_GALLERY,     SERVICE_SHORT_NAME_GALLERY));
        DBAdapter.addDataTablesData(new ProductModel(5,  SERVICE_NO_NEWS,        DBAdapter.TABLE_NEWS,        SERVICE_SHORT_NAME_NEWS));
        DBAdapter.addDataTablesData(new ProductModel(6,  SERVICE_NO_PLACES,      DBAdapter.TABLE_PLACES,      SERVICE_SHORT_NAME_PLACES));
        DBAdapter.addDataTablesData(new ProductModel(7,  SERVICE_NO_HISTORY,     DBAdapter.TABLE_HISTORY,     SERVICE_SHORT_NAME_HISTORY));
        DBAdapter.addDataTablesData(new ProductModel(8,  SERVICE_NO_DAYS,        DBAdapter.TABLE_DAYS,        SERVICE_SHORT_NAME_DAYS));
        DBAdapter.addDataTablesData(new ProductModel(9,  SERVICE_NO_DEPARTMENTS, DBAdapter.TABLE_DEPARTMENTS, SERVICE_SHORT_NAME_DEPARTMENTS));
        DBAdapter.addDataTablesData(new ProductModel(10, SERVICE_NO_APPS,        DBAdapter.TABLE_APPS,        SERVICE_SHORT_NAME_APPS));
        DBAdapter.addDataTablesData(new ProductModel(11, SERVICE_NO_ARTICLES,    DBAdapter.TABLE_ARTICLES,    SERVICE_SHORT_NAME_ARTICLES));
        DBAdapter.addDataTablesData(new ProductModel(12, SERVICE_NO_BLOGS,       DBAdapter.TABLE_BLOGS,       SERVICE_SHORT_NAME_BLOGS));
        DBAdapter.addDataTablesData(new ProductModel(13, SERVICE_NO_POETRY,      DBAdapter.TABLE_POETRY,      SERVICE_SHORT_NAME_POETRY));
    }

    public static boolean isServiceDownloadable (int serviceNo) {

        boolean status = false ;

        switch (serviceNo) {

            case SERVICE_NO_BOOKS:       status = true ; break;
            case SERVICE_NO_MEDIA:       status = true ; break;
            case SERVICE_NO_SCHOLAR:     status = false ; break;
            case SERVICE_NO_GALLERY:     status = true ; break;
            case SERVICE_NO_NEWS:        status = false ; break;
            case SERVICE_NO_PLACES:      status = false ; break;
            case SERVICE_NO_HISTORY:     status = false ; break;
            case SERVICE_NO_DAYS:        status = false ; break;
            case SERVICE_NO_DEPARTMENTS: status = false ; break;
            case SERVICE_NO_APPS:        status = false ; break;
            case SERVICE_NO_ARTICLES:    status = false ; break;
            case SERVICE_NO_BLOGS:       status = false ; break;
            case SERVICE_NO_POETRY:      status = false ; break;
        }

        return status ;
    }

    public static int getServiceColor (int serviceNo) {

        int color = 0 ;

        switch (serviceNo) {

            case SERVICE_NO_BOOKS:       color = Constants.COLOR_LIME ;         break;
            case SERVICE_NO_MEDIA:       color = Constants.COLOR_CYAN ;         break;
            case SERVICE_NO_SCHOLAR:     color = Constants.COLOR_PURPLE ;       break;
            case SERVICE_NO_GALLERY:     color = Constants.COLOR_PINK ;         break;
            case SERVICE_NO_NEWS:        color = Constants.COLOR_BROWN ;        break;
            case SERVICE_NO_PLACES:      color = Constants.COLOR_BLUE_GREY ;    break;
            case SERVICE_NO_HISTORY:     color = Constants.COLOR_ORANGE ;       break;
            case SERVICE_NO_DAYS:        color = Constants.COLOR_LIGHT_GREEN ;  break;
            case SERVICE_NO_DEPARTMENTS: color = Constants.COLOR_TEAL ;         break;
            case SERVICE_NO_APPS:        color = Constants.COLOR_LIGHT_BLUE ;   break;
            case SERVICE_NO_ARTICLES:    color = Constants.COLOR_AMBER ;        break;
            case SERVICE_NO_BLOGS:       color = Constants.COLOR_BLUE ;         break;
            case SERVICE_NO_POETRY:      color = Constants.COLOR_RED ;          break;
        }

        return color ;
    }

    public static int getPrimaryLightColor (int style) {

        int color = 0 ;

        switch (style) {

            case R.style.MyThemeRed:        color = R.color.colorPrimaryLight_1 ;        break;
            case R.style.MyThemePink:       color = R.color.colorPrimaryLight_2 ;        break;
            case R.style.MyThemePurple:     color = R.color.colorPrimaryLight_3 ;        break;
            case R.style.MyThemeDeepPurple: color = R.color.colorPrimaryLight_4 ;        break;
            case R.style.MyThemeIndigo:     color = R.color.colorPrimaryLight_5 ;        break;
            case R.style.MyThemeBlue:       color = R.color.colorPrimaryLight_6 ;        break;
            case R.style.MyThemeLightBlue:  color = R.color.colorPrimaryLight_7 ;        break;
            case R.style.MyThemeCyan:       color = R.color.colorPrimaryLight_8 ;        break;
            case R.style.MyThemeTeal:       color = R.color.colorPrimaryLight_9 ;        break;
            case R.style.MyThemeGreen:      color = R.color.colorPrimaryLight_10 ;       break;
            case R.style.MyThemeLightGreen: color = R.color.colorPrimaryLight_11 ;       break;
            case R.style.MyThemeLime:       color = R.color.colorPrimaryLight_12 ;       break;
            case R.style.MyThemeYellow:     color = R.color.colorPrimaryLight_13 ;       break;
            case R.style.MyThemeAmber:      color = R.color.colorPrimaryLight_14 ;       break;
            case R.style.MyThemeOrange:     color = R.color.colorPrimaryLight_15 ;       break;
            case R.style.MyThemeDeepOrange: color = R.color.colorPrimaryLight_16 ;       break;
            case R.style.MyThemeBrown:      color = R.color.colorPrimaryLight_17 ;       break;
            case R.style.MyThemeGrey:       color = R.color.colorPrimaryLight_18 ;       break;
            case R.style.MyThemeBlueGrey:   color = R.color.colorPrimaryLight_19 ;       break;
        }

        return color ;
    }
}