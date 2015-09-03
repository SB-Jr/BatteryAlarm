package my.sbjr.batteryalarm;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class BatteryFullReceiver extends Service{

	
	public BroadcastReceiver myReceiver = new BroadcastReceiver(){

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Log.d("BroadCast receiver inside service","now in onReceive");
			
			int level = intent.getIntExtra("level", 0);
			if(level==100){
				Intent intent2 = new Intent(getBaseContext(),MainActivity.class);
				intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				Bundle b = new Bundle();
				b.putInt("ring", 1);
				intent2.putExtras(b);
				getApplication().startActivity(intent2);
				Log.d("BroadCast receiver inside service","activity started using 1");
			}
		}
		
	};
	
	@Override
	public int onStartCommand(Intent intent,int flags,int startId){
		
		Log.d("Service","Started in onStartCommand");
		
		final IntentFilter batteryIntent = new IntentFilter(intent.ACTION_BATTERY_CHANGED);
		this.registerReceiver(myReceiver, batteryIntent);
		
		Log.d("Service","Receiver registered");
		
		return super.onStartCommand(intent, flags, startId);
	}
	
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
}