package com.oak.android.appraiser.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AppraiserOpenHelper extends SQLiteOpenHelper {
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "Appraiser.db";

	private static final String TEXT_TYPE = " TEXT";
	private static final String COMMA_SEP = ",";
	
	private static final String SQL_CREATE_TABLE_CATEGORY = "CREATE TABLE "
			+ AppraiserContract.Category.TABLE_NAME + " ("
			+ AppraiserContract.Category._ID + " INTEGER PRIMARY KEY" + COMMA_SEP
			+ AppraiserContract.Category.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP 
			+ AppraiserContract.Category.COLUMN_NAME_PARENT_ID + " INTEGER " 
			+ " );";
	
	private static final String SQL_CREATE_TABLE_ITEM = "CREATE TABLE "
			+ AppraiserContract.Item.TABLE_NAME + " ("
			+ AppraiserContract.Item._ID + " INTEGER PRIMARY KEY" + COMMA_SEP
			+ AppraiserContract.Item.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP
			+ AppraiserContract.Item.COLUMN_NAME_CATEGORY_ID + " INTEGER "
			+ " );";

	private static final String SQL_DROP_TABLE_CATEGORY = "DROP TABLE IF EXISTS "
			+ AppraiserContract.Category.TABLE_NAME + ";";


	private static final String SQL_DROP_TABLE_ITEM = "DROP TABLE IF EXISTS "
			+ AppraiserContract.Item.TABLE_NAME + ";";

	public AppraiserOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_TABLE_CATEGORY);
		db.execSQL(SQL_CREATE_TABLE_ITEM);
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(SQL_DROP_TABLE_CATEGORY);
		db.execSQL(SQL_DROP_TABLE_ITEM);
		onCreate(db);
	}

	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onUpgrade(db, oldVersion, newVersion);
	}
}
