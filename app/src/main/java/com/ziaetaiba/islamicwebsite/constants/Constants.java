package com.ziaetaiba.islamicwebsite.constants;

import android.os.Environment;

import com.ziaetaiba.islamicwebsite.R;

import java.util.ArrayList;

public class Constants {

    public static final String DATA_00                  = "http://pastebin.com/raw.php?i=YnX89UGZ" ;
    public static final String DATA_01                  = "https://www.ziaetaiba.com/mobile-apps/master.php";

    public static final String SD_CARD                  = Environment.getExternalStorageDirectory().getPath();
    private static final String APP_FOLDER              = "Zia e Taiba/App Data";
    public static final String THUMBNAIL_FOLDER         = "Thumbnail";
    private static final String MEDIA_FOLDER            = "Media";

    public static final String EXTERNAL_DATA_PATH       = SD_CARD + "/" + APP_FOLDER;

//    public static final String THUMBNAIL_DATA_PATH      = EXTERNAL_DATA_PATH + "/" + THUMBNAIL_FOLDER;
    public static final String MEDIA_DATA_PATH          = EXTERNAL_DATA_PATH + "/" + MEDIA_FOLDER;

    public static final String ARG_LANGUAGE_ID          = "arg_language_id";
    public static final String ARG_CATEGORY_ID          = "arg_category_id";
    public static final String ARG_SCHEDULE_ID          = "arg_schedule_id";

    public static final String ARG_TYPE_AUDIO           = "arg_type_audio" ;
    public static final String ARG_TYPE_VIDEO           = "arg_type_video" ;

    public static final String ARG_SERVICE_ID           = "arg_service_id";
    public static final String ARG_SERVICE_NO           = "arg_service_no";
    public static final String ARG_THEME_COLOR          = "arg_theme_color";
    public static final String ARG_STYLE_COLOR          = "arg_style_color";
    public static final String ARG_PRODUCT_ITEM_MODEL   = "arg_product_item_model";
    public static final String ARG_DATA_MODEL_ID        = "arg_data_model_id";
    public static final String ARG_FRAGMENT_PARENT      = "arg_fragment_parent";
    public static final String ARG_FRAGMENT_TITLE       = "arg_fragment_title";
    public static final String ARG_MEDIA_ID             = "arg_media_id";

    public static final String ARG_AUDIO_PATH           = "arg_audio_path" ;
    public static final String ARG_VIDEO_PATH           = "arg_video_path" ;

    public static final String TEXT_ALL                 = "All";
    public static final String TEXT_FEATURED            = "Featured" ;
    public static final String TEXT_MOST_VIEW           = "Most View" ;
    public static final String TEXT_MOST_DOWNLOAD       = "Most Download" ;

    public static final String STATUS_DOWNLOAD_YES       = "download" ;
    public static final String STATUS_DOWNLOAD_NOT       = "not_download" ;

    public static final String CONTAINS_NO_DATA         = "Contains no data!" ;

    public static final int SPEECH_RECOGNITION_REQUEST_CODE = 1101 ;
    public static final int UPGRADE_ACTIVITY_CODE 			= 1102 ;
    public static final int DOWNLOAD_ACTIVITY_CODE 	        = 1103;
    public static final int SETTINGS_ACTIVITY_CODE 			= 1103 ;
    public static final int WALLPAPER_ACTIVITY_CODE 		= 1104 ;
    public static final int GALLERY_DETAIL_ACTIVITY_CODE    = 1105 ;

    public static final String VERSION_LITE 			= "version_lite" ;
    public static final String VERSION_PRO 			    = "version_pro" ;

    public static final String AWAKE_YES				= "awake_yes" ;
    public static final String AWAKE_NO 				= "awake_no" ;
    public static final String AUTO_UPDATE_YES			= "auto_update_yes" ;
    public static final String AUTO_UPDATE_NO			= "auto_update_no" ;
    public static final String FIRST_TIME_YES           = "first_time_yes" ;
    public static final String FIRST_TIME_NO            = "first_time_no" ;
    public static final String REPEAT_YES				= "repeat_yes" ;
    public static final String REPEAT_NO 				= "repeat_no" ;
    public static final String DISPLAY_YES				= "display_yes" ;
    public static final String DISPLAY_NO				= "display_no" ;

    public static boolean IS_MAIN_ACTIVITY_RUNNING		= false; // not final

//    public static final String SERVICE_DOWNLOAD_YES 			= "service_download_yes";
//    public static final String SERVICE_DOWNLOAD_NOT 			= "service_download_not";

    public static String STATUS_CURRENT 				= "status_current"; // not final
    public static final String STATUS_VISIBLE 			= "status_visible";
    public static final String STATUS_GONE 			    = "status_gone";
    public static final String STATUS_READY 			= "status_ready";
    public static final String STATUS_COMPLETE 			= "status_complete";
    public static final String STATUS_CANCEL 			= "status_cancel";
    public static final String STATUS_ERROR 			= "status_error";
    public static final String STATUS_NO_CONNECTION 	= "status_no_connection";
    public static final String STATUS_FAILED            = "status_failed";
    public static final String STATUS_PAUSED            = "status_paused";
    public static final String STATUS_PENDING           = "status_pending";
    public static final String STATUS_RUNNING           = "status_running";
    public static final String STATUS_SUCCESSFUL        = "status_successful";
    public static final String STATUS_EMPTY             = "status_empty";

    public static final String ALBUM_DOWNLOAD           = "album_download" ;

    public static final String CATEGORY_ALL             = "category_all" ;
    public static final String CATEGORY_NEW             = "category_new" ;
    public static final String CATEGORY_FAVORITES       = "category_favorites" ;

    public static final String SD_CARD_NOT_AVAILABLE 	= "sd_card_not_available";

    public static final String PHOTO_AVAILABLE          = "photo_available" ;
    public static final String PHOTO_NOT_AVAILABLE      = "photo_not_available";

    public static final String SORT_ID 					= "By ID" ;
    public static final String SORT_ALPHA_A_TO_Z 		= "By name: A to Z" ;
    public static final String SORT_ALPHA_Z_TO_A 		= "By name: Z to A" ;
    public static final String SORT_DATE_OLD_TO_NEW     = "By date: old to new" ;
    public static final String SORT_DATE_NEW_TO_OLD     = "By date: new to old" ;

    public static final int COLOR_RED            = R.style.MyThemeRed ;
    public static final int COLOR_PINK           = R.style.MyThemePink ;
    public static final int COLOR_PURPLE         = R.style.MyThemePurple;
    public static final int COLOR_DEEP_PURPLE    = R.style.MyThemeDeepPurple ;
    public static final int COLOR_INDIGO         = R.style.MyThemeIndigo ;
    public static final int COLOR_BLUE           = R.style.MyThemeBlue ;
    public static final int COLOR_LIGHT_BLUE     = R.style.MyThemeLightBlue ;
    public static final int COLOR_CYAN           = R.style.MyThemeCyan ;
    public static final int COLOR_TEAL           = R.style.MyThemeTeal ;
    public static final int COLOR_GREEN          = R.style.MyThemeGreen ;
    public static final int COLOR_LIGHT_GREEN    = R.style.MyThemeLightGreen ;
    public static final int COLOR_LIME           = R.style.MyThemeLime ;
    public static final int COLOR_YELLOW         = R.style.MyThemeYellow ;
    public static final int COLOR_AMBER          = R.style.MyThemeAmber ;
    public static final int COLOR_ORANGE         = R.style.MyThemeOrange ;
    public static final int COLOR_DEEP_ORANGE    = R.style.MyThemeDeepOrange ;
    public static final int COLOR_BROWN          = R.style.MyThemeBrown ;
    public static final int COLOR_GREY           = R.style.MyThemeGrey ;
    public static final int COLOR_BLUE_GREY      = R.style.MyThemeBlueGrey ;
}
