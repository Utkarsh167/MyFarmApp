package com.example.hp.farmapp.FarmData.FarmPackage;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hp.farmapp.CalendarPackage.LandingActivity;
import com.example.hp.farmapp.DataHandler.DataHandler;
import com.example.hp.farmapp.FarmData.FarmAddActivity;
import com.example.hp.farmapp.R;
import com.example.hp.farmapp.Utiltiy.SharedPreferencesMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditFarmActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String DEFAULT_LOCAL = "India";
    private static final String DEFAULT_LOCAL_STATE = "Madhya Pradesh";
    private static final String DEFAULT_LOCAL_CITY = "Indore";
    private static final String REGISTER_URL = "https://www.oswalcorns.com/my_farm/myfarmapp/index.php/farmApp/insert_new_farm";
    public static final String AREA = "area";
    public static final String SOILTYPE = "soilType";
    public static final String IRRIGATIONTYPE = "irrigationType";
    public static final String SEPCCOMMENT = "specComment";
    public static final String ADDL1 = "addL1";
    public static final String PERSONNUM = "person_num";
    public static final String USERNUM = "user_num";
    public static final String ADDL2 = "addL2";
    public static final String ADDL3 = "addL3";
    public static final String CITY = "city";
    public static final String STATE = "state";
    public static final String COUNTRY = "country";
    public static final String FARM_PET_NAME = "farm_pet_name";
    ProgressDialog progressDialog;

    EditText addl1farmadd, addl2farmadd, addl3farmadd, farmpetname,areafarmadd;
    EditText soiltypefarmadd, irrigationtypefarmadd, speccommentfarmadd;
    Spinner cityfarmadd, statefarmadd, citizenship, spin_soil_type;
    Button submitfarmadd;
    String Countryidonclick = "";
    Toolbar mActionBarToolbar;
    String stateidonclick = "";
    String strirrigationtype, strspeccmnt, straddl1, straddl2, steaddl3,strarea,strsoiltype,strsoiltypefromspinner;
    String strcity, strstate, strcountry, personnumfromfillprofile, usernumfrommainact, strfarmpetname;
    String[] soiltype = {"Select Soil Type", "Alluvial Soils", "Black Soils", "Red Soils", "Laterite Soils", "Mountain Soils", "Desert Soils", "Other"};
    Context context;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*Intent intent=new Intent(context,MainActivity.class);
        startActivity(intent);
        finish();*/
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_farm);

        context = this;
        addl1farmadd = (EditText) findViewById(R.id.addl1farmadd_edit);
        addl2farmadd = (EditText) findViewById(R.id.addl2farmadd_edit);
        addl3farmadd = (EditText) findViewById(R.id.addl3farmadd_edit);
        farmpetname = (EditText) findViewById(R.id.farm_pet_name_edit);
        cityfarmadd = (Spinner) findViewById(R.id.cityfarmadd_edit);
        statefarmadd = (Spinner) findViewById(R.id.satefarmadd_edit);
        citizenship = (Spinner) findViewById(R.id.countryfarmadd_edit);
        areafarmadd = (EditText) findViewById(R.id.areafarmadd_edit);
        spin_soil_type = (Spinner) findViewById(R.id.spinner_soil_type_edit);
        soiltypefarmadd = (EditText) findViewById(R.id.soiltypefarmadd_edit);
        soiltypefarmadd.setVisibility(View.GONE);
        irrigationtypefarmadd = (EditText) findViewById(R.id.irrigationtypefarmadd_edit);
        speccommentfarmadd = (EditText) findViewById(R.id.speccommentfarmadd_edit);
        submitfarmadd = (Button) findViewById(R.id.savefarmadd_edit);
        spin_soil_type.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, soiltype);
        aa.setDropDownViewResource(R.layout.spinner_item_new_black_text);
        spin_soil_type.setAdapter(aa);




        straddl1= DataHandler.newInstance().getFarmaddaddl1();
        straddl2= DataHandler.newInstance().getFarmaddaddl2();
        steaddl3= DataHandler.newInstance().getFarmaddaddl3();
        strfarmpetname= DataHandler.newInstance().getFarmaddpetname();
        strcity = DataHandler.newInstance().getFarmaddcity();
        strstate= DataHandler.newInstance().getFarmdaddstate();
        strcountry= DataHandler.newInstance().getFarmaddcountry();
        strarea= DataHandler.newInstance().getFarmaddarea();
        strspeccmnt= DataHandler.newInstance().getFarmaddspclcmnt();
        strirrigationtype= DataHandler.newInstance().getFarmaddirrigationtype();
        strsoiltype=DataHandler.newInstance().getFardmaddsoiltype();



        spin_soil_type.setSelection(aa.getPosition(strsoiltype));
        addl1farmadd.setText(straddl1);
        addl2farmadd.setText(straddl2);
        addl3farmadd.setText(steaddl3);
        speccommentfarmadd.setText(strspeccmnt);
        areafarmadd.setText(strarea);
        farmpetname.setText(strfarmpetname);
        irrigationtypefarmadd.setText(strirrigationtype);


        TextView title = (TextView) findViewById(R.id.tittle);
        title.setText("Farm Information");
        mActionBarToolbar = (Toolbar) findViewById(R.id.confirm_order_toolbar_layout);
        setSupportActionBar(mActionBarToolbar);


        if (getSupportActionBar() != null) {
          /*  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);*/
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        JSONObject objcount = null;
        try {
            objcount = new JSONObject(loadJSONFromAssetcountry(context));
            JSONArray m_jArrycount = objcount.getJSONArray("countries");
            final String[] newcountry = new String[m_jArrycount.length()];
            final String[] newcountryid = new String[m_jArrycount.length()];
            ArrayList<String> countries = new ArrayList<String>();

            for (int i = 0; i < m_jArrycount.length(); i++) {
                JSONObject jo_insidecount = m_jArrycount.getJSONObject(i);
                newcountry[i] = jo_insidecount.getString("name");
                newcountryid[i] = jo_insidecount.getString("id");
                countries.add(newcountry[i]);

            }

            //Collections.sort(countries, String.CASE_INSENSITIVE_ORDER);
            ArrayAdapter<String> countryadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, countries);
            citizenship.setAdapter(countryadapter);
            citizenship.setSelection(countryadapter.getPosition(DEFAULT_LOCAL));
            citizenship.setSelection(countryadapter.getPosition(strcountry));


            citizenship.setEnabled(false);


            citizenship.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    ArrayList<String> state = new ArrayList<String>();
                    Countryidonclick = newcountryid[position];
                    JSONObject objstate = null;
                    int cntryidcout = 0;

                    try {

                        objstate = new JSONObject(loadJSONFromAssetstate(context));
                        JSONArray m_jArrystate = objstate.getJSONArray("states");
                        String[] newstate = new String[m_jArrystate.length()];
                        String[] newstateid = new String[m_jArrystate.length()];
                        String[] newstatecountid = new String[m_jArrystate.length()];
                        for (int j = 0; j < m_jArrystate.length(); j++) {
                            JSONObject jo_insidestate = m_jArrystate.getJSONObject(j);
                            newstatecountid[j] = jo_insidestate.getString("country_id");
                            if (newstatecountid[j].equals(Countryidonclick)) {
                                newstate[j] = jo_insidestate.getString("name");
                                newstateid[j] = jo_insidestate.getString("id");
                                cntryidcout++;
                            } else {
                            }

                        }

                        final String[] finalstate = new String[cntryidcout];
                        final String[] finalstateid = new String[cntryidcout];

                        for (int k = 0, l = 0; k < m_jArrystate.length(); k++, l++) {
                            if (newstatecountid[k].equals(Countryidonclick)) {
                                finalstate[l] = newstate[k];
                                finalstateid[l] = newstateid[k];
                                state.add(finalstate[l]);
                            } else {
                                l--;
                            }

                        }

                        ArrayAdapter<String> stateadapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, state);
                        statefarmadd.setAdapter(stateadapter);
                        statefarmadd.setSelection(stateadapter.getPosition(DEFAULT_LOCAL_STATE));
                        statefarmadd.setSelection(stateadapter.getPosition(strstate));


                        //Toast.makeText(FarmAddActivity.this, Integer.toString(cntryidcout), Toast.LENGTH_SHORT).show();


                        statefarmadd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                ArrayList<String> city = new ArrayList<String>();

                                // Toast.makeText(FarmAddActivity.this, finalstate[position] + finalstateid[position], Toast.LENGTH_SHORT).show();


                                JSONObject objcity = null;
                                int stateidcount = 0;
                                stateidonclick = finalstateid[position];

                                try {
                                    objcity = new JSONObject(loadJSONFromAssetcity(context));
                                    JSONArray m_jArrycity = objcity.getJSONArray("cities");
                                    String[] newcitye = new String[m_jArrycity.length()];
                                    String[] newcityid = new String[m_jArrycity.length()];
                                    String[] newcitycountid = new String[m_jArrycity.length()];

                                    for (int m = 0; m < m_jArrycity.length(); m++) {
                                        JSONObject jo_insidecity = m_jArrycity.getJSONObject(m);
                                        newcitycountid[m] = jo_insidecity.getString("state_id");
                                        if (newcitycountid[m].equals(stateidonclick)) {
                                            newcitye[m] = jo_insidecity.getString("name");
                                            newcityid[m] = jo_insidecity.getString("id");
                                            stateidcount++;
                                        } else {
                                        }

                                    }

//                                    Toast.makeText(FarmAddActivity.this, Integer.toString(m_jArrycity.length()), Toast.LENGTH_SHORT).show();
                                    final String[] finalcity = new String[stateidcount];
                                    final String[] finalcityid = new String[stateidcount];

                                    for (int n = 0, o = 0; n < m_jArrycity.length(); n++, o++) {
                                        if (newcitycountid[n].equals(stateidonclick)) {
                                            finalcity[o] = newcitye[n];
                                            finalcityid[o] = newcityid[n];
                                            city.add(finalcity[o]);
                                        } else {
                                            o--;
                                        }

                                    }
                                    //Toast.makeText(FarmAddActivity.this, Integer.toString(stateidcount), Toast.LENGTH_SHORT).show();
                                    ArrayAdapter<String> cityadapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, city);
                                    cityfarmadd.setAdapter(cityadapter);
                                    cityfarmadd.setSelection(cityadapter.getPosition(DEFAULT_LOCAL_CITY));
                                    cityfarmadd.setSelection(cityadapter.getPosition(strcity));


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {


                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }

        submitfarmadd.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {


                strarea = areafarmadd.getText().toString().trim();
               /* strgpsc1=gpsc1farmadd.getText().toString().trim();
                strgpsc2=gpsc2farmadd.getText().toString().trim();
                strgpsc3=gpsc3farmadd.getText().toString().trim();
                strgpsc4=gpsc4farmadd.getText().toString().trim();
                strgpsc5=gpsc5farmadd.getText().toString().trim();
                strgpsc6=gpsc6farmadd.getText().toString().trim();*/
                strsoiltype = soiltypefarmadd.getText().toString().trim();
                strirrigationtype = irrigationtypefarmadd.getText().toString().trim();
                strspeccmnt = speccommentfarmadd.getText().toString().trim();
                straddl1 = addl1farmadd.getText().toString().trim();
                straddl2 = addl2farmadd.getText().toString().trim();
                steaddl3 = addl3farmadd.getText().toString().trim();
                strcity = cityfarmadd.getSelectedItem().toString();
                strcountry = citizenship.getSelectedItem().toString();
                strstate = statefarmadd.getSelectedItem().toString();

                if (straddl1.matches("")) {
                    addl1farmadd.setError("Address cannot be null");
                } else if (strarea.matches("")) {
                    areafarmadd.setError("Area can't be null");
                }  else if (strirrigationtype.matches("")) {
                    irrigationtypefarmadd.setError("Irrigationtype can't be null");
                } else {
                    if (spin_soil_type.getSelectedItem().toString().equals("Other")) {
                        Log.e("Tag", "came here in Other category");
                        if (strsoiltype.matches("")) {
                            soiltypefarmadd.setError("Soiltype can't be null");
                        } else {
                            Log.e("Tag", "came here in Other category in if else");
                        }
                    }

                    progressDialog = ProgressDialog.show(EditFarmActivity.this,
                            "Please Wait...", "");
                    try {
                        GetText();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }




    public String loadJSONFromAssetcountry(Context mcontext) {
        String json = null;
        try {
            InputStream is = mcontext.getAssets().open("countries.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public String loadJSONFromAssetstate(Context mcontext) {
        String json = null;
        try {
            InputStream is = mcontext.getAssets().open("states.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public String loadJSONFromAssetcity(Context mcontext) {
        String json = null;
        try {
            InputStream is = mcontext.getAssets().open("cities.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void GetText() throws JSONException {
        // Get user defined values
        strarea = areafarmadd.getText().toString().trim();
       /* strgpsc1=gpsc1farmadd.getText().toString().trim();
        strgpsc2=gpsc2farmadd.getText().toString().trim();
        strgpsc3=gpsc3farmadd.getText().toString().trim();
        strgpsc4=gpsc4farmadd.getText().toString().trim();
        strgpsc5=gpsc5farmadd.getText().toString().trim();
        strgpsc6=gpsc6farmadd.getText().toString().trim();*/
        strsoiltype = soiltypefarmadd.getText().toString().trim();
        strirrigationtype = irrigationtypefarmadd.getText().toString().trim();
        strspeccmnt = speccommentfarmadd.getText().toString().trim();
        straddl1 = addl1farmadd.getText().toString().trim();
        straddl2 = addl2farmadd.getText().toString().trim();
        steaddl3 = addl3farmadd.getText().toString().trim();
        strcity = cityfarmadd.getSelectedItem().toString();
        strcountry = citizenship.getSelectedItem().toString();
        strstate = statefarmadd.getSelectedItem().toString();
        strfarmpetname = farmpetname.getText().toString().trim();
        strsoiltypefromspinner = spin_soil_type.getSelectedItem().toString().trim();




        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.equals("\"Successfully added Farm and Address\"")) {
                            progressDialog.dismiss();
                            Toast.makeText(EditFarmActivity.this, response, Toast.LENGTH_LONG).show();
                            SharedPreferencesMethod.setBoolean(context, "Login", true);
                            Intent intent = new Intent(context, LandingActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Log.e(TAG,error.toString());
                        progressDialog.dismiss();
                        Toast.makeText(EditFarmActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
               /* params.put(GPSC1,sfrstnamefill);
                params.put(GPSC2, smiddlenamefill);
                params.put(GPSC3, slastnamefill);
                params.put(GPSC4, sadharnofill);
                params.put(GPSC5, spannofill);
                params.put(GPSC6, sdobfill);*/
                if (strsoiltypefromspinner.equals("Other")) {
                    params.put(SOILTYPE, strsoiltype);
                } else {
                    params.put(SOILTYPE, strsoiltypefromspinner);
                }
                params.put(IRRIGATIONTYPE, strirrigationtype);
                params.put(SEPCCOMMENT, strspeccmnt);
                params.put(ADDL1, straddl1);
                params.put(ADDL2, straddl2);
                params.put(ADDL3, steaddl3);
                params.put(AREA, strarea);
                params.put(COUNTRY, strcountry);
                params.put(STATE, strstate);
                params.put(CITY, strcity);
                params.put(PERSONNUM, personnumfromfillprofile);
                params.put(USERNUM, usernumfrommainact);
                params.put(FARM_PET_NAME, strfarmpetname);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
