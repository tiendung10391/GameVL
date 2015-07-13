package itpsoft.app.gamevl.database;

import itpsoft.app.gamevl.entity.BoDeEntity;
import itpsoft.app.gamevl.entity.chonDapAnEntity;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

	private static final int DATABASE_VERSISON = 1;
	private static final String DATABASE_NAME = "RememnerNow";

	/*
	 * Khoi tao bang BoDe
	 */
	private static final String TABLE_BODE = "BoDe";
	private static final String KEY_ID_BoDe = "id_BoDe";
	private static final String KEY_MA_BODE = "MaBoDe";
	private static final String KEY_TENBODE = "TenBoDe";

	/*
	 * Khởi tạo bảng chon cau hoi
	 */
	private static final String TABLE_CHONCAUHOI = "ChonCauHoi";
	private static final String KEY_ID_CHONCAUHOI = "id_ChonCauHoi";
	private static final String KEY_CHONCAUHOI_MACH = "MaCH";
	private static final String KEY_CHONCAUHOI_MABODE = "MaBoDe";
	private static final String KEY_CHONCAUHOI_CAUHOI = "CauHoi";
	private static final String KEY_CHONCAUHOI_DAPANDUNG = "DapAnDung";
	private static final String KEY_CHONCAUHOI_DAPANSAI1 = "DapAnSai1";
	private static final String KEY_CHONCAUHOI_DAPANSAI2 = "DapAnSai2";
	private static final String KEY_CHONCAUHOI_DAPANSAI3 = "DapAnSai3";
	private static final String KEY_CHONCAUHOI_MOTA = "MoTa";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSISON);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// tao bang bo de
		String CREATE_TABLE_BODE = "CREATE TABLE " + TABLE_BODE + "("
				+ KEY_ID_BoDe + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ KEY_MA_BODE + " TEXT," + KEY_TENBODE + " TEXT" + ")";
		// tao bang chon cau hoi

		String CREATE_TABLE_CHONCAUHOI = "CREATE TABLE " + TABLE_CHONCAUHOI
				+ "(" + KEY_ID_CHONCAUHOI
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_CHONCAUHOI_MACH
				+ " TEXT," + KEY_CHONCAUHOI_MABODE + " TEXT,"
				+ KEY_CHONCAUHOI_CAUHOI + " TEXT," + KEY_CHONCAUHOI_DAPANDUNG
				+ " TEXT," + KEY_CHONCAUHOI_DAPANSAI1 + " TEXT,"
				+ KEY_CHONCAUHOI_DAPANSAI2 + " TEXT,"
				+ KEY_CHONCAUHOI_DAPANSAI3 + " TEXT," + KEY_CHONCAUHOI_MOTA
				+ " TEXT" + ")";

		db.execSQL(CREATE_TABLE_BODE);
		db.execSQL(CREATE_TABLE_CHONCAUHOI);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_BODE);

		onCreate(db);
	}

	// ///////////////////////// CHON DAP AN ///////////////////////////////

	public int getID_CHONCAUHOI() throws Exception {
		SQLiteDatabase db = null;
		int id = 0;
		try {
			db = this.getReadableDatabase();
			String maxID = "SELECT max(id_ChonCauHoi) FROM " + TABLE_CHONCAUHOI;
			Cursor cursor = db.rawQuery(maxID, null);
			if (cursor.moveToFirst()) {
				id = Integer.parseInt(cursor.getString(0));
			}

			cursor.close();
		} catch (Exception ex) {
			Log.e("get ID", ex.toString());
			Log.e("get ID", id + "");
			throw new Exception(ex.getMessage());
		} finally {
			db.close();
		}
		return id;
	}

	// chon cau hoi theo bo de tong hop
	public ArrayList<chonDapAnEntity> getAllChonCauHoi() {
		ArrayList<chonDapAnEntity> ChonDapAnList = new ArrayList<chonDapAnEntity>();
		String selectQuery = "SELECT * FROM " + TABLE_CHONCAUHOI;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				chonDapAnEntity ch = new chonDapAnEntity();
				ch.setId(Integer.parseInt(cursor.getString(0)));
				ch.setMaCH(cursor.getString(1));
				ch.setMaBoDe(cursor.getString(2));
				ch.setCauHoi(cursor.getString(3));
				ch.setDapAnDung(cursor.getString(4));
				ch.setDapAnSai1(cursor.getString(5));
				ch.setDapAnSai2(cursor.getString(6));
				ch.setDapAnSai3(cursor.getString(7));
				ch.setMoTa(cursor.getString(8));
				ChonDapAnList.add(ch);
			} while (cursor.moveToNext());
		}

		return ChonDapAnList;
	}

	// chon cau hoi theo bo de
	public ArrayList<chonDapAnEntity> getAllChonCauHoiTheoLop(String MaBD) {
		ArrayList<chonDapAnEntity> ChonDapAnList = new ArrayList<chonDapAnEntity>();
		String selectQuery = "SELECT * FROM " + TABLE_CHONCAUHOI + " WHERE "
				+ KEY_CHONCAUHOI_MABODE + " = '" + MaBD + "'";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				chonDapAnEntity ch = new chonDapAnEntity();
				ch.setId(Integer.parseInt(cursor.getString(0)));
				ch.setMaCH(cursor.getString(1));
				ch.setMaBoDe(cursor.getString(2));
				ch.setCauHoi(cursor.getString(3));
				ch.setDapAnDung(cursor.getString(4));
				ch.setDapAnSai1(cursor.getString(5));
				ch.setDapAnSai2(cursor.getString(6));
				ch.setDapAnSai3(cursor.getString(7));
				ch.setMoTa(cursor.getString(8));
				ChonDapAnList.add(ch);
			} while (cursor.moveToNext());
		}

		return ChonDapAnList;
	}

	public int addchChonDapAn(chonDapAnEntity ch) throws Exception {
		SQLiteDatabase db = null;
		int id = 0;
		try {
			db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(KEY_CHONCAUHOI_MACH, ch.getMaCH());
			values.put(KEY_CHONCAUHOI_MABODE, ch.getMaBoDe());
			values.put(KEY_CHONCAUHOI_CAUHOI, ch.getCauHoi());
			values.put(KEY_CHONCAUHOI_DAPANDUNG, ch.getDapAnDung());
			values.put(KEY_CHONCAUHOI_DAPANSAI1, ch.getDapAnSai1());
			values.put(KEY_CHONCAUHOI_DAPANSAI2, ch.getDapAnSai2());
			values.put(KEY_CHONCAUHOI_DAPANSAI3, ch.getDapAnSai3());
			values.put(KEY_CHONCAUHOI_MOTA, ch.getMoTa());
			db.insert(TABLE_CHONCAUHOI, null, values);
			id = getID_CHONCAUHOI();
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		} finally {
			db.close();
		}

		return id;

	}

	// ///////////////////////// BO DE ///////////////////////////////

	public int getID_BODE() throws Exception {
		SQLiteDatabase db = null;
		int id = 0;
		try {
			db = this.getReadableDatabase();
			String maxID = "SELECT max(id_BoDe) FROM " + TABLE_BODE;
			Cursor cursor = db.rawQuery(maxID, null);
			if (cursor.moveToFirst()) {
				id = Integer.parseInt(cursor.getString(0));
			}

			cursor.close();
		} catch (Exception ex) {
			Log.e("get ID", ex.toString());
			Log.e("get ID", id + "");
			throw new Exception(ex.getMessage());
		} finally {
			db.close();
		}
		return id;
	}

	public ArrayList<BoDeEntity> getAllBoDe() {
		ArrayList<BoDeEntity> BoDeList = new ArrayList<BoDeEntity>();
		String selectQuery = "SELECT * FROM " + TABLE_BODE;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				BoDeEntity bd = new BoDeEntity();
				bd.setIdBoDo(Integer.parseInt(cursor.getString(0)));
				bd.setMaBoDe(cursor.getString(1));
				bd.setTenBoDe(cursor.getString(2));
				BoDeList.add(bd);
			} while (cursor.moveToNext());
		}

		return BoDeList;
	}

	public int addBoDe(BoDeEntity bd) throws Exception {
		SQLiteDatabase db = null;
		int id = 0;
		try {
			db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(KEY_MA_BODE, bd.getMaBoDe());
			values.put(KEY_TENBODE, bd.getTenBoDe());
			db.insert(TABLE_BODE, null, values);
			id = getID_BODE();
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		} finally {
			db.close();
		}

		return id;

	}
}
