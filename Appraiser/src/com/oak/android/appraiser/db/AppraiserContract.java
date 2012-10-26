package com.oak.android.appraiser.db;

import android.provider.BaseColumns;

public abstract class AppraiserContract {
	public abstract class Category implements BaseColumns {
		public static final String TABLE_NAME = "category";
		public static final String COLUMN_NAME_TITLE = "title";
		public static final String COLUMN_NAME_PARENT_ID = "parent_id";

		private Category() { }
	}

	public abstract class Item implements BaseColumns {
		public static final String TABLE_NAME = "item";
		public static final String COLUMN_NAME_CATEGORY_ID = "category_id";
		public static final String COLUMN_NAME_NAME = "name";
		public static final String COLUMN_NAME_DESCRIPTION = "description";

		private Item() { }
	}

	private AppraiserContract() { }
}