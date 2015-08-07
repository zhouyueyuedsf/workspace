package com.example.birthdaytree.dialog;
import java.util.Calendar;

import com.example.birthdaytree.R;
import com.example.birthdaytree.datepicker.DatePicker;
import com.gc.materialdesign.views.ButtonFlat;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class CommonDialogs extends Dialog{
	private Context context;
	private String title;
	private String confirmButtonText;
	private String cacelButtonText;
	DatePicker datePicker;
	Calendar mCalendar;
	TextView titleText;
	ButtonFlat cancel,sure;
	private ClickListenerInterface clickListenerInterface;
	public CommonDialogs(Context context) {
		super(context);
	}
	
	public CommonDialogs(Context context, int theme,String title, String confirmButtonText, String cacelButtonText) {
        super(context, theme);
        this.context = context;
        this.title = title;
        this.confirmButtonText = confirmButtonText;
        this.cacelButtonText = cacelButtonText;
    }
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//消除自定义dialog上面的黑色框
		setContentView(R.layout.activity_light);//dialog的布局
		titleText =  (TextView)findViewById(R.id.title);
		cancel = (ButtonFlat)this.findViewById(R.id.cancel);
		cancel.setOnClickListener(new clickListener());

		sure = (ButtonFlat)this.findViewById(R.id.sure);
		sure.setOnClickListener(new clickListener());//一定要记起
		titleText.setText(title);
		titleText.setTextSize(20f);
		cancel.setText(cacelButtonText);
		sure.setText(confirmButtonText);
		
		mCalendar = Calendar.getInstance();
		datePicker = (DatePicker) findViewById(R.id.datePicker);
		//setPerference();//设置窗口的大小和位置
	
	}
	private void setPerference() {
		Window dialogWindow = getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
		lp.width = (int) (d.widthPixels * 0.8); // 高度设置为屏幕的0.6
		lp.height = (int) (d.heightPixels*0.4);
		dialogWindow.setAttributes(lp);	
	}
	public interface ClickListenerInterface {

        public void doConfirm(DatePicker datePicker); 
        public void doCancel();
	}
	public void setClicklistener(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
     }
	private class clickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            int id = v.getId();
            switch (id) {
            case R.id.sure:
            clickListenerInterface.doConfirm(datePicker);
                break;
            case R.id.cancel:
                clickListenerInterface.doCancel();
                break;
            }
        }

    };
}


 
