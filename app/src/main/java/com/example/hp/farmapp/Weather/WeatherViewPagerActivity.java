package com.example.hp.farmapp.Weather;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hp.farmapp.CalendarPackage.LandingActivity;
import com.example.hp.farmapp.R;
import com.example.hp.farmapp.Weather.WeatherFragments.Weather_firstFragment;
import com.example.hp.farmapp.Weather.WeatherFragments.Weather_secondFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class WeatherViewPagerActivity extends AppCompatActivity{

    private static final String REGISTER_URL_ALL = "http://api.wunderground.com/api/a10b55e3e39e060a/conditions/forecast/alert/q/23.1607078,75.78408.json";
    Context context;
    ViewPager pager;
    String highstr,lowstr;
    ProgressDialog progressDialog;
    String[] hightemparr,lowtemparr,dayarr,conditionarr,iconarr,daynumarr,montharr,yeararr;
    String windstringtosend,tempinceltosend,relhumiditytosend,observaiontime_tosend,citystatecountry;
    Toolbar mActionBarToolbar;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*Intent intent=new Intent(context,LandingActivity.class);
        startActivity(intent);
        finish();*/
        super.onBackPressed();
        return super.onOptionsItemSelected(item);
    }
    public void onBackPressed() {
        /*Intent intent=new Intent(context,LandingActivity.class);
        startActivity(intent);
        finish();*/
        super.onBackPressed();}
    @Override
        protected void onCreate(Bundle savedInstanceState) {
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_weather_view_pager);
            context=this;


        TextView title=(TextView)findViewById(R.id.tittle);
        title.setText("Weather Report");
        mActionBarToolbar = (Toolbar) findViewById(R.id.confirm_order_toolbar_layout);
        setSupportActionBar(mActionBarToolbar);

//        getSupportActionBar().setTitle("My Title");

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        progressDialog = ProgressDialog.show(WeatherViewPagerActivity.this,
                getString(R.string.dialog_please_wait),"");
        AsyncTaskRunner runner=new AsyncTaskRunner();
        runner.execute();
            /*PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
            // Attach the view pager to the tab strip
            tabsStrip.setViewPager(pager);*/
        }

        private class MyPagerAdapter extends FragmentPagerAdapter {

            final int PAGE_COUNT = 3;
            private String tabTitles[] = new String[] { "Tab1", "Tab2","Tab3","Tab4" };

            public MyPagerAdapter(FragmentManager fm) {
                super(fm);
            }

            @Override
            public Fragment getItem(int pos) {
                tabTitles= new String[]{"Today", "Tommorow", dayarr[2], dayarr[3]};
                switch(pos) {
                    case 0: return Weather_firstFragment.newInstance(hightemparr[0],lowtemparr[0],dayarr[0],conditionarr[0],iconarr[0],windstringtosend,tempinceltosend,relhumiditytosend,citystatecountry,observaiontime_tosend,pos+1,daynumarr[0],montharr[0],yeararr[0]);
                    case 1: return Weather_secondFragment.newInstance(hightemparr[1],lowtemparr[1],dayarr[1],conditionarr[1],iconarr[1],citystatecountry,observaiontime_tosend,pos+1,daynumarr[1],montharr[1],yeararr[1]);
                    case 2: return Weather_secondFragment.newInstance(hightemparr[2],lowtemparr[2],dayarr[2],conditionarr[2],iconarr[2],citystatecountry,observaiontime_tosend,pos+1,daynumarr[2],montharr[2],yeararr[2]);
                    case 3: return Weather_secondFragment.newInstance(hightemparr[3],lowtemparr[3],dayarr[3],conditionarr[3],iconarr[3],citystatecountry,observaiontime_tosend,pos+1,daynumarr[3],montharr[3],yeararr[3]);
                    /*case 2: return Wheather_thirdFragment.newInstance("ThirdFragment, Instance 1");
                    case 3: return Wheather_fourthFragment.newInstance("FourthFragment, Instance 2");
                    case 4: return Wheather_fifthFragment.newInstance("FifthFragment, Instance 3");
                    case 5: return Wheather_sixthFragment.newInstance("SixthFragment, Instance 3");
                    case 6: return Wheather_seventhFragment.newInstance("SixthFragment, Instance 3");*/
                    default: return Weather_firstFragment.newInstance(hightemparr[0],lowtemparr[0],dayarr[0],conditionarr[0],iconarr[0],windstringtosend,tempinceltosend,relhumiditytosend,citystatecountry,observaiontime_tosend,pos+1,dayarr[0],montharr[0],yeararr[0]);


                }
            }

            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                // Generate title based on item position
                return tabTitles[position];
            }
        }



    private class AsyncTaskRunner extends AsyncTask<String, Void, String> {
        public AsyncTaskRunner() {
            super();
        }


        @Override
        protected String doInBackground(String... params) {



            StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL_ALL,
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //Log.e("RESPONSE",response);

                            JSONObject forecast = null;
                            try {
                                forecast = new JSONObject(response);
                                String forcaststr = forecast.getString("forecast");

                                String current_observation = forecast.getString("current_observation");

                                JSONObject temp_c=null;
                                temp_c=new JSONObject(current_observation);
                                String display_location=temp_c.getString("display_location");

                                JSONObject display_locationobj=null;
                                display_locationobj=new JSONObject(display_location);
                                String city=display_locationobj.getString("city");
                                String state=display_locationobj.getString("state");
                                String country=display_locationobj.getString("state_name");

                                String temp_cstr=temp_c.getString("temp_c");
                                String observation_time=temp_c.getString("observation_time");
                                String relative_humiditystr=temp_c.getString("relative_humidity");
                                String wind_stringstr=temp_c.getString("wind_string");


                                citystatecountry=city+" ,"+state+" ,"+country;
                                windstringtosend=wind_stringstr;
                                relhumiditytosend=relative_humiditystr;
                                tempinceltosend=temp_cstr;
                                observaiontime_tosend=observation_time;



                            /*    JSONObject txt_forecast=null;
                                txt_forecast=new JSONObject(forcaststr);
                                String txt_foreast=txt_forecast.getString("txt_forecast");

                                JSONObject forecastdaysimple=null;
                                forecastdaysimple=new JSONObject(txt_foreast);
                                String forecastsimplestr=forecastdaysimple.getString("forecastday");


                                JSONArray forecastdayarray = null;
                                forecastdayarray = new JSONArray(forecastsimplestr);

                                JSONObject icon_url=null;
                                JSONObject title=null;

                                for (int i = 0; i < forecastdayarray.length(); i++) {
                                    JSONObject jsonobject = forecastdayarray.getJSONObject(i);
                                    String parametername = jsonobject.getString("high");
                                    high=new JSONObject(parametername);
                                    highstr=high.getString("celsius");

                                    String parametervalue=jsonobject.getString("low");
                                    low=new JSONObject(parametervalue);
                                    lowstr=low.getString("celsius");
                                    // String activitydescription=jsonobject.getString("activity_description");

                                    *//*parameternamearray[i]=parametername;
                                    parametervaluearray[i]=parametervalue;*//*
                                    // activitydescriptionfarmal[i]=activitydescription;
                                    Log.e("Date :",highstr+"Parameter value"+lowstr);
                                }
*/


                                JSONObject simpleforecast=null;
                                simpleforecast=new JSONObject(forcaststr);
                                String simpleforecastStr=simpleforecast.getString("simpleforecast");
                                // weather=custom_location.getString("weather");
                                //Log.e("TAG",txt_foreast);

                                JSONObject forecast_day=null;
                                forecast_day=new JSONObject(simpleforecastStr);
                                String forecast_daystr=forecast_day.getString("forecastday");

                                JSONArray jsonarray = null;
                                jsonarray = new JSONArray(forecast_daystr);

                                JSONObject high=null;
                                JSONObject low=null;
                                JSONObject date=null;



                                 hightemparr=new String[jsonarray.length()];
                                 lowtemparr=new String[jsonarray.length()];
                                 dayarr=new String[jsonarray.length()];
                                 conditionarr =new String[jsonarray.length()];
                                 iconarr=new String[jsonarray.length()];
                                 daynumarr=new String[jsonarray.length()];
                                 montharr=new String[jsonarray.length()];
                                 yeararr=new String[jsonarray.length()];


                                for (int i = 0; i < jsonarray.length(); i++) {
                                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                                    String parametername = jsonobject.getString("high");
                                    high=new JSONObject(parametername);
                                     highstr=high.getString("celsius");

                                     hightemparr[i]=highstr;

                                    String parametervalue=jsonobject.getString("low");
                                    low=new JSONObject(parametervalue);
                                     lowstr=low.getString("celsius");

                                    lowtemparr[i]=lowstr;

                                     String datestr=jsonobject.getString("date");
                                     JSONObject weekday=new JSONObject(datestr);
                                     String weekdaystr=weekday.getString("weekday");
                                     String daynum=weekday.getString("day");
                                     String month=weekday.getString("month");
                                    String year=weekday.getString("year");

                                    dayarr[i]=weekdaystr;
                                    daynumarr[i]=daynum;
                                    montharr[i]=month;
                                    yeararr[i]=year;
                                    String conditions = jsonobject.getString("conditions");
                                    conditionarr[i]=conditions;
                                    String icon_url=jsonobject.getString("icon_url");
                                    iconarr[i]=icon_url;



                                    // String activitydescription=jsonobject.getString("activity_description");

                                    /*parameternamearray[i]=parametername;
                                    parametervaluearray[i]=parametervalue;*/
                                    // activitydescriptionfarmal[i]=activitydescription;
                                 //   Log.e("Date :",highstr+"Parameter value"+lowstr+"  "+weekdaystr+" "+conditions+" "+icon_url+" "+wind_stringstr+" "+temp_cstr+" "+relative_humiditystr);
                                Log.e("TAG",city+state+country+" "+observation_time);
                                }

                                progressDialog.dismiss();

                                pager = (ViewPager) findViewById(R.id.viewPager);
                                pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

                            } catch (JSONException e) {
                                e.printStackTrace();
                                progressDialog.dismiss();
                            }

                        }
                    },
                    new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                        }
                    }){
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String, String>();

                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);

            return null;
        }


        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String s) {

        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

    }


}