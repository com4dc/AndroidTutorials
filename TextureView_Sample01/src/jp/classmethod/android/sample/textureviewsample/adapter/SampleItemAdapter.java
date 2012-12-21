package jp.classmethod.android.sample.textureviewsample.adapter;

import java.util.List;

import jp.classmethod.android.sample.textureviewsample.R;
import jp.classmethod.android.sample.textureviewsample.view.CameraPreviewTextureView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SampleItemAdapter extends ArrayAdapter<SampleItemDto> {
	
	private Context context;

	public SampleItemAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		
		this.context = context;
	}

	public SampleItemAdapter(Context context, int resource,
			int textViewResourceId) {
		super(context, resource, textViewResourceId);
		
		this.context = context;
	}

	public SampleItemAdapter(Context context, int textViewResourceId,
			SampleItemDto[] objects) {
		super(context, textViewResourceId, objects);
		
		this.context = context;
	}

	public SampleItemAdapter(Context context, int textViewResourceId,
			List<SampleItemDto> objects) {
		super(context, textViewResourceId, objects);
		
		this.context = context;
	}

	public SampleItemAdapter(Context context, int resource,
			int textViewResourceId, SampleItemDto[] objects) {
		super(context, resource, textViewResourceId, objects);
		
		this.context = context;
	}

	public SampleItemAdapter(Context context, int resource,
			int textViewResourceId, List<SampleItemDto> objects) {
		super(context, resource, textViewResourceId, objects);
		
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		
		if(convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.listview_item, null);
			
			holder = new ViewHolder();
			holder.messageLabel = (TextView) convertView.findViewById(R.id.messageLabel);
			holder.previewView = (CameraPreviewTextureView) convertView.findViewById(R.id.cameraPreview);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		final SampleItemDto item = getItem(position);
		if(item != null) {
			holder.messageLabel.setText(item.getMessage());
			holder.previewView.invalidate();
		}
		
		return convertView;
	}

	class ViewHolder {
		TextView messageLabel;
		CameraPreviewTextureView previewView;
	}
	
}
