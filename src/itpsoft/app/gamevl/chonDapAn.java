package itpsoft.app.gamevl;

import itpsoft.app.gamevl.database.DatabaseHandler;
import itpsoft.app.gamevl.entity.chonDapAnEntity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class chonDapAn extends Activity {
	private CountDownTimer countDownTimer;
	private boolean timerHasStarted = false;
	public TextView tvTime, tvCauHoi;
	public ImageView imvCau1, imvCau2, imvCau3, imvCau4;
	private LinearLayout m_main;
	private int numberQuestion = 1;
	private TextView tvNumerQuestion;

	private final long startTime = 15 * 1000;
	private final long interval = 1 * 1000;

	private ArrayList<chonDapAnEntity> arrQuetion;
	DatabaseHandler db;
	private String arrIdQuetion[];
	String DapAnDung = "";
	private ArrayList<String> AnserAfterRandom;
	private int Diem = 0;
	private int ThoiGian = 0;

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

	}

	public void init() {
		tvTime = (TextView) findViewById(R.id.tvTime);
		tvCauHoi = (TextView) findViewById(R.id.tvCauHoi);
		imvCau1 = (ImageView) findViewById(R.id.imvCau1);
		imvCau2 = (ImageView) findViewById(R.id.imvCau2);
		imvCau3 = (ImageView) findViewById(R.id.imvCau3);
		imvCau4 = (ImageView) findViewById(R.id.imvCau4);
		tvNumerQuestion = (TextView) findViewById(R.id.tvNumberQuestion);

		db = new DatabaseHandler(this);

	}

	public void getQuestion() {
		try {
			// random cau hoi
			arrQuetion = db.getAllChonCauHoi();
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
			numberQuestion++;

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void eventAnsers() {
		imvCau1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				checkAnser(0);

			}
		});

		imvCau2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				checkAnser(1);
			}
		});

		imvCau3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				checkAnser(2);
			}
		});

		imvCau4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				checkAnser(3);
			}
		});
	}

	// kiem tra dap an
	public void checkAnser(int i) {
		if (AnserAfterRandom.get(i).equals(DapAnDung)) {
			Diem++;
			getQuestion();
		} else {
			dialogDiemCao();
			Toast.makeText(getApplicationContext(), "Sai roi",
					Toast.LENGTH_SHORT).show();
		}
	}

	public void dialogDiemCao() {
		final Dialog dialog = new Dialog(chonDapAn.this, R.style.Dialog_No_Border);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		LayoutInflater inflater = LayoutInflater.from(chonDapAn.this);
		View view = inflater.inflate(R.layout.diemcao_chon_dap_an, null);
		m_main = (LinearLayout) view.findViewById(R.id.diemCaoGame1);
		m_main.setBackgroundResource(R.drawable.style_border_dialog);
		ImageView tvStart = (ImageView) view.findViewById(R.id.imvCloseDiemCaoGame1);
		tvStart.setOnClickListener(new OnClickListener() {

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
			countDownTimer.start();
			Toast.makeText(getApplicationContext(), "chuyen cau",
					Toast.LENGTH_SHORT).show();
			getQuestion();

		}

		@Override
		public void onTick(long millisUntilFinished) {
			tvTime.setText("" + millisUntilFinished / 1000);

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

}
