package jp.classmethod.android.sample.badoperationcollection_01;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	
	private Button changeFragment01;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		changeFragment01 = (Button) findViewById(R.id.change_frag_01);
		changeFragment01.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				// Fragmentを生成
				FirstFragment fragment = new FirstFragment();
				// 設定のBundleを設定
				Bundle bundle = new Bundle();
				bundle.putString("category_01", "タンゴ");
				bundle.putString("category_02", "イエーガー");
				fragment.setArguments(bundle);
				
				// Fragmentを表示
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.replace(R.id.fragment_container, fragment);
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				ft.commit();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
