package com.example.birthdaytree.edittext;

import com.example.birthdaytree.LineEditText;
import com.example.birthdaytree.R;

import android.content.Context;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class DefEdittext extends LinearLayout{
	LineEditText  lineEditText;
	ImageView leftView;
	ImageView rightView;
	
	public DefEdittext(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		
	}

	public DefEdittext(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.def_edittext, this,true);
		lineEditText = (LineEditText)this.findViewById(R.id.def_edittext);
		leftView = (ImageView)this.findViewById(R.id.left_image);
		lineEditText.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean bool) {
				// TODO Auto-generated method stub
				if(bool){				
					v.setBackgroundResource(R.drawable.edittext);
				}else{
					v.setBackgroundResource(R.drawable.no_edittext);
				}
				
			}
		});
		}

	 public Editable getContent(){  
	        return lineEditText.getText();  
	    }
	 public void setAppearText(String str){
		 lineEditText.setHint(str);
	 }
	 public void setContent(String str){
		 lineEditText.setText(str);
	 }
	 //这里估计可以用设计模式解决问题
	public void setLeftImage(int resid) {
		// TODO Auto-generated method stub
		leftView.setBackgroundResource(resid);
	}
	public void setRightImage(int resid){
		rightView.setBackgroundResource(resid);
	}
	public DefEdittext(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public LineEditText getLineEditText() {
		return lineEditText;
	}

	public void setLineEditText(LineEditText lineEditText) {
		this.lineEditText = lineEditText;
	}

	public ImageView getLeftView() {
		return leftView;
	}

	public void setLeftView(ImageView leftView) {
		this.leftView = leftView;
	}

	public ImageView getRightView() {
		return rightView;
	}

	public void setRightView(ImageView rightView) {
		this.rightView = rightView;
	}
	
}
