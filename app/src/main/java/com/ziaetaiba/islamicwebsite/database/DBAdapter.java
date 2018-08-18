package com.ziaetaiba.islamicwebsite.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import com.ziaetaiba.islamicwebsite.datamodels.AppModel;
import com.ziaetaiba.islamicwebsite.datamodels.ArticleModel;
import com.ziaetaiba.islamicwebsite.datamodels.AudioVideoModel;
import com.ziaetaiba.islamicwebsite.datamodels.BlogModel;
import com.ziaetaiba.islamicwebsite.datamodels.BookModel;
import com.ziaetaiba.islamicwebsite.datamodels.DayModel;
import com.ziaetaiba.islamicwebsite.datamodels.DepartmentModel;
import com.ziaetaiba.islamicwebsite.datamodels.GalleryModel;
import com.ziaetaiba.islamicwebsite.datamodels.HistoryModel;
import com.ziaetaiba.islamicwebsite.datamodels.MediaModel;
import com.ziaetaiba.islamicwebsite.datamodels.PlaceModel;
import com.ziaetaiba.islamicwebsite.datamodels.PoetryModel;
import com.ziaetaiba.islamicwebsite.datamodels.ProductItemModel;
import com.ziaetaiba.islamicwebsite.datamodels.ProductModel;
import com.ziaetaiba.islamicwebsite.datamodels.MasterModel;
import com.ziaetaiba.islamicwebsite.datamodels.NewsModel;
import com.ziaetaiba.islamicwebsite.datamodels.ScholarModel;
import com.ziaetaiba.islamicwebsite.datamodels.ServiceModel;
import com.ziaetaiba.islamicwebsite.datamodels.LanguageModel;
import com.ziaetaiba.islamicwebsite.datamodels.SettingsModel;
import com.ziaetaiba.islamicwebsite.constants.Constants;
import com.ziaetaiba.islamicwebsite.datamodels.SubMasterModel;

import java.util.ArrayList;

public class DBAdapter {

    /**
     * ***************** if debug is set true then it will show all Logcat message ***********
     */
    private static final boolean DEBUG = true;

    /**
     * ***************** Logcat TAG ***********
     */
    private static final String LOG_TAG = "DBAdapter";

    /**
     * ***************** Table Fields ***********
     */
    private static final String KEY_SETTING_ID = "setting_id";
    private static final String KEY_SETTING_STAY_AWAKE = "setting_stay_awake";
    private static final String KEY_SETTING_VERSION = "setting_version";
    private static final String KEY_SETTING_DATA_PATH = "setting_data_path";
    private static final String KEY_SETTING_AUTO_UPDATE = "setting_auto_update";

    private static final String KEY_LANGUAGE_ID = "language_id";
    private static final String KEY_LANGUAGE_NUMBER = "language_number";
    private static final String KEY_LANGUAGE_TITLE = "language_title";
    private static final String KEY_LANGUAGE_SHORT_NAME = "language_short_name";

    private static final String KEY_SERVICE_ID = "service_id";
    private static final String KEY_SERVICE_NUMBER = "service_number";
    private static final String KEY_SERVICE_TITLE = "service_title";
    private static final String KEY_SERVICE_THUMBNAIL_URL = "service_thumbnail_url";
    private static final String KEY_SERVICE_STATUS = "service_status";

    private static final String KEY_MASTER_ID = "master_id";
    private static final String KEY_MASTER_NUMBER = "master_number";
    private static final String KEY_MASTER_PARENT = "master_parent";
    private static final String KEY_MASTER_TITLE = "master_title";
    private static final String KEY_MASTER_THUMBNAIL_URL = "master_thumbnail_url";
    private static final String KEY_MASTER_STATUS = "master_status";

    private static final String KEY_SUB_MASTER_ID = "sub_master_id";
    private static final String KEY_SUB_MASTER_NUMBER = "sub_master_number";
    private static final String KEY_SUB_MASTER_PARENT = "sub_master_parent";
    private static final String KEY_SUB_MASTER_TITLE = "sub_master_title";

    private static final String KEY_DATA_TABLES_ID = "data_tables_id";
    private static final String KEY_DATA_TABLES_NO = "data_tables_no";
    private static final String KEY_DATA_TABLES_TITLE = "data_tables_title";
    private static final String KEY_DATA_TABLES_SHORT_NAME = "data_tables_short_name";

    private static final String KEY_BOOK_ID = "book_id";
    private static final String KEY_BOOK_NO = "book_no";
    private static final String KEY_BOOK_TITLE = "book_title";
    private static final String KEY_BOOK_DESCRIPTION = "book_description";
    private static final String KEY_BOOK_THUMBNAIL_URL = "book_thumbnail_url";
    private static final String KEY_BOOK_FEATURED = "book_featured";
    private static final String KEY_BOOK_MOST_VIEW = "book_most_view";
    private static final String KEY_BOOK_MOST_DOWNLOAD = "book_most_download";
    private static final String KEY_BOOK_WEBSITE = "book_website";
    private static final String KEY_BOOK_UPDATED_ON = "book_updated_on";
    private static final String KEY_BOOK_STATUS = "book_status";
    private static final String KEY_BOOK_CATEGORY = "book_category";
    private static final String KEY_BOOK_AUTHOR = "book_author";
    private static final String KEY_BOOK_LANGUAGE = "book_language";
    private static final String KEY_BOOK_PUBLISHER = "book_publisher";
    private static final String KEY_BOOK_MONTH = "book_month";
    private static final String KEY_BOOK_PDF_URL = "book_pdf_url";
    private static final String KEY_BOOK_PHOTO_URL = "book_photo_url";

    private static final String KEY_MEDIA_ID = "media_id";
    private static final String KEY_MEDIA_NO = "media_no";
    private static final String KEY_MEDIA_TITLE = "media_title";
    private static final String KEY_MEDIA_DESCRIPTION = "media_description";
    private static final String KEY_MEDIA_THUMBNAIL_URL = "media_thumbnail_url";
    private static final String KEY_MEDIA_FEATURED = "media_featured";
    private static final String KEY_MEDIA_MOST_VIEW = "media_most_view";
    private static final String KEY_MEDIA_MOST_DOWNLOAD = "media_most_download";
    private static final String KEY_MEDIA_WEBSITE = "media_website";
    private static final String KEY_MEDIA_UPDATED_ON = "media_updated_on";
    private static final String KEY_MEDIA_STATUS = "media_status";
    private static final String KEY_MEDIA_CATEGORY = "media_category";
    private static final String KEY_MEDIA_VOCALIST = "media_vocalist";
    private static final String KEY_MEDIA_ATTRIBUTE = "media_attribue";
    private static final String KEY_MEDIA_LANGUAGE = "media_language";
    private static final String KEY_MEDIA_TYPE = "media_type";
    private static final String KEY_MEDIA_PHOTO_URL = "media_photo_url";

    private static final String KEY_AUDIO_VIDEO_ID = "audio_video_id";
    private static final String KEY_AUDIO_VIDEO_TITLE = "audio_video_title";
    private static final String KEY_AUDIO_VIDEO_URL = "audio_video_url";
    private static final String KEY_AUDIO_VIDEO_TYPE = "audio_video_type";

    private static final String KEY_SCHOLAR_ID = "scholar_id";
    private static final String KEY_SCHOLAR_NO = "scholar_no";
    private static final String KEY_SCHOLAR_TITLE = "scholar_title";
    private static final String KEY_SCHOLAR_DESCRIPTION = "scholar_description";
    private static final String KEY_SCHOLAR_THUMBNAIL_URL = "scholar_thumbnail_url";
    private static final String KEY_SCHOLAR_FEATURED = "scholar_featured";
    private static final String KEY_SCHOLAR_MOST_VIEW = "scholar_most_view";
    private static final String KEY_SCHOLAR_MOST_DOWNLOAD = "scholar_most_download";
    private static final String KEY_SCHOLAR_WEBSITE = "scholar_website";
    private static final String KEY_SCHOLAR_UPDATED_ON = "scholar_updated_on";
    private static final String KEY_SCHOLAR_STATUS = "scholar_status";
    private static final String KEY_SCHOLAR_BIRTH_DATE = "scholar_birth_date";
    private static final String KEY_SCHOLAR_URS_DATE = "scholar_urs_date";
    private static final String KEY_SCHOLAR_PHOTO_URL = "scholar_photo_url";

    private static final String KEY_GALLERY_ID = "gallery_id";
    private static final String KEY_GALLERY_NO = "gallery_no";
    private static final String KEY_GALLERY_TITLE = "gallery_title";
    private static final String KEY_GALLERY_DESCRIPTION = "gallery_description";
    private static final String KEY_GALLERY_THUMBNAIL_URL = "gallery_thumbnail_url";
    private static final String KEY_GALLERY_FEATURED = "gallery_featured";
    private static final String KEY_GALLERY_MOST_VIEW = "gallery_most_view";
    private static final String KEY_GALLERY_MOST_DOWNLOAD = "gallery_most_download";
    private static final String KEY_GALLERY_WEBSITE = "gallery_website";
    private static final String KEY_GALLERY_UPDATED_ON = "gallery_updated_on";
    private static final String KEY_GALLERY_STATUS = "gallery_status";
    private static final String KEY_GALLERY_CATEGORY = "gallery_category";
    private static final String KEY_GALLERY_DOWNLOAD_IMAGE = "gallery_download_image";
    private static final String KEY_GALLERY_PHOTO_URL = "gallery_photo_url";

    private static final String KEY_NEWS_ID = "news_id";
    private static final String KEY_NEWS_NO = "news_no";
    private static final String KEY_NEWS_TITLE = "news_title";
    private static final String KEY_NEWS_DESCRIPTION = "news_description";
    private static final String KEY_NEWS_THUMBNAIL_URL = "news_thumbnail_url";
    private static final String KEY_NEWS_FEATURED = "news_featured";
    private static final String KEY_NEWS_MOST_VIEW = "news_most_view";
    private static final String KEY_NEWS_MOST_DOWNLOAD = "news_most_download";
    private static final String KEY_NEWS_WEBSITE = "news_website";
    private static final String KEY_NEWS_UPDATED_ON = "news_updated_on";
    private static final String KEY_NEWS_STATUS = "news_status";
    private static final String KEY_NEWS_ORGANIZATION = "news_organization";
    private static final String KEY_NEWS_COUNTRY = "news_country";
    private static final String KEY_NEWS_NEWS_PAPER = "news_news_paper";
    private static final String KEY_NEWS_PHOTO_URL = "news_photo_url";

    private static final String KEY_PLACE_ID = "place_id";
    private static final String KEY_PLACE_NO = "place_no";
    private static final String KEY_PLACE_TITLE = "place_title";
    private static final String KEY_PLACE_DESCRIPTION = "place_description";
    private static final String KEY_PLACE_THUMBNAIL_URL = "place_thumbnail_url";
    private static final String KEY_PLACE_FEATURED = "place_featured";
    private static final String KEY_PLACE_MOST_VIEW = "place_most_view";
    private static final String KEY_PLACE_MOST_DOWNLOAD = "place_most_download";
    private static final String KEY_PLACE_WEBSITE = "place_website";
    private static final String KEY_PLACE_UPDATED_ON = "place_updated_on";
    private static final String KEY_PLACE_STATUS = "place_status";
    private static final String KEY_PLACE_CATEGORY = "place_category";
    private static final String KEY_PLACE_PHOTO_URL = "place_photo_url";

    private static final String KEY_HISTORY_ID = "history_id";
    private static final String KEY_HISTORY_NO = "history_no";
    private static final String KEY_HISTORY_TITLE = "history_title";
    private static final String KEY_HISTORY_DESCRIPTION = "history_description";
    private static final String KEY_HISTORY_THUMBNAIL_URL = "history_thumbnail_url";
    private static final String KEY_HISTORY_FEATURED = "history_featured";
    private static final String KEY_HISTORY_MOST_VIEW = "history_most_view";
    private static final String KEY_HISTORY_MOST_DOWNLOAD = "history_most_download";
    private static final String KEY_HISTORY_WEBSITE = "history_website";
    private static final String KEY_HISTORY_UPDATED_ON = "history_updated_on";
    private static final String KEY_HISTORY_STATUS = "history_status";
    private static final String KEY_HISTORY_CATEGORY = "history_category";
    private static final String KEY_HISTORY_PHOTO_URL = "history_photo_url";

    private static final String KEY_DAY_ID = "day_id";
    private static final String KEY_DAY_NO = "day_no";
    private static final String KEY_DAY_TITLE = "day_title";
    private static final String KEY_DAY_DESCRIPTION = "day_description";
    private static final String KEY_DAY_THUMBNAIL_URL = "day_thumbnail_url";
    private static final String KEY_DAY_FEATURED = "day_featured";
    private static final String KEY_DAY_MOST_VIEW = "day_most_view";
    private static final String KEY_DAY_MOST_DOWNLOAD = "day_most_download";
    private static final String KEY_DAY_WEBSITE = "day_website";
    private static final String KEY_DAY_UPDATED_ON = "day_updated_on";
    private static final String KEY_DAY_STATUS = "day_status";
    private static final String KEY_DAY_PHOTO_URL = "day_photo_url";

    private static final String KEY_DEPARTMENT_ID = "department_id";
    private static final String KEY_DEPARTMENT_NO = "department_no";
    private static final String KEY_DEPARTMENT_TITLE = "department_title";
    private static final String KEY_DEPARTMENT_DESCRIPTION = "department_description";
    private static final String KEY_DEPARTMENT_THUMBNAIL_URL = "department_thumbnail_url";
    private static final String KEY_DEPARTMENT_FEATURED = "department_featured";
    private static final String KEY_DEPARTMENT_MOST_VIEW = "department_most_view";
    private static final String KEY_DEPARTMENT_MOST_DOWNLOAD = "department_most_download";
    private static final String KEY_DEPARTMENT_WEBSITE = "department_website";
    private static final String KEY_DEPARTMENT_UPDATED_ON = "department_updated_on";
    private static final String KEY_DEPARTMENT_STATUS = "department_status";
    private static final String KEY_DEPARTMENT_PHOTO_URL = "department_photo_url";

    private static final String KEY_APP_ID = "app_id";
    private static final String KEY_APP_NO = "app_no";
    private static final String KEY_APP_TITLE = "app_title";
    private static final String KEY_APP_DESCRIPTION = "app_description";
    private static final String KEY_APP_THUMBNAIL_URL = "app_thumbnail_url";
    private static final String KEY_APP_FEATURED = "app_featured";
    private static final String KEY_APP_MOST_VIEW = "app_most_view";
    private static final String KEY_APP_MOST_DOWNLOAD = "app_most_download";
    private static final String KEY_APP_WEBSITE = "app_website";
    private static final String KEY_APP_UPDATED_ON = "app_updated_on";
    private static final String KEY_APP_STATUS = "app_status";
    private static final String KEY_APP_PLAY_STORE_LINK = "app_play_store_link";
    private static final String KEY_APP_PHOTO_URL = "app_photo_url";

    private static final String KEY_ARTICLE_ID = "article_id";
    private static final String KEY_ARTICLE_NO = "article_no";
    private static final String KEY_ARTICLE_TITLE = "article_title";
    private static final String KEY_ARTICLE_DESCRIPTION = "article_description";
    private static final String KEY_ARTICLE_THUMBNAIL_URL = "article_thumbnail_url";
    private static final String KEY_ARTICLE_FEATURED = "article_featured";
    private static final String KEY_ARTICLE_MOST_VIEW = "article_most_view";
    private static final String KEY_ARTICLE_MOST_DOWNLOAD = "article_most_download";
    private static final String KEY_ARTICLE_WEBSITE = "article_website";
    private static final String KEY_ARTICLE_UPDATED_ON = "article_updated_on";
    private static final String KEY_ARTICLE_STATUS = "article_status";
    private static final String KEY_ARTICLE_CATEGORY = "article_category";
    private static final String KEY_ARTICLE_WRITER = "article_writer";
    private static final String KEY_ARTICLE_PHOTO_URL = "article_photo_url";

    private static final String KEY_BLOG_ID = "blog_id";
    private static final String KEY_BLOG_NO = "blog_no";
    private static final String KEY_BLOG_TITLE = "blog_title";
    private static final String KEY_BLOG_DESCRIPTION = "blog_description";
    private static final String KEY_BLOG_THUMBNAIL_URL = "blog_thumbnail_url";
    private static final String KEY_BLOG_FEATURED = "blog_featured";
    private static final String KEY_BLOG_MOST_VIEW = "blog_most_view";
    private static final String KEY_BLOG_MOST_DOWNLOAD = "blog_most_download";
    private static final String KEY_BLOG_WEBSITE = "blog_website";
    private static final String KEY_BLOG_UPDATED_ON = "blog_updated_on";
    private static final String KEY_BLOG_STATUS = "blog_status";
    private static final String KEY_BLOG_CATEGORY = "blog_category";
    private static final String KEY_BLOG_WRITER = "blog_writer";
    private static final String KEY_BLOG_PHOTO_URL = "blog_photo_url";

    private static final String KEY_POETRY_ID = "poetry_id";
    private static final String KEY_POETRY_NO = "poetry_no";
    private static final String KEY_POETRY_TITLE = "poetry_title";
    private static final String KEY_POETRY_DESCRIPTION = "poetry_description";
    private static final String KEY_POETRY_THUMBNAIL_URL = "poetry_thumbnail_url";
    private static final String KEY_POETRY_FEATURED = "poetry_featured";
    private static final String KEY_POETRY_MOST_VIEW = "poetry_most_view";
    private static final String KEY_POETRY_MOST_DOWNLOAD = "poetry_most_download";
    private static final String KEY_POETRY_WEBSITE = "poetry_website";
    private static final String KEY_POETRY_UPDATED_ON = "poetry_updated_on";
    private static final String KEY_POETRY_STATUS = "poetry_status";
    private static final String KEY_POETRY_CATEGORY = "poetry_category";
    private static final String KEY_POETRY_WRITER = "poetry_writer";
    private static final String KEY_POETRY_PHOTO_URL = "poetry_photo_url";


    /**
     * ***************** Database Name ***********
     */
    private static final String DATABASE_NAME = "DB_Zia_e_Taibah";

    /**
     * ***************** Database Version (Increase one if want to upgrade your database) ***********
     */
    private static final int DATABASE_VERSION = 1;// started at 1

    /**
     * Table names
     */
    private static final String TABLE_SETTINGS = "table_settings";
    private static final String TABLE_LANGUAGES = "table_languages";
    private static final String TABLE_SERVICES = "table_services";
    private static final String TABLE_MASTER = "table_master";
    private static final String TABLE_SUB_MASTER = "table_sub_master";
    private static final String TABLE_DATA_TABLES = "table_data_tables";

    public static final String TABLE_BOOKS = "table_books";           // public
    public static final String TABLE_MEDIA = "table_media";           // public
    public static final String TABLE_AUDIO_VIDEO = "table_audio_video";     // public
    public static final String TABLE_SCHOLAR = "table_scholar";         // public
    public static final String TABLE_GALLERY = "table_gallery";         // public
    public static final String TABLE_NEWS = "table_news";            // public
    public static final String TABLE_PLACES = "table_places";          // public
    public static final String TABLE_HISTORY = "table_history";         // public
    public static final String TABLE_DAYS = "table_days";            // public
    public static final String TABLE_DEPARTMENTS = "table_departments";     // public
    public static final String TABLE_APPS = "table_apps";            // public
    public static final String TABLE_ARTICLES = "table_articles";        // public
    public static final String TABLE_BLOGS = "table_blogs";           // public
    public static final String TABLE_POETRY = "table_poetry";          // public

    /**
     * ***************** Set all table with comma seperated like USER_TABLE,ABC_TABLE ***********
     */
    private static final String[] ALL_TABLES = {TABLE_SETTINGS, TABLE_LANGUAGES, TABLE_SERVICES, TABLE_MASTER, TABLE_SUB_MASTER,
            TABLE_DATA_TABLES, TABLE_BOOKS, TABLE_MEDIA, TABLE_AUDIO_VIDEO, TABLE_SCHOLAR, TABLE_GALLERY, TABLE_NEWS, TABLE_PLACES,
            TABLE_HISTORY, TABLE_DAYS, TABLE_DEPARTMENTS, TABLE_APPS, TABLE_ARTICLES, TABLE_BLOGS, TABLE_POETRY};

    /**
     * Create table syntax
     */

    private static final String CREATE_SETTINGS_TABLE = "create table " + TABLE_SETTINGS + "("
            + KEY_SETTING_ID + " integer primary key autoincrement, "
            + KEY_SETTING_STAY_AWAKE + " text not null, "
            + KEY_SETTING_VERSION + " text not null, "
            + KEY_SETTING_DATA_PATH + " text not null, "
            + KEY_SETTING_AUTO_UPDATE + " text not null);";

    private static final String CREATE_LANGUAGE_TABLE = "create table " + TABLE_LANGUAGES + "("
            + KEY_LANGUAGE_ID + " integer primary key autoincrement, "
            + KEY_LANGUAGE_NUMBER + " integer not null, "
            + KEY_LANGUAGE_TITLE + " text not null, "
            + KEY_LANGUAGE_SHORT_NAME + " text not null);";

    private static final String CREATE_SERVICE_TABLE = "create table " + TABLE_SERVICES + "("
            + KEY_SERVICE_ID + " integer primary key autoincrement, "
            + KEY_SERVICE_NUMBER + " integer not null, "
            + KEY_LANGUAGE_ID + " integer not null, "
            + KEY_SERVICE_TITLE + " text not null, "
            + KEY_SERVICE_THUMBNAIL_URL + " text not null, "
            + KEY_SERVICE_STATUS + " text not null);";

    private static final String CREATE_MASTER_TABLE = "create table " + TABLE_MASTER + "("
            + KEY_MASTER_ID + " integer primary key autoincrement, "
            + KEY_MASTER_NUMBER + " integer not null, "
            + KEY_SERVICE_ID + " integer not null, "
            + KEY_MASTER_PARENT + " text not null, "
            + KEY_MASTER_TITLE + " text not null, "
            + KEY_MASTER_THUMBNAIL_URL + " text not null, "
            + KEY_MASTER_STATUS + " text not null);";

    private static final String CREATE_SUB_MASTER_TABLE = "create table " + TABLE_SUB_MASTER + "("
            + KEY_SUB_MASTER_ID + " integer primary key autoincrement, "
            + KEY_SUB_MASTER_NUMBER + " integer not null, "
            + KEY_MASTER_ID + " integer not null, "
            + KEY_SERVICE_ID + " integer not null, "
            + KEY_SUB_MASTER_PARENT + " text not null, "
            + KEY_SUB_MASTER_TITLE + " text not null);";

    private static final String CREATE_DATA_TABLES_TABLE = "create table " + TABLE_DATA_TABLES + "("
            + KEY_DATA_TABLES_ID + " integer primary key autoincrement, "
            + KEY_DATA_TABLES_NO + " integer not null, "
            + KEY_DATA_TABLES_TITLE + " text not null, "
            + KEY_DATA_TABLES_SHORT_NAME + " text not null);";

    private static final String CREATE_BOOK_TABLE = "create table " + TABLE_BOOKS + "("
            + KEY_BOOK_ID + " integer primary key autoincrement, "
            + KEY_BOOK_NO + " integer not null, "
            + KEY_SERVICE_ID + " integer not null, "
            + KEY_BOOK_TITLE + " text not null, "
            + KEY_BOOK_DESCRIPTION + " text not null, "
            + KEY_BOOK_THUMBNAIL_URL + " text not null, "
            + KEY_BOOK_FEATURED + " integer not null, "
            + KEY_BOOK_MOST_VIEW + " integer not null, "
            + KEY_BOOK_MOST_DOWNLOAD + " integer not null, "
            + KEY_BOOK_WEBSITE + " text not null, "
            + KEY_BOOK_UPDATED_ON + " text not null, "
            + KEY_BOOK_STATUS + " text not null, "
            + KEY_BOOK_CATEGORY + " text not null, "
            + KEY_BOOK_AUTHOR + " text not null, "
            + KEY_BOOK_LANGUAGE + " text not null, "
            + KEY_BOOK_PUBLISHER + " text not null, "
            + KEY_BOOK_MONTH + " text not null, "
            + KEY_BOOK_PDF_URL + " text not null, "
            + KEY_BOOK_PHOTO_URL + " text not null);";

    private static final String CREATE_MEDIA_TABLE = "create table " + TABLE_MEDIA + "("
            + KEY_MEDIA_ID + " integer primary key autoincrement, "
            + KEY_MEDIA_NO + " integer not null, "
            + KEY_SERVICE_ID + " integer not null, "
            + KEY_MEDIA_TITLE + " text not null, "
            + KEY_MEDIA_DESCRIPTION + " text not null, "
            + KEY_MEDIA_THUMBNAIL_URL + " text not null, "
            + KEY_MEDIA_FEATURED + " integer not null, "
            + KEY_MEDIA_MOST_VIEW + " integer not null, "
            + KEY_MEDIA_MOST_DOWNLOAD + " integer not null, "
            + KEY_MEDIA_WEBSITE + " text not null, "
            + KEY_MEDIA_UPDATED_ON + " text not null, "
            + KEY_MEDIA_STATUS + " text not null, "
            + KEY_MEDIA_CATEGORY + " text not null, "
            + KEY_MEDIA_VOCALIST + " text not null, "
            + KEY_MEDIA_ATTRIBUTE + " text not null, "
            + KEY_MEDIA_LANGUAGE + " text not null, "
            + KEY_MEDIA_TYPE + " text not null, "
            + KEY_MEDIA_PHOTO_URL + " text not null);";

    private static final String CREATE_AUDIO_VIDEO_TABLE = "create table " + TABLE_AUDIO_VIDEO + "("
            + KEY_AUDIO_VIDEO_ID + " integer primary key autoincrement, "
            + KEY_MEDIA_ID + " integer not null, "
            + KEY_AUDIO_VIDEO_TITLE + " text not null, "
            + KEY_AUDIO_VIDEO_URL + " text not null, "
            + KEY_AUDIO_VIDEO_TYPE + " text not null);";

    private static final String CREATE_SCHOLAR_TABLE = "create table " + TABLE_SCHOLAR + "("
            + KEY_SCHOLAR_ID + " integer primary key autoincrement, "
            + KEY_SCHOLAR_NO + " integer not null, "
            + KEY_SERVICE_ID + " integer not null, "
            + KEY_SCHOLAR_TITLE + " text not null, "
            + KEY_SCHOLAR_DESCRIPTION + " text not null, "
            + KEY_SCHOLAR_THUMBNAIL_URL + " text not null, "
            + KEY_SCHOLAR_FEATURED + " integer not null, "
            + KEY_SCHOLAR_MOST_VIEW + " integer not null, "
            + KEY_SCHOLAR_MOST_DOWNLOAD + " integer not null, "
            + KEY_SCHOLAR_WEBSITE + " text not null, "
            + KEY_SCHOLAR_UPDATED_ON + " text not null, "
            + KEY_SCHOLAR_STATUS + " text not null, "
            + KEY_SCHOLAR_BIRTH_DATE + " text not null, "
            + KEY_SCHOLAR_URS_DATE + " text not null, "
            + KEY_SCHOLAR_PHOTO_URL + " text not null);";

    private static final String CREATE_GALLERY_TABLE = "create table " + TABLE_GALLERY + "("
            + KEY_GALLERY_ID + " integer primary key autoincrement, "
            + KEY_GALLERY_NO + " integer not null, "
            + KEY_SERVICE_ID + " integer not null, "
            + KEY_GALLERY_TITLE + " text not null, "
            + KEY_GALLERY_DESCRIPTION + " text not null, "
            + KEY_GALLERY_THUMBNAIL_URL + " text not null, "
            + KEY_GALLERY_FEATURED + " integer not null, "
            + KEY_GALLERY_MOST_VIEW + " integer not null, "
            + KEY_GALLERY_MOST_DOWNLOAD + " integer not null, "
            + KEY_GALLERY_WEBSITE + " text not null, "
            + KEY_GALLERY_UPDATED_ON + " text not null, "
            + KEY_GALLERY_STATUS + " text not null, "
            + KEY_GALLERY_CATEGORY + " text not null, "
            + KEY_GALLERY_DOWNLOAD_IMAGE + " text not null, "
            + KEY_GALLERY_PHOTO_URL + " text not null);";

    private static final String CREATE_NEWS_TABLE = "create table " + TABLE_NEWS + "("
            + KEY_NEWS_ID + " integer primary key autoincrement, "
            + KEY_NEWS_NO + " integer not null, "
            + KEY_SERVICE_ID + " integer not null, "
            + KEY_NEWS_TITLE + " text not null, "
            + KEY_NEWS_DESCRIPTION + " text not null, "
            + KEY_NEWS_THUMBNAIL_URL + " text not null, "
            + KEY_NEWS_FEATURED + " integer not null, "
            + KEY_NEWS_MOST_VIEW + " integer not null, "
            + KEY_NEWS_MOST_DOWNLOAD + " integer not null, "
            + KEY_NEWS_WEBSITE + " text not null, "
            + KEY_NEWS_UPDATED_ON + " text not null, "
            + KEY_NEWS_STATUS + " text not null, "
            + KEY_NEWS_ORGANIZATION + " text not null, "
            + KEY_NEWS_COUNTRY + " text not null, "
            + KEY_NEWS_NEWS_PAPER + " text not null, "
            + KEY_NEWS_PHOTO_URL + " text not null);";

    private static final String CREATE_PLACE_TABLE = "create table " + TABLE_PLACES + "("
            + KEY_PLACE_ID + " integer primary key autoincrement, "
            + KEY_PLACE_NO + " integer not null, "
            + KEY_SERVICE_ID + " integer not null, "
            + KEY_PLACE_TITLE + " text not null, "
            + KEY_PLACE_DESCRIPTION + " text not null, "
            + KEY_PLACE_THUMBNAIL_URL + " text not null, "
            + KEY_PLACE_FEATURED + " integer not null, "
            + KEY_PLACE_MOST_VIEW + " integer not null, "
            + KEY_PLACE_MOST_DOWNLOAD + " integer not null, "
            + KEY_PLACE_WEBSITE + " text not null, "
            + KEY_PLACE_UPDATED_ON + " text not null, "
            + KEY_PLACE_STATUS + " text not null, "
            + KEY_PLACE_CATEGORY + " text not null, "
            + KEY_PLACE_PHOTO_URL + " text not null);";

    private static final String CREATE_HISTORY_TABLE = "create table " + TABLE_HISTORY + "("
            + KEY_HISTORY_ID + " integer primary key autoincrement, "
            + KEY_HISTORY_NO + " integer not null, "
            + KEY_SERVICE_ID + " integer not null, "
            + KEY_HISTORY_TITLE + " text not null, "
            + KEY_HISTORY_DESCRIPTION + " text not null, "
            + KEY_HISTORY_THUMBNAIL_URL + " text not null, "
            + KEY_HISTORY_FEATURED + " integer not null, "
            + KEY_HISTORY_MOST_VIEW + " integer not null, "
            + KEY_HISTORY_MOST_DOWNLOAD + " integer not null, "
            + KEY_HISTORY_WEBSITE + " text not null, "
            + KEY_HISTORY_UPDATED_ON + " text not null, "
            + KEY_HISTORY_STATUS + " text not null, "
            + KEY_HISTORY_CATEGORY + " text not null, "
            + KEY_HISTORY_PHOTO_URL + " text not null);";

    private static final String CREATE_DAYS_TABLE = "create table " + TABLE_DAYS + "("
            + KEY_DAY_ID + " integer primary key autoincrement, "
            + KEY_DAY_NO + " integer not null, "
            + KEY_SERVICE_ID + " integer not null, "
            + KEY_DAY_TITLE + " text not null, "
            + KEY_DAY_DESCRIPTION + " text not null, "
            + KEY_DAY_THUMBNAIL_URL + " text not null, "
            + KEY_DAY_FEATURED + " integer not null, "
            + KEY_DAY_MOST_VIEW + " integer not null, "
            + KEY_DAY_MOST_DOWNLOAD + " integer not null, "
            + KEY_DAY_WEBSITE + " text not null, "
            + KEY_DAY_UPDATED_ON + " text not null, "
            + KEY_DAY_STATUS + " text not null, "
            + KEY_DAY_PHOTO_URL + " text not null);";

    private static final String CREATE_DEPARTMENTS_TABLE = "create table " + TABLE_DEPARTMENTS + "("
            + KEY_DEPARTMENT_ID + " integer primary key autoincrement, "
            + KEY_DEPARTMENT_NO + " integer not null, "
            + KEY_SERVICE_ID + " integer not null, "
            + KEY_DEPARTMENT_TITLE + " text not null, "
            + KEY_DEPARTMENT_DESCRIPTION + " text not null, "
            + KEY_DEPARTMENT_THUMBNAIL_URL + " text not null, "
            + KEY_DEPARTMENT_FEATURED + " integer not null, "
            + KEY_DEPARTMENT_MOST_VIEW + " integer not null, "
            + KEY_DEPARTMENT_MOST_DOWNLOAD + " integer not null, "
            + KEY_DEPARTMENT_WEBSITE + " text not null, "
            + KEY_DEPARTMENT_UPDATED_ON + " text not null, "
            + KEY_DEPARTMENT_STATUS + " text not null, "
            + KEY_DEPARTMENT_PHOTO_URL + " text not null);";

    private static final String CREATE_APPS_TABLE = "create table " + TABLE_APPS + "("
            + KEY_APP_ID + " integer primary key autoincrement, "
            + KEY_APP_NO + " integer not null, "
            + KEY_SERVICE_ID + " integer not null, "
            + KEY_APP_TITLE + " text not null, "
            + KEY_APP_DESCRIPTION + " text not null, "
            + KEY_APP_THUMBNAIL_URL + " text not null, "
            + KEY_APP_FEATURED + " integer not null, "
            + KEY_APP_MOST_VIEW + " integer not null, "
            + KEY_APP_MOST_DOWNLOAD + " integer not null, "
            + KEY_APP_WEBSITE + " text not null, "
            + KEY_APP_UPDATED_ON + " text not null, "
            + KEY_APP_STATUS + " text not null, "
            + KEY_APP_PLAY_STORE_LINK + " text not null, "
            + KEY_APP_PHOTO_URL + " text not null);";

    private static final String CREATE_ARTICLE_TABLE = "create table " + TABLE_ARTICLES + "("
            + KEY_ARTICLE_ID + " integer primary key autoincrement, "
            + KEY_ARTICLE_NO + " integer not null, "
            + KEY_SERVICE_ID + " integer not null, "
            + KEY_ARTICLE_TITLE + " text not null, "
            + KEY_ARTICLE_DESCRIPTION + " text not null, "
            + KEY_ARTICLE_THUMBNAIL_URL + " text not null, "
            + KEY_ARTICLE_FEATURED + " integer not null, "
            + KEY_ARTICLE_MOST_VIEW + " integer not null, "
            + KEY_ARTICLE_MOST_DOWNLOAD + " integer not null, "
            + KEY_ARTICLE_WEBSITE + " text not null, "
            + KEY_ARTICLE_UPDATED_ON + " text not null, "
            + KEY_ARTICLE_STATUS + " text not null, "
            + KEY_ARTICLE_CATEGORY + " text not null, "
            + KEY_ARTICLE_WRITER + " text not null, "
            + KEY_ARTICLE_PHOTO_URL + " text not null);";

    private static final String CREATE_BLOG_TABLE = "create table " + TABLE_BLOGS + "("
            + KEY_BLOG_ID + " integer primary key autoincrement, "
            + KEY_BLOG_NO + " integer not null, "
            + KEY_SERVICE_ID + " integer not null, "
            + KEY_BLOG_TITLE + " text not null, "
            + KEY_BLOG_DESCRIPTION + " text not null, "
            + KEY_BLOG_THUMBNAIL_URL + " text not null, "
            + KEY_BLOG_FEATURED + " integer not null, "
            + KEY_BLOG_MOST_VIEW + " integer not null, "
            + KEY_BLOG_MOST_DOWNLOAD + " integer not null, "
            + KEY_BLOG_WEBSITE + " text not null, "
            + KEY_BLOG_UPDATED_ON + " text not null, "
            + KEY_BLOG_STATUS + " text not null, "
            + KEY_BLOG_CATEGORY + " text not null, "
            + KEY_BLOG_WRITER + " text not null, "
            + KEY_BLOG_PHOTO_URL + " text not null);";

    private static final String CREATE_POETRY_TABLE = "create table " + TABLE_POETRY + "("
            + KEY_POETRY_ID + " integer primary key autoincrement, "
            + KEY_POETRY_NO + " integer not null, "
            + KEY_SERVICE_ID + " integer not null, "
            + KEY_POETRY_TITLE + " text not null, "
            + KEY_POETRY_DESCRIPTION + " text not null, "
            + KEY_POETRY_THUMBNAIL_URL + " text not null, "
            + KEY_POETRY_FEATURED + " integer not null, "
            + KEY_POETRY_MOST_VIEW + " integer not null, "
            + KEY_POETRY_MOST_DOWNLOAD + " integer not null, "
            + KEY_POETRY_WEBSITE + " text not null, "
            + KEY_POETRY_UPDATED_ON + " text not null, "
            + KEY_POETRY_STATUS + " text not null, "
            + KEY_POETRY_CATEGORY + " text not null, "
            + KEY_POETRY_WRITER + " text not null, "
            + KEY_POETRY_PHOTO_URL + " text not null);";

    /**
     * ***************** Used to open database in syncronized way ***********
     */
    private static DataBaseHelper DBHelper = null;

    protected DBAdapter() {

    }

    /**
     * **************** Initialize database ************
     */
    public static void init(Context context) {

        if (DBHelper == null) {

            if (DEBUG)
                Log.i("DBAdapter", context.toString());
            DBHelper = new DataBaseHelper(context);
        }
    }

    /**
     * ******************* Main Database creation INNER class *******************
     */
    private static class DataBaseHelper extends SQLiteOpenHelper {

        public DataBaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            if (DEBUG)
                Log.i(LOG_TAG, "new create");
            try {

                db.execSQL(CREATE_SETTINGS_TABLE);
                db.execSQL(CREATE_LANGUAGE_TABLE);
                db.execSQL(CREATE_SERVICE_TABLE);
                db.execSQL(CREATE_MASTER_TABLE);
                db.execSQL(CREATE_SUB_MASTER_TABLE);
                db.execSQL(CREATE_DATA_TABLES_TABLE);
                db.execSQL(CREATE_BOOK_TABLE);
                db.execSQL(CREATE_MEDIA_TABLE);
                db.execSQL(CREATE_AUDIO_VIDEO_TABLE);
                db.execSQL(CREATE_SCHOLAR_TABLE);
                db.execSQL(CREATE_GALLERY_TABLE);
                db.execSQL(CREATE_NEWS_TABLE);
                db.execSQL(CREATE_PLACE_TABLE);
                db.execSQL(CREATE_HISTORY_TABLE);
                db.execSQL(CREATE_DAYS_TABLE);
                db.execSQL(CREATE_DEPARTMENTS_TABLE);
                db.execSQL(CREATE_APPS_TABLE);
                db.execSQL(CREATE_ARTICLE_TABLE);
                db.execSQL(CREATE_BLOG_TABLE);
                db.execSQL(CREATE_POETRY_TABLE);

            } catch (Exception exception) {
                if (DEBUG)
                    Log.e(LOG_TAG, "Exception onCreate() exception");
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            if (DEBUG)
                Log.w(LOG_TAG, "Upgrading database from version" + oldVersion
                        + "to" + newVersion + "...");

            for (String table : ALL_TABLES) {
                db.execSQL("DROP TABLE IF EXISTS " + table);
            }

            onCreate(db);
        }

    } // Inner class closed

    /**
     * ******************* Open database for insert,update,delete in synchronized manner *******************
     */
    private static synchronized SQLiteDatabase open() throws SQLException {
        return DBHelper.getWritableDatabase();
    }

    /**
     * ********************* General functions *************************
     */
    public static boolean isDatabaseAvailable() {

        if (DBHelper == null)
            return false;
        else
            return true;
    }

    public static void closeDatabase() {

        if (DBHelper != null)
            DBHelper.close();

    }

    /**
     * ******************** Escape string for single quotes (Insert,Update) ***********
     */
    private static String sqlEscapeString(String aString) {

        String aReturn = "";

        if (null != aString) {
            // aReturn = aString.replace("'", "''");
            aReturn = DatabaseUtils.sqlEscapeString(aString);
            // Remove the enclosing single quotes ...
            aReturn = aReturn.substring(1, aReturn.length() - 1);
        }

        return aReturn;
    }

    /**
     * ******************** UnEscape string for single quotes (show data) ***********
     */
    private static String sqlUnEscapeString(String aString) {

        String aReturn = "";

        if (null != aString) {
            aReturn = aString.replace("''", "'");
        }

        return aReturn;
    }

    /********************************************************************/

    /**
     * All Operations (Create, Read, Update, Delete)
     *
     * @return
     */
    // Adding new contact
    public static long addSettingsData(SettingsModel settingsModel) {

        final SQLiteDatabase db = open();

        // int id = settingsModel.getId();
        String awake = sqlEscapeString(settingsModel.getStayAwake());
        String version = sqlEscapeString(settingsModel.getVersion());
        String dataPath = sqlEscapeString(settingsModel.getDataPath());
        String update = sqlEscapeString(settingsModel.getAutoUpdate());

        ContentValues cVal = new ContentValues();

        cVal.put(KEY_SETTING_STAY_AWAKE, awake);
        cVal.put(KEY_SETTING_VERSION, version);
        cVal.put(KEY_SETTING_DATA_PATH, dataPath);
        cVal.put(KEY_SETTING_AUTO_UPDATE, update);

        long rowId = db.insert(TABLE_SETTINGS, null, cVal);
        // db.close(); // Closing database connection

        return rowId;
    }

    public static long addLanguageData(LanguageModel languageModel) {

        final SQLiteDatabase db = open();

//        int id = languageModel.getId();
        int no = languageModel.getNo();
        String title = sqlEscapeString(languageModel.getTitle());
        String shortName = sqlEscapeString(languageModel.getShortName());

        ContentValues cVal = new ContentValues();

        cVal.put(KEY_LANGUAGE_NUMBER, no);
        cVal.put(KEY_LANGUAGE_TITLE, title);
        cVal.put(KEY_LANGUAGE_SHORT_NAME, shortName);

        long rowId = db.insert(TABLE_LANGUAGES, null, cVal);
        // db.close(); // Closing database connection

        return rowId;
    }

    public static long addServiceData(ServiceModel serviceModel) {

        final SQLiteDatabase db = open();

        int number = serviceModel.getNumber();
        int languageId = serviceModel.getLanguageId();
        String title = sqlEscapeString(serviceModel.getTitle());
        String thumbnailUrl = sqlEscapeString(serviceModel.getThumbnailUrl());
        String status = sqlEscapeString(serviceModel.getStatus());

        ContentValues cVal = new ContentValues();

        cVal.put(KEY_SERVICE_NUMBER, number);
        cVal.put(KEY_LANGUAGE_ID, languageId);
        cVal.put(KEY_SERVICE_TITLE, title);
        cVal.put(KEY_SERVICE_THUMBNAIL_URL, thumbnailUrl);
        cVal.put(KEY_SERVICE_STATUS, status);

        long rowId = db.insert(TABLE_SERVICES, null, cVal);

        // db.close(); // Closing database connection

        return rowId;
    }

    public static long addMasterData(MasterModel masterModel) {

        final SQLiteDatabase db = open();

        int number = masterModel.getNumber();
        int serviceId = masterModel.getServiceId();
        String parent = sqlEscapeString(masterModel.getParent());
        String title = sqlEscapeString(masterModel.getTitle());
        String thumbnailUrl = sqlEscapeString(masterModel.getThumbnailUrl());
        String status = sqlEscapeString(masterModel.getStatus());

        ContentValues cVal = new ContentValues();

        cVal.put(KEY_MASTER_NUMBER, number);
        cVal.put(KEY_SERVICE_ID, serviceId);
        cVal.put(KEY_MASTER_PARENT, parent);
        cVal.put(KEY_MASTER_TITLE, title);
        cVal.put(KEY_MASTER_THUMBNAIL_URL, thumbnailUrl);
        cVal.put(KEY_MASTER_STATUS, status);

        long rowId = db.insert(TABLE_MASTER, null, cVal);

        // db.close(); // Closing database connection

        return rowId;
    }

    public static long addSubMasterData(SubMasterModel subMasterModel) {

        final SQLiteDatabase db = open();

        int number = subMasterModel.getNumber();
        int masterId = subMasterModel.getMasterId();
        int serviceId = subMasterModel.getServiceId();
        String parent = sqlEscapeString(subMasterModel.getParent());
        String title = sqlEscapeString(subMasterModel.getTitle());

        ContentValues cVal = new ContentValues();

        cVal.put(KEY_SUB_MASTER_NUMBER, number);
        cVal.put(KEY_MASTER_ID, masterId);
        cVal.put(KEY_SERVICE_ID, serviceId);
        cVal.put(KEY_SUB_MASTER_PARENT, parent);
        cVal.put(KEY_SUB_MASTER_TITLE, title);

        long rowId = db.insert(TABLE_SUB_MASTER, null, cVal);

        // db.close(); // Closing database connection

        return rowId;
    }

    public static long addDataTablesData(ProductModel productModel) {

        final SQLiteDatabase db = open();

        // int id = productModel.getId();
        int no = productModel.getServiceNo();
        String title = sqlEscapeString(productModel.getTitle());
        String shortName = sqlEscapeString(productModel.getShortName());

        ContentValues cVal = new ContentValues();

        cVal.put(KEY_DATA_TABLES_NO, no);
        cVal.put(KEY_DATA_TABLES_TITLE, title);
        cVal.put(KEY_DATA_TABLES_SHORT_NAME, shortName);

        long rowId = db.insert(TABLE_DATA_TABLES, null, cVal);
        // db.close(); // Closing database connection

        return rowId;
    }

    public static long addBookData(BookModel bookModel) {

        final SQLiteDatabase db = open();

        // int id = bookModel.getId();
        int no = bookModel.getNo();
        int serviceId = bookModel.getServiceId();
        String title = sqlEscapeString(bookModel.getTitle());
        String description = sqlEscapeString(bookModel.getDescription());
        String thumbnailUrl = sqlEscapeString(bookModel.getThumbnailUrl());
        int featured = bookModel.getFeatured();
        int mostView = bookModel.getMostView();
        int mostDownload = bookModel.getMostDownload();
        String website = sqlEscapeString(bookModel.getWebsite());
        String updatedOn = sqlEscapeString(bookModel.getUpdatedOn());
        String status = sqlEscapeString(bookModel.getStatus());
        String category = sqlEscapeString(bookModel.getCategory());
        String author = sqlEscapeString(bookModel.getAuthor());
        String language = sqlEscapeString(bookModel.getLanguage());
        String publisher = sqlEscapeString(bookModel.getPublisher());
        String month = sqlEscapeString(bookModel.getMonth());
        String pdfUrl = sqlEscapeString(bookModel.getPdfUrl());
        String photoUrl = sqlEscapeString(bookModel.getPhotoUrl());

        ContentValues cVal = new ContentValues();

        cVal.put(KEY_BOOK_NO, no);
        cVal.put(KEY_SERVICE_ID, serviceId);
        cVal.put(KEY_BOOK_TITLE, title);
        cVal.put(KEY_BOOK_DESCRIPTION, description);
        cVal.put(KEY_BOOK_THUMBNAIL_URL, thumbnailUrl);
        cVal.put(KEY_BOOK_FEATURED, featured);
        cVal.put(KEY_BOOK_MOST_VIEW, mostView);
        cVal.put(KEY_BOOK_MOST_DOWNLOAD, mostDownload);
        cVal.put(KEY_BOOK_WEBSITE, website);
        cVal.put(KEY_BOOK_UPDATED_ON, updatedOn);
        cVal.put(KEY_BOOK_STATUS, status);
        cVal.put(KEY_BOOK_CATEGORY, category);
        cVal.put(KEY_BOOK_AUTHOR, author);
        cVal.put(KEY_BOOK_LANGUAGE, language);
        cVal.put(KEY_BOOK_PUBLISHER, publisher);
        cVal.put(KEY_BOOK_MONTH, month);
        cVal.put(KEY_BOOK_PDF_URL, pdfUrl);
        cVal.put(KEY_BOOK_PHOTO_URL, photoUrl);

        long rowId = db.insert(TABLE_BOOKS, null, cVal);
        // db.close(); // Closing database connection

        return rowId;
    }

    public static long addMediaData(MediaModel mediaModel) {

        final SQLiteDatabase db = open();

        // int id = mediaModel.getId();
        int no = mediaModel.getNo();
        int serviceId = mediaModel.getServiceId();
        String title = sqlEscapeString(mediaModel.getTitle());
        String description = sqlEscapeString(mediaModel.getDescription());
        String thumbnailUrl = sqlEscapeString(mediaModel.getThumbnailUrl());
        int featured = mediaModel.getFeatured();
        int mostView = mediaModel.getMostView();
        int mostDownload = mediaModel.getMostDownload();
        String website = sqlEscapeString(mediaModel.getWebsite());
        String updatedOn = sqlEscapeString(mediaModel.getUpdatedOn());
        String status = sqlEscapeString(mediaModel.getStatus());
        String category = sqlEscapeString(mediaModel.getCategory());
        String vocalist = sqlEscapeString(mediaModel.getVocalist());
        String attribute = sqlEscapeString(mediaModel.getAttribute());
        String language = sqlEscapeString(mediaModel.getLanguage());
        String type = sqlEscapeString(mediaModel.getType());
        String photoUrl = sqlEscapeString(mediaModel.getPhotoUrl());

        ContentValues cVal = new ContentValues();
        cVal.put(KEY_MEDIA_NO, no);
        cVal.put(KEY_SERVICE_ID, serviceId);
        cVal.put(KEY_MEDIA_TITLE, title);
        cVal.put(KEY_MEDIA_DESCRIPTION, description);
        cVal.put(KEY_MEDIA_THUMBNAIL_URL, thumbnailUrl);
        cVal.put(KEY_MEDIA_FEATURED, featured);
        cVal.put(KEY_MEDIA_MOST_VIEW, mostView);
        cVal.put(KEY_MEDIA_MOST_DOWNLOAD, mostDownload);
        cVal.put(KEY_MEDIA_WEBSITE, website);
        cVal.put(KEY_MEDIA_UPDATED_ON, updatedOn);
        cVal.put(KEY_MEDIA_STATUS, status);
        cVal.put(KEY_MEDIA_CATEGORY, category);
        cVal.put(KEY_MEDIA_VOCALIST, vocalist);
        cVal.put(KEY_MEDIA_ATTRIBUTE, attribute);
        cVal.put(KEY_MEDIA_LANGUAGE, language);
        cVal.put(KEY_MEDIA_TYPE, type);
        cVal.put(KEY_MEDIA_PHOTO_URL, photoUrl);

        long rowId = db.insert(TABLE_MEDIA, null, cVal);
        // db.close(); // Closing database connection

        return rowId;
    }

    public static long addAudioVideoData(AudioVideoModel audioVideoModel) {

        final SQLiteDatabase db = open();

        // int id = audioVideoModel.getId();
        int mediaId = audioVideoModel.getMediaId();
        String title = sqlEscapeString(audioVideoModel.getTitle());
        String url = sqlEscapeString(audioVideoModel.getUrl());
        String type = sqlEscapeString(audioVideoModel.getType());

        ContentValues cVal = new ContentValues();
        cVal.put(KEY_MEDIA_ID, mediaId);
        cVal.put(KEY_AUDIO_VIDEO_TITLE, title);
        cVal.put(KEY_AUDIO_VIDEO_URL, url);
        cVal.put(KEY_AUDIO_VIDEO_TYPE, type);

        long rowId = db.insert(TABLE_AUDIO_VIDEO, null, cVal);
        // db.close(); // Closing database connection

        return rowId;
    }

    public static long addScholarData(ScholarModel scholarModel) {

        final SQLiteDatabase db = open();

        // int id = scholarModel.getId();
        int no = scholarModel.getNo();
        int serviceId = scholarModel.getServiceId();
        String title = sqlEscapeString(scholarModel.getTitle());
        String description = sqlEscapeString(scholarModel.getDescription());
        String thumbnailUrl = sqlEscapeString(scholarModel.getThumbnailUrl());
        int featured = scholarModel.getFeatured();
        int mostView = scholarModel.getMostView();
        int mostDownload = scholarModel.getMostDownload();
        String website = sqlEscapeString(scholarModel.getWebsite());
        String updatedOn = sqlEscapeString(scholarModel.getUpdatedOn());
        String status = sqlEscapeString(scholarModel.getStatus());
        String birthDate = sqlEscapeString(scholarModel.getBirthDate());
        String ursDate = sqlEscapeString(scholarModel.getUrsDate());
        String photoUrl = sqlEscapeString(scholarModel.getPhotoUrl());

        ContentValues cVal = new ContentValues();
        cVal.put(KEY_SCHOLAR_NO, no);
        cVal.put(KEY_SERVICE_ID, serviceId);
        cVal.put(KEY_SCHOLAR_TITLE, title);
        cVal.put(KEY_SCHOLAR_DESCRIPTION, description);
        cVal.put(KEY_SCHOLAR_THUMBNAIL_URL, thumbnailUrl);
        cVal.put(KEY_SCHOLAR_FEATURED, featured);
        cVal.put(KEY_SCHOLAR_MOST_VIEW, mostView);
        cVal.put(KEY_SCHOLAR_MOST_DOWNLOAD, mostDownload);
        cVal.put(KEY_SCHOLAR_WEBSITE, website);
        cVal.put(KEY_SCHOLAR_UPDATED_ON, updatedOn);
        cVal.put(KEY_SCHOLAR_STATUS, status);
        cVal.put(KEY_SCHOLAR_BIRTH_DATE, birthDate);
        cVal.put(KEY_SCHOLAR_URS_DATE, ursDate);
        cVal.put(KEY_SCHOLAR_PHOTO_URL, photoUrl);

        long rowId = db.insert(TABLE_SCHOLAR, null, cVal);
        // db.close(); // Closing database connection

        return rowId;
    }

    public static long addGalleryData(GalleryModel galleryModel) {

        final SQLiteDatabase db = open();

        // int id = galleryModel.getId();
        int no = galleryModel.getNo();
        int serviceId = galleryModel.getServiceId();
        String title = sqlEscapeString(galleryModel.getTitle());
        String description = sqlEscapeString(galleryModel.getDescription());
        String thumbnailUrl = sqlEscapeString(galleryModel.getThumbnailUrl());
        int featured = galleryModel.getFeatured();
        int mostView = galleryModel.getMostView();
        int mostDownload = galleryModel.getMostDownload();
        String website = sqlEscapeString(galleryModel.getWebsite());
        String updatedOn = sqlEscapeString(galleryModel.getUpdatedOn());
        String status = sqlEscapeString(galleryModel.getStatus());
        String category = sqlEscapeString(galleryModel.getCategory());
        String downloadImage = sqlEscapeString(galleryModel.getDownloadImage());
        String photoUrl = sqlEscapeString(galleryModel.getPhotoUrl());

        ContentValues cVal = new ContentValues();
        cVal.put(KEY_GALLERY_NO, no);
        cVal.put(KEY_SERVICE_ID, serviceId);
        cVal.put(KEY_GALLERY_TITLE, title);
        cVal.put(KEY_GALLERY_DESCRIPTION, description);
        cVal.put(KEY_GALLERY_THUMBNAIL_URL, thumbnailUrl);
        cVal.put(KEY_GALLERY_FEATURED, featured);
        cVal.put(KEY_GALLERY_MOST_VIEW, mostView);
        cVal.put(KEY_GALLERY_MOST_DOWNLOAD, mostDownload);
        cVal.put(KEY_GALLERY_WEBSITE, website);
        cVal.put(KEY_GALLERY_UPDATED_ON, updatedOn);
        cVal.put(KEY_GALLERY_STATUS, status);
        cVal.put(KEY_GALLERY_CATEGORY, category);
        cVal.put(KEY_GALLERY_DOWNLOAD_IMAGE, downloadImage);
        cVal.put(KEY_GALLERY_PHOTO_URL, photoUrl);

        long rowId = db.insert(TABLE_GALLERY, null, cVal);
        // db.close(); // Closing database connection

        return rowId;
    }

    public static long addNewsData(NewsModel newsModel) {

        final SQLiteDatabase db = open();

        // int id = newsModel.getId();
        int no = newsModel.getNo();
        int serviceId = newsModel.getServiceId();
        String title = sqlEscapeString(newsModel.getTitle());
        String description = sqlEscapeString(newsModel.getDescription());
        String thumbnailUrl = sqlEscapeString(newsModel.getThumbnailUrl());
        int featured = newsModel.getFeatured();
        int mostView = newsModel.getMostView();
        int mostDownload = newsModel.getMostDownload();
        String website = sqlEscapeString(newsModel.getWebsite());
        String updatedOn = sqlEscapeString(newsModel.getUpdatedOn());
        String status = sqlEscapeString(newsModel.getStatus());
        String organization = sqlEscapeString(newsModel.getOrganization());
        String country = sqlEscapeString(newsModel.getCountry());
        String newspaper = sqlEscapeString(newsModel.getNewsPaper());
        String photoUrl = sqlEscapeString(newsModel.getPhotoUrl());

        ContentValues cVal = new ContentValues();
        cVal.put(KEY_NEWS_NO, no);
        cVal.put(KEY_SERVICE_ID, serviceId);
        cVal.put(KEY_NEWS_TITLE, title);
        cVal.put(KEY_NEWS_DESCRIPTION, description);
        cVal.put(KEY_NEWS_THUMBNAIL_URL, thumbnailUrl);
        cVal.put(KEY_NEWS_FEATURED, featured);
        cVal.put(KEY_NEWS_MOST_VIEW, mostView);
        cVal.put(KEY_NEWS_MOST_DOWNLOAD, mostDownload);
        cVal.put(KEY_NEWS_WEBSITE, website);
        cVal.put(KEY_NEWS_UPDATED_ON, updatedOn);
        cVal.put(KEY_NEWS_STATUS, status);
        cVal.put(KEY_NEWS_ORGANIZATION, organization);
        cVal.put(KEY_NEWS_COUNTRY, country);
        cVal.put(KEY_NEWS_NEWS_PAPER, newspaper);
        cVal.put(KEY_NEWS_PHOTO_URL, photoUrl);

        long rowId = db.insert(TABLE_NEWS, null, cVal);
        // db.close(); // Closing database connection

        return rowId;
    }

    public static long addPlaceData(PlaceModel placeModel) {

        final SQLiteDatabase db = open();

        // int id = placeModel.getId();
        int no = placeModel.getNo();
        int serviceId = placeModel.getServiceId();
        String title = sqlEscapeString(placeModel.getTitle());
        String description = sqlEscapeString(placeModel.getDescription());
        String thumbnailUrl = sqlEscapeString(placeModel.getThumbnailUrl());
        int featured = placeModel.getFeatured();
        int mostView = placeModel.getMostView();
        int mostDownload = placeModel.getMostDownload();
        String website = sqlEscapeString(placeModel.getWebsite());
        String updatedOn = sqlEscapeString(placeModel.getUpdatedOn());
        String status = sqlEscapeString(placeModel.getStatus());
        String category = sqlEscapeString(placeModel.getCategory());
        String photoUrl = sqlEscapeString(placeModel.getPhotoUrl());

        ContentValues cVal = new ContentValues();
        cVal.put(KEY_PLACE_NO, no);
        cVal.put(KEY_SERVICE_ID, serviceId);
        cVal.put(KEY_PLACE_TITLE, title);
        cVal.put(KEY_PLACE_DESCRIPTION, description);
        cVal.put(KEY_PLACE_THUMBNAIL_URL, thumbnailUrl);
        cVal.put(KEY_PLACE_FEATURED, featured);
        cVal.put(KEY_PLACE_MOST_VIEW, mostView);
        cVal.put(KEY_PLACE_MOST_DOWNLOAD, mostDownload);
        cVal.put(KEY_PLACE_WEBSITE, website);
        cVal.put(KEY_PLACE_UPDATED_ON, updatedOn);
        cVal.put(KEY_PLACE_STATUS, status);
        cVal.put(KEY_PLACE_CATEGORY, category);
        cVal.put(KEY_PLACE_PHOTO_URL, photoUrl);

        long rowId = db.insert(TABLE_PLACES, null, cVal);
        // db.close(); // Closing database connection

        return rowId;
    }

    public static long addHistoryData(HistoryModel historyModel) {

        final SQLiteDatabase db = open();

        // int id = galleryModel.getId();
        int no = historyModel.getNo();
        int serviceId = historyModel.getServiceId();
        String title = sqlEscapeString(historyModel.getTitle());
        String description = sqlEscapeString(historyModel.getDescription());
        String thumbnailUrl = sqlEscapeString(historyModel.getThumbnailUrl());
        int featured = historyModel.getFeatured();
        int mostView = historyModel.getMostView();
        int mostDownload = historyModel.getMostDownload();
        String website = sqlEscapeString(historyModel.getWebsite());
        String updatedOn = sqlEscapeString(historyModel.getUpdatedOn());
        String status = sqlEscapeString(historyModel.getStatus());
        String category = sqlEscapeString(historyModel.getCategory());
        String photoUrl = sqlEscapeString(historyModel.getPhotoUrl());

        ContentValues cVal = new ContentValues();
        cVal.put(KEY_HISTORY_NO, no);
        cVal.put(KEY_SERVICE_ID, serviceId);
        cVal.put(KEY_HISTORY_TITLE, title);
        cVal.put(KEY_HISTORY_DESCRIPTION, description);
        cVal.put(KEY_HISTORY_THUMBNAIL_URL, thumbnailUrl);
        cVal.put(KEY_HISTORY_FEATURED, featured);
        cVal.put(KEY_HISTORY_MOST_VIEW, mostView);
        cVal.put(KEY_HISTORY_MOST_DOWNLOAD, mostDownload);
        cVal.put(KEY_HISTORY_WEBSITE, website);
        cVal.put(KEY_HISTORY_UPDATED_ON, updatedOn);
        cVal.put(KEY_HISTORY_STATUS, status);
        cVal.put(KEY_HISTORY_CATEGORY, category);
        cVal.put(KEY_HISTORY_PHOTO_URL, photoUrl);

        long rowId = db.insert(TABLE_HISTORY, null, cVal);
        // db.close(); // Closing database connection

        return rowId;
    }

    public static long addDaysData(DayModel dayModel) {

        final SQLiteDatabase db = open();

        // int id = dayModel.getId();
        int no = dayModel.getNo();
        int serviceId = dayModel.getServiceId();
        String title = sqlEscapeString(dayModel.getTitle());
        String description = sqlEscapeString(dayModel.getDescription());
        String thumbnailUrl = sqlEscapeString(dayModel.getThumbnailUrl());
        int featured = dayModel.getFeatured();
        int mostView = dayModel.getMostView();
        int mostDownload = dayModel.getMostDownload();
        String website = sqlEscapeString(dayModel.getWebsite());
        String updatedOn = sqlEscapeString(dayModel.getUpdatedOn());
        String status = sqlEscapeString(dayModel.getStatus());
        String photoUrl = sqlEscapeString(dayModel.getPhotoUrl());

        ContentValues cVal = new ContentValues();
        cVal.put(KEY_DAY_NO, no);
        cVal.put(KEY_SERVICE_ID, serviceId);
        cVal.put(KEY_DAY_TITLE, title);
        cVal.put(KEY_DAY_DESCRIPTION, description);
        cVal.put(KEY_DAY_THUMBNAIL_URL, thumbnailUrl);
        cVal.put(KEY_DAY_FEATURED, featured);
        cVal.put(KEY_DAY_MOST_VIEW, mostView);
        cVal.put(KEY_DAY_MOST_DOWNLOAD, mostDownload);
        cVal.put(KEY_DAY_UPDATED_ON, updatedOn);
        cVal.put(KEY_DAY_WEBSITE, website);
        cVal.put(KEY_DAY_STATUS, status);
        cVal.put(KEY_DAY_PHOTO_URL, photoUrl);

        long rowId = db.insert(TABLE_DAYS, null, cVal);
        // db.close(); // Closing database connection

        return rowId;
    }

    public static long addDepartmentsData(DepartmentModel departmentModel) {

        final SQLiteDatabase db = open();

        // int id = departmentModel.getId();
        int no = departmentModel.getNo();
        int serviceId = departmentModel.getServiceId();
        String title = sqlEscapeString(departmentModel.getTitle());
        String description = sqlEscapeString(departmentModel.getDescription());
        String thumbnailUrl = sqlEscapeString(departmentModel.getThumbnailUrl());
        int featured = departmentModel.getFeatured();
        int mostView = departmentModel.getMostView();
        int mostDownload = departmentModel.getMostDownload();
        String website = sqlEscapeString(departmentModel.getWebsite());
        String updatedOn = sqlEscapeString(departmentModel.getUpdatedOn());
        String status = sqlEscapeString(departmentModel.getStatus());
        String photoUrl = sqlEscapeString(departmentModel.getPhotoUrl());

        ContentValues cVal = new ContentValues();
        cVal.put(KEY_DEPARTMENT_NO, no);
        cVal.put(KEY_SERVICE_ID, serviceId);
        cVal.put(KEY_DEPARTMENT_TITLE, title);
        cVal.put(KEY_DEPARTMENT_DESCRIPTION, description);
        cVal.put(KEY_DEPARTMENT_THUMBNAIL_URL, thumbnailUrl);
        cVal.put(KEY_DEPARTMENT_FEATURED, featured);
        cVal.put(KEY_DEPARTMENT_MOST_VIEW, mostView);
        cVal.put(KEY_DEPARTMENT_MOST_DOWNLOAD, mostDownload);
        cVal.put(KEY_DEPARTMENT_WEBSITE, website);
        cVal.put(KEY_DEPARTMENT_UPDATED_ON, updatedOn);
        cVal.put(KEY_DEPARTMENT_STATUS, status);
        cVal.put(KEY_DEPARTMENT_PHOTO_URL, photoUrl);

        long rowId = db.insert(TABLE_DEPARTMENTS, null, cVal);
        // db.close(); // Closing database connection

        return rowId;
    }

    public static long addAppsData(AppModel appModel) {

        final SQLiteDatabase db = open();

        // int id = appModel.getId();
        int no = appModel.getNo();
        int serviceId = appModel.getServiceId();
        String title = sqlEscapeString(appModel.getTitle());
        String description = sqlEscapeString(appModel.getDescription());
        String thumbnailUrl = sqlEscapeString(appModel.getThumbnailUrl());
        int featured = appModel.getFeatured();
        int mostView = appModel.getMostView();
        int mostDownload = appModel.getMostDownload();
        String website = sqlEscapeString(appModel.getWebsite());
        String updatedOn = sqlEscapeString(appModel.getUpdatedOn());
        String status = sqlEscapeString(appModel.getStatus());
        String playStoreLink = sqlEscapeString(appModel.getDownloadApp());
        String photoUrl = sqlEscapeString(appModel.getPhotoUrl());

        ContentValues cVal = new ContentValues();
        cVal.put(KEY_APP_NO, no);
        cVal.put(KEY_SERVICE_ID, serviceId);
        cVal.put(KEY_APP_TITLE, title);
        cVal.put(KEY_APP_DESCRIPTION, description);
        cVal.put(KEY_APP_THUMBNAIL_URL, thumbnailUrl);
        cVal.put(KEY_APP_FEATURED, featured);
        cVal.put(KEY_APP_MOST_VIEW, mostView);
        cVal.put(KEY_APP_MOST_DOWNLOAD, mostDownload);
        cVal.put(KEY_APP_WEBSITE, website);
        cVal.put(KEY_APP_UPDATED_ON, updatedOn);
        cVal.put(KEY_APP_STATUS, status);
        cVal.put(KEY_APP_PLAY_STORE_LINK, playStoreLink);
        cVal.put(KEY_APP_PHOTO_URL, photoUrl);

        long rowId = db.insert(TABLE_APPS, null, cVal);
        // db.close(); // Closing database connection

        return rowId;
    }

    public static long addArticleData(ArticleModel articleModel) {

        final SQLiteDatabase db = open();

        // int id = articleModel.getId();
        int no = articleModel.getNo();
        int serviceId = articleModel.getServiceId();
        String title = sqlEscapeString(articleModel.getTitle());
        String description = sqlEscapeString(articleModel.getDescription());
        String thumbnailUrl = sqlEscapeString(articleModel.getThumbnailUrl());
        int featured = articleModel.getFeatured();
        int mostView = articleModel.getMostView();
        int mostDownload = articleModel.getMostDownload();
        String website = sqlEscapeString(articleModel.getWebsite());
        String updatedOn = sqlEscapeString(articleModel.getUpdatedOn());
        String status = sqlEscapeString(articleModel.getStatus());
        String category = sqlEscapeString(articleModel.getCategory());
        String writer = sqlEscapeString(articleModel.getWriter());
        String photoUrl = sqlEscapeString(articleModel.getPhotoUrl());

        ContentValues cVal = new ContentValues();
        cVal.put(KEY_ARTICLE_NO, no);
        cVal.put(KEY_SERVICE_ID, serviceId);
        cVal.put(KEY_ARTICLE_TITLE, title);
        cVal.put(KEY_ARTICLE_DESCRIPTION, description);
        cVal.put(KEY_ARTICLE_THUMBNAIL_URL, thumbnailUrl);
        cVal.put(KEY_ARTICLE_FEATURED, featured);
        cVal.put(KEY_ARTICLE_MOST_VIEW, mostView);
        cVal.put(KEY_ARTICLE_MOST_DOWNLOAD, mostDownload);
        cVal.put(KEY_ARTICLE_WEBSITE, website);
        cVal.put(KEY_ARTICLE_UPDATED_ON, updatedOn);
        cVal.put(KEY_ARTICLE_STATUS, status);
        cVal.put(KEY_ARTICLE_CATEGORY, category);
        cVal.put(KEY_ARTICLE_WRITER, writer);
        cVal.put(KEY_ARTICLE_PHOTO_URL, photoUrl);

        long rowId = db.insert(TABLE_ARTICLES, null, cVal);
        // db.close(); // Closing database connection

        return rowId;
    }

    public static long addBlogData(BlogModel blogModel) {

        final SQLiteDatabase db = open();

        // int id = blogModel.getId();
        int no = blogModel.getNo();
        int serviceId = blogModel.getServiceId();
        String title = sqlEscapeString(blogModel.getTitle());
        String description = sqlEscapeString(blogModel.getDescription());
        String category = sqlEscapeString(blogModel.getCategory());
        String writer = sqlEscapeString(blogModel.getWriter());
        int featured = blogModel.getFeatured();
        int mostView = blogModel.getMostView();
        int mostDownload = blogModel.getMostDownload();
        String website = sqlEscapeString(blogModel.getWebsite());
        String updatedOn = sqlEscapeString(blogModel.getUpdatedOn());
        String status = sqlEscapeString(blogModel.getStatus());
        String thumbnailUrl = sqlEscapeString(blogModel.getThumbnailUrl());
        String photoUrl = sqlEscapeString(blogModel.getPhotoUrl());

        ContentValues cVal = new ContentValues();
        cVal.put(KEY_BLOG_NO, no);
        cVal.put(KEY_SERVICE_ID, serviceId);
        cVal.put(KEY_BLOG_TITLE, title);
        cVal.put(KEY_BLOG_DESCRIPTION, description);
        cVal.put(KEY_BLOG_THUMBNAIL_URL, thumbnailUrl);
        cVal.put(KEY_BLOG_FEATURED, featured);
        cVal.put(KEY_BLOG_MOST_VIEW, mostView);
        cVal.put(KEY_BLOG_MOST_DOWNLOAD, mostDownload);
        cVal.put(KEY_BLOG_WEBSITE, website);
        cVal.put(KEY_BLOG_UPDATED_ON, updatedOn);
        cVal.put(KEY_BLOG_STATUS, status);
        cVal.put(KEY_BLOG_CATEGORY, category);
        cVal.put(KEY_BLOG_WRITER, writer);
        cVal.put(KEY_BLOG_PHOTO_URL, photoUrl);

        long rowId = db.insert(TABLE_BLOGS, null, cVal);
        // db.close(); // Closing database connection

        return rowId;
    }

    public static long addPoetryData(PoetryModel poetryModel) {

        final SQLiteDatabase db = open();

        // int id = poetryModel.getId();
        int no = poetryModel.getNo();
        int serviceId = poetryModel.getServiceId();
        String title = sqlEscapeString(poetryModel.getTitle());
        String description = sqlEscapeString(poetryModel.getDescription());
        String category = sqlEscapeString(poetryModel.getCategory());
        String writer = sqlEscapeString(poetryModel.getWriter());
        int featured = poetryModel.getFeatured();
        int mostView = poetryModel.getMostView();
        int mostDownload = poetryModel.getMostDownload();
        String website = sqlEscapeString(poetryModel.getWebsite());
        String updatedOn = sqlEscapeString(poetryModel.getUpdatedOn());
        String status = sqlEscapeString(poetryModel.getStatus());
        String thumbnailUrl = sqlEscapeString(poetryModel.getThumbnailUrl());
        String photoUrl = sqlEscapeString(poetryModel.getPhotoUrl());

        ContentValues cVal = new ContentValues();
        cVal.put(KEY_POETRY_NO, no);
        cVal.put(KEY_SERVICE_ID, serviceId);
        cVal.put(KEY_POETRY_TITLE, title);
        cVal.put(KEY_POETRY_DESCRIPTION, description);
        cVal.put(KEY_POETRY_THUMBNAIL_URL, thumbnailUrl);
        cVal.put(KEY_POETRY_FEATURED, featured);
        cVal.put(KEY_POETRY_MOST_VIEW, mostView);
        cVal.put(KEY_POETRY_MOST_DOWNLOAD, mostDownload);
        cVal.put(KEY_POETRY_WEBSITE, website);
        cVal.put(KEY_POETRY_UPDATED_ON, updatedOn);
        cVal.put(KEY_POETRY_STATUS, status);
        cVal.put(KEY_POETRY_CATEGORY, category);
        cVal.put(KEY_POETRY_WRITER, writer);
        cVal.put(KEY_POETRY_PHOTO_URL, photoUrl);

        long rowId = db.insert(TABLE_POETRY, null, cVal);
        // db.close(); // Closing database connection

        return rowId;
    }


    // Getting single contact
    public synchronized static SettingsModel getSettingBean(int id) {

        final SQLiteDatabase db = open();

        Cursor cursor = db.query(TABLE_SETTINGS, new String[]{KEY_SETTING_ID, KEY_SETTING_STAY_AWAKE, KEY_SETTING_VERSION,
                        KEY_SETTING_DATA_PATH, KEY_SETTING_AUTO_UPDATE}, KEY_SETTING_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null, null);

        SettingsModel data = new SettingsModel();

        if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {

            data = new SettingsModel(
                    cursor.getInt(0),
                    sqlUnEscapeString(cursor.getString(1)),
                    sqlUnEscapeString(cursor.getString(2)),
                    sqlUnEscapeString(cursor.getString(3)),
                    sqlUnEscapeString(cursor.getString(4)));
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        // return contact
        return data;
    }

    public synchronized static SettingsModel getSettingsData() {

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_SETTINGS;

        final SQLiteDatabase db = open();
        Cursor cursor = db.rawQuery(selectQuery, null);

        SettingsModel data = new SettingsModel();

        // looping through all rows and adding to list
        if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {

            data = new SettingsModel(
                    cursor.getInt(0),
                    sqlUnEscapeString(cursor.getString(1)),
                    sqlUnEscapeString(cursor.getString(2)),
                    sqlUnEscapeString(cursor.getString(3)),
                    sqlUnEscapeString(cursor.getString(4)));
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        // return contact list
        return data;
    }


    public synchronized static LanguageModel getLanguageModelById(int id) {

        final SQLiteDatabase db = open();

        Cursor cursor = db.query(TABLE_LANGUAGES, new String[]{
                        KEY_LANGUAGE_ID, KEY_LANGUAGE_NUMBER, KEY_LANGUAGE_TITLE, KEY_LANGUAGE_SHORT_NAME},
                KEY_LANGUAGE_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);

        LanguageModel data = new LanguageModel();

        if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {

            data = new LanguageModel(
                    cursor.getInt(0), cursor.getInt(1),
                    sqlUnEscapeString(cursor.getString(2)),
                    sqlUnEscapeString(cursor.getString(3)));
        }

        if (cursor != null && !cursor.isClosed())
            cursor.close();

        // return contact
        return data;
    }

    public synchronized static LanguageModel getLanguageModelByNo(int no) {

        final SQLiteDatabase db = open();

        Cursor cursor = db.query(TABLE_LANGUAGES, new String[]{
                        KEY_LANGUAGE_ID, KEY_LANGUAGE_NUMBER, KEY_LANGUAGE_TITLE, KEY_LANGUAGE_SHORT_NAME},
                KEY_LANGUAGE_NUMBER + "=?", new String[]{String.valueOf(no)}, null, null, null, null);

        LanguageModel data = new LanguageModel();

        if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {

            data = new LanguageModel(
                    cursor.getInt(0), cursor.getInt(1),
                    sqlUnEscapeString(cursor.getString(2)),
                    sqlUnEscapeString(cursor.getString(3)));
        }

        if (cursor != null && !cursor.isClosed())
            cursor.close();

        // return contact
        return data;
    }

    public synchronized static ServiceModel getServiceModelById(int id) {

        final SQLiteDatabase db = open();

        Cursor cursor = db.query(TABLE_SERVICES, new String[]{
                        KEY_SERVICE_ID, KEY_SERVICE_NUMBER, KEY_LANGUAGE_ID, KEY_SERVICE_TITLE,
                        KEY_SERVICE_THUMBNAIL_URL, KEY_SERVICE_STATUS}, KEY_SERVICE_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        ServiceModel data = new ServiceModel();

        if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {

            data = new ServiceModel(
                    cursor.getInt(0),
                    cursor.getInt(1), cursor.getInt(2),
                    sqlUnEscapeString(cursor.getString(3)),
                    sqlUnEscapeString(cursor.getString(4)),
                    sqlUnEscapeString(cursor.getString(5)));
        }

        if (cursor != null && !cursor.isClosed())
            cursor.close();

        // return contact
        return data;
    }

    public synchronized static ServiceModel getServiceModel(int no, int languageId) {

        final SQLiteDatabase db = open();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_SERVICES
                + " WHERE " + KEY_SERVICE_NUMBER + " = " + no
                + " AND " + KEY_LANGUAGE_ID + " = " + languageId;

        Cursor cursor = db.rawQuery(selectQuery, null);

        ServiceModel data = new ServiceModel();

        if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {

            data = new ServiceModel(
                    cursor.getInt(0),
                    cursor.getInt(1), cursor.getInt(2),
                    sqlUnEscapeString(cursor.getString(3)),
                    sqlUnEscapeString(cursor.getString(4)),
                    sqlUnEscapeString(cursor.getString(5)));
        }

        if (cursor != null && !cursor.isClosed())
            cursor.close();


        // return contact
        return data;
    }

    public static MasterModel getMasterById(int id) {

        final SQLiteDatabase db = open();

        Cursor cursor = db.query(TABLE_MASTER, new String[]{
                        KEY_MASTER_ID, KEY_MASTER_NUMBER, KEY_SERVICE_ID, KEY_MASTER_PARENT, KEY_MASTER_TITLE,
                        KEY_MASTER_THUMBNAIL_URL, KEY_MASTER_STATUS}, KEY_MASTER_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        MasterModel data = new MasterModel();

        if (cursor != null && cursor.moveToFirst()) {

            data = new MasterModel(
                    cursor.getInt(0),
                    cursor.getInt(1), cursor.getInt(2),
                    sqlUnEscapeString(cursor.getString(3)),
                    sqlUnEscapeString(cursor.getString(4)),
                    sqlUnEscapeString(cursor.getString(5)),
                    sqlUnEscapeString(cursor.getString(6)));
        }

        if (cursor != null && !cursor.isClosed())
            cursor.close();

        // return contact
        return data;
    }

    public synchronized static MasterModel getMasterModel(int masterNo, int serviceId) {

        final SQLiteDatabase db = open();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_MASTER
                + " WHERE " + KEY_MASTER_NUMBER + " = " + masterNo
                + " AND " + KEY_SERVICE_ID + " = " + serviceId;

        Cursor cursor = db.rawQuery(selectQuery, null);

        MasterModel data = new MasterModel();

        if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {

            data = new MasterModel(
                    cursor.getInt(0),
                    cursor.getInt(1), cursor.getInt(2),
                    sqlUnEscapeString(cursor.getString(3)),
                    sqlUnEscapeString(cursor.getString(4)),
                    sqlUnEscapeString(cursor.getString(5)),
                    sqlUnEscapeString(cursor.getString(6)));
        }

        if (cursor != null && !cursor.isClosed())
            cursor.close();

        // return contact
        return data;
    }

    public static SubMasterModel getSubMasterById(int id) {

        final SQLiteDatabase db = open();

        Cursor cursor = db.query(TABLE_SUB_MASTER, new String[]{
                        KEY_SUB_MASTER_ID, KEY_SUB_MASTER_NUMBER, KEY_MASTER_ID, KEY_SERVICE_ID, KEY_SUB_MASTER_PARENT,
                        KEY_SUB_MASTER_TITLE}, KEY_SUB_MASTER_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null, null);

        SubMasterModel data = new SubMasterModel();

        if (cursor != null && cursor.moveToFirst()) {

            data = new SubMasterModel(
                    cursor.getInt(0), cursor.getInt(1),
                    cursor.getInt(2), cursor.getInt(3),
                    sqlUnEscapeString(cursor.getString(4)),
                    sqlUnEscapeString(cursor.getString(5)));
        }

        if (cursor != null && !cursor.isClosed())
            cursor.close();

        // return contact
        return data;
    }

    public synchronized static SubMasterModel getSubMasterModel(int subMasterNo, int serviceId) {

        final SQLiteDatabase db = open();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_SUB_MASTER
                + " WHERE " + KEY_SUB_MASTER_NUMBER + " = " + subMasterNo
                + " AND " + KEY_SERVICE_ID + " = " + serviceId;

        Cursor cursor = db.rawQuery(selectQuery, null);

        SubMasterModel data = new SubMasterModel();

        if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {

            data = new SubMasterModel(
                    cursor.getInt(0), cursor.getInt(1),
                    cursor.getInt(2), cursor.getInt(3),
                    sqlUnEscapeString(cursor.getString(4)),
                    sqlUnEscapeString(cursor.getString(5)));
        }

        if (cursor != null && !cursor.isClosed())
            cursor.close();

        // return contact
        return data;
    }

    public static ProductModel getDataTablesModelFromId(int id) {

        final SQLiteDatabase db = open();

        Cursor cursor = db.query(TABLE_DATA_TABLES, new String[]{KEY_DATA_TABLES_ID, KEY_DATA_TABLES_NO,
                        KEY_DATA_TABLES_TITLE, KEY_DATA_TABLES_SHORT_NAME},
                KEY_DATA_TABLES_ID + "=?", new String[]{String.valueOf(id)}, null, null,
                null, null);

        ProductModel data = new ProductModel();

        if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {

            data = new ProductModel(
                    cursor.getInt(0), cursor.getInt(1),
                    sqlUnEscapeString(cursor.getString(2)),
                    sqlUnEscapeString(cursor.getString(3)));
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        // return contact
        return data;
    }

    public synchronized static ProductModel getDataTablesModelFromNo(int no) {

        final SQLiteDatabase db = open();

        Cursor cursor = db.query(TABLE_DATA_TABLES, new String[]{KEY_DATA_TABLES_ID, KEY_DATA_TABLES_NO,
                        KEY_DATA_TABLES_TITLE, KEY_DATA_TABLES_SHORT_NAME},
                KEY_DATA_TABLES_NO + "=?", new String[]{String.valueOf(no)}, null, null,
                null, null);

        ProductModel data = new ProductModel();

        if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {

            data = new ProductModel(
                    cursor.getInt(0), cursor.getInt(1),
                    sqlUnEscapeString(cursor.getString(2)),
                    sqlUnEscapeString(cursor.getString(3)));
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        // return contact
        return data;
    }

    public synchronized static ProductModel getDataTablesModelFromServiceId(int serviceNo) {

        final SQLiteDatabase db = open();

        Cursor cursor = db.query(TABLE_DATA_TABLES, new String[]{KEY_DATA_TABLES_ID, KEY_DATA_TABLES_NO,
                        KEY_DATA_TABLES_TITLE, KEY_DATA_TABLES_SHORT_NAME},
                KEY_DATA_TABLES_NO + "=?", new String[]{String.valueOf(serviceNo)}, null, null,
                null, null);

        ProductModel data = new ProductModel();

        if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {

            data = new ProductModel(
                    cursor.getInt(0), cursor.getInt(1),
                    sqlUnEscapeString(cursor.getString(2)),
                    sqlUnEscapeString(cursor.getString(3)));
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        // return contact
        return data;
    }

    public synchronized static BookModel getBookModelFromId(int id) {

        final SQLiteDatabase db = open();

        Cursor cursor = db.query(TABLE_BOOKS, new String[]{KEY_BOOK_ID, KEY_BOOK_NO, KEY_SERVICE_ID, KEY_BOOK_TITLE,
                        KEY_BOOK_DESCRIPTION, KEY_BOOK_THUMBNAIL_URL, KEY_BOOK_FEATURED, KEY_BOOK_MOST_VIEW,
                        KEY_BOOK_MOST_DOWNLOAD, KEY_BOOK_WEBSITE, KEY_BOOK_UPDATED_ON, KEY_BOOK_STATUS,
                        KEY_BOOK_CATEGORY, KEY_BOOK_AUTHOR, KEY_BOOK_LANGUAGE, KEY_BOOK_PUBLISHER, KEY_BOOK_MONTH,
                        KEY_BOOK_PDF_URL, KEY_BOOK_PHOTO_URL}, KEY_BOOK_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        BookModel data = new BookModel();

        if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {

            data = new BookModel(
                    cursor.getInt(0),
                    cursor.getInt(1), cursor.getInt(2),
                    sqlUnEscapeString(cursor.getString(3)),
                    sqlUnEscapeString(cursor.getString(4)),
                    sqlUnEscapeString(cursor.getString(5)),
                    cursor.getInt(6),
                    cursor.getInt(7), cursor.getInt(8),
                    sqlUnEscapeString(cursor.getString(9)),
                    sqlUnEscapeString(cursor.getString(10)),
                    sqlUnEscapeString(cursor.getString(11)),
                    sqlUnEscapeString(cursor.getString(12)),
                    sqlUnEscapeString(cursor.getString(13)),
                    sqlUnEscapeString(cursor.getString(14)),
                    sqlUnEscapeString(cursor.getString(15)),
                    sqlUnEscapeString(cursor.getString(16)),
                    sqlUnEscapeString(cursor.getString(17)),
                    sqlUnEscapeString(cursor.getString(18)));
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        // return contact
        return data;
    }

    public synchronized static BookModel getBookModel(int no, int serviceId) {

        final SQLiteDatabase db = open();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_BOOKS
                + " WHERE " + KEY_BOOK_NO + " = " + no
                + " AND " + KEY_SERVICE_ID + " = " + serviceId;

        Cursor cursor = db.rawQuery(selectQuery, null);

        BookModel data = new BookModel();

        if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {

            data = new BookModel(
                    cursor.getInt(0),
                    cursor.getInt(1), cursor.getInt(2),
                    sqlUnEscapeString(cursor.getString(3)),
                    sqlUnEscapeString(cursor.getString(4)),
                    sqlUnEscapeString(cursor.getString(5)),
                    cursor.getInt(6),
                    cursor.getInt(7), cursor.getInt(8),
                    sqlUnEscapeString(cursor.getString(9)),
                    sqlUnEscapeString(cursor.getString(10)),
                    sqlUnEscapeString(cursor.getString(11)),
                    sqlUnEscapeString(cursor.getString(12)),
                    sqlUnEscapeString(cursor.getString(13)),
                    sqlUnEscapeString(cursor.getString(14)),
                    sqlUnEscapeString(cursor.getString(15)),
                    sqlUnEscapeString(cursor.getString(16)),
                    sqlUnEscapeString(cursor.getString(17)),
                    sqlUnEscapeString(cursor.getString(18)));
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        // return contact
        return data;
    }

    public synchronized static MediaModel getMediaModelFromId(int id) {

        final SQLiteDatabase db = open();

        Cursor cursor = db.query(TABLE_MEDIA, new String[]{KEY_MEDIA_ID, KEY_MEDIA_NO, KEY_SERVICE_ID, KEY_MEDIA_TITLE,
                        KEY_MEDIA_DESCRIPTION, KEY_MEDIA_THUMBNAIL_URL, KEY_MEDIA_FEATURED, KEY_MEDIA_MOST_VIEW,
                        KEY_MEDIA_MOST_DOWNLOAD, KEY_MEDIA_WEBSITE, KEY_MEDIA_UPDATED_ON, KEY_MEDIA_STATUS,
                        KEY_MEDIA_CATEGORY, KEY_MEDIA_VOCALIST, KEY_MEDIA_ATTRIBUTE,
                        KEY_MEDIA_LANGUAGE, KEY_MEDIA_TYPE, KEY_MEDIA_PHOTO_URL}, KEY_MEDIA_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        MediaModel data = new MediaModel();

        if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {

            data = new MediaModel(
                    cursor.getInt(0),
                    cursor.getInt(1), cursor.getInt(2),
                    sqlUnEscapeString(cursor.getString(3)),
                    sqlUnEscapeString(cursor.getString(4)),
                    sqlUnEscapeString(cursor.getString(5)),
                    cursor.getInt(6),
                    cursor.getInt(7), cursor.getInt(8),
                    sqlUnEscapeString(cursor.getString(9)),
                    sqlUnEscapeString(cursor.getString(10)),
                    sqlUnEscapeString(cursor.getString(11)),
                    sqlUnEscapeString(cursor.getString(12)),
                    sqlUnEscapeString(cursor.getString(13)),
                    sqlUnEscapeString(cursor.getString(14)),
                    sqlUnEscapeString(cursor.getString(15)),
                    sqlUnEscapeString(cursor.getString(16)),
                    sqlUnEscapeString(cursor.getString(17)));
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        // return contact
        return data;
    }

    public synchronized static MediaModel getMediaModel(int no, int serviceId) {

        final SQLiteDatabase db = open();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_MEDIA
                + " WHERE " + KEY_MEDIA_NO + " = " + no
                + " AND " + KEY_SERVICE_ID + " = " + serviceId;

        Cursor cursor = db.rawQuery(selectQuery, null);

        MediaModel data = new MediaModel();

        if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {

            data = new MediaModel(
                    cursor.getInt(0),
                    cursor.getInt(1), cursor.getInt(2),
                    sqlUnEscapeString(cursor.getString(3)),
                    sqlUnEscapeString(cursor.getString(4)),
                    sqlUnEscapeString(cursor.getString(5)),
                    cursor.getInt(6),
                    cursor.getInt(7), cursor.getInt(8),
                    sqlUnEscapeString(cursor.getString(9)),
                    sqlUnEscapeString(cursor.getString(10)),
                    sqlUnEscapeString(cursor.getString(11)),
                    sqlUnEscapeString(cursor.getString(12)),
                    sqlUnEscapeString(cursor.getString(13)),
                    sqlUnEscapeString(cursor.getString(14)),
                    sqlUnEscapeString(cursor.getString(15)),
                    sqlUnEscapeString(cursor.getString(16)),
                    sqlUnEscapeString(cursor.getString(17)));
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        // return contact
        return data;
    }

    public synchronized static AudioVideoModel getAudioVideoModel(int mediaId, String type) {

        final SQLiteDatabase db = open();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_AUDIO_VIDEO
                + " WHERE " + KEY_MEDIA_ID + " = " + mediaId
                + " AND " + KEY_AUDIO_VIDEO_TYPE + " = '" + type + "'";

        Cursor cursor = db.rawQuery(selectQuery, null);

        AudioVideoModel data = new AudioVideoModel();

        if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {

            data = new AudioVideoModel(
                    cursor.getInt(0), cursor.getInt(1),
                    sqlUnEscapeString(cursor.getString(2)),
                    sqlUnEscapeString(cursor.getString(3)),
                    sqlUnEscapeString(cursor.getString(4)));
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        // return contact
        return data;
    }

    public synchronized static ScholarModel getScholarModelFromId(int id) {

        final SQLiteDatabase db = open();

        Cursor cursor = db.query(TABLE_SCHOLAR, new String[]{KEY_SCHOLAR_ID, KEY_SCHOLAR_NO, KEY_SERVICE_ID, KEY_SCHOLAR_TITLE,
                        KEY_SCHOLAR_DESCRIPTION, KEY_SCHOLAR_THUMBNAIL_URL, KEY_SCHOLAR_FEATURED, KEY_SCHOLAR_MOST_VIEW,
                        KEY_SCHOLAR_MOST_DOWNLOAD, KEY_SCHOLAR_WEBSITE, KEY_SCHOLAR_UPDATED_ON, KEY_SCHOLAR_STATUS,
                        KEY_SCHOLAR_BIRTH_DATE, KEY_SCHOLAR_URS_DATE, KEY_SCHOLAR_PHOTO_URL}, KEY_SCHOLAR_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        ScholarModel data = new ScholarModel();

        if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {

            data = new ScholarModel(
                    cursor.getInt(0),
                    cursor.getInt(1), cursor.getInt(2),
                    sqlUnEscapeString(cursor.getString(3)),
                    sqlUnEscapeString(cursor.getString(4)),
                    sqlUnEscapeString(cursor.getString(5)),
                    cursor.getInt(6),
                    cursor.getInt(7), cursor.getInt(8),
                    sqlUnEscapeString(cursor.getString(9)),
                    sqlUnEscapeString(cursor.getString(10)),
                    sqlUnEscapeString(cursor.getString(11)),
                    sqlUnEscapeString(cursor.getString(12)),
                    sqlUnEscapeString(cursor.getString(13)),
                    sqlUnEscapeString(cursor.getString(14)));
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        // return contact
        return data;
    }

    public synchronized static ScholarModel getScholarModel(int no, int serviceId) {

        final SQLiteDatabase db = open();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_SCHOLAR
                + " WHERE " + KEY_SCHOLAR_NO + " = " + no
                + " AND " + KEY_SERVICE_ID + " = " + serviceId;

        Cursor cursor = db.rawQuery(selectQuery, null);

        ScholarModel data = new ScholarModel();

        if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {

            data = new ScholarModel(
                    cursor.getInt(0),
                    cursor.getInt(1), cursor.getInt(2),
                    sqlUnEscapeString(cursor.getString(3)),
                    sqlUnEscapeString(cursor.getString(4)),
                    sqlUnEscapeString(cursor.getString(5)),
                    cursor.getInt(6),
                    cursor.getInt(7), cursor.getInt(8),
                    sqlUnEscapeString(cursor.getString(9)),
                    sqlUnEscapeString(cursor.getString(10)),
                    sqlUnEscapeString(cursor.getString(11)),
                    sqlUnEscapeString(cursor.getString(12)),
                    sqlUnEscapeString(cursor.getString(13)),
                    sqlUnEscapeString(cursor.getString(14)));
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        // return contact
        return data;
    }

    public synchronized static GalleryModel getGalleryModelFromId(int id) {

        final SQLiteDatabase db = open();

        Cursor cursor = db.query(TABLE_GALLERY, new String[]{KEY_GALLERY_ID, KEY_GALLERY_NO, KEY_SERVICE_ID, KEY_GALLERY_TITLE,
                        KEY_GALLERY_DESCRIPTION, KEY_GALLERY_THUMBNAIL_URL, KEY_GALLERY_FEATURED, KEY_GALLERY_MOST_VIEW,
                        KEY_GALLERY_MOST_DOWNLOAD, KEY_GALLERY_WEBSITE, KEY_GALLERY_UPDATED_ON, KEY_GALLERY_STATUS,
                        KEY_GALLERY_CATEGORY, KEY_GALLERY_DOWNLOAD_IMAGE, KEY_GALLERY_PHOTO_URL}, KEY_GALLERY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        GalleryModel data = new GalleryModel();

        if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {

            data = new GalleryModel(
                    cursor.getInt(0),
                    cursor.getInt(1), cursor.getInt(2),
                    sqlUnEscapeString(cursor.getString(3)),
                    sqlUnEscapeString(cursor.getString(4)),
                    sqlUnEscapeString(cursor.getString(5)),
                    cursor.getInt(6),
                    cursor.getInt(7), cursor.getInt(8),
                    sqlUnEscapeString(cursor.getString(9)),
                    sqlUnEscapeString(cursor.getString(10)),
                    sqlUnEscapeString(cursor.getString(11)),
                    sqlUnEscapeString(cursor.getString(12)),
                    sqlUnEscapeString(cursor.getString(13)),
                    sqlUnEscapeString(cursor.getString(14)));
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        // return contact
        return data;
    }

    public synchronized static GalleryModel getGalleryModel(int no, int serviceId) {

        final SQLiteDatabase db = open();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_GALLERY
                + " WHERE " + KEY_GALLERY_NO + " = " + no
                + " AND " + KEY_SERVICE_ID + " = " + serviceId;

        Cursor cursor = db.rawQuery(selectQuery, null);

        GalleryModel data = new GalleryModel();

        if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {

            data = new GalleryModel(
                    cursor.getInt(0),
                    cursor.getInt(1), cursor.getInt(2),
                    sqlUnEscapeString(cursor.getString(3)),
                    sqlUnEscapeString(cursor.getString(4)),
                    sqlUnEscapeString(cursor.getString(5)),
                    cursor.getInt(6),
                    cursor.getInt(7), cursor.getInt(8),
                    sqlUnEscapeString(cursor.getString(9)),
                    sqlUnEscapeString(cursor.getString(10)),
                    sqlUnEscapeString(cursor.getString(11)),
                    sqlUnEscapeString(cursor.getString(12)),
                    sqlUnEscapeString(cursor.getString(13)),
                    sqlUnEscapeString(cursor.getString(14)));
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        // return contact
        return data;
    }

    public synchronized static NewsModel getNewsModelFromId(int id) {

        final SQLiteDatabase db = open();

        Cursor cursor = db.query(TABLE_NEWS, new String[]{KEY_NEWS_ID, KEY_NEWS_NO, KEY_SERVICE_ID, KEY_NEWS_TITLE,
                        KEY_NEWS_DESCRIPTION, KEY_NEWS_THUMBNAIL_URL, KEY_NEWS_FEATURED, KEY_NEWS_MOST_VIEW,
                        KEY_NEWS_MOST_DOWNLOAD, KEY_NEWS_WEBSITE, KEY_NEWS_UPDATED_ON, KEY_NEWS_STATUS,
                        KEY_NEWS_ORGANIZATION, KEY_NEWS_COUNTRY, KEY_NEWS_NEWS_PAPER, KEY_NEWS_PHOTO_URL}, KEY_NEWS_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        NewsModel data = new NewsModel();

        if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {

            data = new NewsModel(
                    cursor.getInt(0),
                    cursor.getInt(1), cursor.getInt(2),
                    sqlUnEscapeString(cursor.getString(3)),
                    sqlUnEscapeString(cursor.getString(4)),
                    sqlUnEscapeString(cursor.getString(5)),
                    cursor.getInt(6),
                    cursor.getInt(7), cursor.getInt(8),
                    sqlUnEscapeString(cursor.getString(9)),
                    sqlUnEscapeString(cursor.getString(10)),
                    sqlUnEscapeString(cursor.getString(11)),
                    sqlUnEscapeString(cursor.getString(12)),
                    sqlUnEscapeString(cursor.getString(13)),
                    sqlUnEscapeString(cursor.getString(14)),
                    sqlUnEscapeString(cursor.getString(15)));
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        // return contact
        return data;
    }

    public synchronized static NewsModel getNewsModel(int no, int serviceId) {

        final SQLiteDatabase db = open();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_NEWS
                + " WHERE " + KEY_NEWS_NO + " = " + no
                + " AND " + KEY_SERVICE_ID + " = " + serviceId;

        Cursor cursor = db.rawQuery(selectQuery, null);

        NewsModel data = new NewsModel();

        if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {

            data = new NewsModel(
                    cursor.getInt(0),
                    cursor.getInt(1), cursor.getInt(2),
                    sqlUnEscapeString(cursor.getString(3)),
                    sqlUnEscapeString(cursor.getString(4)),
                    sqlUnEscapeString(cursor.getString(5)),
                    cursor.getInt(6),
                    cursor.getInt(7), cursor.getInt(8),
                    sqlUnEscapeString(cursor.getString(9)),
                    sqlUnEscapeString(cursor.getString(10)),
                    sqlUnEscapeString(cursor.getString(11)),
                    sqlUnEscapeString(cursor.getString(12)),
                    sqlUnEscapeString(cursor.getString(13)),
                    sqlUnEscapeString(cursor.getString(14)),
                    sqlUnEscapeString(cursor.getString(15)));
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        // return contact
        return data;
    }

    public synchronized static PlaceModel getPlaceModelFromId(int id) {

        final SQLiteDatabase db = open();

        Cursor cursor = db.query(TABLE_PLACES, new String[]{KEY_PLACE_ID, KEY_PLACE_NO, KEY_SERVICE_ID, KEY_PLACE_TITLE,
                        KEY_PLACE_DESCRIPTION, KEY_PLACE_THUMBNAIL_URL, KEY_PLACE_FEATURED, KEY_PLACE_MOST_VIEW,
                        KEY_PLACE_MOST_DOWNLOAD, KEY_PLACE_WEBSITE, KEY_PLACE_UPDATED_ON, KEY_PLACE_STATUS,
                        KEY_PLACE_CATEGORY, KEY_PLACE_PHOTO_URL}, KEY_PLACE_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        PlaceModel data = new PlaceModel();

        if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {

            data = new PlaceModel(
                    cursor.getInt(0),
                    cursor.getInt(1), cursor.getInt(2),
                    sqlUnEscapeString(cursor.getString(3)),
                    sqlUnEscapeString(cursor.getString(4)),
                    sqlUnEscapeString(cursor.getString(5)),
                    cursor.getInt(6),
                    cursor.getInt(7), cursor.getInt(8),
                    sqlUnEscapeString(cursor.getString(9)),
                    sqlUnEscapeString(cursor.getString(10)),
                    sqlUnEscapeString(cursor.getString(11)),
                    sqlUnEscapeString(cursor.getString(12)),
                    sqlUnEscapeString(cursor.getString(13)));
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        // return contact
        return data;
    }

    public synchronized static PlaceModel getPlaceModel(int no, int serviceId) {

        final SQLiteDatabase db = open();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_PLACES
                + " WHERE " + KEY_PLACE_NO + " = " + no
                + " AND " + KEY_SERVICE_ID + " = " + serviceId;

        Cursor cursor = db.rawQuery(selectQuery, null);

        PlaceModel data = new PlaceModel();

        if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {

            data = new PlaceModel(
                    cursor.getInt(0),
                    cursor.getInt(1), cursor.getInt(2),
                    sqlUnEscapeString(cursor.getString(3)),
                    sqlUnEscapeString(cursor.getString(4)),
                    sqlUnEscapeString(cursor.getString(5)),
                    cursor.getInt(6),
                    cursor.getInt(7), cursor.getInt(8),
                    sqlUnEscapeString(cursor.getString(9)),
                    sqlUnEscapeString(cursor.getString(10)),
                    sqlUnEscapeString(cursor.getString(11)),
                    sqlUnEscapeString(cursor.getString(12)),
                    sqlUnEscapeString(cursor.getString(13)));
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        // return contact
        return data;
    }

    public synchronized static HistoryModel getHistoryModelFromId(int id) {

        final SQLiteDatabase db = open();

        Cursor cursor = db.query(TABLE_HISTORY, new String[]{KEY_HISTORY_ID, KEY_HISTORY_NO, KEY_SERVICE_ID, KEY_HISTORY_TITLE,
                        KEY_HISTORY_DESCRIPTION, KEY_HISTORY_THUMBNAIL_URL, KEY_HISTORY_FEATURED, KEY_HISTORY_MOST_VIEW,
                        KEY_HISTORY_MOST_DOWNLOAD, KEY_HISTORY_WEBSITE, KEY_HISTORY_UPDATED_ON, KEY_HISTORY_STATUS,
                        KEY_HISTORY_CATEGORY, KEY_HISTORY_PHOTO_URL}, KEY_HISTORY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        HistoryModel data = new HistoryModel();

        if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {

            data = new HistoryModel(
                    cursor.getInt(0),
                    cursor.getInt(1), cursor.getInt(2),
                    sqlUnEscapeString(cursor.getString(3)),
                    sqlUnEscapeString(cursor.getString(4)),
                    sqlUnEscapeString(cursor.getString(5)),
                    cursor.getInt(6),
                    cursor.getInt(7), cursor.getInt(8),
                    sqlUnEscapeString(cursor.getString(9)),
                    sqlUnEscapeString(cursor.getString(10)),
                    sqlUnEscapeString(cursor.getString(11)),
                    sqlUnEscapeString(cursor.getString(12)),
                    sqlUnEscapeString(cursor.getString(13)));
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        // return contact
        return data;
    }

    public synchronized static HistoryModel getHistoryModel(int no, int serviceId) {

        final SQLiteDatabase db = open();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_HISTORY
                + " WHERE " + KEY_HISTORY_NO + " = " + no
                + " AND " + KEY_SERVICE_ID + " = " + serviceId;

        Cursor cursor = db.rawQuery(selectQuery, null);

        HistoryModel data = new HistoryModel();

        if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {

            data = new HistoryModel(
                    cursor.getInt(0),
                    cursor.getInt(1), cursor.getInt(2),
                    sqlUnEscapeString(cursor.getString(3)),
                    sqlUnEscapeString(cursor.getString(4)),
                    sqlUnEscapeString(cursor.getString(5)),
                    cursor.getInt(6),
                    cursor.getInt(7), cursor.getInt(8),
                    sqlUnEscapeString(cursor.getString(9)),
                    sqlUnEscapeString(cursor.getString(10)),
                    sqlUnEscapeString(cursor.getString(11)),
                    sqlUnEscapeString(cursor.getString(12)),
                    sqlUnEscapeString(cursor.getString(13)));
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        // return contact
        return data;
    }

    public synchronized static DayModel getDayModelFromId(int id) {

        final SQLiteDatabase db = open();

        Cursor cursor = db.query(TABLE_DAYS, new String[]{KEY_DAY_ID, KEY_DAY_NO, KEY_SERVICE_ID, KEY_DAY_TITLE,
                        KEY_DAY_DESCRIPTION, KEY_DAY_THUMBNAIL_URL, KEY_DAY_FEATURED, KEY_DAY_MOST_VIEW,
                        KEY_DAY_MOST_DOWNLOAD, KEY_DAY_WEBSITE, KEY_DAY_UPDATED_ON, KEY_DAY_STATUS,
                        KEY_DAY_PHOTO_URL}, KEY_DAY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        DayModel data = new DayModel();

        if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {

            data = new DayModel(
                    cursor.getInt(0),
                    cursor.getInt(1), cursor.getInt(2),
                    sqlUnEscapeString(cursor.getString(3)),
                    sqlUnEscapeString(cursor.getString(4)),
                    sqlUnEscapeString(cursor.getString(5)),
                    cursor.getInt(6),
                    cursor.getInt(7), cursor.getInt(8),
                    sqlUnEscapeString(cursor.getString(9)),
                    sqlUnEscapeString(cursor.getString(10)),
                    sqlUnEscapeString(cursor.getString(11)),
                    sqlUnEscapeString(cursor.getString(12)));
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        // return contact
        return data;
    }

    public synchronized static DayModel getDaysModel(int no, int serviceId) {

        final SQLiteDatabase db = open();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_DAYS
                + " WHERE " + KEY_DAY_NO + " = " + no
                + " AND " + KEY_SERVICE_ID + " = " + serviceId;

        Cursor cursor = db.rawQuery(selectQuery, null);

        DayModel data = new DayModel();

        if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {

            data = new DayModel(
                    cursor.getInt(0),
                    cursor.getInt(1), cursor.getInt(2),
                    sqlUnEscapeString(cursor.getString(3)),
                    sqlUnEscapeString(cursor.getString(4)),
                    sqlUnEscapeString(cursor.getString(5)),
                    cursor.getInt(6),
                    cursor.getInt(7), cursor.getInt(8),
                    sqlUnEscapeString(cursor.getString(9)),
                    sqlUnEscapeString(cursor.getString(10)),
                    sqlUnEscapeString(cursor.getString(11)),
                    sqlUnEscapeString(cursor.getString(12)));
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        // return contact
        return data;
    }

    public synchronized static DepartmentModel getDepartmentModelFromId(int id) {

        final SQLiteDatabase db = open();

        Cursor cursor = db.query(TABLE_DEPARTMENTS, new String[]{KEY_DEPARTMENT_ID, KEY_DEPARTMENT_NO, KEY_SERVICE_ID, KEY_DEPARTMENT_TITLE,
                        KEY_DEPARTMENT_DESCRIPTION, KEY_DEPARTMENT_THUMBNAIL_URL, KEY_DEPARTMENT_FEATURED, KEY_DEPARTMENT_MOST_VIEW,
                        KEY_DEPARTMENT_MOST_DOWNLOAD, KEY_DEPARTMENT_WEBSITE, KEY_DEPARTMENT_UPDATED_ON, KEY_DEPARTMENT_STATUS,
                        KEY_DEPARTMENT_PHOTO_URL}, KEY_DEPARTMENT_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        DepartmentModel data = new DepartmentModel();

        if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {

            data = new DepartmentModel(
                    cursor.getInt(0),
                    cursor.getInt(1), cursor.getInt(2),
                    sqlUnEscapeString(cursor.getString(3)),
                    sqlUnEscapeString(cursor.getString(4)),
                    sqlUnEscapeString(cursor.getString(5)),
                    cursor.getInt(6),
                    cursor.getInt(7), cursor.getInt(8),
                    sqlUnEscapeString(cursor.getString(9)),
                    sqlUnEscapeString(cursor.getString(10)),
                    sqlUnEscapeString(cursor.getString(11)),
                    sqlUnEscapeString(cursor.getString(12)));
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        // return contact
        return data;
    }

    public synchronized static DepartmentModel getDepartmentsModel(int no, int serviceId) {

        final SQLiteDatabase db = open();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_DEPARTMENTS
                + " WHERE " + KEY_DEPARTMENT_NO + " = " + no
                + " AND " + KEY_SERVICE_ID + " = " + serviceId;

        Cursor cursor = db.rawQuery(selectQuery, null);

        DepartmentModel data = new DepartmentModel();

        if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {

            data = new DepartmentModel(
                    cursor.getInt(0),
                    cursor.getInt(1), cursor.getInt(2),
                    sqlUnEscapeString(cursor.getString(3)),
                    sqlUnEscapeString(cursor.getString(4)),
                    sqlUnEscapeString(cursor.getString(5)),
                    cursor.getInt(6),
                    cursor.getInt(7), cursor.getInt(8),
                    sqlUnEscapeString(cursor.getString(9)),
                    sqlUnEscapeString(cursor.getString(10)),
                    sqlUnEscapeString(cursor.getString(11)),
                    sqlUnEscapeString(cursor.getString(12)));
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        // return contact
        return data;
    }

    public synchronized static AppModel getAppsModelFromId(int id) {

        final SQLiteDatabase db = open();

        Cursor cursor = db.query(TABLE_APPS, new String[]{KEY_APP_ID, KEY_APP_NO, KEY_SERVICE_ID, KEY_APP_TITLE,
                        KEY_APP_DESCRIPTION, KEY_APP_THUMBNAIL_URL, KEY_APP_FEATURED, KEY_APP_MOST_VIEW,
                        KEY_APP_MOST_DOWNLOAD, KEY_APP_WEBSITE, KEY_APP_UPDATED_ON, KEY_APP_STATUS,
                        KEY_APP_PLAY_STORE_LINK, KEY_APP_PHOTO_URL}, KEY_APP_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        AppModel data = new AppModel();

        if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {

            data = new AppModel(
                    cursor.getInt(0),
                    cursor.getInt(1), cursor.getInt(2),
                    sqlUnEscapeString(cursor.getString(3)),
                    sqlUnEscapeString(cursor.getString(4)),
                    sqlUnEscapeString(cursor.getString(5)),
                    cursor.getInt(6),
                    cursor.getInt(7), cursor.getInt(8),
                    sqlUnEscapeString(cursor.getString(9)),
                    sqlUnEscapeString(cursor.getString(10)),
                    sqlUnEscapeString(cursor.getString(11)),
                    sqlUnEscapeString(cursor.getString(12)),
                    sqlUnEscapeString(cursor.getString(13)));
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        // return contact
        return data;
    }

    public synchronized static AppModel getAppsModel(int no, int serviceId) {

        final SQLiteDatabase db = open();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_APPS
                + " WHERE " + KEY_APP_NO + " = " + no
                + " AND " + KEY_SERVICE_ID + " = " + serviceId;

        Cursor cursor = db.rawQuery(selectQuery, null);

        AppModel data = new AppModel();

        if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {

            data = new AppModel(
                    cursor.getInt(0),
                    cursor.getInt(1), cursor.getInt(2),
                    sqlUnEscapeString(cursor.getString(3)),
                    sqlUnEscapeString(cursor.getString(4)),
                    sqlUnEscapeString(cursor.getString(5)),
                    cursor.getInt(6),
                    cursor.getInt(7), cursor.getInt(8),
                    sqlUnEscapeString(cursor.getString(9)),
                    sqlUnEscapeString(cursor.getString(10)),
                    sqlUnEscapeString(cursor.getString(11)),
                    sqlUnEscapeString(cursor.getString(12)),
                    sqlUnEscapeString(cursor.getString(13)));
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        // return contact
        return data;
    }

    public synchronized static ArticleModel getArticleModelFromId(int id) {

        final SQLiteDatabase db = open();

        Cursor cursor = db.query(TABLE_ARTICLES, new String[]{KEY_ARTICLE_ID, KEY_ARTICLE_NO, KEY_SERVICE_ID, KEY_ARTICLE_TITLE,
                        KEY_ARTICLE_DESCRIPTION, KEY_ARTICLE_THUMBNAIL_URL, KEY_ARTICLE_FEATURED, KEY_ARTICLE_MOST_VIEW,
                        KEY_ARTICLE_MOST_DOWNLOAD, KEY_ARTICLE_WEBSITE, KEY_ARTICLE_UPDATED_ON, KEY_ARTICLE_STATUS,
                        KEY_ARTICLE_CATEGORY, KEY_ARTICLE_WRITER, KEY_ARTICLE_PHOTO_URL}, KEY_ARTICLE_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        ArticleModel data = new ArticleModel();

        if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {

            data = new ArticleModel(
                    cursor.getInt(0),
                    cursor.getInt(1), cursor.getInt(2),
                    sqlUnEscapeString(cursor.getString(3)),
                    sqlUnEscapeString(cursor.getString(4)),
                    sqlUnEscapeString(cursor.getString(5)),
                    cursor.getInt(6),
                    cursor.getInt(7), cursor.getInt(8),
                    sqlUnEscapeString(cursor.getString(9)),
                    sqlUnEscapeString(cursor.getString(10)),
                    sqlUnEscapeString(cursor.getString(11)),
                    sqlUnEscapeString(cursor.getString(12)),
                    sqlUnEscapeString(cursor.getString(13)),
                    sqlUnEscapeString(cursor.getString(14)));
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        // return contact
        return data;
    }

    public synchronized static ArticleModel getArticleModel(int no, int serviceId) {

        final SQLiteDatabase db = open();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_ARTICLES
                + " WHERE " + KEY_ARTICLE_NO + " = " + no
                + " AND " + KEY_SERVICE_ID + " = " + serviceId;

        Cursor cursor = db.rawQuery(selectQuery, null);

        ArticleModel data = new ArticleModel();

        if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {

            data = new ArticleModel(
                    cursor.getInt(0),
                    cursor.getInt(1), cursor.getInt(2),
                    sqlUnEscapeString(cursor.getString(3)),
                    sqlUnEscapeString(cursor.getString(4)),
                    sqlUnEscapeString(cursor.getString(5)),
                    cursor.getInt(6),
                    cursor.getInt(7), cursor.getInt(8),
                    sqlUnEscapeString(cursor.getString(9)),
                    sqlUnEscapeString(cursor.getString(10)),
                    sqlUnEscapeString(cursor.getString(11)),
                    sqlUnEscapeString(cursor.getString(12)),
                    sqlUnEscapeString(cursor.getString(13)),
                    sqlUnEscapeString(cursor.getString(14)));
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        // return contact
        return data;
    }

    public synchronized static BlogModel getBlogModelFromId(int id) {

        final SQLiteDatabase db = open();

        Cursor cursor = db.query(TABLE_BLOGS, new String[]{KEY_BLOG_ID, KEY_BLOG_NO, KEY_SERVICE_ID, KEY_BLOG_TITLE,
                        KEY_BLOG_DESCRIPTION, KEY_BLOG_THUMBNAIL_URL, KEY_BLOG_FEATURED, KEY_BLOG_MOST_VIEW,
                        KEY_BLOG_MOST_DOWNLOAD, KEY_BLOG_WEBSITE, KEY_BLOG_UPDATED_ON, KEY_BLOG_STATUS,
                        KEY_BLOG_CATEGORY, KEY_BLOG_WRITER, KEY_BLOG_PHOTO_URL}, KEY_BLOG_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        BlogModel data = new BlogModel();

        if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {

            data = new BlogModel(
                    cursor.getInt(0),
                    cursor.getInt(1), cursor.getInt(2),
                    sqlUnEscapeString(cursor.getString(3)),
                    sqlUnEscapeString(cursor.getString(4)),
                    sqlUnEscapeString(cursor.getString(5)),
                    cursor.getInt(6),
                    cursor.getInt(7), cursor.getInt(8),
                    sqlUnEscapeString(cursor.getString(9)),
                    sqlUnEscapeString(cursor.getString(10)),
                    sqlUnEscapeString(cursor.getString(11)),
                    sqlUnEscapeString(cursor.getString(12)),
                    sqlUnEscapeString(cursor.getString(13)),
                    sqlUnEscapeString(cursor.getString(14)));
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        // return contact
        return data;
    }

    public synchronized static BlogModel getBlogModel(int no, int serviceId) {

        final SQLiteDatabase db = open();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_BLOGS
                + " WHERE " + KEY_BLOG_NO + " = " + no
                + " AND " + KEY_SERVICE_ID + " = " + serviceId;

        Cursor cursor = db.rawQuery(selectQuery, null);

        BlogModel data = new BlogModel();

        if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {

            data = new BlogModel(
                    cursor.getInt(0),
                    cursor.getInt(1), cursor.getInt(2),
                    sqlUnEscapeString(cursor.getString(3)),
                    sqlUnEscapeString(cursor.getString(4)),
                    sqlUnEscapeString(cursor.getString(5)),
                    cursor.getInt(6),
                    cursor.getInt(7), cursor.getInt(8),
                    sqlUnEscapeString(cursor.getString(9)),
                    sqlUnEscapeString(cursor.getString(10)),
                    sqlUnEscapeString(cursor.getString(11)),
                    sqlUnEscapeString(cursor.getString(12)),
                    sqlUnEscapeString(cursor.getString(13)),
                    sqlUnEscapeString(cursor.getString(14)));
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        // return contact
        return data;
    }

    public synchronized static PoetryModel getPoetryModelFromId(int id) {

        final SQLiteDatabase db = open();

        Cursor cursor = db.query(TABLE_POETRY, new String[]{KEY_POETRY_ID, KEY_POETRY_NO, KEY_SERVICE_ID, KEY_POETRY_TITLE,
                        KEY_POETRY_DESCRIPTION, KEY_POETRY_THUMBNAIL_URL, KEY_POETRY_FEATURED, KEY_POETRY_MOST_VIEW,
                        KEY_POETRY_MOST_DOWNLOAD, KEY_POETRY_WEBSITE, KEY_POETRY_UPDATED_ON, KEY_POETRY_STATUS,
                        KEY_POETRY_CATEGORY, KEY_POETRY_WRITER, KEY_POETRY_PHOTO_URL}, KEY_POETRY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        PoetryModel data = new PoetryModel();

        if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {

            data = new PoetryModel(
                    cursor.getInt(0),
                    cursor.getInt(1), cursor.getInt(2),
                    sqlUnEscapeString(cursor.getString(3)),
                    sqlUnEscapeString(cursor.getString(4)),
                    sqlUnEscapeString(cursor.getString(5)),
                    cursor.getInt(6),
                    cursor.getInt(7), cursor.getInt(8),
                    sqlUnEscapeString(cursor.getString(9)),
                    sqlUnEscapeString(cursor.getString(10)),
                    sqlUnEscapeString(cursor.getString(11)),
                    sqlUnEscapeString(cursor.getString(12)),
                    sqlUnEscapeString(cursor.getString(13)),
                    sqlUnEscapeString(cursor.getString(14)));
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        // return contact
        return data;
    }

    public synchronized static PoetryModel getPoetryModel(int no, int serviceId) {

        final SQLiteDatabase db = open();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_POETRY
                + " WHERE " + KEY_POETRY_NO + " = " + no
                + " AND " + KEY_SERVICE_ID + " = " + serviceId;

        Cursor cursor = db.rawQuery(selectQuery, null);

        PoetryModel data = new PoetryModel();

        if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {

            data = new PoetryModel(
                    cursor.getInt(0),
                    cursor.getInt(1), cursor.getInt(2),
                    sqlUnEscapeString(cursor.getString(3)),
                    sqlUnEscapeString(cursor.getString(4)),
                    sqlUnEscapeString(cursor.getString(5)),
                    cursor.getInt(6),
                    cursor.getInt(7), cursor.getInt(8),
                    sqlUnEscapeString(cursor.getString(9)),
                    sqlUnEscapeString(cursor.getString(10)),
                    sqlUnEscapeString(cursor.getString(11)),
                    sqlUnEscapeString(cursor.getString(12)),
                    sqlUnEscapeString(cursor.getString(13)),
                    sqlUnEscapeString(cursor.getString(14)));
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        // return contact
        return data;
    }

    /********************** Getting All Contacts *************************/

    public synchronized static ArrayList<LanguageModel> getAllLanguages() {

        ArrayList<LanguageModel> list = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_LANGUAGES
                + " ORDER BY " + KEY_LANGUAGE_TITLE + " ASC";

        final SQLiteDatabase db = open();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {

            do {

                LanguageModel data = new LanguageModel(
                        cursor.getInt(0), cursor.getInt(1),
                        sqlUnEscapeString(cursor.getString(2)),
                        sqlUnEscapeString(cursor.getString(3)));

                // Adding contact to list
                list.add(data);

            } while (cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed())
            cursor.close();

        // return contact list
        return list;
    }

    public synchronized static ArrayList<ServiceModel> getAllServices(int languageId) {

        ArrayList<ServiceModel> list = new ArrayList<ServiceModel>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_SERVICES
                + " WHERE " + KEY_SERVICE_STATUS + " = '" + Constants.STATUS_VISIBLE + "'"
                + " AND " + KEY_LANGUAGE_ID + " = " + languageId
                + " ORDER BY " + KEY_SERVICE_TITLE + " ASC";

        final SQLiteDatabase db = open();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {

            do {

                ServiceModel data = new ServiceModel(
                        cursor.getInt(0),
                        cursor.getInt(1), cursor.getInt(2),
                        sqlUnEscapeString(cursor.getString(3)),
                        sqlUnEscapeString(cursor.getString(4)),
                        sqlUnEscapeString(cursor.getString(5)));

                // Adding contact to list
                list.add(data);

            } while (cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed())
            cursor.close();

        // return contact list
        return list;
    }

    public synchronized static ArrayList<MasterModel> getAllMasters() {

        ArrayList<MasterModel> list = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_MASTER
                + " ORDER BY " + KEY_MASTER_ID + " ASC";

        final SQLiteDatabase db = open();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {

            do {

                MasterModel data = new MasterModel(
                        cursor.getInt(0),
                        cursor.getInt(1), cursor.getInt(2),
                        sqlUnEscapeString(cursor.getString(3)),
                        sqlUnEscapeString(cursor.getString(4)),
                        sqlUnEscapeString(cursor.getString(5)),
                        sqlUnEscapeString(cursor.getString(6)));

                // Adding contact to list
                list.add(data);

            } while (cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed())
            cursor.close();

        // return contact list
        return list;
    }

    public synchronized static ArrayList<String> getAllServicesName() {

        ArrayList<String> list = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT " + KEY_SERVICE_TITLE + " FROM " + TABLE_SERVICES
                + " ORDER BY " + KEY_SERVICE_TITLE + " ASC";

        final SQLiteDatabase db = open();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {

            do {

                // Adding contact to list
                list.add(sqlUnEscapeString(cursor.getString(0)));

            } while (cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed())
            cursor.close();

        // return contact list
        return list;
    }

    public synchronized static ArrayList<String> getAllMasterParents(int serviceId) {

        ArrayList<String> list = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT DISTINCT " + KEY_MASTER_PARENT + " FROM " + TABLE_MASTER
                + " WHERE " + KEY_MASTER_STATUS + " = '" + Constants.STATUS_VISIBLE + "'"
                + " AND " + KEY_SERVICE_ID + " = " + serviceId
                + " ORDER BY " + KEY_MASTER_PARENT + " ASC";

        final SQLiteDatabase db = open();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {

            do {

                String data = sqlUnEscapeString(cursor.getString(0));

                // Adding contact to list
                list.add(data);

            } while (cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed())
            cursor.close();

        // return contact list
        return list;
    }

    public static ArrayList<String> getAllMastersTitle(int serviceId, String parent) {

        ArrayList<String> list = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT DISTINCT " + TABLE_MASTER + "." + KEY_MASTER_TITLE
                + " FROM " + TABLE_MASTER + ", " + TABLE_SUB_MASTER
                + " WHERE " + TABLE_MASTER + "." + KEY_MASTER_STATUS + " = '" + Constants.STATUS_VISIBLE + "'"
                + " AND " + TABLE_MASTER + "." + KEY_MASTER_PARENT + " = '" + parent + "'"
                + " AND " + TABLE_MASTER + "." + KEY_SERVICE_ID + " = " + serviceId
                + " AND " + TABLE_MASTER + "." + KEY_MASTER_ID + " = " + TABLE_SUB_MASTER + "." + KEY_MASTER_ID
                + " ORDER BY " + TABLE_MASTER + "." + KEY_MASTER_TITLE + " ASC";

        final SQLiteDatabase db = open();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {

            do {

                String data = sqlUnEscapeString(cursor.getString(0));

                // Adding contact to list
                list.add(data);

            } while (cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed())
            cursor.close();

        // return contact list
        return list;
    }

    public static ArrayList<String> getAllSubMasterTitles(int serviceId, String parent) {

        ArrayList<String> list = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT DISTINCT " + KEY_SUB_MASTER_TITLE + " FROM " + TABLE_SUB_MASTER
                + " WHERE " + KEY_SUB_MASTER_PARENT + " = '" + parent + "'"
                + " AND " + KEY_SERVICE_ID + " = " + serviceId
                + " ORDER BY " + KEY_SUB_MASTER_TITLE + " ASC";

        final SQLiteDatabase db = open();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {

            do {

                String data = sqlUnEscapeString(cursor.getString(0));

                // Adding contact to list
                list.add(data);

            } while (cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed())
            cursor.close();

        // return contact list
        return list;
    }

    public static ArrayList<SubMasterModel> getAllSubMasters() {

        ArrayList<SubMasterModel> list = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_SUB_MASTER
                + " ORDER BY " + KEY_SUB_MASTER_ID + " ASC";

        final SQLiteDatabase db = open();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {

            do {

                SubMasterModel data = new SubMasterModel(
                        cursor.getInt(0), cursor.getInt(1),
                        cursor.getInt(2), cursor.getInt(3),
                        sqlUnEscapeString(cursor.getString(4)),
                        sqlUnEscapeString(cursor.getString(5)));

                // Adding contact to list
                list.add(data);

            } while (cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed())
            cursor.close();

        // return contact list
        return list;
    }

    public static ArrayList<SubMasterModel> getAllSubMastersData(int masterId) {

        ArrayList<SubMasterModel> list = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_SUB_MASTER
                + " WHERE " + KEY_MASTER_ID + " = " + masterId
                + " ORDER BY " + KEY_SUB_MASTER_TITLE + " ASC";

        final SQLiteDatabase db = open();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {

            do {

                SubMasterModel data = new SubMasterModel(
                        cursor.getInt(0), cursor.getInt(1),
                        cursor.getInt(2), cursor.getInt(3),
                        sqlUnEscapeString(cursor.getString(4)),
                        sqlUnEscapeString(cursor.getString(5)));

                // Adding contact to list
                list.add(data);

            } while (cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed())
            cursor.close();

        // return contact list
        return list;
    }

    public static ArrayList<ProductModel> getAllDataTables() {

        ArrayList<ProductModel> list = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_DATA_TABLES
                + " ORDER BY " + KEY_DATA_TABLES_TITLE + " ASC";

        final SQLiteDatabase db = open();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor != null && cursor.moveToFirst()) {

            do {

                ProductModel data = new ProductModel(
                        cursor.getInt(0), cursor.getInt(1),
                        sqlUnEscapeString(cursor.getString(2)),
                        sqlUnEscapeString(cursor.getString(3)));

                // Adding contact to list
                list.add(data);

            } while (cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed())
            cursor.close();

        // return contact list
        return list;
    }

    public synchronized static ArrayList<ProductItemModel> getAllProducts(String table, String shortName, String column,
                                                                          int serviceId, String title) {

        // most-view
        ArrayList<ProductItemModel> list = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT " + shortName + "_id" + ", " + shortName + "_no" + ", "
                + shortName + "_title" + ", " + shortName + "_description" + ", "
                + shortName + "_thumbnail_url" + ", " + shortName + "_website"
                + " FROM " + table
                + " WHERE " + KEY_SERVICE_ID + " = " + serviceId;

        if (TextUtils.equals(title, Constants.TEXT_ALL)) {

            selectQuery += " ORDER BY " + shortName + "_title" + " ASC";

        } else if (TextUtils.equals(title, Constants.TEXT_FEATURED)) {

            selectQuery += " AND " + shortName + "_featured" + " > " + 0;
            selectQuery += " ORDER BY " + shortName + "_featured" + " DESC";

        } else if (TextUtils.equals(title, Constants.TEXT_MOST_VIEW)) {

            selectQuery += " AND " + shortName + "_most_view" + " > " + 0;
            selectQuery += " ORDER BY " + shortName + "_most_view" + " DESC";

        } else if (TextUtils.equals(title, Constants.TEXT_MOST_DOWNLOAD)) {

            selectQuery += " AND " + shortName + "_most_download" + " > " + 0;
            selectQuery += " ORDER BY " + shortName + "_most_download" + " DESC";

        } else {

            selectQuery += " AND " + column + " = '" + title + "'";
            selectQuery += " ORDER BY " + shortName + "_title" + " ASC";
        }

        final SQLiteDatabase db = open();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {

            do {

                String extension = "";
                String downloadUrl = "";

                ProductItemModel data = new ProductItemModel(
                        cursor.getInt(0), cursor.getInt(1),
                        sqlUnEscapeString(cursor.getString(2)),
                        extension,
                        sqlUnEscapeString(cursor.getString(3)),
                        sqlUnEscapeString(cursor.getString(4)),
                        sqlUnEscapeString(cursor.getString(5)),
                        downloadUrl);

                // Adding contact to list
                list.add(data);

            } while (cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed())
            cursor.close();

        // return contact list
        return list;
    }

    public synchronized static ArrayList<String> getAllProductTitle(int serviceId, String table, String column) {

        ArrayList<String> list = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT DISTINCT " + column + " FROM " + table
                + " WHERE " + KEY_SERVICE_ID + " = " + serviceId
                + " AND " + column + " != '-'"
                + " ORDER BY " + column + " ASC";

        final SQLiteDatabase db = open();

        Cursor cursor = null;

        try {

            cursor = db.rawQuery(selectQuery, null);

        } catch (SQLiteException ex) {
            if (DEBUG)
                Log.e(LOG_TAG, "Exception: " + ex.toString());
            list.clear();
        }

        // looping through all rows and adding to list
        if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {

            do {

                String data = sqlUnEscapeString(cursor.getString(0));

                // Adding contact to list
                list.add(data);

            } while (cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed())
            cursor.close();

        // return contact list
        return list;
    }

    public synchronized static ArrayList<AudioVideoModel> getAllAudioVideoModel(int mediaId, String type) {

        ArrayList<AudioVideoModel> list = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_AUDIO_VIDEO
                + " WHERE " + KEY_MEDIA_ID + " = " + mediaId
                + " AND " + KEY_AUDIO_VIDEO_TYPE + " = '" + type + "'";

        final SQLiteDatabase db = open();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {

            do {

                AudioVideoModel data = new AudioVideoModel(
                        cursor.getInt(0), cursor.getInt(1),
                        sqlUnEscapeString(cursor.getString(2)),
                        sqlUnEscapeString(cursor.getString(3)),
                        sqlUnEscapeString(cursor.getString(4)));

                // Adding contact to list
                list.add(data);

            } while (cursor.moveToNext());
        }

        if (cursor != null && !cursor.isClosed())
            cursor.close();

        // return contact list
        return list;
    }

    // Updating single contact
    public static int updateSettingsData(SettingsModel settingsModel) {

        final SQLiteDatabase db = open();

        int id = settingsModel.getId();
        String awake = sqlEscapeString(settingsModel.getStayAwake());
        String version = sqlEscapeString(settingsModel.getVersion());
        String dataPath = sqlEscapeString(settingsModel.getDataPath());
        String update = sqlEscapeString(settingsModel.getAutoUpdate());

        ContentValues cVal = new ContentValues();

        cVal.put(KEY_SETTING_STAY_AWAKE, awake);
        cVal.put(KEY_SETTING_VERSION, version);
        cVal.put(KEY_SETTING_DATA_PATH, dataPath);
        cVal.put(KEY_SETTING_AUTO_UPDATE, update);

        // updating row
        return db.update(TABLE_SETTINGS, cVal, KEY_SETTING_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public static int updateLanguage(LanguageModel languageModel) {

        final SQLiteDatabase db = open();

        int id = languageModel.getId();
        int no = languageModel.getNo();
        String title = sqlEscapeString(languageModel.getTitle());
        String shortName = sqlEscapeString(languageModel.getShortName());

        ContentValues cVal = new ContentValues();

        cVal.put(KEY_LANGUAGE_NUMBER, no);
        cVal.put(KEY_LANGUAGE_TITLE, title);
        cVal.put(KEY_LANGUAGE_SHORT_NAME, shortName);

        // updating row
        return db.update(TABLE_LANGUAGES, cVal, KEY_LANGUAGE_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public static int updateService(ServiceModel serviceModel) {

        final SQLiteDatabase db = open();

        int id = serviceModel.getId();
        int number = serviceModel.getNumber();
        int languageId = serviceModel.getLanguageId();
        String title = sqlEscapeString(serviceModel.getTitle());
        String thumbnailUrl = sqlEscapeString(serviceModel.getThumbnailUrl());
        String status = sqlEscapeString(serviceModel.getStatus());

        ContentValues cVal = new ContentValues();

        cVal.put(KEY_SERVICE_NUMBER, number);
        cVal.put(KEY_LANGUAGE_ID, languageId);
        cVal.put(KEY_SERVICE_TITLE, title);
        cVal.put(KEY_SERVICE_THUMBNAIL_URL, thumbnailUrl);
        cVal.put(KEY_SERVICE_STATUS, status);

        // updating row
        return db.update(TABLE_SERVICES, cVal, KEY_SERVICE_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public static int updateMaster(MasterModel masterModel) {

        final SQLiteDatabase db = open();

        int id = masterModel.getId();
        int number = masterModel.getNumber();
        int serviceId = masterModel.getServiceId();
        String parent = sqlEscapeString(masterModel.getParent());
        String title = sqlEscapeString(masterModel.getTitle());
        String thumbnailUrl = sqlEscapeString(masterModel.getThumbnailUrl());
        String status = sqlEscapeString(masterModel.getStatus());

        ContentValues cVal = new ContentValues();

        cVal.put(KEY_MASTER_NUMBER, number);
        cVal.put(KEY_SERVICE_ID, serviceId);
        cVal.put(KEY_MASTER_PARENT, parent);
        cVal.put(KEY_MASTER_TITLE, title);
        cVal.put(KEY_MASTER_THUMBNAIL_URL, thumbnailUrl);
        cVal.put(KEY_MASTER_STATUS, status);

        // updating row
        return db.update(TABLE_MASTER, cVal, KEY_MASTER_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public static int updateSubMaster(SubMasterModel subMasterModel) {

        final SQLiteDatabase db = open();

        int id = subMasterModel.getId();
        int number = subMasterModel.getNumber();
        int masterId = subMasterModel.getMasterId();
        String title = sqlEscapeString(subMasterModel.getTitle());

        ContentValues cVal = new ContentValues();

        cVal.put(KEY_SUB_MASTER_NUMBER, number);
        cVal.put(KEY_MASTER_ID, masterId);
        cVal.put(KEY_SUB_MASTER_TITLE, title);

        // updating row
        return db.update(TABLE_SUB_MASTER, cVal, KEY_SUB_MASTER_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public static int updateDataTables(ProductModel productModel) {

        final SQLiteDatabase db = open();

        int id = productModel.getId();
        int no = productModel.getServiceNo();
        String title = sqlEscapeString(productModel.getTitle());
        String shortName = sqlEscapeString(productModel.getShortName());

        ContentValues cVal = new ContentValues();

        cVal.put(KEY_DATA_TABLES_NO, no);
        cVal.put(KEY_DATA_TABLES_TITLE, title);
        cVal.put(KEY_DATA_TABLES_SHORT_NAME, shortName);

        // updating row
        return db.update(TABLE_DATA_TABLES, cVal, KEY_DATA_TABLES_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public static int updateBookData(BookModel bookModel) {

        final SQLiteDatabase db = open();

        int id = bookModel.getId();
        int no = bookModel.getNo();
        int serviceId = bookModel.getServiceId();
        String title = sqlEscapeString(bookModel.getTitle());
        String description = sqlEscapeString(bookModel.getDescription());
        String thumbnailUrl = sqlEscapeString(bookModel.getThumbnailUrl());
        int featured = bookModel.getFeatured();
        int mostView = bookModel.getMostView();
        int mostDownload = bookModel.getMostDownload();
        String website = sqlEscapeString(bookModel.getWebsite());
        String updatedOn = sqlEscapeString(bookModel.getUpdatedOn());
        String status = sqlEscapeString(bookModel.getStatus());
        String category = sqlEscapeString(bookModel.getCategory());
        String author = sqlEscapeString(bookModel.getAuthor());
        String language = sqlEscapeString(bookModel.getLanguage());
        String publisher = sqlEscapeString(bookModel.getPublisher());
        String month = sqlEscapeString(bookModel.getMonth());
        String pdfUrl = sqlEscapeString(bookModel.getPdfUrl());
        String photoUrl = sqlEscapeString(bookModel.getPhotoUrl());

        ContentValues cVal = new ContentValues();
        cVal.put(KEY_BOOK_NO, no);
        cVal.put(KEY_SERVICE_ID, serviceId);
        cVal.put(KEY_BOOK_TITLE, title);
        cVal.put(KEY_BOOK_DESCRIPTION, description);
        cVal.put(KEY_BOOK_THUMBNAIL_URL, thumbnailUrl);
        cVal.put(KEY_BOOK_FEATURED, featured);
        cVal.put(KEY_BOOK_MOST_VIEW, mostView);
        cVal.put(KEY_BOOK_MOST_DOWNLOAD, mostDownload);
        cVal.put(KEY_BOOK_WEBSITE, website);
        cVal.put(KEY_BOOK_UPDATED_ON, updatedOn);
        cVal.put(KEY_BOOK_STATUS, status);
        cVal.put(KEY_BOOK_CATEGORY, category);
        cVal.put(KEY_BOOK_AUTHOR, author);
        cVal.put(KEY_BOOK_LANGUAGE, language);
        cVal.put(KEY_BOOK_PUBLISHER, publisher);
        cVal.put(KEY_BOOK_MONTH, month);
        cVal.put(KEY_BOOK_PDF_URL, pdfUrl);
        cVal.put(KEY_BOOK_PHOTO_URL, photoUrl);

        // updating row
        return db.update(TABLE_BOOKS, cVal, KEY_BOOK_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public static int updateMediaData(MediaModel mediaModel) {

        final SQLiteDatabase db = open();

        int id = mediaModel.getId();
        int no = mediaModel.getNo();
        int serviceId = mediaModel.getServiceId();
        String title = sqlEscapeString(mediaModel.getTitle());
        String description = sqlEscapeString(mediaModel.getDescription());
        String thumbnailUrl = sqlEscapeString(mediaModel.getThumbnailUrl());
        int featured = mediaModel.getFeatured();
        int mostView = mediaModel.getMostView();
        int mostDownload = mediaModel.getMostDownload();
        String website = sqlEscapeString(mediaModel.getWebsite());
        String updatedOn = sqlEscapeString(mediaModel.getUpdatedOn());
        String status = sqlEscapeString(mediaModel.getStatus());
        String category = sqlEscapeString(mediaModel.getCategory());
        String vocalist = sqlEscapeString(mediaModel.getVocalist());
        String attribute = sqlEscapeString(mediaModel.getAttribute());
        String language = sqlEscapeString(mediaModel.getLanguage());
        String type = sqlEscapeString(mediaModel.getType());
        String photoUrl = sqlEscapeString(mediaModel.getPhotoUrl());

        ContentValues cVal = new ContentValues();
        cVal.put(KEY_MEDIA_NO, no);
        cVal.put(KEY_SERVICE_ID, serviceId);
        cVal.put(KEY_MEDIA_TITLE, title);
        cVal.put(KEY_MEDIA_DESCRIPTION, description);
        cVal.put(KEY_MEDIA_THUMBNAIL_URL, thumbnailUrl);
        cVal.put(KEY_MEDIA_FEATURED, featured);
        cVal.put(KEY_MEDIA_MOST_VIEW, mostView);
        cVal.put(KEY_MEDIA_MOST_DOWNLOAD, mostDownload);
        cVal.put(KEY_MEDIA_WEBSITE, website);
        cVal.put(KEY_MEDIA_UPDATED_ON, updatedOn);
        cVal.put(KEY_MEDIA_STATUS, status);
        cVal.put(KEY_MEDIA_CATEGORY, category);
        cVal.put(KEY_MEDIA_VOCALIST, vocalist);
        cVal.put(KEY_MEDIA_ATTRIBUTE, attribute);
        cVal.put(KEY_MEDIA_LANGUAGE, language);
        cVal.put(KEY_MEDIA_TYPE, type);
        cVal.put(KEY_MEDIA_PHOTO_URL, photoUrl);

        // updating row
        return db.update(TABLE_MEDIA, cVal, KEY_MEDIA_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public static long updateAudioVideoData(AudioVideoModel audioVideoModel) {

        final SQLiteDatabase db = open();

        int id = audioVideoModel.getId();
        int mediaId = audioVideoModel.getMediaId();
        String title = sqlEscapeString(audioVideoModel.getTitle());
        String url = sqlEscapeString(audioVideoModel.getUrl());
        String type = sqlEscapeString(audioVideoModel.getType());

        ContentValues cVal = new ContentValues();
        cVal.put(KEY_MEDIA_ID, mediaId);
        cVal.put(KEY_AUDIO_VIDEO_TITLE, title);
        cVal.put(KEY_AUDIO_VIDEO_URL, url);
        cVal.put(KEY_AUDIO_VIDEO_TYPE, type);

        return db.update(TABLE_AUDIO_VIDEO, cVal, KEY_AUDIO_VIDEO_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public static int updateScholarData(ScholarModel scholarModel) {

        final SQLiteDatabase db = open();

        int id = scholarModel.getId();
        int no = scholarModel.getNo();
        int serviceId = scholarModel.getServiceId();
        String title = sqlEscapeString(scholarModel.getTitle());
        String description = sqlEscapeString(scholarModel.getDescription());
        String thumbnailUrl = sqlEscapeString(scholarModel.getThumbnailUrl());
        int featured = scholarModel.getFeatured();
        int mostView = scholarModel.getMostView();
        int mostDownload = scholarModel.getMostDownload();
        String website = sqlEscapeString(scholarModel.getWebsite());
        String updatedOn = sqlEscapeString(scholarModel.getUpdatedOn());
        String status = sqlEscapeString(scholarModel.getStatus());
        String birthDate = sqlEscapeString(scholarModel.getBirthDate());
        String ursDate = sqlEscapeString(scholarModel.getUrsDate());
        String photoUrl = sqlEscapeString(scholarModel.getPhotoUrl());

        ContentValues cVal = new ContentValues();
        cVal.put(KEY_SCHOLAR_NO, no);
        cVal.put(KEY_SERVICE_ID, serviceId);
        cVal.put(KEY_SCHOLAR_TITLE, title);
        cVal.put(KEY_SCHOLAR_DESCRIPTION, description);
        cVal.put(KEY_SCHOLAR_THUMBNAIL_URL, thumbnailUrl);
        cVal.put(KEY_SCHOLAR_FEATURED, featured);
        cVal.put(KEY_SCHOLAR_MOST_VIEW, mostView);
        cVal.put(KEY_SCHOLAR_MOST_DOWNLOAD, mostDownload);
        cVal.put(KEY_SCHOLAR_WEBSITE, website);
        cVal.put(KEY_SCHOLAR_UPDATED_ON, updatedOn);
        cVal.put(KEY_SCHOLAR_STATUS, status);
        cVal.put(KEY_SCHOLAR_BIRTH_DATE, birthDate);
        cVal.put(KEY_SCHOLAR_URS_DATE, ursDate);
        cVal.put(KEY_SCHOLAR_PHOTO_URL, photoUrl);

        // updating row
        return db.update(TABLE_SCHOLAR, cVal, KEY_SCHOLAR_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public static int updateGalleryData(GalleryModel galleryModel) {

        final SQLiteDatabase db = open();

        int id = galleryModel.getId();
        int no = galleryModel.getNo();
        int serviceId = galleryModel.getServiceId();
        String title = sqlEscapeString(galleryModel.getTitle());
        String description = sqlEscapeString(galleryModel.getDescription());
        String thumbnailUrl = sqlEscapeString(galleryModel.getThumbnailUrl());
        int featured = galleryModel.getFeatured();
        int mostView = galleryModel.getMostView();
        int mostDownload = galleryModel.getMostDownload();
        String website = sqlEscapeString(galleryModel.getWebsite());
        String updatedOn = sqlEscapeString(galleryModel.getUpdatedOn());
        String status = sqlEscapeString(galleryModel.getStatus());
        String category = sqlEscapeString(galleryModel.getCategory());
        String downloadImage = sqlEscapeString(galleryModel.getDownloadImage());
        String photoUrl = sqlEscapeString(galleryModel.getPhotoUrl());

        ContentValues cVal = new ContentValues();
        cVal.put(KEY_GALLERY_NO, no);
        cVal.put(KEY_SERVICE_ID, serviceId);
        cVal.put(KEY_GALLERY_TITLE, title);
        cVal.put(KEY_GALLERY_DESCRIPTION, description);
        cVal.put(KEY_GALLERY_THUMBNAIL_URL, thumbnailUrl);
        cVal.put(KEY_GALLERY_FEATURED, featured);
        cVal.put(KEY_GALLERY_MOST_VIEW, mostView);
        cVal.put(KEY_GALLERY_MOST_DOWNLOAD, mostDownload);
        cVal.put(KEY_GALLERY_WEBSITE, website);
        cVal.put(KEY_GALLERY_UPDATED_ON, updatedOn);
        cVal.put(KEY_GALLERY_STATUS, status);
        cVal.put(KEY_GALLERY_CATEGORY, category);
        cVal.put(KEY_GALLERY_DOWNLOAD_IMAGE, downloadImage);
        cVal.put(KEY_GALLERY_PHOTO_URL, photoUrl);

        // updating row
        return db.update(TABLE_GALLERY, cVal, KEY_GALLERY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public static int updateNewsData(NewsModel newsModel) {

        final SQLiteDatabase db = open();

        int id = newsModel.getId();
        int no = newsModel.getNo();
        int serviceId = newsModel.getServiceId();
        String title = sqlEscapeString(newsModel.getTitle());
        String description = sqlEscapeString(newsModel.getDescription());
        String thumbnailUrl = sqlEscapeString(newsModel.getThumbnailUrl());
        int featured = newsModel.getFeatured();
        int mostView = newsModel.getMostView();
        int mostDownload = newsModel.getMostDownload();
        String website = sqlEscapeString(newsModel.getWebsite());
        String updatedOn = sqlEscapeString(newsModel.getUpdatedOn());
        String status = sqlEscapeString(newsModel.getStatus());
        String organization = sqlEscapeString(newsModel.getOrganization());
        String country = sqlEscapeString(newsModel.getCountry());
        String newspaper = sqlEscapeString(newsModel.getNewsPaper());
        String photoUrl = sqlEscapeString(newsModel.getPhotoUrl());

        ContentValues cVal = new ContentValues();
        cVal.put(KEY_NEWS_NO, no);
        cVal.put(KEY_SERVICE_ID, serviceId);
        cVal.put(KEY_NEWS_TITLE, title);
        cVal.put(KEY_NEWS_DESCRIPTION, description);
        cVal.put(KEY_NEWS_THUMBNAIL_URL, thumbnailUrl);
        cVal.put(KEY_NEWS_FEATURED, featured);
        cVal.put(KEY_NEWS_MOST_VIEW, mostView);
        cVal.put(KEY_NEWS_MOST_DOWNLOAD, mostDownload);
        cVal.put(KEY_NEWS_WEBSITE, website);
        cVal.put(KEY_NEWS_UPDATED_ON, updatedOn);
        cVal.put(KEY_NEWS_STATUS, status);
        cVal.put(KEY_NEWS_ORGANIZATION, organization);
        cVal.put(KEY_NEWS_COUNTRY, country);
        cVal.put(KEY_NEWS_NEWS_PAPER, newspaper);
        cVal.put(KEY_NEWS_PHOTO_URL, photoUrl);

        // updating row
        return db.update(TABLE_NEWS, cVal, KEY_NEWS_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public static int updatePlaceData(PlaceModel placeModel) {

        final SQLiteDatabase db = open();

        int id = placeModel.getId();
        int no = placeModel.getNo();
        int serviceId = placeModel.getServiceId();
        String title = sqlEscapeString(placeModel.getTitle());
        String description = sqlEscapeString(placeModel.getDescription());
        String thumbnailUrl = sqlEscapeString(placeModel.getThumbnailUrl());
        int featured = placeModel.getFeatured();
        int mostView = placeModel.getMostView();
        int mostDownload = placeModel.getMostDownload();
        String website = sqlEscapeString(placeModel.getWebsite());
        String updatedOn = sqlEscapeString(placeModel.getUpdatedOn());
        String status = sqlEscapeString(placeModel.getStatus());
        String category = sqlEscapeString(placeModel.getCategory());
        String photoUrl = sqlEscapeString(placeModel.getPhotoUrl());

        ContentValues cVal = new ContentValues();
        cVal.put(KEY_PLACE_NO, no);
        cVal.put(KEY_SERVICE_ID, serviceId);
        cVal.put(KEY_PLACE_TITLE, title);
        cVal.put(KEY_PLACE_DESCRIPTION, description);
        cVal.put(KEY_PLACE_THUMBNAIL_URL, thumbnailUrl);
        cVal.put(KEY_PLACE_FEATURED, featured);
        cVal.put(KEY_PLACE_MOST_VIEW, mostView);
        cVal.put(KEY_PLACE_MOST_DOWNLOAD, mostDownload);
        cVal.put(KEY_PLACE_WEBSITE, website);
        cVal.put(KEY_PLACE_UPDATED_ON, updatedOn);
        cVal.put(KEY_PLACE_STATUS, status);
        cVal.put(KEY_PLACE_CATEGORY, category);
        cVal.put(KEY_PLACE_PHOTO_URL, photoUrl);

        // updating row
        return db.update(TABLE_PLACES, cVal, KEY_PLACE_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public static int updateHistoryData(HistoryModel historyModel) {

        final SQLiteDatabase db = open();

        int id = historyModel.getId();
        int no = historyModel.getNo();
        int serviceId = historyModel.getServiceId();
        String title = sqlEscapeString(historyModel.getTitle());
        String description = sqlEscapeString(historyModel.getDescription());
        String thumbnailUrl = sqlEscapeString(historyModel.getThumbnailUrl());
        int featured = historyModel.getFeatured();
        int mostView = historyModel.getMostView();
        int mostDownload = historyModel.getMostDownload();
        String website = sqlEscapeString(historyModel.getWebsite());
        String updatedOn = sqlEscapeString(historyModel.getUpdatedOn());
        String status = sqlEscapeString(historyModel.getStatus());
        String category = sqlEscapeString(historyModel.getCategory());
        String photoUrl = sqlEscapeString(historyModel.getPhotoUrl());

        ContentValues cVal = new ContentValues();
        cVal.put(KEY_HISTORY_NO, no);
        cVal.put(KEY_SERVICE_ID, serviceId);
        cVal.put(KEY_HISTORY_TITLE, title);
        cVal.put(KEY_HISTORY_DESCRIPTION, description);
        cVal.put(KEY_HISTORY_THUMBNAIL_URL, thumbnailUrl);
        cVal.put(KEY_HISTORY_FEATURED, featured);
        cVal.put(KEY_HISTORY_MOST_VIEW, mostView);
        cVal.put(KEY_HISTORY_MOST_DOWNLOAD, mostDownload);
        cVal.put(KEY_HISTORY_WEBSITE, website);
        cVal.put(KEY_HISTORY_UPDATED_ON, updatedOn);
        cVal.put(KEY_HISTORY_STATUS, status);
        cVal.put(KEY_HISTORY_CATEGORY, category);
        cVal.put(KEY_HISTORY_PHOTO_URL, photoUrl);

        // updating row
        return db.update(TABLE_HISTORY, cVal, KEY_HISTORY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public static int updateDaysData(DayModel dayModel) {

        final SQLiteDatabase db = open();

        int id = dayModel.getId();
        int no = dayModel.getNo();
        int serviceId = dayModel.getServiceId();
        String title = sqlEscapeString(dayModel.getTitle());
        String description = sqlEscapeString(dayModel.getDescription());
        String thumbnailUrl = sqlEscapeString(dayModel.getThumbnailUrl());
        int featured = dayModel.getFeatured();
        int mostView = dayModel.getMostView();
        int mostDownload = dayModel.getMostDownload();
        String website = sqlEscapeString(dayModel.getWebsite());
        String updatedOn = sqlEscapeString(dayModel.getUpdatedOn());
        String status = sqlEscapeString(dayModel.getStatus());
        String photoUrl = sqlEscapeString(dayModel.getPhotoUrl());

        ContentValues cVal = new ContentValues();
        cVal.put(KEY_DAY_NO, no);
        cVal.put(KEY_SERVICE_ID, serviceId);
        cVal.put(KEY_DAY_TITLE, title);
        cVal.put(KEY_DAY_DESCRIPTION, description);
        cVal.put(KEY_DAY_THUMBNAIL_URL, thumbnailUrl);
        cVal.put(KEY_DAY_FEATURED, featured);
        cVal.put(KEY_DAY_MOST_VIEW, mostView);
        cVal.put(KEY_DAY_MOST_DOWNLOAD, mostDownload);
        cVal.put(KEY_DAY_WEBSITE, website);
        cVal.put(KEY_DAY_UPDATED_ON, updatedOn);
        cVal.put(KEY_DAY_STATUS, status);
        cVal.put(KEY_DAY_PHOTO_URL, photoUrl);

        // updating row
        return db.update(TABLE_DAYS, cVal, KEY_DAY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public static int updateDepartmentsData(DepartmentModel departmentModel) {

        final SQLiteDatabase db = open();

        int id = departmentModel.getId();
        int no = departmentModel.getNo();
        int serviceId = departmentModel.getServiceId();
        String title = sqlEscapeString(departmentModel.getTitle());
        String description = sqlEscapeString(departmentModel.getDescription());
        String thumbnailUrl = sqlEscapeString(departmentModel.getThumbnailUrl());
        int featured = departmentModel.getFeatured();
        int mostView = departmentModel.getMostView();
        int mostDownload = departmentModel.getMostDownload();
        String website = sqlEscapeString(departmentModel.getWebsite());
        String updatedOn = sqlEscapeString(departmentModel.getUpdatedOn());
        String status = sqlEscapeString(departmentModel.getStatus());
        String photoUrl = sqlEscapeString(departmentModel.getPhotoUrl());

        ContentValues cVal = new ContentValues();
        cVal.put(KEY_DEPARTMENT_NO, no);
        cVal.put(KEY_SERVICE_ID, serviceId);
        cVal.put(KEY_DEPARTMENT_TITLE, title);
        cVal.put(KEY_DEPARTMENT_DESCRIPTION, description);
        cVal.put(KEY_DEPARTMENT_THUMBNAIL_URL, thumbnailUrl);
        cVal.put(KEY_DEPARTMENT_FEATURED, featured);
        cVal.put(KEY_DEPARTMENT_MOST_VIEW, mostView);
        cVal.put(KEY_DEPARTMENT_MOST_DOWNLOAD, mostDownload);
        cVal.put(KEY_DEPARTMENT_WEBSITE, website);
        cVal.put(KEY_DEPARTMENT_UPDATED_ON, updatedOn);
        cVal.put(KEY_DEPARTMENT_STATUS, status);
        cVal.put(KEY_DEPARTMENT_PHOTO_URL, photoUrl);

        // updating row
        return db.update(TABLE_DEPARTMENTS, cVal, KEY_DEPARTMENT_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public static int updateAppsData(AppModel appModel) {

        final SQLiteDatabase db = open();

        int id = appModel.getId();
        int no = appModel.getNo();
        int serviceId = appModel.getServiceId();
        String title = sqlEscapeString(appModel.getTitle());
        String description = sqlEscapeString(appModel.getDescription());
        String thumbnailUrl = sqlEscapeString(appModel.getThumbnailUrl());
        int featured = appModel.getFeatured();
        int mostView = appModel.getMostView();
        int mostDownload = appModel.getMostDownload();
        String website = sqlEscapeString(appModel.getWebsite());
        String updatedOn = sqlEscapeString(appModel.getUpdatedOn());
        String status = sqlEscapeString(appModel.getStatus());
        String playStoreLink = sqlEscapeString(appModel.getDownloadApp());
        String photoUrl = sqlEscapeString(appModel.getPhotoUrl());

        ContentValues cVal = new ContentValues();
        cVal.put(KEY_APP_NO, no);
        cVal.put(KEY_SERVICE_ID, serviceId);
        cVal.put(KEY_APP_TITLE, title);
        cVal.put(KEY_APP_DESCRIPTION, description);
        cVal.put(KEY_APP_THUMBNAIL_URL, thumbnailUrl);
        cVal.put(KEY_APP_FEATURED, featured);
        cVal.put(KEY_APP_MOST_VIEW, mostView);
        cVal.put(KEY_APP_MOST_DOWNLOAD, mostDownload);
        cVal.put(KEY_APP_WEBSITE, website);
        cVal.put(KEY_APP_UPDATED_ON, updatedOn);
        cVal.put(KEY_APP_STATUS, status);
        cVal.put(KEY_APP_PLAY_STORE_LINK, playStoreLink);
        cVal.put(KEY_APP_PHOTO_URL, photoUrl);

        // updating row
        return db.update(TABLE_APPS, cVal, KEY_APP_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public static int updateArticleData(ArticleModel articleModel) {

        final SQLiteDatabase db = open();

        int id = articleModel.getId();
        int no = articleModel.getNo();
        int serviceId = articleModel.getServiceId();
        String title = sqlEscapeString(articleModel.getTitle());
        String description = sqlEscapeString(articleModel.getDescription());
        String thumbnailUrl = sqlEscapeString(articleModel.getThumbnailUrl());
        int featured = articleModel.getFeatured();
        int mostView = articleModel.getMostView();
        int mostDownload = articleModel.getMostDownload();
        String website = sqlEscapeString(articleModel.getWebsite());
        String updatedOn = sqlEscapeString(articleModel.getUpdatedOn());
        String status = sqlEscapeString(articleModel.getStatus());
        String category = sqlEscapeString(articleModel.getCategory());
        String writer = sqlEscapeString(articleModel.getWriter());
        String photoUrl = sqlEscapeString(articleModel.getPhotoUrl());

        ContentValues cVal = new ContentValues();
        cVal.put(KEY_ARTICLE_NO, no);
        cVal.put(KEY_SERVICE_ID, serviceId);
        cVal.put(KEY_ARTICLE_TITLE, title);
        cVal.put(KEY_ARTICLE_DESCRIPTION, description);
        cVal.put(KEY_ARTICLE_THUMBNAIL_URL, thumbnailUrl);
        cVal.put(KEY_ARTICLE_FEATURED, featured);
        cVal.put(KEY_ARTICLE_MOST_VIEW, mostView);
        cVal.put(KEY_ARTICLE_MOST_DOWNLOAD, mostDownload);
        cVal.put(KEY_ARTICLE_WEBSITE, website);
        cVal.put(KEY_ARTICLE_UPDATED_ON, updatedOn);
        cVal.put(KEY_ARTICLE_STATUS, status);
        cVal.put(KEY_ARTICLE_CATEGORY, category);
        cVal.put(KEY_ARTICLE_WRITER, writer);
        cVal.put(KEY_ARTICLE_PHOTO_URL, photoUrl);

        // updating row
        return db.update(TABLE_ARTICLES, cVal, KEY_ARTICLE_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public static int updateBlogData(BlogModel blogModel) {

        final SQLiteDatabase db = open();

        int id = blogModel.getId();
        int no = blogModel.getNo();
        int serviceId = blogModel.getServiceId();
        String title = sqlEscapeString(blogModel.getTitle());
        String description = sqlEscapeString(blogModel.getDescription());
        String category = sqlEscapeString(blogModel.getCategory());
        String writer = sqlEscapeString(blogModel.getWriter());
        int featured = blogModel.getFeatured();
        int mostView = blogModel.getMostView();
        int mostDownload = blogModel.getMostDownload();
        String website = sqlEscapeString(blogModel.getWebsite());
        String updatedOn = sqlEscapeString(blogModel.getUpdatedOn());
        String status = sqlEscapeString(blogModel.getStatus());
        String thumbnailUrl = sqlEscapeString(blogModel.getThumbnailUrl());
        String photoUrl = sqlEscapeString(blogModel.getPhotoUrl());

        ContentValues cVal = new ContentValues();
        cVal.put(KEY_BLOG_NO, no);
        cVal.put(KEY_SERVICE_ID, serviceId);
        cVal.put(KEY_BLOG_TITLE, title);
        cVal.put(KEY_BLOG_DESCRIPTION, description);
        cVal.put(KEY_BLOG_THUMBNAIL_URL, thumbnailUrl);
        cVal.put(KEY_BLOG_FEATURED, featured);
        cVal.put(KEY_BLOG_MOST_VIEW, mostView);
        cVal.put(KEY_BLOG_MOST_DOWNLOAD, mostDownload);
        cVal.put(KEY_BLOG_WEBSITE, website);
        cVal.put(KEY_BLOG_UPDATED_ON, updatedOn);
        cVal.put(KEY_BLOG_STATUS, status);
        cVal.put(KEY_BLOG_CATEGORY, category);
        cVal.put(KEY_BLOG_WRITER, writer);
        cVal.put(KEY_BLOG_PHOTO_URL, photoUrl);

        // updating row
        return db.update(TABLE_BLOGS, cVal, KEY_BLOG_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public static int updatePoetryData(PoetryModel poetryModel) {

        final SQLiteDatabase db = open();

        int id = poetryModel.getId();
        int no = poetryModel.getNo();
        int serviceId = poetryModel.getServiceId();
        String title = sqlEscapeString(poetryModel.getTitle());
        String description = sqlEscapeString(poetryModel.getDescription());
        String category = sqlEscapeString(poetryModel.getCategory());
        String writer = sqlEscapeString(poetryModel.getWriter());
        int featured = poetryModel.getFeatured();
        int mostView = poetryModel.getMostView();
        int mostDownload = poetryModel.getMostDownload();
        String website = sqlEscapeString(poetryModel.getWebsite());
        String updatedOn = sqlEscapeString(poetryModel.getUpdatedOn());
        String status = sqlEscapeString(poetryModel.getStatus());
        String thumbnailUrl = sqlEscapeString(poetryModel.getThumbnailUrl());
        String photoUrl = sqlEscapeString(poetryModel.getPhotoUrl());

        ContentValues cVal = new ContentValues();
        cVal.put(KEY_POETRY_NO, no);
        cVal.put(KEY_SERVICE_ID, serviceId);
        cVal.put(KEY_POETRY_TITLE, title);
        cVal.put(KEY_POETRY_DESCRIPTION, description);
        cVal.put(KEY_POETRY_THUMBNAIL_URL, thumbnailUrl);
        cVal.put(KEY_POETRY_FEATURED, featured);
        cVal.put(KEY_POETRY_MOST_VIEW, mostView);
        cVal.put(KEY_POETRY_MOST_DOWNLOAD, mostDownload);
        cVal.put(KEY_POETRY_WEBSITE, website);
        cVal.put(KEY_POETRY_UPDATED_ON, updatedOn);
        cVal.put(KEY_POETRY_STATUS, status);
        cVal.put(KEY_POETRY_CATEGORY, category);
        cVal.put(KEY_POETRY_WRITER, writer);
        cVal.put(KEY_POETRY_PHOTO_URL, photoUrl);

        // updating row
        return db.update(TABLE_POETRY, cVal, KEY_POETRY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    // Deleting single contact
    public static void deleteSettingsData(SettingsModel data) {

        final SQLiteDatabase db = open();
        db.delete(TABLE_SETTINGS, KEY_SETTING_ID + " = ?",
                new String[]{String.valueOf(data.getId())});
        // db.close();
    }

    public static int deleteLanguage(int id) {

        final SQLiteDatabase db = open();
        int rows = db.delete(TABLE_LANGUAGES, KEY_LANGUAGE_ID + " = ?",
                new String[]{String.valueOf(id)});
        // db.close();

        return rows;
    }

    public static int deleteService(int id) {

        final SQLiteDatabase db = open();
        int rows = db.delete(TABLE_SERVICES, KEY_SERVICE_ID + " = ?",
                new String[]{String.valueOf(id)});
        // db.close();

        return rows;
    }

    public static int deleteMaster(int id) {

        final SQLiteDatabase db = open();
        int rows = db.delete(TABLE_MASTER, KEY_MASTER_ID + " = ?",
                new String[]{String.valueOf(id)});
        // db.close();

        return rows;
    }

    public static int deleteSubMaster(int id) {

        final SQLiteDatabase db = open();
        int rows = db.delete(TABLE_SUB_MASTER, KEY_SUB_MASTER_ID + " = ?",
                new String[]{String.valueOf(id)});
        // db.close();

        return rows;
    }

    public static void deleteDataTablesData(ProductModel data) {

        final SQLiteDatabase db = open();
        db.delete(TABLE_DATA_TABLES, KEY_DATA_TABLES_ID + " = ?",
                new String[]{String.valueOf(data.getId())});
        // db.close();
    }

    public static void deleteProductData(String table, String shortName, int id) {

        final SQLiteDatabase db = open();
        db.delete(table, shortName + "_id = ?",
                new String[]{String.valueOf(id)});
        // db.close();
    }

    // Deleting all contacts
    public static int deleteAllSettingsData() {

        final SQLiteDatabase db = open();
        int rows = db.delete(TABLE_SETTINGS, null, null);
        // db.close();

        return rows;
    }

    public static int deleteAllLanguages() {

        final SQLiteDatabase db = open();
        int rows = db.delete(TABLE_LANGUAGES, null, null);
        // db.close();

        return rows;
    }

    public static int deleteAllServices() {

        final SQLiteDatabase db = open();
        int rows = db.delete(TABLE_SERVICES, null, null);
        // db.close();

        return rows;
    }

    public static int deleteAllMasters() {

        final SQLiteDatabase db = open();
        int rows = db.delete(TABLE_MASTER, null, null);
        // db.close();

        return rows;
    }

    public static int deleteAllSubMasters() {

        final SQLiteDatabase db = open();
        int rows = db.delete(TABLE_SUB_MASTER, null, null);
        // db.close();

        return rows;
    }

    public static int deleteAllDataTables() {

        final SQLiteDatabase db = open();
        int rows = db.delete(TABLE_DATA_TABLES, null, null);
        // db.close();

        return rows;
    }

    public static int deleteAllProductData(String table) {

        final SQLiteDatabase db = open();
        int rows = db.delete(table, null, null);
        // db.close();

        return rows;
    }

    // Getting contacts Count
    public static int getSettingsCount() {

        int count = 0;
        String countQuery = "SELECT  * FROM " + TABLE_SETTINGS;
        final SQLiteDatabase db = open();
        Cursor cursor = db.rawQuery(countQuery, null);

        if (cursor != null && !cursor.isClosed()) {

            count = cursor.getCount();
            cursor.close();
        }

        // return count
        return count;
    }

    public static int getLanguageCount() {

        int count = 0;
        String countQuery = "SELECT * FROM " + TABLE_LANGUAGES;
        final SQLiteDatabase db = open();
        Cursor cursor = db.rawQuery(countQuery, null);

        if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {

            count = cursor.getCount();
            cursor.close();
        }

        // return count
        return count;
    }

    public static int getServiceCount() {

        int count = 0;
        String countQuery = "SELECT * FROM " + TABLE_SERVICES;
        final SQLiteDatabase db = open();
        Cursor cursor = db.rawQuery(countQuery, null);

        if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {

            count = cursor.getCount();
            cursor.close();
        }

        // return count
        return count;
    }

    public static int getMasterCount() {

        int count = 0;
        String countQuery = "SELECT * FROM " + TABLE_MASTER;
        final SQLiteDatabase db = open();
        Cursor cursor = db.rawQuery(countQuery, null);

        if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {

            count = cursor.getCount();
            cursor.close();
        }

        // return count
        return count;
    }

    public static int getSubMasterCount() {

        int count = 0;
        String countQuery = "SELECT * FROM " + TABLE_SUB_MASTER;
        final SQLiteDatabase db = open();
        Cursor cursor = db.rawQuery(countQuery, null);

        if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {

            count = cursor.getCount();
            cursor.close();
        }

        // return count
        return count;
    }

    public static int getDataTablesCount() {

        int count = 0;
        String countQuery = "SELECT * FROM " + TABLE_DATA_TABLES;
        final SQLiteDatabase db = open();
        Cursor cursor = db.rawQuery(countQuery, null);

        if (cursor != null && !cursor.isClosed()) {

            count = cursor.getCount();
            cursor.close();
        }

        // return count
        return count;
    }

    public static int getProductCount(String table) {

        int count = 0;

        try {

            String countQuery = "SELECT * FROM " + table;
            final SQLiteDatabase db = open();
            Cursor cursor = db.rawQuery(countQuery, null);

            if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {

                count = cursor.getCount();
                cursor.close();
            }

        } catch (SQLiteException ex) {
            count = 0;
        }

        // return count
        return count;
    }
}