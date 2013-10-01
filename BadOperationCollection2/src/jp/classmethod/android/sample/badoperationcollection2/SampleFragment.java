package jp.classmethod.android.sample.badoperationcollection2;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 
 * @author komuro.hiraku
 *
 */
public class SampleFragment extends Fragment {
	
	public static final String FRAGMENT_ID_KEY = "fragment_id_key";
	
	private TextView fragmentIdTv;
	
	public SampleFragment() {
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		Bundle arguments = getArguments();
		String val = arguments.getString(FRAGMENT_ID_KEY);
		
		fragmentIdTv.setText(val);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.sample_fragment, container, false);
		fragmentIdTv = (TextView) view.findViewById(R.id.fragment_number);
		return view;
	}
}
