package my.sbjr.batteryalarm;

import java.io.IOException;

import org.w3c.dom.Text;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {

	private int alarm = 1;
	private int r = 2;
	MediaPlayer mMediaPlayer=null;
	Button bt1;
	TextView tv1;
	ProgressBar pb1;
    TextView tv2;
    BatteryFullReceiver myService;
    
 	
    private BroadcastReceiver batteryRec = new BroadcastReceiver(){

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			int level = intent.getIntExtra("level",0);
			pb1.setProgress(level);
			tv2.setText("Charged "+level+"%");
			if(level == 100){
				tv2.setText("Fully Charged");
			}
		}
    	
    };
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt1 = (Button) findViewById(R.id.button1);
        tv1 = (TextView) findViewById(R.id.textView1);
        pb1 = (ProgressBar) findViewById(R.id.progressBar1);
        tv2 = (TextView) findViewById(R.id.textView2);
        registerReceiver(batteryRec, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        
        if(myService==null){
        	Intent iservice = new Intent(this,BatteryFullReceiver.class);
        	startService(iservice);
        	Log.d("main activity", "started the service");
        }
        
        
        try{
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        r = b.getInt("ring");
        }
        catch(Exception e){
        	tv1.setText("excetion caught");
        }
        if(r==1&&alarm==1){
        	tv1.setText("Charged 100%");
        	ring();
        }
        else if(r==0){
        	tv1.setText("Charging");
        }
    }
    
    
    
    public void ring(){
    	Uri ringtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
    	mMediaPlayer = new MediaPlayer();
    	try {
			mMediaPlayer.setDataSource(getApplicationContext(), ringtone);
			final AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
			mMediaPlayer.setLooping(true);
			mMediaPlayer.prepare();
			if(alarm==1)
				mMediaPlayer.start();
			else
				mMediaPlayer.stop();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void stop(View v){
    	alarm = 0;
    	bt1.setText("Set Alarm On");
    	mMediaPlayer.stop();
    }
    
    public void clickmethod(View v){
    	if(alarm == 1){
    		alarm = 0;
    		bt1.setText("Set Alarm On");
    		Intent iservice = new Intent(this,BatteryFullReceiver.class);
    		stopService(iservice);
    		Log.d("main activity", "stopped the service from alarm button");
    		
    	}
    	else{
    		alarm = 1;
    		bt1.setText("Set Alarm Off");
    		Intent iservice = new Intent(this,BatteryFullReceiver.class);
    		startService(iservice);
        	Log.d("main activity", "started the service from alarm button");
    	}
    }
}
