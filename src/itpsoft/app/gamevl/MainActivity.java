package itpsoft.app.gamevl;

import itpsoft.app.gamevl.database.DatabaseHandler;
import itpsoft.app.gamevl.entity.BoDeEntity;
import itpsoft.app.gamevl.entity.chonDapAnEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private LinearLayout m_main;

	TextView tvChonCongThuc, tvVietCongThuc, tvCongThuc, tvCaiDat, tvDiemCao,
			tvGioiThieu;
	private String preName = "my_data";
	SharedPreferences pre;
	DatabaseHandler db;
	String myjsonstring;

	// json BoDe

	private static final String TAG_BODE = "bode";
	private static final String TAG_BODE_MABODE = "MaBoDe";
	private static final String TAG_BODE_TENDODE = "TenBoDe";

	// json ChonDapAn

	private static final String TAG_CHONDAPAN = "ChonDapAn";
	private static final String TAG_CHONDAPAN_MACH = "MaCH";
	private static final String TAG_CHONDAPAN_MABODE = "MaBoDe";
	private static final String TAG_CHONDAPAN_CAUHOI = "CauHoi";
	private static final String TAG_CHONDAPAN_DAPANDUNG = "DapAnDung";
	private static final String TAG_CHONDAPAN_DAPANSAI1 = "DapAnSai1";
	private static final String TAG_CHONDAPAN_DAPANSAI2 = "DapAnSai2";
	private static final String TAG_CHONDAPAN_DAPANSAI3 = "DapAnSai3";
	private static final String TAG_CHONDAPAN_MOTA = "MoTa";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
		readData();
		actionEvent();
	}

	public void init() {
		tvChonCongThuc = (TextView) findViewById(R.id.tvChonCongThuc);
		tvVietCongThuc = (TextView) findViewById(R.id.tvVietCongThuc);
		tvCongThuc = (TextView) findViewById(R.id.tvCongThuc);
		tvDiemCao = (TextView) findViewById(R.id.tvDiemCao);
		tvCaiDat = (TextView) findViewById(R.id.tvCaiDat);
		tvGioiThieu = (TextView) findViewById(R.id.tvGioiThieu);

		pre = getSharedPreferences(preName, MODE_PRIVATE);

	}

	public void actionEvent() {
		tvChonCongThuc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, chonDapAn.class);
				startActivity(intent);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				

			}
		});

		tvVietCongThuc.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// code here
				final Dialog dialog = new Dialog(MainActivity.this,
						R.style.Dialog_No_Border);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				LayoutInflater inflater = LayoutInflater
						.from(MainActivity.this);
				View view = inflater.inflate(R.layout.chon_game, null);
				m_main = (LinearLayout) view.findViewById(R.id.chonGame);
				m_main.setBackgroundResource(R.drawable.style_border_dialog);
				ImageView imvClose = (ImageView) view
						.findViewById(R.id.imvCloseChonGame);
				imvClose.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog.dismiss();
						dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
					}
				});
				dialog.setContentView(view);
				dialog.show();

				dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;

			}
		});

		// su kien khi nhan vao nut Cong thuc

		tvCongThuc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DatabaseHandler db = new DatabaseHandler(
						getApplicationContext());
				ArrayList<BoDeEntity> arrBode = db.getAllBoDe();

				Toast.makeText(getApplicationContext(),
						arrBode.get(1).getTenBoDe(), Toast.LENGTH_LONG).show();
				// TODO Auto-generated method stub

			}
		});

		// su kien khi nhan vao nut cai dat
		tvCaiDat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		// su kien khi nhan vao nut diem cao
		tvDiemCao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		// su kien khi nhan vao nut gioi thieu
		tvGioiThieu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
	}

	public void readData() {
		boolean checkreadData = pre.getBoolean("checkData", true);
		if (checkreadData) {
			readJSONBoDe();
			readJSONChonDapAn();

			SharedPreferences.Editor editor = pre.edit();

			editor.putBoolean("checkData", false);
			editor.commit();
		}
	}

	public void readJSONBoDe() {
		// doc file tu foder assets
		StringBuffer sb = new StringBuffer();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(getAssets().open(
					"bode.txt")));
			String temp;
			while ((temp = br.readLine()) != null) {
				sb.append(temp);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				br.close(); // dung doc
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		myjsonstring = sb.toString();

		try {
			// tao doi tuong JSON
			JSONObject jsonObjMain = new JSONObject(myjsonstring);
			// tao mang tu JSONObj
			JSONArray jsonArray = jsonObjMain.getJSONArray(TAG_BODE);

			for (int i = 0; i < jsonArray.length(); i++) {
				// lay ra mot doi tuong trong mang
				JSONObject jsonObj = jsonArray.getJSONObject(i);
				// Log.e("chay qua", "i");
				// lay du lieu
				String MaBoDe = jsonObj.getString(TAG_BODE_MABODE);
				String TenBoDe = jsonObj.getString(TAG_BODE_TENDODE);

				BoDeEntity bd = new BoDeEntity();

				bd.setMaBoDe(MaBoDe);
				bd.setTenBoDe(TenBoDe);

				db = new DatabaseHandler(this);
				int id = db.addBoDe(bd);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void readJSONChonDapAn() {
		// doc file tu foder assets
		StringBuffer sb = new StringBuffer();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(getAssets().open(
					"ChonDapAn.txt")));
			String temp;
			while ((temp = br.readLine()) != null) {
				sb.append(temp);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				br.close(); // dung doc
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		myjsonstring = sb.toString();

		try {
			// tao doi tuong JSON
			JSONObject jsonObjMain = new JSONObject(myjsonstring);
			// tao mang tu JSONObj
			JSONArray jsonArray = jsonObjMain.getJSONArray(TAG_CHONDAPAN);

			for (int i = 0; i < jsonArray.length(); i++) {
				// lay ra mot doi tuong trong mang
				JSONObject jsonObj = jsonArray.getJSONObject(i);
				// Log.e("chay qua", "i");
				// lay du lieu
				String MaCH = jsonObj.getString(TAG_CHONDAPAN_MACH);
				String MaBoDe = jsonObj.getString(TAG_CHONDAPAN_MABODE);
				String CauHoi = jsonObj.getString(TAG_CHONDAPAN_CAUHOI);
				String DapAnDung = jsonObj.getString(TAG_CHONDAPAN_DAPANDUNG);
				String DapAnSai1 = jsonObj.getString(TAG_CHONDAPAN_DAPANSAI1);
				String DapAnSai2 = jsonObj.getString(TAG_CHONDAPAN_DAPANSAI2);
				String DapAnSai3 = jsonObj.getString(TAG_CHONDAPAN_DAPANSAI3);
				String MoTa = jsonObj.getString(TAG_CHONDAPAN_MOTA);

				chonDapAnEntity ch = new chonDapAnEntity();
				ch.setMaCH(MaCH);
				ch.setMaBoDe(MaBoDe);
				ch.setCauHoi(CauHoi);
				ch.setDapAnDung(DapAnDung);
				ch.setDapAnSai1(DapAnSai1);
				ch.setDapAnSai2(DapAnSai2);
				ch.setDapAnSai3(DapAnSai3);
				ch.setMoTa(MoTa);

				db = new DatabaseHandler(this);
				int id = db.addchChonDapAn(ch);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
