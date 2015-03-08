package com.example.birthdaytree;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Editable;
import android.util.AttributeSet;
import android.widget.EditText;


public class LineEditText extends EditText {
	 private Paint mPaint;  
	 	
	 public LineEditText(Context context) {
	        super(context);
	        // TODO Auto-generated constructor stub
	        mPaint = new Paint();

	        mPaint.setStyle(Paint.Style.STROKE);
	        mPaint.setColor(Color.GREEN);
	    }

		/** 
	     * @param context 
	     * @param attrs 
	     */  
	    public LineEditText(Context context, AttributeSet attrs) {  
	        super(context, attrs);  
	        // TODO Auto-generated constructor stub  
	        mPaint = new Paint();
	        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
	        mPaint.setStyle(Paint.Style.STROKE);
	        mPaint.setColor(Color.rgb(94, 141, 52)); 
	        
	    }  
	      @Override
	    public Editable getText() {
	    	// TODO Auto-generated method stub
	    	return super.getText();
	    }
	    @Override  
	    public void onDraw(Canvas canvas)  
	    {  
	        super.onDraw(canvas);  
	          
//	      画底线  
	        canvas.drawLine(0,this.getHeight()-1,  this.getWidth()-1, this.getHeight()-1, mPaint);  
	    }  

}
