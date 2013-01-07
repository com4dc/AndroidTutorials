package jp.classmethod.renderscript.sample.gravity;

/**
 * http://frog-on-air.blogspot.jp/2011/06/flasherrenderscript.html
 * 
 * http://sakplus.jp/2012/07/19/renderscript-step2/
 */
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

	private GravityView mView;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        super.onCreate(savedInstanceState);        
        mView = new GravityView(this); // RSSurfaceViewの作成
        setContentView(mView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
}
