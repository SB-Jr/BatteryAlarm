package my.sbjr.batteryalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class MyReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Intent intent2 = new Intent(context,MainActivity.class);//.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		//intent2.setClassName("my.sbjr.batteryalarm","MainActivity");
		intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Bundle b = new Bundle();
		b.putInt("ring", 0);
		intent2.putExtras(b);
		context.startActivity(intent2);
	}
	
}
