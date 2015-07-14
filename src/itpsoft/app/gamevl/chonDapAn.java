package itpsoft.app.gamevl;

import itpsoft.app.gamevl.database.DatabaseHandler;
import itpsoft.app.gamevl.entity.BoDeEntity;
import itpsoft.app.gamevl.entity.chonDapAnEntity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class chonDapAn extends Activity {
	private CountDownTimer countDownTimer;
	private boolean timerHasStarted = false;
	private TextView tvTime, tvCauHoi;
	private ImageView imvCau1, imvCau2, imvCau3, imvCau4;
	private ImageView imvNext, imv50, imvX2;
	private RelativeLayout layoutCau1, layoutCau2, layoutCau3, layoutCau4;
	private LinearLayout m_main;
	private int numberQuestion = 1;
	private TextView tvNumerQuestion;

	private MediaPlayer mpMusic;
	private AudioManager amMusic;
	private int curVolumeSound = 0;

	private final long startTime = 15 * 1000;
	private final long interval = 1 * 1000;

	private ArrayList<chonDapAnEntity> arrQuetion;
	DatabaseHandler db;
	private String preName = "my_data";
	SharedPreferences pre;
	private String arrIdQuetion[];
	String DapAnDung = "";
	private ArrayList<String> AnserAfterRandom;
	private int Diem = 0;
	private int ThoiGian = 0;
	private boolean checkTroGiupX2 = false;
	private boolean checkCloseDialogDiemCao = false;
	private boolean check50 = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chon_dap_an);
		init();
		dialogStart();
		getQuestion();
		countDownTime();
		eventAnsers();
		eventSupport();

	}

	public void init() {
		tvTime = (TextView) findViewById(R.id.tvTime);
		tvCauHoi = (TextView) findViewById(R.id.tvCauHoi);
		imvCau1 = (ImageView) findViewById(R.id.imvCau1);
		imvCau2 = (ImageView) findViewById(R.id.imvCau2);
		imvCau3 = (ImageView) findViewById(R.id.imvCau3);
		imvCau4 = (ImageView) findViewById(R.id.imvCau4);
		tvNumerQuestion = (TextView) findViewById(R.id.tvNumberQuestion);
		imvNext = (ImageView) findViewById(R.id.imvNext);
		imvX2 = (ImageView) findViewById(R.id.imvx2);
		imv50 = (ImageView) findViewById(R.id.imv50);
		layoutCau1 = (RelativeLayout) findViewById(R.id.layoutCau1);
		layoutCau2 = (RelativeLayout) findViewById(R.id.layoutCau2);
		layoutCau3 = (RelativeLayout) findViewById(R.id.layoutCau3);
		layoutCau4 = (RelativeLayout) findViewById(R.id.layoutCau4);

		db = new DatabaseHandler(this);
		pre = getSharedPreferences(preName, MODE_PRIVATE);
		amMusic = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		int VolumnSound = pre.getInt("VolumnSound", 10);
		amMusic.setStreamVolume(AudioManager.STREAM_MUSIC, VolumnSound, 0);
	}

	public void getQuestion() {
		try {
			setDefaltBackGroundDapAn();
			if (check50) {
				layoutCau1.setVisibility(View.VISIBLE);
				layoutCau2.setVisibility(View.VISIBLE);
				layoutCau3.setVisibility(View.VISIBLE);
				layoutCau4.setVisibility(View.VISIBLE);
				check50 = false;
			}
			// random cau hoi
			int idBoDe = pre.getInt("idDe", 0);
			if (idBoDe == 3) {
				arrQuetion = db.getAllChonCauHoi();
			} else {
				ArrayList<BoDeEntity> arrBoDe = db.getAllBoDe();
				String MaBD = arrBoDe.get(idBoDe).getMaBoDe();
				arrQuetion = db.getAllChonCauHoiTheoLop(MaBD);
			}

			int randomId = getIDQuestion(arrQuetion.size());
			tvCauHoi.setText(arrQuetion.get(randomId).getCauHoi());

			DapAnDung = arrQuetion.get(randomId).getDapAnDung();
			String DapAnSai1 = arrQuetion.get(randomId).getDapAnSai1();
			String DapAnSai2 = arrQuetion.get(randomId).getDapAnSai2();
			String DapAnSai3 = arrQuetion.get(randomId).getDapAnSai3();

			ArrayList<String> AnsersBeforeRandom = new ArrayList<String>();
			AnsersBeforeRandom.add(DapAnDung);
			AnsersBeforeRandom.add(DapAnSai1);
			AnsersBeforeRandom.add(DapAnSai2);
			AnsersBeforeRandom.add(DapAnSai3);

			// random dap an
			AnserAfterRandom = new ArrayList<String>();
			ArrayList<Integer> arrNumber = new ArrayList<Integer>();
			Random randomNumber = new Random();
			while (AnserAfterRandom.size() < 4) {
				int number = randomNumber.nextInt(4);
				if (!arrNumber.contains(number)) {
					arrNumber.add(number);
					AnserAfterRandom.add(AnsersBeforeRandom.get(number));
				}
			}

			// set question

			imvCau1.setImageResource(getResource(AnserAfterRandom.get(0),
					R.drawable.class));
			imvCau2.setImageResource(getResource(AnserAfterRandom.get(1),
					R.drawable.class));
			imvCau3.setImageResource(getResource(AnserAfterRandom.get(2),
					R.drawable.class));
			imvCau4.setImageResource(getResource(AnserAfterRandom.get(3),
					R.drawable.class));

			// set numberQuestion
			String NumberQuestion = "Câu " + String.valueOf(numberQuestion);
			tvNumerQuestion.setText(NumberQuestion);
			// numberQuestion++;

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void eventSupport() {
		imvNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (checkCloseDialogDiemCao) {
					checkCloseDialogDiemCao = false;
					restartTimeAndQuestion();
					getQuestion();
					dialogStart();
				} else {
					mpMusic = MediaPlayer.create(getApplicationContext(),
							R.raw.support);
					mpMusic.start();
					countDownTimer.cancel();
					countDownTimer.start();
					getQuestion();
					checkTroGiupX2 = false;
					AlphaAnimation fade_out = new AlphaAnimation(1.0f, 0.0f);
					fade_out.setDuration(500);
					fade_out.setAnimationListener(new AnimationListener() {
						public void onAnimationStart(Animation arg0) {
						}

						public void onAnimationRepeat(Animation arg0) {
						}

						public void onAnimationEnd(Animation arg0) {
							imvNext.setVisibility(View.GONE);
						}
					});
					imvNext.startAnimation(fade_out);
				}
			}
		});

		imv50.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (checkCloseDialogDiemCao) {
					checkCloseDialogDiemCao = false;
					restartTimeAndQuestion();
					getQuestion();
					dialogStart();
				} else {
					mpMusic = MediaPlayer.create(getApplicationContext(),
							R.raw.support);
					mpMusic.start();
					AlphaAnimation fade_out = new AlphaAnimation(1.0f, 0.0f);
					fade_out.setDuration(500);
					fade_out.setAnimationListener(new AnimationListener() {
						public void onAnimationStart(Animation arg0) {
						}

						public void onAnimationRepeat(Animation arg0) {
						}

						public void onAnimationEnd(Animation arg0) {
							imv50.setVisibility(View.GONE);
						}
					});
					imv50.startAnimation(fade_out);

					// code
					ArrayList<Integer> arrDapAnSai = new ArrayList<Integer>();
					for (int i = 0; i < 4; i++) {
						if (!AnserAfterRandom.get(i).endsWith(DapAnDung)) {
							arrDapAnSai.add(i);
						}
					}

					Collections.shuffle(arrDapAnSai);

					for (int j = 0; j < 2; j++) {
						int loai = arrDapAnSai.get(j);
						switch (loai) {
						case 0:
							layoutCau1.setVisibility(View.GONE);
							break;
						case 1:
							layoutCau2.setVisibility(View.GONE);
							break;
						case 2:
							layoutCau3.setVisibility(View.GONE);
							break;
						case 3:
							layoutCau4.setVisibility(View.GONE);
							break;
						default:
							break;
						}
					}

					check50 = true;
				}
			}
		});

		imvX2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (checkCloseDialogDiemCao) {
					checkCloseDialogDiemCao = false;
					restartTimeAndQuestion();
					getQuestion();
					dialogStart();
				} else {
					mpMusic = MediaPlayer.create(getApplicationContext(),
							R.raw.support);
					mpMusic.start();
					checkTroGiupX2 = true;
					AlphaAnimation fade_out = new AlphaAnimation(1.0f, 0.0f);
					fade_out.setDuration(500);
					fade_out.setAnimationListener(new AnimationListener() {
						public void onAnimationStart(Animation arg0) {
						}

						public void onAnimationRepeat(Animation arg0) {
						}

						public void onAnimationEnd(Animation arg0) {
							imvX2.setVisibility(View.GONE);
						}
					});
					imvX2.startAnimation(fade_out);
				}

			}
		});

	}

	public void eventAnsers() {
		imvCau1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (checkCloseDialogDiemCao) {
					checkCloseDialogDiemCao = false;
					restartTimeAndQuestion();
					getQuestion();
					dialogStart();
				} else {
					if (!checkAnser(0)) {
						layoutCau1
								.setBackgroundResource(R.drawable.background_dapan_false);
					}
				}
			}
		});

		imvCau2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (checkCloseDialogDiemCao) {
					checkCloseDialogDiemCao = false;
					restartTimeAndQuestion();
					getQuestion();
					dialogStart();
				} else {
					if (!checkAnser(1)) {
						layoutCau2
								.setBackgroundResource(R.drawable.background_dapan_false);
					}
				}
			}
		});

		imvCau3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (checkCloseDialogDiemCao) {
					checkCloseDialogDiemCao = false;
					restartTimeAndQuestion();
					getQuestion();
					dialogStart();
				} else {
					if (!checkAnser(2)) {
						layoutCau3
								.setBackgroundResource(R.drawable.background_dapan_false);
					}
				}
			}
		});

		imvCau4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (checkCloseDialogDiemCao) {
					checkCloseDialogDiemCao = false;
					restartTimeAndQuestion();
					getQuestion();
					dialogStart();
				} else {
					if (!checkAnser(3)) {
						layoutCau4
								.setBackgroundResource(R.drawable.background_dapan_false);
					}

				}
			}
		});
	}

	// kiem tra dap an
	public boolean checkAnser(int i) {
		if (AnserAfterRandom.get(i).equals(DapAnDung)) {
			Diem++;
			countDownTimer.cancel();
			countDownTimer.start();
			numberQuestion++;
			getQuestion();
			checkTroGiupX2 = false;
			mpMusic = MediaPlayer.create(getApplicationContext(), R.raw.mtrue);
			mpMusic.start();
			return true;
		} else {
			if (checkTroGiupX2) {
				checkTroGiupX2 = false;
				mpMusic = MediaPlayer.create(getApplicationContext(),
						R.raw.mfalse);
				mpMusic.start();
				return false;
			} else {
				for (int j = 0; j < 4; j++) {
					if (AnserAfterRandom.get(j).equals(DapAnDung)) {
						switch (j) {
						case 0:
							layoutCau1
									.setBackgroundResource(R.drawable.background_dapan_true);
							break;
						case 1:
							layoutCau2
									.setBackgroundResource(R.drawable.background_dapan_true);
							break;
						case 2:
							layoutCau3
									.setBackgroundResource(R.drawable.background_dapan_true);
							break;
						case 3:
							layoutCau4
									.setBackgroundResource(R.drawable.background_dapan_true);
							break;

						default:
							break;
						}
					}
				}
				dialogDiemCao();
				mpMusic = MediaPlayer.create(getApplicationContext(),
						R.raw.mfalse);
				mpMusic.start();
				return false;
			}
		}
	}

	public void dialogDiemCao() {
		final Dialog dialog = new Dialog(chonDapAn.this,
				R.style.Dialog_No_Border);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		LayoutInflater inflater = LayoutInflater.from(chonDapAn.this);
		View view = inflater.inflate(R.layout.diemcao_chon_dap_an, null);
		m_main = (LinearLayout) view.findViewById(R.id.diemCaoGame1);
		m_main.setBackgroundResource(R.drawable.style_border_dialog);
		ImageView btClose = (ImageView) view
				.findViewById(R.id.imvCloseDiemCaoGame1);
		Button btChoiTiep = (Button) view.findViewById(R.id.btChoiTiepGame1);
		Button btThoat = (Button) view.findViewById(R.id.btThoatGame1);
		TextView tvDiem = (TextView) view.findViewById(R.id.tvDiemGame1);
		TextView tvThoiGian = (TextView) view
				.findViewById(R.id.tvThoiGianGame1);
		ImageView imvFinish = (ImageView) view
				.findViewById(R.id.imvFinishGame1);
		tvDiem.setText(String.valueOf(Diem));

		String thoigian = "";
		if (ThoiGian > 60) {
			thoigian = String.format(
					"%dp %ds",
					TimeUnit.MILLISECONDS.toMinutes(ThoiGian * 1000),
					TimeUnit.MILLISECONDS.toSeconds(ThoiGian * 1000)
							- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
									.toMinutes(ThoiGian * 1000)));
		} else {
			thoigian = ThoiGian + " s";
		}

		tvThoiGian.setText(thoigian);

		int diemLuu = pre.getInt("diemCT", 0);
		int thoiGianLuu = pre.getInt("thoigianCT", 0);

		if (diemLuu < Diem) {
			// luu diem
			imvFinish.setBackgroundResource(R.drawable.winner);
			SharedPreferences.Editor editor = pre.edit();
			editor.putInt("diemCT", Diem);
			editor.putInt("thoigianCT", ThoiGian);
			editor.commit();
		} else if (diemLuu == Diem) {
			if (thoiGianLuu > ThoiGian) {
				// luu diem
				imvFinish.setBackgroundResource(R.drawable.winner);
				imvFinish.setBackgroundResource(R.drawable.winner);
				SharedPreferences.Editor editor = pre.edit();
				editor.putInt("diemCT", Diem);
				editor.putInt("thoigianCT", ThoiGian);
				editor.commit();
			} else {
				// hien thi hinh sao
				imvFinish.setBackgroundResource(R.drawable.lost);
			}
		} else {
			// hien thi hinh sao
			imvFinish.setBackgroundResource(R.drawable.lost);
		}

		btClose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
				dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
				checkCloseDialogDiemCao = true;
			}
		});

		btThoat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
				dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
				finish();
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			}
		});

		btChoiTiep.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				countDownTimer.start();
				restartTimeAndQuestion();
				dialog.dismiss();
				dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
			}
		});

		dialog.setContentView(view);
		dialog.show();

		countDownTimer.cancel();

		dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
	}

	public void dialogStart() {
		final Dialog dialog = new Dialog(chonDapAn.this, R.style.Dialog_Start);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		LayoutInflater inflater = LayoutInflater.from(chonDapAn.this);
		View view = inflater.inflate(R.layout.start_game, null);
		m_main = (LinearLayout) view.findViewById(R.id.startGame);
		m_main.setBackgroundResource(R.drawable.dialog_start);
		ImageView tvStart = (ImageView) view.findViewById(R.id.tvStart);
		tvStart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				countDownTimer.start();
				dialog.dismiss();
				dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
			}
		});
		dialog.setContentView(view);
		dialog.show();

		dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
	}

	public void setDefaltBackGroundDapAn() {
		layoutCau1.setBackgroundResource(R.drawable.background_dapan);
		layoutCau2.setBackgroundResource(R.drawable.background_dapan);
		layoutCau3.setBackgroundResource(R.drawable.background_dapan);
		layoutCau4.setBackgroundResource(R.drawable.background_dapan);
	}

	public void restartTimeAndQuestion() {
		Diem = 0;
		ThoiGian = 0;
		numberQuestion = 1;
		imvNext.setVisibility(View.VISIBLE);
		imv50.setVisibility(View.VISIBLE);
		imvX2.setVisibility(View.VISIBLE);
		getQuestion();
	}

	public void countDownTime() {
		countDownTimer = new MyCountDownTimer(startTime, interval);
		tvTime.setText(tvTime.getText() + String.valueOf(startTime / 1000));

	}

	public class MyCountDownTimer extends CountDownTimer {
		public MyCountDownTimer(long startTime, long interval) {
			super(startTime, interval);
		}

		@Override
		public void onFinish() {
			// code
			dialogDiemCao();

		}

		@Override
		public void onTick(long millisUntilFinished) {
			tvTime.setText("" + millisUntilFinished / 1000);
			ThoiGian++;
		}
	}

	public void onFinish() {
		tvTime.setText("Time's up!");
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		finish();
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
	}

	// convert String to Resource
	public static int getResource(String resourceName, Class<?> c) {
		try {
			Field idField = c.getDeclaredField(resourceName);
			return idField.getInt(idField);
		} catch (Exception e) {
			throw new RuntimeException("No resource ID found for: "
					+ resourceName + " / " + c, e);
		}
	}

	// random vi tri cau hoi
	public int getIDQuestion(int size) {
		Random rand = new Random();
		int index = rand.nextInt(size);
		return index;
	}

	protected void onPause() {
		super.onPause();

		// mp.release();

		int volume_level = pre.getInt("VoluomnHeThong", 10);
		amMusic.setStreamVolume(AudioManager.STREAM_MUSIC, volume_level, 0);
	}

	protected void onResume() {
		super.onResume();

		int VolumnSound = pre.getInt("VolumnSound", 10);
		amMusic.setStreamVolume(AudioManager.STREAM_MUSIC, VolumnSound, 0);
		// mpMusic.start();

	}

}
