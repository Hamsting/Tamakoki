package com.sunrin.peropero.tamakoki;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import java.util.ArrayList;

/**
 * Created by Hamsting on 2017-06-09.
 * IScene, 또는 IObject의 자식관계로 있을 수 있는 기본 오브젝트이며,
 * 이 클래스를 상속한 클래스에서 생성자와 tick()과 draw()를 재정의해서 사용한다.
 */

public class IObject
{
	public boolean remove;
	public ArrayList<IObject> node;
	public IObject parent = null;
	protected Resources res;
	protected BitmapFactory.Options bmpOptions;



	public IObject()
	{
		remove = false;
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

	public void setRemove()
	{
		remove = true;
	}

	public void setRemove(boolean _remove)
	{
		remove = _remove;
	}
}
