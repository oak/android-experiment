package com.oak.android.appraiser;

import android.app.Activity;
import android.app.ListFragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.oak.android.appraiser.db.AppraiserContract;
import com.oak.android.appraiser.db.AppraiserOpenHelper;

public class CategoryFragment extends ListFragment {
	// Container Activity must implement this interface
	public interface OnCategorySelectedListener {
		public void onCategorySelected(Long categoryId);
	}

	private static final String TAG = "CategoryFragment";

	// This is the Adapter being used to display the list's data
	private SimpleCursorAdapter mAdapter;

	OnCategorySelectedListener mListener;

	private Long parentId;

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	// These are the Contacts rows that we will retrieve
	private static final String[] PROJECTION = new String[] {
			AppraiserContract.Category._ID,
			AppraiserContract.Category.COLUMN_NAME_TITLE };

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		Log.v(TAG, "onAttach");
		try {
			mListener = (OnCategorySelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnArticleSelectedListener");
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v(TAG, "onCreate");
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		Log.v(TAG, "onCreateView");
		return inflater.inflate(R.layout.category_list_fragment, container,
				false);
	}

	@Override
	public void onStart() {
		super.onStart();

		loadList();
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Cursor selectedCategory = (Cursor) l.getItemAtPosition(position);
		Long categoryId = selectedCategory.getLong(selectedCategory
				.getColumnIndex(AppraiserContract.Category._ID));

		mListener.onCategorySelected(categoryId);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
		menuInflater.inflate(R.menu.category_list_fragment,
				menu);
		Log.v(TAG, "onCreateOptionsMenu");
	}

	private void loadList() {
		AppraiserOpenHelper mDbHelper = new AppraiserOpenHelper(getActivity());
		SQLiteDatabase rdb = mDbHelper.getReadableDatabase();
		
		String where = AppraiserContract.Category.COLUMN_NAME_PARENT_ID;

		where += this.getParentId() != null ? " = "
				+ this.getParentId().toString() : " IS NULL";

		Cursor cursor = rdb.query(AppraiserContract.Category.TABLE_NAME,
				PROJECTION, where, null, null, null, null);

		String[] fromColumns = { AppraiserContract.Category.COLUMN_NAME_TITLE };
		int[] toViews = { android.R.id.title };

		mAdapter = new SimpleCursorAdapter(getActivity(),
				android.R.layout.simple_list_item_1, cursor, fromColumns,
				toViews, 0);

		setListAdapter(mAdapter);
	}
}
