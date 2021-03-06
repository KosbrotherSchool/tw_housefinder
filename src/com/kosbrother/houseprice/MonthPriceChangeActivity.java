package com.kosbrother.houseprice;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.kosbrother.houseprice.api.InfoParserApi;

public class MonthPriceChangeActivity extends Activity implements
		OnNavigationListener
{

	private LinearLayout monthItemLayout;
	private LayoutInflater mInflater;
	private TextView priceChangeText;

	private RelativeLayout adBannerLayout;
	private AdView adMobAdView;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_price_change);

		if (Datas.mEstatesMap == null)
		{
			Toast.makeText(MonthPriceChangeActivity.this, "無資料!",
					Toast.LENGTH_SHORT).show();
			finish();
		}

		monthItemLayout = (LinearLayout) findViewById(R.id.month_item_layout);
		mInflater = getLayoutInflater();
		priceChangeText = (TextView) findViewById(R.id.price_change_text);

		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		if (Build.VERSION.SDK_INT >= 14)
		{
			actionBar.setDisplayHomeAsUpEnabled(true);
			actionBar.setHomeButtonEnabled(true);
		}
		ArrayList<String> itemList = new ArrayList<String>();
		itemList.add("單價");
		itemList.add("總價");

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, itemList)
		{

			@Override
			public View getView(int position, View convertView, ViewGroup parent)
			{
				View view = super.getView(position, convertView, parent);

				TextView textView = (TextView) view
						.findViewById(android.R.id.text1);

				/* YOUR CHOICE OF COLOR */
				textView.setTextColor(Color.WHITE);

				return view;
			}

			@Override
			public View getDropDownView(int position, View convertView,
					ViewGroup parent)
			{
				View view = super.getView(position, convertView, parent);

				TextView textView = (TextView) view
						.findViewById(android.R.id.text1);

				/* YOUR CHOICE OF COLOR */
				textView.setTextColor(Color.WHITE);

				return view;
			}
		};

		actionBar.setListNavigationCallbacks(adapter, this);
		CallAds();

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case android.R.id.home:
			finish();
			return true;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId)
	{
		switch (itemPosition)
		{
		case 0:
			// Toast.makeText(MonthPriceChangeActivity.this, "0",
			// Toast.LENGTH_SHORT).show();
			setSquarePriceView();
			break;
		case 1:
			// Toast.makeText(MonthPriceChangeActivity.this, "1",
			// Toast.LENGTH_SHORT).show();
			setTotalPriceView();
			break;
		default:
			break;
		}
		return false;
	}

	private void setTotalPriceView()
	{
		monthItemLayout.removeAllViews();
		priceChangeText.setVisibility(View.GONE);
		for (int i = 0; i < Datas.mArrayKey.size(); i++)
		{
			View view = mInflater.inflate(R.layout.item_month_square_price,
					null);
			TextView monthYearTextView = (TextView) view
					.findViewById(R.id.price_month_year_text);
			TextView priceHighTextView = (TextView) view
					.findViewById(R.id.price_month_high_text);
			TextView priceAvgTextView = (TextView) view
					.findViewById(R.id.price_month_avg_text);
			TextView priceLowTextView = (TextView) view
					.findViewById(R.id.price_month_low_text);
			TextView priceChangeTextView = (TextView) view
					.findViewById(R.id.price_month_change_text);
			TextView priceQuantityTextView = (TextView) view
					.findViewById(R.id.price_month_quantity_text);

			String monthKey = Datas.getKeyByPosition(i);
			monthYearTextView.setText(InfoParserApi.parseMonthKey(monthKey));

			int hp1 = Datas.getMonthHighTotalPrice(monthKey);
			priceHighTextView.setText(Integer.toString(hp1) + "萬");

			int avg1 = Datas.getMonthAvgTotalPrice(monthKey);
			priceAvgTextView.setText(Integer.toString(avg1) + "萬");

			int lp1 = Datas.getMonthLowTotalPrice(monthKey);
			priceLowTextView.setText(Integer.toString(lp1) + "萬");

			int num1 = Datas.getMonthEstatesNum(monthKey);
			priceQuantityTextView.setText(Integer.toString(num1));

			priceChangeTextView.setVisibility(View.GONE);

			monthItemLayout.addView(view);
		}

	}

	private void setSquarePriceView()
	{
		monthItemLayout.removeAllViews();
		priceChangeText.setVisibility(View.VISIBLE);
		for (int i = 0; i < Datas.mArrayKey.size(); i++)
		{
			View view = mInflater.inflate(R.layout.item_month_square_price,
					null);
			TextView monthYearTextView = (TextView) view
					.findViewById(R.id.price_month_year_text);
			TextView priceHighTextView = (TextView) view
					.findViewById(R.id.price_month_high_text);
			TextView priceAvgTextView = (TextView) view
					.findViewById(R.id.price_month_avg_text);
			TextView priceLowTextView = (TextView) view
					.findViewById(R.id.price_month_low_text);
			TextView priceChangeTextView = (TextView) view
					.findViewById(R.id.price_month_change_text);
			TextView priceQuantityTextView = (TextView) view
					.findViewById(R.id.price_month_quantity_text);

			String monthKey = Datas.getKeyByPosition(i);
			monthYearTextView.setText(InfoParserApi.parseMonthKey(monthKey));

			double hp = Datas.getMonthHighSquarePrice(monthKey);
			priceHighTextView.setText(InfoParserApi.parseSquarePrice(hp));

			double avg = Datas.getMonthAvgSquarePrice(monthKey);
			priceAvgTextView.setText(InfoParserApi.parseSquarePrice(avg));

			try
			{
				String monthKey2 = Datas.getKeyByPosition(i - 1);
				double priceChange1 = Datas.getSquarePriceChange(monthKey,
						monthKey2);
				priceChangeTextView.setText(InfoParserApi
						.parsePriceChangePercent(priceChange1));
			} catch (Exception e)
			{
				priceChangeTextView.setText("~");
			}

			double lp = Datas.getMonthLowSquarePrice(monthKey);
			priceLowTextView.setText(InfoParserApi.parseSquarePrice(lp));

			int num = Datas.getMonthEstatesNum(monthKey);
			priceQuantityTextView.setText(Integer.toString(num));

			monthItemLayout.addView(view);
		}

	}

	@Override
	public void onStart()
	{
		super.onStart();
		EasyTracker.getInstance(this).activityStart(this); // Add this method.
	}

	@Override
	public void onStop()
	{
		super.onStop();
		EasyTracker.getInstance(this).activityStop(this); // Add this method.
	}

	private void CallAds()
	{
		boolean isGivenStar = Setting.getBooleanSetting(Setting.KeyGiveStar,
				MonthPriceChangeActivity.this);

		if (!isGivenStar)
		{
			adBannerLayout = (RelativeLayout) findViewById(R.id.adLayout);
			final AdRequest adReq = new AdRequest.Builder().build();

			// 12-18 17:01:12.438: I/Ads(8252): Use
			// AdRequest.Builder.addTestDevice("A25819A64B56C65500038B8A9E7C19DD")
			// to get test ads on this device.

			adMobAdView = new AdView(MonthPriceChangeActivity.this);
			adMobAdView.setAdSize(AdSize.SMART_BANNER);
			adMobAdView.setAdUnitId(AppConstants.MEDIATION_KEY);

			adMobAdView.loadAd(adReq);
			adMobAdView.setAdListener(new AdListener()
			{
				@Override
				public void onAdLoaded()
				{
					adBannerLayout.setVisibility(View.VISIBLE);
					if (adBannerLayout.getChildAt(0) != null)
					{
						adBannerLayout.removeViewAt(0);
					}
					adBannerLayout.addView(adMobAdView);
				}

				public void onAdFailedToLoad(int errorCode)
				{
					adBannerLayout.setVisibility(View.GONE);
				}

			});
		}
	}

}
