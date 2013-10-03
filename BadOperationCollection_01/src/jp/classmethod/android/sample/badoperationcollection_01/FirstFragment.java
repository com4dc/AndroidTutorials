package jp.classmethod.android.sample.badoperationcollection_01;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class FirstFragment extends Fragment {
	
	private Button callSetArgument;
	
	private EditText category01;
	
	private EditText category02;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		// 渡ってきたBundleから初期値を設定
		Bundle bundle = getArguments();
		String category01Val = bundle.getString("category_01");
		String category02Val = bundle.getString("category_02");
		
		category01.setText(category01Val);
		category02.setText(category02Val);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_01, container, false);
		
		callSetArgument = (Button) view.findViewById(R.id.call_method);
		callSetArgument.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 現在入力されてる状態をBundleに格納する
				Bundle bundle = newFragmentSetting();
				setArguments(bundle);
			}
		});
		
		category01 = (EditText) view.findViewById(R.id.category_01_value);
		category02 = (EditText) view.findViewById(R.id.category_02_value);
		
		return view;
	}

	/**
	 * 今の情報のBundleを新規作成
	 * @return
	 */
	private Bundle newFragmentSetting() {
		Bundle bundle = new Bundle();
		bundle.putString("category_01", category01.getText().toString());
		bundle.putString("category_02", category02.getText().toString());
		
		return bundle;
	}
	
}
