package sample.daniel.ecomsample.ui;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import sample.daniel.ecomsample.app.R;


public class AspectRatioImageView extends ImageView {
	
	private int mWidthRatio;
	private int mHeightRatio;
	
	public AspectRatioImageView(Context context)
	{
	    super(context);
	}

	public AspectRatioImageView(Context context, AttributeSet attrs)
	{
	    super(context, attrs);
	    
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.AspectRatioImageView, 0, 0);

		try
		{
			mWidthRatio = a.getInteger(R.styleable.AspectRatioImageView_widthAspect, 0);
			mHeightRatio = a.getInteger(R.styleable.AspectRatioImageView_heightAspect, 0);
		}
		finally
		{
			a.recycle();
		}
	}

	public AspectRatioImageView(Context context, AttributeSet attrs, int defStyle)
	{
	    super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
	    Drawable drawable = getDrawable();
	    if (drawable != null)
	    {
	        int width =  MeasureSpec.getSize(widthMeasureSpec);
	        int height = width * mHeightRatio / mWidthRatio;
	        setMeasuredDimension(width, height);
	    }
	    else
	    {
	    	super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	    }
	}
	
	
}
