package com.pieropan.julien.bouncingball.activities;

import java.util.List;

import com.pieropan.julien.bouncingball.listview.MapRow;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MapRowAdaptater extends BaseAdapter {

	// FIELDS
	
	private List<MapRow> items = null;
	private LayoutInflater inflater;
	
	private Typeface font = null;
	
	// METHODS
	
	public MapRowAdaptater(Context context, List<MapRow> items)
	{
		this.inflater = LayoutInflater.from(context);
		this.items = items;
		
		// Chargement de la police de caractère
		font = Typeface.createFromAsset(context.getAssets(), "font/Churli_cute5.ttf");
	}
	
	@Override
	public int getCount() {
		return this.items.size();
	}

	@Override
	public Object getItem(int position) {
		return this.items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.map_item, null);
			
			holder.tvName = (TextView)convertView.findViewById(R.id.tvName);
			holder.tvScore = (TextView)convertView.findViewById(R.id.tvScore);
			
			holder.tvName.setTypeface(font);
			holder.tvScore.setTypeface(font);

			convertView.setTag(holder);

			} else {

			holder = (ViewHolder) convertView.getTag();

			}

		holder.tvName.setText(items.get(position).getMapName());
		holder.tvScore.setText(items.get(position).getMapHighScore());
		return convertView;
	}
	
	// ANONYME
	
	private class ViewHolder {
		TextView tvName;
		TextView tvScore;
	}

}
