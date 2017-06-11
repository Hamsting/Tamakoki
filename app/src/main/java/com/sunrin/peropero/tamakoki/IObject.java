package com.sunrin.peropero.tamakoki;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import java.util.ArrayList;

/**
 * Created by ww on 2017-06-09.
 */

public class IObject
{
	public Matrix mat;
	public ArrayList<IObject> node;
	public IObject parent = null;
	protected Resources res;
	protected BitmapFactory.Options bmpOptions;



	public IObject()
	{
		mat = new Matrix();

		node = new ArrayList<>();
		res = AppManager.instance.res;
		bmpOptions = AppManager.instance.bmpOptions;
	}

	public void tick(float _eTime)
	{

	}

	public void draw(Canvas _canvas)
	{

	}

	public void addNode(IObject _node)
	{
		if (node == null)
			return;
		node.add(_node);
		_node.parent = this;
	}

	public void removeNode(IObject _node)
	{
		if (node == null)
			return;
		node.remove(_node);
		_node.parent = null;
	}

	public void removeNode(int _index)
	{
		IObject n = node.remove(_index);
		if (n != null)
			n.parent = null;
	}
}
