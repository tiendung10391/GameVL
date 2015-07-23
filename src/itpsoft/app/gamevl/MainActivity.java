package itpsoft.app.gamevl;

import itpsoft.app.gamevl.database.DatabaseHandler;
import itpsoft.app.gamevl.entity.BoDeEntity;
import itpsoft.app.gamevl.entity.chonDapAnEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends Activity {
	private LinearLayout m_main;

	TextView tvChonCongThuc, tvLyThuyet, tvThachDau, tvCaiDat, tvDiemCao,
			tvGioiThieu, tvTongHopCT;
	private Spinner spBoDe;
	private String preName = "my_data";
	SharedPreferences pre;
	DatabaseHandler db;
	String myjsonstring;

	private SeekBar sbMusic, sbSound;
	private MediaPlayer mpMusic;
	private AudioManager amMusic;
	private int VolumnMusic = 0;
	private int VolumnSound = 0;
	private ToggleButton swMusic, swSound;
	private boolean checkMusic = true;
	private boolean checkSound = true;
	private int curVolume = 0;
	private int idBoDe = 0;
	private boolean checkChoose = false;

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
		playMusic();
		readData();
		actionEvent();

	}

	public void init() {
		tvChonCongThuc = (TextView) findViewById(R.id.tvChonCongThuc);
		tvLyThuyet = (TextView) findViewById(R.id.tvVietCongThuc);
		tvThachDau = (TextView) findViewById(R.id.tvThachDau);
		tvDiemCao = (TextView) findViewById(R.id.tvDiemCao);
		tvCaiDat = (TextView) findViewById(R.id.tvCaiDat);
		tvGioiThieu = (TextView) findViewById(R.id.tvGioiThieu);
		tvTongHopCT = (TextView) findViewById(R.id.tvTongHopCT);

		pre = getSharedPreferences(preName, MODE_PRIVATE);
		
		
		AudioManager am = (AudioManager) getSystemService(AUDIO_SERVICE);
		int volume_level = am.getStreamVolume(AudioManager.STREAM_MUSIC);
		
		
		SharedPreferences.Editor editor = pre.edit();
		editor.putInt("VoluomnHeThong", volume_level);
		editor.commit();
	}

	public void playMusic() {

		mpMusic = MediaPlayer.create(this, R.raw.music);
		amMusic = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		mpMusic.setLooping(true);
		mpMusic.start();
	}

	public void actionEvent() {
		tvChonCongThuc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				checkChoose = pre.getBoolean("checkChon", false);
				if (checkChoose) {
					Intent intent = new Intent(MainActivity.this,
							chonDapAn.class);
					startActivity(intent);
					overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

				} else {
					// code here
					final Dialog dialog = new Dialog(MainActivity.this,
							R.style.Dialog_No_Border);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					LayoutInflater inflater = LayoutInflater
							.from(MainActivity.this);
					View view = inflater.inflate(R.layout.chon_bo_de, null);
					m_main = (LinearLayout) view.findViewById(R.id.chonBoDe);
					m_main.setBackgroundResource(R.drawable.style_border_dialog);
					ImageView imvClose = (ImageView) view
							.findViewById(R.id.imvCloseChonBoDe);
					imvClose.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							dialog.dismiss();
							dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
						}
					});
					TextView tvLop10 = (TextView) view
							.findViewById(R.id.tvLop10);
					TextView tvLop11 = (TextView) view
							.findViewById(R.id.tvLop11);
					TextView tvLop12 = (TextView) view
							.findViewById(R.id.tvLop12);
					TextView tvTongHop = (TextView) view
							.findViewById(R.id.tvTongHop);

					tvLop10.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							SharedPreferences.Editor editor = pre.edit();
							editor.putInt("idDe", 0);
							editor.putBoolean("checkChon", true);
							editor.commit();
							Intent intent = new Intent(MainActivity.this,
									chonDapAn.class);
							startActivity(intent);
							overridePendingTransition(R.anim.fade_in,
									R.anim.fade_out);
							dialog.dismiss();
						}
					});

					tvLop11.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							SharedPreferences.Editor editor = pre.edit();
							editor.putInt("idDe", 1);
							editor.putBoolean("checkChon", true);
							editor.commit();
							Intent intent = new Intent(MainActivity.this,
									chonDapAn.class);
							startActivity(intent);
							overridePendingTransition(R.anim.fade_in,
									R.anim.fade_out);
							dialog.dismiss();
						}
					});

					tvLop12.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							SharedPreferences.Editor editor = pre.edit();
							editor.putInt("idDe", 2);
							editor.putBoolean("checkChon", true);
							editor.commit();
							Intent intent = new Intent(MainActivity.this,
									chonDapAn.class);
							startActivity(intent);
							overridePendingTransition(R.anim.fade_in,
									R.anim.fade_out);
							dialog.dismiss();
						}
					});

					tvTongHop.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							SharedPreferences.Editor editor = pre.edit();
							editor.putInt("idDe", 3);
							editor.putBoolean("checkChon", true);
							editor.commit();
							Intent intent = new Intent(MainActivity.this,
									chonDapAn.class);
							startActivity(intent);
							overridePendingTransition(R.anim.fade_in,
									R.anim.fade_out);
							dialog.dismiss();
						}
					});

					dialog.setContentView(view);
					dialog.show();

					dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
				}

			}
		});

		tvLyThuyet.setOnClickListener(new OnClickListener() {

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

		// su kien khi nhan vao nut tong hop cong thuc
		tvTongHopCT.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// code vao day Tu nhe !!! day sang trang tong hop cong thuc cua em
				
			}
		});
		
		// su kien khi nhan vao nut thach dau

		tvThachDau.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(isInternetOn()){
					Intent intent = new Intent(MainActivity.this,
							loginFacebook.class);
					startActivity(intent);
					overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				}
				

			}
		});

		// su kien khi nhan vao nut cai dat
		tvCaiDat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Dialog dialog = new Dialog(MainActivity.this,
						R.style.Dialog_No_Border);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				LayoutInflater inflater = LayoutInflater
						.from(MainActivity.this);
				View view = inflater.inflate(R.layout.cai_dat, null);
				m_main = (LinearLayout) view.findViewById(R.id.caiDat);
				m_main.setBackgroundResource(R.drawable.style_border_dialog);
				ImageView imvClose = (ImageView) view
						.findViewById(R.id.imvCloseCaiDat);
				imvClose.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						SharedPreferences.Editor editor = pre.edit();
						editor.putInt("idDe", idBoDe);
						editor.commit();
						dialog.dismiss();
						dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
					}
				});

				// Spinner
				spBoDe = (Spinner) view.findViewById(R.id.spBoDe);
				List<String> bd = new ArrayList<String>();
				bd.add("Lớp 10");
				bd.add("Lớp 11");
				bd.add("Lớp 12");
				bd.add("Tổng hợp");
				ArrayAdapter<String> adapterList = new ArrayAdapter<String>(
						getApplicationContext(),
						android.R.layout.simple_spinner_item, bd);
				adapterList
						.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spBoDe.setAdapter(adapterList);

				spBoDe.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						idBoDe = position;
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub

					}
				});
				idBoDe = pre.getInt("idDe", 0);
				spBoDe.setSelection(idBoDe);

				// Music
				sbMusic = (SeekBar) view.findViewById(R.id.sBarMusic);
				swMusic = (ToggleButton) view
						.findViewById(R.id.toggleButtonMusic);

				checkMusic = pre.getBoolean("CheckMusic", true);
				swMusic.setChecked(checkMusic);
				final int maxVolume = amMusic
						.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

				int curVolume = pre.getInt("VolumnMusic", 10);

				swMusic.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {

						SharedPreferences.Editor editor = pre.edit();
						// check on off Music
						if (buttonView.isChecked()) {

							editor.putBoolean("CheckMusic", true);
							sbMusic.setProgress(8);

						} else {
							editor.putBoolean("CheckMusic", false);
							sbMusic.setProgress(0);
						}
						editor.commit();
					}
				});

				// kiem tra nut tat bat Music
				sbMusic.setMax(maxVolume);

				if (checkMusic) {
					sbMusic.setProgress(curVolume);
				} else {
					sbMusic.setProgress(0);
				}
				sbMusic.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {

					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub
					}

					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						amMusic.setStreamVolume(AudioManager.STREAM_MUSIC,
								progress, 0);
						VolumnMusic = progress;
						if (progress == 0) {
							swMusic.setChecked(false);
						} else {
							swMusic.setChecked(true);
						}
						SharedPreferences.Editor editor = pre.edit();
						editor.putInt("VolumnMusic", VolumnMusic);
						editor.commit();

					}
				});

				// Sound
				sbSound = (SeekBar) view.findViewById(R.id.sBarSound);
				swSound = (ToggleButton) view
						.findViewById(R.id.toggleButtonSound);

				checkSound = pre.getBoolean("CheckSound", true);
				swSound.setChecked(checkSound);
				final int maxVolumeSound = amMusic
						.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

				int curVolumeSound = pre.getInt("VolumnSound", 10);

				swSound.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {

						SharedPreferences.Editor editor = pre.edit();
						// check on off Music
						if (buttonView.isChecked()) {

							editor.putBoolean("CheckSound", true);
							sbSound.setProgress(10);

						} else {
							editor.putBoolean("CheckSound", false);
							sbSound.setProgress(0);
						}
						editor.commit();
					}
				});

				// am luong volumn
				sbSound.setMax(maxVolumeSound);

				if (checkSound) {
					sbSound.setProgress(curVolumeSound);
				} else {
					sbSound.setProgress(0);
				}
				sbSound.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {

					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub
					}

					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						// amMusic.setStreamVolume(AudioManager.STREAM_MUSIC,
						// progress, 0);
						if (progress == 0) {
							swSound.setChecked(false);
						} else {
							swSound.setChecked(true);
						}
						SharedPreferences.Editor editor = pre.edit();
						editor.putInt("VolumnSound", progress);
						editor.commit();

					}
				});
				dialog.setContentView(view);
				dialog.show();

				dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;

			}
		});

		// su kien khi nhan vao nut diem cao
		tvDiemCao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Dialog dialog = new Dialog(MainActivity.this,
						R.style.Dialog_No_Border);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				LayoutInflater inflater = LayoutInflater
						.from(MainActivity.this);
				View view = inflater.inflate(R.layout.diem_cao, null);
				m_main = (LinearLayout) view.findViewById(R.id.diemCao);
				m_main.setBackgroundResource(R.drawable.style_border_dialog);
				ImageView imvClose = (ImageView) view
						.findViewById(R.id.imvCloseDiemCao);
				imvClose.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog.dismiss();
						dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
					}
				});

				TextView tvDiemCaoCongThuc = (TextView) view
						.findViewById(R.id.tvDiemCongThuc);
				TextView tvThoiGianCongThuc = (TextView) view
						.findViewById(R.id.tvThoiGianCongThuc);
				TextView tvDiemCaoLyThuyet = (TextView) view
						.findViewById(R.id.tvDiemLyThuyet);
				TextView tvThoiGianLyThuyet = (TextView) view
						.findViewById(R.id.tvThoiGianLyThuyet);

				int diemCaoCongThuc = pre.getInt("diemCT", 0);
				int thoiGianCongThuc = pre.getInt("thoigianCT", 0);
				String thoigian = "";
				if (thoiGianCongThuc > 60) {
					thoigian = String.format(
							"%dp %ds",
							TimeUnit.MILLISECONDS
									.toMinutes(thoiGianCongThuc * 1000),
							TimeUnit.MILLISECONDS
									.toSeconds(thoiGianCongThuc * 1000)
									- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
											.toMinutes(thoiGianCongThuc * 1000)));
				} else {
					thoigian = thoiGianCongThuc + " s";
				}
				tvDiemCaoCongThuc.setText(String.valueOf(diemCaoCongThuc));
				tvThoiGianCongThuc.setText(thoigian);

				dialog.setContentView(view);
				dialog.show();

				dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
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
	
	// kiem tra ket noi internet
	 public final boolean isInternetOn() {
         
	        // get Connectivity Manager object to check connection
	        ConnectivityManager connec =  
	                       (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
	         
	           // Check for network connections
	            if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
	                 connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
	                 connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
	                 connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {
	                
	                // if connected with internet
	                 
//	                Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();
	                return true;
	                 
	            } else if ( 
	              connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
	              connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {
	               
	                Toast.makeText(this, " không có kết nối internet ", Toast.LENGTH_LONG).show();
	                return false;
	            }
	          return false;
	        }

	protected void onPause() {
		super.onPause();

		mpMusic.pause();
		// mp.release();
		
		int volume_level = pre.getInt("VoluomnHeThong", 10);
		amMusic.setStreamVolume(AudioManager.STREAM_MUSIC, volume_level, 0);
	}

	protected void onResume() {
		super.onResume();

		int VolumnSound = pre.getInt("VolumnMusic", 10);
		amMusic.setStreamVolume(AudioManager.STREAM_MUSIC, VolumnSound, 0);
		mpMusic.start();

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
