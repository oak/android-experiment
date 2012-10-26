package com.oak.android.appraiser;

import android.app.ListFragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.oak.android.appraiser.db.AppraiserContract;
import com.oak.android.appraiser.db.AppraiserOpenHelper;

public class ItemFragment extends ListFragment {
	private static final String TAG = "ItemFragment";

	// This is the Adapter being used to display the list's data
	private SimpleCursorAdapter mAdapter;

	private Long categoryId;

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	// These are the Contacts rows that we will retrieve
	private static final String[] PROJECTION = new String[] {
			AppraiserContract.Item._ID,
			AppraiserContract.Item.COLUMN_NAME_NAME };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		Log.v(TAG, "onCreateView");
		return inflater.inflate(R.layout.item_list_fragment, container,
				false);
	}

	@Override
	public void onStart() {
		super.onStart();
		Log.v(TAG, "onStart");

		loadList();
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Cursor selectedItem = (Cursor) l.getItemAtPosition(position);
		Long item = selectedItem.getLong(selectedItem
				.getColumnIndex(AppraiserContract.Category._ID));
		
	}

	/*@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
		menuInflater.inflate(R.menu.activity_main, menu);
		Log.v(TAG, "onCreateOptionsMenu");
	}*/

	private void loadList() {
		AppraiserOpenHelper mDbHelper = new AppraiserOpenHelper(getActivity());
		SQLiteDatabase rdb = mDbHelper.getReadableDatabase();

		String where = AppraiserContract.Item.COLUMN_NAME_CATEGORY_ID;

		where += this.getCategoryId() != null ? " = "
				+ this.getCategoryId().toString() : " = 1";

		Cursor cursor = rdb.query(AppraiserContract.Item.TABLE_NAME,
				PROJECTION, where, null, null, null, null);

		// For the cursor adapter, specify which columns go into which views
		String[] fromColumns = { AppraiserContract.Item.COLUMN_NAME_NAME };
		int[] toViews = { android.R.id.text1 }; // The TextView in
												// simple_list_item_1

		mAdapter = new SimpleCursorAdapter(getActivity(),
				android.R.layout.simple_list_item_1, cursor, fromColumns,
				toViews, 0);

		setListAdapter(mAdapter);
	}
}
