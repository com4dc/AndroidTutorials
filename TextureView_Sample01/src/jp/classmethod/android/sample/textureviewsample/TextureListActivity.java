package jp.classmethod.android.sample.textureviewsample;

import java.util.ArrayList;
import java.util.List;

import jp.classmethod.android.sample.textureviewsample.adapter.SampleItemAdapter;
import jp.classmethod.android.sample.textureviewsample.adapter.SampleItemDto;

import android.app.ListActivity;
import android.os.Bundle;

public class TextureListActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.listview_main);
		
		// データ作成
		List<SampleItemDto> list = new ArrayList<SampleItemDto>();
		list.add(new SampleItemDto("Sample01"));
		list.add(new SampleItemDto("Sample01"));
		
		getListView().setAdapter(new SampleItemAdapter(this, 0, list));
	}

}
