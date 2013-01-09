package jp.classmethod.komuro.uiautomater;

import java.util.ArrayList;
import java.util.List;

import jp.classmethod.komuro.uiautomater.adapter.TextureViewItemListAdapter;
import jp.classmethod.komuro.uiautomater.dto.ItemDto;
import android.app.ListActivity;
import android.os.Bundle;

public class TextureListViewSampleActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.listview_layout);
		
		List<ItemDto> objects = new ArrayList<ItemDto>();
		objects.add(new ItemDto("001"));
		objects.add(new ItemDto("002"));
		objects.add(new ItemDto("003"));
		objects.add(new ItemDto("004"));
		objects.add(new ItemDto("005"));
		objects.add(new ItemDto("006"));
		objects.add(new ItemDto("007"));
		TextureViewItemListAdapter adapter = new TextureViewItemListAdapter(this, 0, objects);
		
		getListView().setAdapter(adapter);
	}
}
