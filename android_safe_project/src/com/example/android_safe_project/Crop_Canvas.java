package com.example.android_safe_project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

public class Crop_Canvas extends ImageView {  
  
    private final static int PRESS_LB = 0;
    private final static int PRESS_LT = 1;//��ʾ���ϽǾ��ο�  
    private final static int PRESS_RB = 2;//��ʾ���½Ǿ��ο�  
    private final static int PRESS_RT = 3;//��ʾ���ϽǾ��ο�  
    float start_x;
    float start_y; 
    float last_x ;
    float last_y;
    private Bitmap bitMap = null;               //ԭʼͼƬ  
    private RectF src = null;                   //��������ת����Ĳü�����  
    private RectF dst = null;                   //ͼƬ��ʾ����Ҳ����drawBitmap�����е�Ŀ��dst  
    private RectF ChooseArea = null;                //ѡ������                
    private Paint mPaint = null;                //����  
    private Matrix matrix = null;               //����  
      
    private int mx = 0;                          
    private int my = 0;                        
    private boolean touchFlag = false;        
    private boolean cutFlag = false;          
    private int recFlag = -1;                   //�����洢���ʵ�����ĸ�С���ο򣨸ı�ѡ�������С��С���ο�  
    private boolean firstFlag = false;  
      
    private RectF recLT = null;                 //���Ͻǵ�С���ο�  
    private RectF recRT = null;                 //���Ͻǵ�С���ο�  
    private RectF recLB = null;                 //���½ǵ�С���ο�  
    private RectF recRB = null;                 //���½ǵ�С���ο�  
    private static final int LEFT_AREA_ALPHA = 50 * 255 / 100;  
    private RectF leftRectL = null;  
    private RectF leftRectR = null;  
    private RectF leftRectT = null;  
    private RectF leftRectB = null;  
    private Paint leftAreaPaint = null;  
      
    public Crop_Canvas(Context context, AttributeSet attrs) {  
        super(context, attrs);  
        this.init();  
    }  
      
    public Crop_Canvas(Context context) {  
        super(context);  
        this.init();  
    }   
      
    public void init(){  
        cutFlag = true;  
        recLT = new RectF();  
        recLB = new RectF();  
        recRT = new RectF();  
        recRB = new RectF();  
        dst = new RectF();  
        mPaint = new Paint();  
        mPaint.setColor(Color.RED);  
        mPaint.setStyle(Paint.Style.STROKE);      //�����ʵķ���Ϊ����  
        ChooseArea = new RectF();  
        this.setPressRecLoc();  
        src = null;  
        firstFlag = true;  
          
        //ѡ���֮��Ļ�ɫ���򣬷ֳ��ĸ����ο�  
          
        leftAreaPaint = new Paint();  
        leftAreaPaint.setStyle(Paint.Style.FILL);  
        leftAreaPaint.setAlpha(Crop_Canvas.LEFT_AREA_ALPHA);  
    }  
      
    public void setBitmap(Bitmap bitmap){  
        BitmapDrawable bd = new BitmapDrawable(bitmap);  
        src = new RectF(0,0,bd.getIntrinsicWidth(),bd.getIntrinsicHeight());  
        this.bitMap = bitmap.copy(Config.ARGB_8888, true);  
          
        this.setImageBitmap(bitMap);  
        leftRectB = new RectF();  
        leftRectL = new RectF();  
        leftRectR = new RectF();  
        leftRectT = new RectF();  
    }  
      
    public void imageScale(){  
       
    }  
      
    public Bitmap getSubsetBitmap(){  
        float ratioWidth = bitMap.getWidth()/(float)(dst.right-dst.left);  
        float ratioHeight = bitMap.getHeight()/(float)(dst.bottom - dst.top);  
        int left = (int)((ChooseArea.left - dst.left) * ratioWidth);  
        int right = (int)(left + (ChooseArea.right - ChooseArea.left) * ratioWidth);  
        int top = (int)((ChooseArea.top - dst.top) * ratioHeight);  
        int bottom = (int)(top + (ChooseArea.bottom - ChooseArea.top) * ratioHeight);  
        src = new RectF(left,top,right,bottom);  
        firstFlag = true;  
        set_LeftArea_Alpha();  
        return Bitmap.createBitmap(bitMap, left, top, right-left, bottom-top);  
    }  
      
    //���ChooseArea����  
    public RectF getChooseArea(){  
        return ChooseArea;  
    }  
      
    public void moveChooseArea(int move_x,int move_y){  
        if(ChooseArea.left + move_x >= dst.left && ChooseArea.right + move_x <= dst.right  
        && ChooseArea.top + move_y >= dst.top && ChooseArea.bottom + move_y <= dst.bottom){  
            ChooseArea.set(ChooseArea.left + move_x,ChooseArea.top+move_y  
                    ,ChooseArea.right + move_x,ChooseArea.bottom+move_y);  
        }else{  
            if(ChooseArea.left + move_x < dst.left){  
                ChooseArea.set(dst.left,ChooseArea.top  
                        ,ChooseArea.right+dst.left-ChooseArea.left,ChooseArea.bottom);  
            }  
            if(ChooseArea.right + move_x > dst.right){  
                ChooseArea.set(ChooseArea.left+dst.right-ChooseArea.right,ChooseArea.top  
                        ,dst.right,ChooseArea.bottom);  
            }  
              
            if(ChooseArea.top + move_y < dst.top){  
                ChooseArea.set(ChooseArea.left,dst.top  
                        ,ChooseArea.right,ChooseArea.bottom+dst.top-ChooseArea.top);  
            }  
              
            if(ChooseArea.bottom + move_y > dst.bottom){  
                ChooseArea.set(ChooseArea.left,ChooseArea.top+dst.bottom-ChooseArea.bottom  
                        ,ChooseArea.right,dst.bottom);  
            }  
        }  
        this.setPressRecLoc();  
        mPaint.setColor(Color.GREEN);  
        this.invalidate();  
    }  
      
    public boolean onTouchEvent(MotionEvent event){  
        mPaint.setColor(Color.RED);  
          
          
        if(event.getAction() == MotionEvent.ACTION_DOWN && cutFlag){  
            //System.out.println(event.getX() + "," + event.getY());  
              
            mx = (int)event.getX();  
            my = (int)event.getY();  
            if(this.judgeLocation(mx,my)){  
                touchFlag = true;  
                mPaint.setColor(Color.GREEN);  
                this.invalidate();  
                return true;  
            }else{  
              
                if(this.findPresseddst((int)event.getX(), (int)event.getY())){  
                    touchFlag = true;  
                    mPaint.setColor(Color.RED);  
                    return true;  
                }  
            }  
        }  
          
        if(event.getAction() == MotionEvent.ACTION_MOVE && touchFlag){  
            //�ж��Ƿ������ĸ���С���ο�  
            if(this.isOutOfArea((int)event.getX(), (int)event.getY())){  
                return true;  
            }  
              
            //���ѡ�������С��ͼ���Сһ��ʱ���Ͳ����ƶ�  
            if(ChooseArea.left == dst.left && ChooseArea.top == dst.top &&  
               ChooseArea.right == dst.right && ChooseArea.bottom == dst.bottom){  
            }else{  
                this.moveChooseArea((int)event.getX() - mx, (int)event.getY() - my);  
                mx = (int)event.getX();  
                my = (int)event.getY();  
            }  
        }  
          
          
        if(event.getAction() == MotionEvent.ACTION_UP){  
            recFlag = -1;  
            this.invalidate();  
            touchFlag = false;  
        }  
          
        return super.onTouchEvent(event);  
    }  
      
      
      
      
    private boolean isOutOfArea(int x,int y){  
        switch(recFlag){  
        case Crop_Canvas.PRESS_LB:  
            this.pressLB(x - mx, y - my);  
            break;  
        case Crop_Canvas.PRESS_LT:  
            this.pressLT(x - mx, y - my);  
            break;  
        case Crop_Canvas.PRESS_RB:  
            this.pressRB(x - mx, y - my);  
            break;  
        case Crop_Canvas.PRESS_RT:  
            this.pressRT(x - mx, y - my);  
            break;  
        default:return false;  
        }  
        mx = x;  
        my = y;  
        this.invalidate();  
        return true;  
    }  
      
    public boolean findPresseddst(int x,int y){  
        boolean returnFlag = false;  
        if(this.isInRect(x, y, recLB)){  
            recFlag = Crop_Canvas.PRESS_LB;  
            returnFlag = true;  
        }else if(this.isInRect(x, y, recLT)){  
            recFlag = Crop_Canvas.PRESS_LT;  
            returnFlag = true;  
        }else if(this.isInRect(x, y, recRB)){  
            recFlag = Crop_Canvas.PRESS_RB;  
            returnFlag = true;  
        }else if(this.isInRect(x, y, recRT)){  
            recFlag = Crop_Canvas.PRESS_RT;  
            returnFlag = true;  
        }  
          
        return returnFlag;  
    }  
      
    public boolean isInRect(int x,int y,RectF rect){  
        if(x >= rect.left -20 && x <= rect.right + 20 && y > rect.top - 20 && y < rect.bottom + 20){  
            return true;  
        }  
        return false;  
    }  
      
    private void pressLB(int x,int y){  
        float left = ChooseArea.left + x;  
        float right = ChooseArea.right;  
        float top = ChooseArea.top;  
        float bottom = ChooseArea.bottom + y;  
        if(left <= right - 30 && left >= dst.left && bottom <= dst.bottom && bottom >= top + 30){  
                ChooseArea.set(left,top,right,bottom);  
        }else{  
            if(left + x < dst.left){  
                left = dst.left;  
            }  
              
            if(bottom + y > dst.bottom){  
                bottom = dst.bottom;  
            }  
              
            if(ChooseArea.left + x > ChooseArea.right - 30){  
                left = ChooseArea.right - 30;  
            }  
              
            if(ChooseArea.bottom + y < ChooseArea.top + 30){  
                bottom = ChooseArea.top + 30;  
            }  
            ChooseArea.set(left,top,right,bottom);  
        }  
        this.setPressRecLoc();  
    }  
      
      
    private void pressLT(int x,int y){  
        float left = ChooseArea.left + x;  
        float right = ChooseArea.right;  
        float top = ChooseArea.top + y;  
        float bottom = ChooseArea.bottom;  
        if(left <= right - 30 && left >= dst.left && top <= bottom - 30 && top >= dst.top){  
            ChooseArea.set(left,top,right,bottom);  
        }else{  
            if(left < dst.left){  
                left = dst.left;  
            }  
              
            if(top < dst.top){  
                top = dst.top;  
            }  
              
            if(left > right - 30){  
                left = right - 30;  
            }  
              
            if(top > bottom - 30){  
                top = bottom - 30;  
            }  
            ChooseArea.set(left,top,right,bottom);  
        }  
        this.setPressRecLoc();  
    }  
      
      
    private void pressRT(int x,int y){  
        float left = ChooseArea.left;  
        float right = ChooseArea.right + x;  
        float top = ChooseArea.top + y;  
        float bottom = ChooseArea.bottom;  
          
        if(right <= dst.right && right >= left + 30 && top <= bottom - 30 && top >= dst.top){  
            ChooseArea.set(left,top,right,bottom);  
        }else{  
            if(right > dst.right){  
                right = dst.right;  
            }  
              
            if(top < dst.top){  
                top = dst.top;  
            }  
              
            if(right < left + 30){  
                right = left + 30;  
            }  
              
            if(top > bottom - 30){  
                top = bottom - 30;  
            }  
            ChooseArea.set(left,top,right,bottom);  
        }  
        this.setPressRecLoc();  
    }  
      
      
    private void pressRB(int x,int y){  
        float left = ChooseArea.left;  
        float right = ChooseArea.right + x;  
        float top = ChooseArea.top;  
        float bottom = ChooseArea.bottom + y;  
          
        if(right<= dst.right && right >= left + 30 && bottom <= dst.bottom && bottom >= top + 30){  
            ChooseArea.set(left,top,right,bottom);  
        }else{  
            if(right > dst.right){  
                right = dst.right;  
            }  
              
            if(bottom > dst.bottom){  
                bottom = dst.bottom;  
            }  
              
            if(right < left + 30){  
                right = left + 30;  
            }  
              
            if(bottom < top + 30){  
                bottom = top + 30;  
            }  
            ChooseArea.set(left,top,right,bottom);  
        }  
        this.setPressRecLoc();  
    }  
      
    //ÿ�θı�ѡ��������εĴ�С�����ƶ����������ϵ�С����ҲҪ�ı�����Location  
    private void setPressRecLoc(){  
        recLT.set(ChooseArea.left-5,ChooseArea.top-5 , ChooseArea.left+5, ChooseArea.top+5);  
        recLB.set(ChooseArea.left-5,ChooseArea.bottom-5 , ChooseArea.left+5, ChooseArea.bottom+5);  
        recRT.set(ChooseArea.right-5,ChooseArea.top-5 , ChooseArea.right+5, ChooseArea.top+5);  
        recRB.set(ChooseArea.right-5,ChooseArea.bottom-5 , ChooseArea.right+5, ChooseArea.bottom+5);  
    }  
      
  
    public boolean judgeLocation(float x,float y){  
       start_x = this.getChooseArea().left;  
      start_y = this.getChooseArea().top;  
        last_x = this.getChooseArea().right;  
       last_y = this.getChooseArea().bottom;  
        //System.out.println("chubi:" + x + "," + y);  
        //System.out.println(start_y + "," + last_y);  
        if(x > start_x+10 && x < last_x-10 && y > start_y+10 && y < last_y-10){  
            return true;  
        }  
        return false;  
    }  
      
    public void onDraw(Canvas canvas){  
        super.onDraw(canvas);  
        if(firstFlag){  
        	 matrix = this.getImageMatrix();  
//             matrix.mapRect(dst, src);  
//             int padding = this.getPaddingBottom();  
             int width = this.getWidth();  
             int height = this.getHeight();  
             //dst.set(dst.left+padding,dst.top+padding,dst.right+padding,dst.bottom+padding);  
             dst.set(dst.left+20,dst.top+20,width-20,height - 40);  
             ChooseArea = new RectF(dst);  
             this.setPressRecLoc();   
            firstFlag = false;  
            mPaint.setColor(Color.RED);  
//            System.out.println("Width: " + (dst.right - dst.left));  
//            System.out.println("Height: " + (dst.bottom - dst.top));  
//            System.out.println("Width: " + this.getDrawable().getIntrinsicWidth());  
//            System.out.println("Height: " + this.getDrawable().getIntrinsicHeight());  
        }else{  
            set_LeftArea_Alpha();  
        }  
        canvas.drawRect(ChooseArea, mPaint);  
        mPaint.setColor(Color.BLUE);  
        canvas.drawRect(recLT, mPaint);  
        canvas.drawRect(recLB, mPaint);  
        canvas.drawRect(recRT, mPaint);     
        canvas.drawRect(recRB, mPaint);  
          
//        canvas.drawRect(leftRectL, leftAreaPaint);  
//        canvas.drawRect(leftRectR, leftAreaPaint);  
//        canvas.drawRect(leftRectT, leftAreaPaint);  
//        canvas.drawRect(leftRectB, leftAreaPaint);  
          
    }  
     public float getbottom()
     {
    	 return ChooseArea.bottom;
    	
     }
     public float gettop()
     {
    	 return ChooseArea.top;
     }
     public float getleft()
     {
    	 return ChooseArea.left;
     }
     public float getright()
     {
    	 return  ChooseArea.right;
     }
    public void set_LeftArea_Alpha(){  
        leftRectL.set(dst.left, dst.top, ChooseArea.left, dst.bottom);  
        leftRectR.set(ChooseArea.right,dst.top,dst.right,dst.bottom);  
        leftRectT.set(ChooseArea.left, dst.top, ChooseArea.right, ChooseArea.top);  
        leftRectB.set(ChooseArea.left,ChooseArea.bottom,ChooseArea.right,dst.bottom);  
    }   }
